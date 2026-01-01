/**
 * 
 */
package de.uhingen.kielkopf.andreas.smartmetergui.data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.TreeMap;

/**
 * @author Andreas Kielkopf
 */
public class Stunde implements Serializable {
   private static final long         serialVersionUID=-3882823258961184089L;
   // private WeakReference<Tag> tag;
   private Integer                   stunde;
   // private Duration d;
   private TreeMap<Integer, Integer> takte;
   /**
    * Konstruktor mit datenübergabe
    */
   public Stunde() {
      // TODO Auto-generated constructor stub
   }
   /**
    * @param stunde2
    * @param minuten2
    */
   public Stunde(String stunde_, String[] takte_) {
      stunde=Integer.parseInt(stunde_);
      if (stunde >= 0 && stunde <= 24)
         if (takte_ instanceof String[] m) {
            takte=new TreeMap<>();
            for (int i=0; i < m.length; i++)
               takte.put(i, Integer.parseInt(m[i]));
            if (takte.size() > 0)
               return;
         }
      throw new NumberFormatException("kann keine Stunde für " + stunde_ + " = " + Arrays.asList(takte) + " erzeugen");
   }
   public Integer getStunde() {
      return stunde;
   }
   public TreeMap<Integer, Integer> getTakte() {
      return takte;
   }
}
