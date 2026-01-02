/**
 * 
 */
package de.uhingen.kielkopf.andreas.smartmetergui.data;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.TreeSet;

import javax.swing.*;

import de.uhingen.kielkopf.andreas.smartmetergui.Manage;
import de.uhingen.kielkopf.andreas.smartmetergui.http.Client;

/**
 * @author Andreas Kielkopf
 *
 */
public class Smartmeter implements Serializable {
   private static final long serialVersionUID=-1026838884840481195L;
   private final InetAddress ip;
   private Client            client;
   private String            name;
   private TreeSet<Tag>      speicher;
   private Manage            manage;
   /**
    * kompletter Datensatz eines Smartmrters
    * 
    * @throws URISyntaxException
    */
   public Smartmeter(InetAddress ip_) throws URISyntaxException {
      ip=ip_;
      name=ip.toString();
      speicher=new TreeSet<>();
      client=new Client(ip);
   }
   public String getName() {
      return name;
   }
   /**
    * Ändert den Namen und benennt die Dateien um
    * 
    * @param name_
    */
   public void setName(String name_) {
      this.name=name_;
   }
   static final public void save() {}
   /**
    * Lädt die Daten aus der Datei als Basisdaten
    */
   final public static void load() {}
   /**
    * @param jListModel
    */
   public void read() {
      if (manage instanceof Manage m && m.getListModel() instanceof DefaultListModel<Tag> lm) {
         Thread t=new Thread(() -> {
            try {
               read(lm);
            } catch (InterruptedException e) {
               System.err.println(e.getMessage());
            }
         });
         t.setDaemon(true);
         t.start();
      }
   }
   private void read(DefaultListModel<Tag> jListModel) throws InterruptedException {
      // setInfo("start to read " + toString());
      int year=Instant.now().atOffset(ZoneOffset.UTC).getYear();
      for (int jahr=year - 10; jahr <= year; jahr++)
         try {
            if (getAntwort(jahr) instanceof String[] monate)
               for (String monat:monate)
                  if (getAntwort(jahr, monat) instanceof String[] tage)
                     for (String tag:tage)
                        if (getAntwort(jahr, monat, tag) instanceof String[] stunden)
                           for (String stunde:stunden)
                              if (getAntwort(jahr, monat, tag, stunde) instanceof String[] minuten) {
                                 if (eintragen(jahr, monat, tag, stunde, minuten) instanceof Tag t
                                          && jListModel instanceof DefaultListModel<Tag> jlt)
                                    SwingUtilities.invokeLater(() -> jlt.addElement(t));
                              }
         } catch (IOException e) {
            System.err.println(e.getMessage());
         }
   }
   /**
    * @param jahr
    * @param monat
    * @param tag
    * @param stunde
    * @param minuten
    */
   private Tag eintragen(int jahr, String monat, String tag, String stunde, String[] takte) {
      try {
         int summe=0;
         for (String ticks:takte)
            summe+=Integer.parseInt(ticks);
         if (summe > 0) {
            Tag testTag=new Tag(jahr + "-" + monat + "-" + tag);
            boolean hinzugefuegt=speicher.add(testTag); // nur wenn nicht schon da
            try {
               if (speicher.floor(testTag) instanceof Tag t) {
                  t.add(new Stunde(stunde, takte));
                  if (hinzugefuegt)
                     return t; // Erfolg und bitte in die GUI eintragen!!!
                  return null;// Erfolg aber schon vorhanden !!!
               }
            } catch (NumberFormatException _) { /* */ }
            if (hinzugefuegt)// Ein Tag ohne Stunden wird nicht eingetragen
               speicher.remove(testTag);
         }
      } catch (NumberFormatException _) { /* */ }
      return null;
   }
   private String[] getAntwort(int j, String... m) throws IOException, InterruptedException {
      StringBuilder frage=new StringBuilder("data/").append(j);
      for (String string:m)
         frage.append("/").append(string);
      Thread.sleep(50);// maximal alle 50ms eine Anfrage starten
      setInfo(frage.toString());
      if (client.anfrage(frage.toString()) instanceof String erg //
               && erg.split("[\\]\\[]") instanceof String[] array //
               && array.length > 2 //
               && !array[1].isBlank()) {
         if (frage.length() <= 15)
            setInfo(frage.toString() + "  :  " + array[1]);
         return array[1].split(",");
      }
      return null;
   }
   /**
    * @param string
    */
   private void setInfo(String string) {
      if (manage instanceof Manage m && m.getLbl_Info() instanceof JLabel info)
         SwingUtilities.invokeLater(() -> info.setText(string));
   }
   /**
    * @param lbl_Info
    */
   public void setManage(Manage manage_) {
      if (manage_ instanceof Manage m)
         manage=m;
   }
   @Override
   public String toString() {
      return new StringBuilder().append(getClass().getSimpleName()).append("(").append(getName()).append(")")
               .toString();
   }
}
