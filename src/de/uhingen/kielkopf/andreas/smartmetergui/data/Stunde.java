/**
 * 
 */
package de.uhingen.kielkopf.andreas.smartmetergui.data;

import java.io.Serializable;

import java.time.Duration;
import java.util.*;

/**
 * @author Andreas Kielkopf
 */
public class Stunde implements Serializable {
   private static final long         serialVersionUID=-3882823258961184089L;
   // private WeakReference<Tag> tag;
   private Integer                   stunde;
   private Duration                  d;
   private TreeMap<Integer, Integer> minuten         =new TreeMap<>();
   /**
    * Konstruktor mit datenübergabe
    */
   public Stunde() {
      // TODO Auto-generated constructor stub
   }
   public Integer getStunde() {
      return stunde;
   }
   public void setStunde(Integer stunde_) {
      this.stunde=stunde_;
   }
   public TreeMap<Integer, Integer> getMinuten() {
      return minuten;
   }
   public void setMinuten(TreeMap<Integer, Integer> minuten_) {
      this.minuten=minuten_;
   }
}
