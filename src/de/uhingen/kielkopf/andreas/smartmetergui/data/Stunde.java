/**
 * 
 */
package de.uhingen.kielkopf.andreas.smartmetergui.data;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * @author Andreas Kielkopf
 */
public class Stunde implements Serializable {
   private static final long                    serialVersionUID=-3882823358961184089L;
   final private int                            tzOffsetMin;
   final private Integer                        stunde;
   final private TreeMap<Integer, Integer>      takte           =new TreeMap<>();
   transient private TreeMap<Integer, Drawable> icons           =new TreeMap<>();
   private int                                  max;
   private int                                  sum;
   private int                                  count;
   /**
    * @param stunde_
    * @param takte_
    * @param tzOffsetMin_
    * 
    */
   public Stunde(String stunde_, String[] takte_, int tzOffsetMin_) {
      stunde=Integer.parseInt(stunde_);
      tzOffsetMin=tzOffsetMin_;
      if (stunde >= 0 && stunde <= 24)
         if (takte_ instanceof String[] m) {
            for (int i=0; i < m.length; i++) {
               int v=Integer.parseInt(m[i]);
               if (v > 0)
                  takte.put(i, v);
            }
            max=0;// max muss später neu berechnet werden
            sum=0;
            count=0;
            if (takte.size() > 0)
               return;
         }
      throw new NumberFormatException("kann keine Stunde für " + stunde_ + " = " + Arrays.asList(takte_) + " erzeugen");
   }
   public Integer getStunde() {
      return stunde;
   }
   public TreeMap<Integer, Integer> getTakte() {
      return takte;
   }
   /**
    * @return insgesamt Höchster Wert
    */
   public int getMaxticks() {
      if (max <= 0)
         for (Integer i:takte.values())
            max=(i > max) ? i : max;
      return max;
   }
   /**
    * @return Summe aller Werte
    */
   public int getSumme() {
      if (sum == 0)
         for (Integer i:takte.values())
            sum+=i;
      return sum;
   }
   /**
    * @return Anzahl der Werte die nicht 0 sind
    */
   public int getCount() {
      if (count == 0)
         for (Integer i:takte.values())
            if (i > 0)
               count++;
      return count;
   }
   public TreeMap<Integer, Drawable> getTicks() {
      if (icons == null)
         icons=new TreeMap<>();
      if (icons.size() < getCount()) {
         for (Entry<Integer, Integer> entry:takte.entrySet()) {
            Tick tick=new Tick(stunde, entry);
            icons.put(tick.getSec(), tick);
         }
         for (int minute=0; minute <= 60; minute+=10) {
            int x=stunde * 60 + minute;
            int sec=x * 60 - 1;
            Color c=(minute == 0) ? Color.GRAY : Color.LIGHT_GRAY;
            String text=(minute == 0) ? Integer.toString((24 + stunde + tzOffsetMin / 60) % 24) + "Uhr" : null;
            Drawable zeit=new Drawable() {
               @Override
               public void paint(Graphics2D g2d) {
                  g2d.setColor(c);
                  g2d.drawLine(x * 5, 0, x * 5, 1000);
                  if (text != null) {
                     AffineTransform tmp=g2d.getTransform();
                     g2d.scale(1, -1);
                     g2d.drawString(text, x * 5 - 7, -200);
                     g2d.setTransform(tmp);
                  }
               }
               @Override
               public int getSec() {
                  return sec;
               }
            };
            icons.put(sec, zeit);
         }
      }
      return icons;
   }
}
