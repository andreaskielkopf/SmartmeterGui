/**
 * 
 */
package de.uhingen.kielkopf.andreas.smartmetergui.data;

import java.io.Serializable;
import java.time.*;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * @author Andreas Kielkopf
 *
 */
public class Tag implements Serializable, Comparable<Tag> {
   private static final long                    serialVersionUID=-2454316201074905264L;
   public final int                             tzOffsetMin;
   final public int                             jahr;
   final public int                             monat;
   final public int                             tag;
   final public String                          name;
   private TreeMap<Integer, Stunde>             stunden         =new TreeMap<>();
   private int                                  max;
   private int                                  sum;
   private int                                  count;
   transient private TreeMap<Integer, Drawable> icons           =new TreeMap<>();
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
            ZoneOffset offset=LocalDate.of(jahr, monat, tag).atTime(LocalTime.NOON).atZone(ZoneId.systemDefault())
                     .getOffset();
            System.out.println(tagName + " " + ZoneId.systemDefault() + " -> Offset:" + offset);
            tzOffsetMin=offset.getTotalSeconds() / 60;
            return;
         }
      }
      throw new NumberFormatException("Kann keinen Tag mit " + tagName + " erzeugen");
   }
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
      max=0; // muss später neu berechnet werden
      sum=0;
      count=0;
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
   /**
    * @return
    */
   public boolean istLeer() {
      return stunden.isEmpty();
   }
   /**
    * @return insgesamt Höchster Wert
    */
   public int getMaxticks() {
      if (max <= 0)
         for (Stunde stunde:stunden.values())
            max=(stunde.getMaxticks() > max) ? stunde.getMaxticks() : max;
      return max;
   }
   /**
    * @return Summe aller Werte
    */
   public int getSumme() {
      if (sum == 0)
         for (Stunde stunde:stunden.values())
            sum+=stunde.getSumme();
      return sum;
   }
   /**
    * @return Anzahl der Werte die nicht 0 sind
    */
   public int getCount() {
      if (count == 0)
         for (Stunde stunde:stunden.values())
            count+=stunde.getCount();
      return count;
   }
   public TreeMap<Integer, Drawable> getTicks() {
      if (icons == null)
         icons=new TreeMap<>();
      if (icons.size() < getCount()) { // icons.clear();
         for (Stunde stunde:stunden.values())
            for (Entry<Integer, Drawable> entry:stunde.getTicks().entrySet())
               icons.put(entry.getKey(), entry.getValue());
      }
      return icons;
   }
}
