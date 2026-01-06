/**
 * 
 */
package de.uhingen.kielkopf.andreas.smartmetergui.http;

import java.awt.Color;
import java.io.IOException;
import java.io.Serializable;
import java.net.*;
import java.net.http.*;
import java.net.http.HttpRequest.Builder;

import javax.swing.*;

import de.uhingen.kielkopf.andreas.smartmetergui.Display;
import de.uhingen.kielkopf.andreas.smartmetergui.Manage;
import de.uhingen.kielkopf.andreas.smartmetergui.data.Smartmeter;

/**
 * Dienstleister, der den netzwerkverkehr übernimmt
 * 
 * @author Andreas Kielkopf
 *
 */
public class Client implements Serializable {
   // Fensterposition x;
   static private HttpClient client;   // gemeinsam
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
    * Testet diese Ip und informiert dann die GUI
    * 
    * @param manage
    * @return
    */
   public static void ping(Manage manage) {
      // System.out.println(manage);
      if (manage instanceof Manage m) {
         JFormattedTextField fIP=m.getField_IP();
         JButton bPing=m.getBtn_Ping();
         JButton bTest=m.getBtn_Test();
         if (fIP.getValue() instanceof InetAddress ip) {
            fIP.setForeground(Color.ORANGE);
            bPing.setBackground(Color.YELLOW);
            bTest.setEnabled(false);
            Thread t=new Thread(() -> {
               try { // mit der IP eine Anfrage starten und wenn die Antwort kommt:
                  final boolean reachable=ip.isReachable(5_000);
                  SwingUtilities.invokeLater(() -> {
                     if (reachable) {
                        fIP.setForeground(Color.GREEN.darker());
                        bPing.setBackground(Color.GREEN);
                        bTest.setEnabled(true);
                        System.out.println("Ping " + ip + " erfolgreich");
                     } else {
                        fIP.setForeground(Color.RED);
                        bPing.setBackground(Color.RED);
                        System.err.println("Ping " + ip + " erfolglos");
                     }
                  });
               } catch (IOException e) {
                  System.err.println(e.toString());
               }
            });
            t.setDaemon(true);
            t.start();
         } else {
            bPing.setBackground(Manage.DEFAULT_BUTTON_BACKGROUND);
            bPing.setEnabled(false);
            bTest.setEnabled(false);
         }
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
   // public static void testSmartmeter(JFormattedTextField field_IP, JButton btn_Test, Manage manage) {
   // if (field_IP.getValue() instanceof InetAddress ip) {
   // btn_Test.setBackground(Color.YELLOW);
   // if (isSmartmeter(ip)) {
   // btn_Test.setBackground(Color.GREEN);
   // System.out.println("Smartmeter bei " + ip + " gefunden");
   // try {
   // Smartmeter smartm_=new Smartmeter(ip);
   // Display.setSmartmeter(smartm_);
   // manage.setSmartmeter(smartm_);
   // } catch (URISyntaxException e) {
   // e.printStackTrace();
   // }
   // } else {
   // btn_Test.setBackground(Color.RED);
   // System.err.println("KEIN Smartmeter bei " + ip + " gefunden");
   // }
   // } else {
   // btn_Test.setBackground(Manage.DEFAULT_BUTTON_BACKGROUND);
   // btn_Test.setEnabled(false);
   // }
   // }
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
      HttpRequest request=builder.uri(basisUri.resolve(pfad)).build();
      HttpResponse<String> response=client.send(request, HttpResponse.BodyHandlers.ofString());
      if (response.statusCode() != 200) {
         System.out.println(response.statusCode());
         System.out.println(response.body());
         return "";
      }
      return response.body();
   }
   /**
    * @return
    */
   public static void test(Manage manage) {
      if (manage instanceof Manage m) {
         JButton bTest=m.getBtn_Test();
         if (m.getField_IP().getValue() instanceof InetAddress ip) {
            bTest.setBackground(Color.YELLOW);
            Thread t=new Thread(() -> { // try {
               boolean isSmartmeter=isSmartmeter(ip);
               SwingUtilities.invokeLater(() -> {
                  try {
                     if (isSmartmeter) {
                        bTest.setBackground(Color.GREEN);
                        System.out.println("Smartmeter bei " + ip + " gefunden");
                        Smartmeter smart_=new Smartmeter(ip);
                        Display.setSmartmeter(smart_);
                        m.setSmartmeter(smart_);
                     } else {
                        bTest.setBackground(Color.RED);
                        System.err.println("KEIN Smartmeter bei " + ip + " gefunden");
                     }
                  } catch (URISyntaxException e) {
                     System.err.println(e.getMessage());
                  }
               });
               // } catch (InterruptedException e) { System.err.println(e.getMessage());}
            });
            t.setDaemon(true);
            t.start();
         } else {
            bTest.setBackground(Manage.DEFAULT_BUTTON_BACKGROUND);
            bTest.setEnabled(false);
         }
      }
   }
}
