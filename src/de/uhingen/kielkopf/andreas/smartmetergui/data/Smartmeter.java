/**
 * 
 */
package de.uhingen.kielkopf.andreas.smartmetergui.data;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.TreeSet;

/**
 * @author Andreas Kielkopf
 *
 */
public class Smartmeter implements Serializable {
   private static final long serialVersionUID=-1026838884840481195L;
   private final InetAddress ip;
   private String            name;
   private TreeSet<Tag>      tage;
   /**
    * kompletter Datensatz eines Smartmrters
    */
   public Smartmeter(InetAddress ip_) {
      ip=ip_;
      name=ip.toString();
      tage=new TreeSet<>();
   }
   public String getName() {
      return name;
   }
   /**
    * Ändert den Namen und benennt die Dateien um
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
}
