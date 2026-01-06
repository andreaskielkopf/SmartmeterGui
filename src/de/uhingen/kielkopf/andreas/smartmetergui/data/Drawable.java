/**
 * 
 */
package de.uhingen.kielkopf.andreas.smartmetergui.data;

import java.awt.Graphics2D;

/**
 * @author Andreas Kielkopf
 *
 */
public interface Drawable {
   public void paint(Graphics2D g2d);
   public int getSec();
}
