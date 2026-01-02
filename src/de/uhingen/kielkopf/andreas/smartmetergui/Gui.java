/**
 * 
 */
package de.uhingen.kielkopf.andreas.smartmetergui;

import java.awt.*;

import javax.swing.*;

/**
 * @author Andreas Kielkopf
 *
 */
public class Gui {
   private JFrame      frmSmartmetergui;
   private JPanel      panel;
   private JLabel      info;
   private JTabbedPane tabbedPane;
   private Manage      manage;
   private Display     display;
   /**
    * Launch the application.
    */
   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            try {
               Gui window=new Gui();
               window.frmSmartmetergui.setVisible(true);
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      });
   }
   /**
    * Create the application.
    */
   public Gui() {
      initialize();
   }
   /**
    * Initialize the contents of the frame.
    */
   private void initialize() {
      frmSmartmetergui=new JFrame();
      frmSmartmetergui.setTitle("Smartmeter-Gui");
      frmSmartmetergui.setBounds(100, 100, 800, 650);
      frmSmartmetergui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frmSmartmetergui.getContentPane().add(getPanel(), BorderLayout.CENTER);
      frmSmartmetergui.getContentPane().add(getInfo(), BorderLayout.SOUTH);
   }
   private JPanel getPanel() {
      if (panel == null) {
         panel=new JPanel();
         panel.setLayout(new BorderLayout(0, 0));
         panel.add(getTabbedPane(), BorderLayout.CENTER);
      }
      return panel;
   }
   private JLabel getInfo() {
      if (info == null) {
         info=new JLabel("Programm um Messmodule am Smartmeter auszulesen. Siehe https://github.com/andreaskielkopf");
         info.setFont(new Font("Noto Sans", Font.PLAIN, 14));
         info.setHorizontalAlignment(SwingConstants.CENTER);
      }
      return info;
   }
   private JTabbedPane getTabbedPane() {
      if (tabbedPane == null) {
         tabbedPane=new JTabbedPane(JTabbedPane.TOP);
         tabbedPane.addTab(getManage().getName(), getManage());
         tabbedPane.addTab(getDisplay().getName(), getDisplay());
      }
      return tabbedPane;
   }
   private Manage getManage() {
      if (manage == null) {
         manage=new Manage();
      }
      return manage;
   }
   private Display getDisplay() {
      if (display == null) {
         display=new Display();
      }
      return display;
   }
}
