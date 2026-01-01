/**
 * 
 */
package de.uhingen.kielkopf.andreas.smartmetergui.data;

import java.io.Serializable;
import java.util.TreeMap;

/**
 * @author Andreas Kielkopf
 *
 */
public class Tag implements Serializable, Comparable<Tag> {
   private static final long serialVersionUID=-2455316200074905264L;
   final public int          jahr;
   final public int          monat;
   final public int          tag;
   final String              name;
   /**
    * @param tagName
    */
   public Tag(String tagName) {
      if (tagName instanceof String s && s.length() == 10) {
         String[] array=s.split("-");
         if (array.length == 3) {
            jahr=Integer.parseInt(array[0]);
            monat=Integer.parseInt(array[1]);
            tag=Integer.parseInt(array[2]);
            name=tagName;
            stunden=new TreeMap<>();
            return;
         }
      }
      throw new NumberFormatException("Kann keinen Tag mit " + tagName + " erzeugen");
   }
   // Instant punkt;
   TreeMap<Integer, Stunde> stunden;
   @Override
   public int compareTo(Tag o) {
      if (o instanceof Tag other)
         return name.compareTo(other.name);
      return 1;
   }
   /**
    * @param s
    */
   public void add(Stunde s) {
      stunden.put(s.getStunde(), s);
   }
   @Override
   public String toString() {
      StringBuilder sb=new StringBuilder(name);
      sb.insert(0, " ");
      sb.append(" [");
      for (Integer i:stunden.keySet())
         sb.append(i).append(",");
      sb.setLength(sb.length() - 1);
      sb.append("] ");
      // sb.append(stunden.size());
      return sb.toString();
   }
}
