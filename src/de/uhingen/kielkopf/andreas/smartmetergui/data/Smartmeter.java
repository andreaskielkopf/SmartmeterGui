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
   private JLabel            infoline;
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
   public void read(DefaultListModel<Tag> jListModel) {
      try {
         setInfo("start to read " + toString());
         Instant jetzt=Instant.now();
         int year=jetzt.atOffset(ZoneOffset.UTC).getYear();
         // String erg;
         for (int jahr=year - 10; jahr <= year; jahr++) {
            try {
               if (getData("data/" + jahr) instanceof String[] monate)
                  for (String monat:monate) {
                     if (getData("data/" + jahr + "/" + monat) instanceof String[] tage)
                        for (String tag:tage) {
                           if (getData("data/" + jahr + "/" + monat + "/" + tag) instanceof String[] stunden)
                              for (String stunde:stunden) {
                                 if (getData("data/" + jahr + "/" + monat + "/" + tag + "/"
                                          + stunde) instanceof String[] minuten) {
                                    // System.out.println(Arrays.asList(minuten));
                                    if (eintragen(jahr, monat, tag, stunde, minuten) instanceof Tag t) {
                                       System.out.println("neu: " + t);
                                       if (jListModel instanceof DefaultListModel<Tag> jlt)
                                          SwingUtilities.invokeLater(() -> jlt.addElement(t));
                                    }
                                 }
                              }
                        }
                  }
               else {
                  setInfo(jahr + ":");
               }
            } catch (IOException e) {
               System.err.println(e.getMessage());
            }
         }
      } catch (InterruptedException e) {
         e.printStackTrace();
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
   private String[] getData(String frage) throws IOException, InterruptedException {
      Thread.sleep(50);// maximal alle 50ms eine Anfrage starten
      if (client.anfrage(frage) instanceof String erg) {
         String[] array=erg.split("[\\]\\[]");
         if (array.length > 2 & !array[1].isBlank()) {
            if (frage.length() <= 15)
               setInfo(frage + "  :  " + array[1]);
            return array[1].split(",");
         }
      }
      return null;
   }
   /**
    * @param string
    */
   private void setInfo(String string) {
      if (infoline instanceof JLabel info)
         SwingUtilities.invokeLater(() -> info.setText(string));
   }
   /**
    * @param lbl_Info
    */
   public void setInfoline(JLabel lbl_Info) {
      infoline=lbl_Info;
   }
   @Override
   public String toString() {
      return new StringBuilder().append(getClass().getSimpleName()).append("(").append(getName()).append(")")
               .toString();
   }
}
