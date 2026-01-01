/**
 * 
 */
package de.uhingen.kielkopf.andreas.smartmetergui.http;

import java.nio.file.Path;
import java.util.ArrayList;

import de.uhingen.kielkopf.andreas.smartmetergui.data.Smartmeter;

/**
 * Dienstleister, der den netzwerkverkehr übernimmt
 * 
 * @author Andreas Kielkopf
 *
 */
public class Client {
   private Path          pfad;
   private CallBack      cb;
   // Fensterposition x;
   static private Client c;
   /**
    * @return privates Singleton
    */
   static final private Client get() {
      if (c == null)
         c=new Client();
      return c;
   }
   /**
    * 
    */
   private Client() {
      // TODO Auto-generated constructor stub
   }
   /**
    * Gibt eine Suche in Auftrag und registriert einen callback sobald was gefunden wird
    * 
    * @param cb_
    * @return leere Liste, die mit callbacks gefüllt wird
    */
   public static ArrayList<Smartmeter> search(CallBack cb_) {
      get().cb=cb_;
      return new ArrayList<>();
   }
}
