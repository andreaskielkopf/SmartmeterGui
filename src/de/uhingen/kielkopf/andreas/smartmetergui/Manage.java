/**
 * 
 */
package de.uhingen.kielkopf.andreas.smartmetergui;

import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Paths;
import java.text.ParseException;

import javax.swing.*;
import javax.swing.JFormattedTextField.AbstractFormatter;

/**
 * @author Andreas Kielkopf
 *
 */
public class Manage extends JPanel {
   private static final long serialVersionUID=1L;
   private JPanel            panel;
   private JPanel            knoepfe;
   private JButton           btn_load;
   private JButton           btn_save;
   private JButton           btn_read;
   private JButton           btn_delete;
   private JPanel            config;
   private JPanel            scroller;
   private JScrollPane       scrollPane;
   private JList             list;
   private JTextField        field_IP;
   private JLabel            lbl_IP;
   private JLabel            lbl_Name;
   private JTextField        field_Name;
   private JLabel            lbl_Path;
   private JTextField        field_Path;
   private JButton           btn_Ping;
   private JButton           btn_test;
   /**
    * Create the panel.
    */
   public Manage() {
      initialize();
   }
   private void initialize() {
      setName(getClass().getSimpleName()); // Name für Tab
      setLayout(new BorderLayout(0, 0));
      add(getPanel());
   }
   private JPanel getPanel() {
      if (panel == null) {
         panel=new JPanel();
         panel.setLayout(new BorderLayout(0, 0));
         panel.add(getKnoepfe(), BorderLayout.SOUTH);
         panel.add(getConfig(), BorderLayout.NORTH);
         panel.add(getScroller(), BorderLayout.CENTER);
      }
      return panel;
   }
   private JPanel getKnoepfe() {
      if (knoepfe == null) {
         knoepfe=new JPanel();
         knoepfe.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 5));
         knoepfe.add(getBtn_read());
         knoepfe.add(getBtn_load());
         knoepfe.add(getBtn_save());
         knoepfe.add(getBtn_delete());
      }
      return knoepfe;
   }
   private JButton getBtn_load() {
      if (btn_load == null) {
         btn_load=new JButton("load");
         btn_load.setEnabled(false);
         btn_load.setToolTipText("lade Daten von disk");
      }
      return btn_load;
   }
   private JButton getBtn_save() {
      if (btn_save == null) {
         btn_save=new JButton("save");
         btn_save.setEnabled(false);
         btn_save.setToolTipText("Speichre Daten auf disk");
      }
      return btn_save;
   }
   private JButton getBtn_read() {
      if (btn_read == null) {
         btn_read=new JButton("read Smartmeter");
         btn_read.setToolTipText("lies Daten vonm Smartmeter");
      }
      return btn_read;
   }
   private JButton getBtn_delete() {
      if (btn_delete == null) {
         btn_delete=new JButton("New button");
         btn_delete.setEnabled(false);
         btn_delete.setToolTipText("lösche die markierten Tage");
      }
      return btn_delete;
   }
   private JPanel getConfig() {
      if (config == null) {
         config=new JPanel();
         config.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 5));
         config.add(getLbl_IP());
         config.add(getField_IP());
         config.add(getBtn_Ping());
         config.add(getLbl_Name());
         config.add(getField_Name());
         config.add(getBtn_test());
         config.add(getLbl_Path());
         config.add(getField_Path());
      }
      return config;
   }
   private JPanel getScroller() {
      if (scroller == null) {
         scroller=new JPanel();
         scroller.setLayout(new BorderLayout(0, 0));
         scroller.add(getScrollPane(), BorderLayout.CENTER);
      }
      return scroller;
   }
   private JScrollPane getScrollPane() {
      if (scrollPane == null) {
         scrollPane=new JScrollPane(getList());
      }
      return scrollPane;
   }
   private JList getList() {
      if (list == null) {
         list=new JList();
      }
      return list;
   }
   /**
    * IP-Feld mit grober Prüfung der Eingabe
    * 
    * @return
    */
   private JTextField getField_IP() {
      if (field_IP == null) {
         AbstractFormatter formatter=new JFormattedTextField.AbstractFormatter() {
            @Override
            public String valueToString(Object value) throws ParseException {
               if (value instanceof InetAddress inet) {
                  getFormattedTextField().setForeground(Color.BLUE);
                  System.out.println(inet.toString());
                  return inet.getHostAddress();
               }
               getFormattedTextField().setForeground(Color.GRAY);
               return null;
            }
            @Override
            public Object stringToValue(String text) throws ParseException {
               try {
                  System.out.println(InetAddress.getByName(text).toString());
                  getFormattedTextField().setForeground(Color.BLACK);
                  getBtn_Ping().setEnabled(true);
                  return InetAddress.getByName(text);
               } catch (UnknownHostException e) {
                  getFormattedTextField().setForeground(Color.RED);
                  getBtn_Ping().setEnabled(false);
                  System.err.println(e.getMessage());
               }
               throw new ParseException("keine passende IP", text.length() - 1);
            }
         };
         field_IP=new JFormattedTextField(formatter);
         field_IP.setColumns(10);
         field_IP.setText("192.168.178.2");
      }
      return field_IP;
   }
   private JLabel getLbl_IP() {
      if (lbl_IP == null) {
         lbl_IP=new JLabel("IP:");
      }
      return lbl_IP;
   }
   private JLabel getLbl_Name() {
      if (lbl_Name == null) {
         lbl_Name=new JLabel("Name:");
         lbl_Name.setToolTipText("Name des Smartmeters");
      }
      return lbl_Name;
   }
   private JTextField getField_Name() {
      if (field_Name == null) {
         field_Name=new JTextField();
         field_Name.setColumns(10);
         field_Name.setEnabled(false);
         field_Name.setEditable(false);
         field_Name.setText("Smartmeter");
      }
      return field_Name;
   }
   private JLabel getLbl_Path() {
      if (lbl_Path == null) {
         lbl_Path=new JLabel("Pfad:");
         lbl_Path.setToolTipText("Ort wo die Daten gespeichert werden");
      }
      return lbl_Path;
   }
   private JTextField getField_Path() {
      if (field_Path == null) {
         field_Path=new JTextField();
         field_Path.setColumns(20);
         field_Path.setText(Paths.get(".").toAbsolutePath().toString());
         field_Path.setEnabled(false);
         field_Path.setEditable(false);
      }
      return field_Path;
   }
   private JButton getBtn_Ping() {
      if (btn_Ping == null) {
         btn_Ping=new JButton("ping");
         btn_Ping.setToolTipText("teste ob diese IP auf Ping antwortet");
      }
      return btn_Ping;
   }
   private JButton getBtn_test() {
      if (btn_test == null) {
         btn_test=new JButton("test");
         btn_test.setToolTipText("teste ob das ein Smartmeter ist");
      }
      return btn_test;
   }
}
