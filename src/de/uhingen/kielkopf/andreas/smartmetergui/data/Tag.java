/**
 * 
 */
package de.uhingen.kielkopf.andreas.smartmetergui.data;

import java.io.Serializable;
import java.time.Instant;
import java.util.TreeMap;

/**
 * @author Andreas Kielkopf
 *
 */
public class Tag implements Serializable {
   String                   name;
   Instant                  punkt;
   TreeMap<Integer, Stunde> stunden=new TreeMap<>();
}
