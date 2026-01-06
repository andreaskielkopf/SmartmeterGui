/**
 * 
 */
package de.uhingen.kielkopf.andreas.smartmetergui;

import java.awt.*;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import de.uhingen.kielkopf.andreas.smartmetergui.data.*;

/**
 * @author Andreas Kielkopf
 *
 */
public class Grafik extends JPanel implements DocumentListener {
   private static final long serialVersionUID=7751058752218115641L;
   final Display             myDisplay;
   private Tag               tag;
   private int               rand;
   private int               breite;
   private int               hoehe;
   /**
    * @param display
    */
   public Grafik(Display display) {
      rand=20;
      breite=24 * 60 * 5; // 24 Stunden x 60 Minuten x 5 Pixel
      hoehe=2000; // ca. 12 kW ??? ==> 17A je Phase
      myDisplay=display;
      myDisplay.getField_maxticks().getDocument().addDocumentListener(this);
      setPreferredSize(new Dimension(breite + 2 * rand, hoehe + 2 * rand)); // 24 Stunden x 60 Minuten x 5 Pixel, 1024 Pixel Höhe
   }
   @Override
   public void paint(Graphics g) {
      if (g instanceof Graphics2D g2d && tag instanceof Tag t) {
         super.paint(g2d);
         g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
         Stroke stroke=new BasicStroke(1.75f);
         // double maxTicks=t.getMaxticks() * 1.05d; // für die Höhe
         // int h=getHeight() - 5;
         // int w=getWidth();
         // double scale=-hoehe / maxTicks;
         g2d.setStroke(stroke);
         g2d.translate(rand, hoehe - rand);
         g2d.setColor(Color.LIGHT_GRAY);
         for (double kWh:List.of(.1, .2, .3, .4, .6, .7, .8, .9, 1.1, 1.2, 1.3, 1.4)) {
            int a=(int) -(kWh * 10000d / 60d);
            g2d.drawLine(0, a, breite, a);
            g2d.drawString(Integer.toString((int) (kWh * 1000)) + "W", 50, a);
         }
         g2d.setColor(Color.GRAY);
         for (double kWh:List.of(0d, .5, 1d, 1.5, 2d, 2.5, 3d, 4d, 5d, 6d, 7d, 8d, 9d, 10d, 11d, 12d)) {
            int a=(int) -(kWh * 10000d / 60d);
            g2d.drawLine(0, a, breite, a);
            g2d.drawString(Double.toString(kWh) + "kW", 50, a);
         }
         g2d.scale(1d, -1d);
         // AffineTransform tmp=g2d.getTransform();
         // g2d.setTransform(tmp);
         for (Drawable tick:t.getTicks().values())
            tick.paint(g2d);
      } else
         super.paint(g);
   }
   public void actionPerformed() {
      if (myDisplay.getSmartmeter() instanceof Smartmeter sm)
         if (myDisplay.getSpinTag1().getValue() instanceof String datum) {
            if (sm.getTag(datum) instanceof Tag t)
               tag=t;
         }
      repaint(1000);
   }
   @Override
   public void insertUpdate(DocumentEvent e) {
      actionPerformed();
   }
   @Override
   public void removeUpdate(DocumentEvent e) { /* actionPerformed(); */ }
   @Override
   public void changedUpdate(DocumentEvent e) {
      actionPerformed();
   }
}
