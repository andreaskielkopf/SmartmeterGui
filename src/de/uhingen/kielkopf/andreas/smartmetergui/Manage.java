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
import javax.swing.border.TitledBorder;

import de.uhingen.kielkopf.andreas.smartmetergui.data.Smartmeter;
import de.uhingen.kielkopf.andreas.smartmetergui.data.Tag;
import de.uhingen.kielkopf.andreas.smartmetergui.http.Client;

/**
 * @author Andreas Kielkopf
 *
 */
public class Manage extends JPanel {
   private static final long     serialVersionUID         =1L;
   private JPanel                panel;
   private JPanel                knoepfe;
   private JButton               btn_load;
   private JButton               btn_save;
   private JButton               btn_read;
   private JButton               btn_delete;
   private JPanel                config;
   private JPanel                scroller;
   private JScrollPane           scrollPane;
   private JList<Tag>            list;
   private JFormattedTextField   field_IP;
   private JLabel                lbl_IP;
   private JLabel                lbl_Name;
   private JTextField            field_Name;
   private JLabel                lbl_Path;
   private JTextField            field_Path;
   private JButton               btn_Ping;
   private JButton               btn_Test;
   private Smartmeter            smartmeter;
   /** Brauchts um die Buttons rücksetzen zu können */
   static public Color           DEFAULT_BUTTON_BACKGROUND=Color.CYAN;
   private JLabel                lbl_Info;
   private JPanel                panel_1;
   private DefaultListModel<Tag> listModel;
   private JPanel                smartmeter_1;
   private JPanel                panel_2;
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
         panel.add(getConfig(), BorderLayout.NORTH);
         panel.add(getSmartmeter_1(), BorderLayout.CENTER);
      }
      return panel;
   }
   private JPanel getKnoepfe() {
      if (knoepfe == null) {
         knoepfe=new JPanel();
         knoepfe.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 5));
         knoepfe.add(getLbl_Path());
         knoepfe.add(getField_Path());
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
         btn_read.setEnabled(false);
         btn_read.addActionListener(_ -> {
            if (smartmeter instanceof Smartmeter s)
               s.read();
         });
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
         config.add(getBtn_Test());
      }
      return config;
   }
   private JPanel getScroller() {
      if (scroller == null) {
         scroller=new JPanel();
         scroller.setLayout(new BorderLayout(0, 0));
         scroller.add(getScrollPane(), BorderLayout.CENTER);
         scroller.add(getPanel_1(), BorderLayout.SOUTH);
      }
      return scroller;
   }
   private JScrollPane getScrollPane() {
      if (scrollPane == null) {
         scrollPane=new JScrollPane(getList());
      }
      return scrollPane;
   }
   private JList<Tag> getList() {
      if (list == null) {
         list=new JList<>(getListModel());
         list.setFont(new Font("Noto Sans", Font.BOLD, 14));
         list.setBorder(new TitledBorder(null, "Daten (UCT)", TitledBorder.LEADING, TitledBorder.TOP, null,
                  new Color(59, 59, 59)));
         list.setVisibleRowCount(-1);
         list.setToolTipText("Alle bisher vorhandenen Daten vom Smartmeter");
         list.setLayoutOrientation(JList.VERTICAL_WRAP);
      }
      return list;
   }
   /**
    * @return
    */
   public DefaultListModel<Tag> getListModel() {
      if (listModel == null)
         listModel=new DefaultListModel<Tag>();
      return listModel;
   }
   /**
    * IP-Feld mit grober Prüfung der Eingabe
    * 
    * @return
    */
   public JFormattedTextField getField_IP() {
      if (field_IP == null) {
         AbstractFormatter formatter=new JFormattedTextField.AbstractFormatter() {
            @Override
            public String valueToString(Object value) throws ParseException {
               getBtn_Ping().setBackground(DEFAULT_BUTTON_BACKGROUND);
               if (value instanceof InetAddress inet) {
                  getFormattedTextField().setForeground(Color.BLUE);
                  // System.out.println(inet.toString());
                  return inet.getHostAddress();
               }
               getFormattedTextField().setForeground(Color.BLACK);
               return null;
            }
            @Override
            public Object stringToValue(String text) throws ParseException {
               try {
                  getBtn_Ping().setBackground(DEFAULT_BUTTON_BACKGROUND);
                  // System.out.println(InetAddress.getByName(text).toString());
                  getFormattedTextField().setForeground(Color.BLACK);
                  getBtn_Ping().setEnabled(true);
                  return InetAddress.getByName(text);
               } catch (UnknownHostException e) {
                  getFormattedTextField().setForeground(Color.RED);
                  getBtn_Ping().setEnabled(false);
                  System.err.println(e.getMessage());
               }
               throw new ParseException("keine passende IP", text.length());
            }
         };
         field_IP=new JFormattedTextField(formatter);
         field_IP.setColumns(10);
         field_IP.setText("192.168.178.57");
         try {
            field_IP.commitEdit();
         } catch (ParseException e) { /* */ }
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
         field_Name.setEnabled(false);
         field_Name.setColumns(10);
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
   public JButton getBtn_Ping() {
      if (btn_Ping == null) {
         btn_Ping=new JButton("ping");
         DEFAULT_BUTTON_BACKGROUND=btn_Ping.getBackground();
         btn_Ping.addActionListener(_ -> Client.ping(this));
         btn_Ping.setToolTipText("teste ob diese IP auf Ping antwortet");
      }
      return btn_Ping;
   }
   public JButton getBtn_Test() {
      if (btn_Test == null) {
         btn_Test=new JButton("test");
         btn_Test.addActionListener(_ -> Client.test(this));
         btn_Test.setToolTipText("teste ob das ein Smartmeter ist");
      }
      return btn_Test;
   }
   /**
    * @param smartmeter
    */
   public void setSmartmeter(Smartmeter smartmeter_) {
      if (smartmeter_ instanceof Smartmeter s) {
         smartmeter=s;
         smartmeter.setManage(this);
         getField_Name().setText(smartmeter_.getName());
         getField_Name().setEnabled(true);
         getBtn_read().setEnabled(true);
      } else {
         getBtn_read().setEnabled(false);
         getField_Name().setText("------------");
         smartmeter=null;
      }
   }
   public JLabel getLbl_Info() {
      if (lbl_Info == null) {
         lbl_Info=new JLabel("--");
         lbl_Info.setHorizontalAlignment(SwingConstants.LEFT);
      }
      return lbl_Info;
   }
   private JPanel getPanel_1() {
      if (panel_1 == null) {
         panel_1=new JPanel();
         panel_1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
         panel_1.add(getLbl_Info());
      }
      return panel_1;
   }
   private JPanel getSmartmeter_1() {
      if (smartmeter_1 == null) {
         smartmeter_1=new JPanel();
         smartmeter_1
                  .setBorder(new TitledBorder(null, "Smartmeter:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
         smartmeter_1.setLayout(new BorderLayout(0, 0));
         smartmeter_1.add(getScroller(), BorderLayout.CENTER);
         smartmeter_1.add(getPanel_2(), BorderLayout.NORTH);
         smartmeter_1.add(getKnoepfe(), BorderLayout.SOUTH);
      }
      return smartmeter_1;
   }
   private JPanel getPanel_2() {
      if (panel_2 == null) {
         panel_2=new JPanel();
         panel_2.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 5));
         panel_2.add(getLbl_Name());
         panel_2.add(getField_Name());
         panel_2.add(getBtn_read());
      }
      return panel_2;
   }
}
