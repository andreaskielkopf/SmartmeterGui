/**
 * 
 */
package de.uhingen.kielkopf.andreas.smartmetergui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import de.uhingen.kielkopf.andreas.smartmetergui.data.Smartmeter;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.FlowLayout;

/**
 * @author Andreas Kielkopf
 *
 */
public class Display extends JPanel {
   static private Display singleton;
   private JPanel         panel;
   private Smartmeter     smartmeter;
   private JPanel         panel_1;
   private JPanel         panel_2;
   private JLabel         lbl_Name;
   private JTextField     field_Name;
   public Display() {
      initialize();
      singleton=this;
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
         panel.add(getPanel_1(), BorderLayout.NORTH);
         panel.add(getPanel_2(), BorderLayout.SOUTH);
      }
      return panel;
   }
   static public void setSmartmeter(Smartmeter smartmeter_) {
      if (singleton instanceof Display display)
         display.setSm(smartmeter_);
   }
   private void setSm(Smartmeter sm_) {
      if (sm_ instanceof Smartmeter sm) {
         smartmeter=sm;
         getField_Name().setText(sm.getName());
         getField_Name().setEnabled(true);
      }
   }
   private JPanel getPanel_1() {
      if (panel_1 == null) {
         panel_1=new JPanel();
         panel_1.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 5));
         panel_1.add(getLbl_Name());
         panel_1.add(getField_Name());
      }
      return panel_1;
   }
   private JPanel getPanel_2() {
      if (panel_2 == null) {
         panel_2=new JPanel();
         panel_2.setLayout(new BorderLayout(0, 0));
      }
      return panel_2;
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
}
