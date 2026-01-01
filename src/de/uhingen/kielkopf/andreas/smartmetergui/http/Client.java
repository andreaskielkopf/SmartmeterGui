/**
 * 
 */
package de.uhingen.kielkopf.andreas.smartmetergui.http;

import java.awt.Color;
import java.io.IOException;
import java.net.*;
import java.net.http.*;
import java.net.http.HttpRequest.Builder;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;

import de.uhingen.kielkopf.andreas.smartmetergui.Manage;
import de.uhingen.kielkopf.andreas.smartmetergui.data.Smartmeter;

/**
 * Dienstleister, der den netzwerkverkehr übernimmt
 * 
 * @author Andreas Kielkopf
 *
 */
public class Client {
   // private CallBack cb;
   // Fensterposition x;
   static private HttpClient client;   // gemeinsam
   // private URI uri;
   static private Builder    builder;  // gemeinsam
   private URI               basisUri; // je client
   /**
    * 
    */
   private Client() {
      // TODO Auto-generated constructor stub
   }
   /**
    * @param ip
    * @throws URISyntaxException
    */
   public Client(InetAddress ip) throws URISyntaxException {
      setIP(ip);
   }
   /**
    * Testet diese Ip und informiert dann die GUI Es wir inkaufgenommen, dass die GUI kurz "hängt"
    * 
    * @param field_IP
    * @param btn_Ping
    * @param btn_Test
    * @return
    */
   public static void ping(JFormattedTextField field_IP, JButton btn_Ping, JButton btn_Test) {
      if (field_IP.getValue() instanceof InetAddress ip) {
         try {
            field_IP.setForeground(Color.ORANGE);
            btn_Ping.setBackground(Color.YELLOW);
            btn_Test.setEnabled(false);
            // mit der IP eine Anfrage starten und wenn die Antwort kommt:
            if (ip.isReachable(5_000)) {
               field_IP.setForeground(Color.GREEN.darker());
               btn_Ping.setBackground(Color.GREEN);
               btn_Test.setEnabled(true);
               System.out.println("Ping " + ip + " erfolgreich");
            } else {
               field_IP.setForeground(Color.RED);
               btn_Ping.setBackground(Color.RED);
               System.err.println("Ping " + ip + " erfolglos");
            }
         } catch (IOException e) {
            System.err.println(e.toString());
            e.printStackTrace();
         }
      } else {
         // field_IP.setForeground(Color.RED);
         btn_Ping.setBackground(Manage.DEFAULT_BUTTON_BACKGROUND);
         btn_Ping.setEnabled(false);
         btn_Test.setEnabled(false);
      }
   }
   /**
    * Es wir getestet, ob das ein Smartmeter ist (anhand der Antworten) Es wir inkaufgenommen, dass die GUI kurz "hängt"
    * 
    * @param field_IP
    * @param manage
    * @param btn_test
    * @return
    */
   public static void testSmartmeter(JFormattedTextField field_IP, JButton btn_Test, Manage manage) {
      if (field_IP.getValue() instanceof InetAddress ip) {
         btn_Test.setBackground(Color.YELLOW);
         if (isSmartmeter(ip)) {
            btn_Test.setBackground(Color.GREEN);
            System.out.println("Smartmeter bei " + ip + " gefunden");
            try {
               manage.setSmartmeter(new Smartmeter(ip));
            } catch (URISyntaxException e) {
               e.printStackTrace();
            }
         } else {
            btn_Test.setBackground(Color.RED);
            System.err.println("KEIN Smartmeter bei " + ip + " gefunden");
         }
      } else {
         btn_Test.setBackground(Manage.DEFAULT_BUTTON_BACKGROUND);
         btn_Test.setEnabled(false);
      }
   }
   /**
    * @param ip
    * @return
    */
   private static boolean isSmartmeter(InetAddress ip) {
      try {
         // setze diese IP probeweise ein
         Client c=new Client();
         c.setIP(ip);
         if (c.anfrage("") instanceof String antwort)
            return antwort.contains("/smartmeter/{");
         return false;
      } catch (URISyntaxException | IOException | InterruptedException e) {
         e.printStackTrace();
      }
      return false;
   }
   /**
    * @param ip
    * @throws URISyntaxException
    */
   private void setIP(InetAddress ip) throws URISyntaxException {
      if (client == null)
         client=HttpClient.newHttpClient();
      if (builder == null)
         builder=HttpRequest.newBuilder().GET();// .uri(c.uri).GET();
      basisUri=new URI("http:/" + ip + "/smartmeter/");
      // System.out.println(basisUri);
   }
   /**
    * @param string
    * @return
    * @throws InterruptedException
    * @throws IOException
    */
   public String anfrage(String pfad) throws IOException, InterruptedException {
      URI uri=basisUri.resolve(pfad);
      // System.out.println(uri);
      HttpRequest request=builder.uri(uri).build();
      HttpResponse<String> response=client.send(request, HttpResponse.BodyHandlers.ofString());
      if (response.statusCode() != 200) {
         System.out.println(response.statusCode());
         System.out.println(response.body());
         return "";
      }
      return response.body();
   }
}
