/**
 * 
 */
package de.uhingen.kielkopf.andreas.smartmetergui.data;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Map.Entry;

/**
 * @author Andreas Kielkopf
 *
 */
public class Tick implements Drawable {
   final private int x;
   final public int  sec; // Zeitachse in Sekunden
   final public int  y;   // Höhe
   /**
    * @param stunde
    * @param entry
    *           (minute, wert)
    */
   public Tick(int stunde, Entry<Integer, Integer> entry) {
      int minute=entry.getKey();
      x=60 * stunde + minute;
      sec=60 * x;
      y=entry.getValue();
   }
   @Override
   public void paint(Graphics2D g2d) {
      g2d.setColor(Color.RED);
      g2d.drawOval(x * 5 - 2, y - 2, 5, 5);
   }
   @Override
   public int getSec() {
      return sec;
   }
}
