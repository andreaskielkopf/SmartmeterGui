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
   private JPanel      panel_2;
   private JPanel      panel_3;
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
         info=new JLabel("info");
         info.setFont(new Font("Noto Sans", Font.PLAIN, 14));
         info.setHorizontalAlignment(SwingConstants.CENTER);
      }
      return info;
   }
   // private class JTabbedPane2 extends JTabbedPane {
   // public JTabbedPane2(int top) {
   // super(top);
   // }
   // public void addTab(Component component) {
   // addTab(component.getName(), component);
   // }
   // }
   private JTabbedPane getTabbedPane() {
      if (tabbedPane == null) {
         tabbedPane=new JTabbedPane(JTabbedPane.TOP);
         tabbedPane.addTab(getManage().getName(), getManage());
         tabbedPane.addTab("New tab", null, getPanel_2(), null);
         tabbedPane.addTab("New tab", null, getPanel_3(), null);
      }
      return tabbedPane;
   }
   private Manage getManage() {
      if (manage == null) {
         manage=new Manage();
      }
      return manage;
   }
   private JPanel getPanel_2() {
      if (panel_2 == null) {
         panel_2=new JPanel();
      }
      return panel_2;
   }
   private JPanel getPanel_3() {
      if (panel_3 == null) {
         panel_3=new JPanel();
      }
      return panel_3;
   }
}
