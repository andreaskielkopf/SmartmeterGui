/**
 * 
 */
package de.uhingen.kielkopf.andreas.smartmetergui;

import java.awt.BorderLayout;

import de.uhingen.kielkopf.andreas.smartmetergui.beans.TagSpinner;
import de.uhingen.kielkopf.andreas.smartmetergui.data.Smartmeter;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.Color;

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
   private JPanel         panel_3;
   private JPanel         panel_4;
   private JPanel         panel_5;
   private JPanel         panel_6;
   private JLabel         lblNewLabel;
   private TagSpinner     tagSpinner1;
   private JSpinner       spinner_1;
   private JLabel         lblNewLabel_1;
   private TagSpinner     tagSpinner2;
   private JSpinner       spinner_3;
   private JLabel         lblNewLabel_2;
   private JTextField     textField;
   private JButton        btnNewButton;
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
         panel.add(getPanel_2(), BorderLayout.CENTER);
      }
      return panel;
   }
   static public void setSmartmeter(Smartmeter smartmeter_) {
      if (singleton instanceof Display display && smartmeter_ instanceof Smartmeter sm)
         display.setSm(sm);
   }
   private void setSm(Smartmeter sm_) {
      smartmeter=sm_;
      getField_Name().setText(smartmeter.getName());
      getField_Name().setEnabled(true);
      getTagSpinner1().getTSM().setSmartmeter(sm_);
      getTagSpinner2().getTSM().setSmartmeter(sm_);
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
         panel_2.add(getPanel_3(), BorderLayout.NORTH);
         panel_2.add(getPanel_4(), BorderLayout.CENTER);
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
   private JPanel getPanel_3() {
      if (panel_3 == null) {
         panel_3=new JPanel();
         panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));
         panel_3.add(getPanel_5());
         panel_3.add(getPanel_8());
      }
      return panel_3;
   }
   private JPanel getPanel_4() {
      if (panel_4 == null) {
         panel_4=new JPanel();
         panel_4.setLayout(new BorderLayout(0, 0));
         panel_4.add(getPanel_9(), BorderLayout.WEST);
         panel_4.add(getPanel_10(), BorderLayout.SOUTH);
         panel_4.add(getScrollPane(), BorderLayout.CENTER);
      }
      return panel_4;
   }
   private JPanel getPanel_5() {
      if (panel_5 == null) {
         panel_5=new JPanel();
         panel_5.setBorder(new TitledBorder(null, "Auswahl:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
         panel_5.setLayout(new GridLayout(3, 3, 10, 5));
         panel_5.add(getLblNewLabel());
         panel_5.add(getTagSpinner1());
         panel_5.add(getSpinner_1());
         panel_5.add(getLblNewLabel_1());
         panel_5.add(getTagSpinner2());
         panel_5.add(getSpinner_3());
         panel_5.add(getBtnNewButton());
         panel_5.add(getLblNewLabel_2());
         panel_5.add(getTextField());
      }
      return panel_5;
   }
   private JPanel getPanel_6() {
      if (panel_6 == null) {
         panel_6=new JPanel();
         panel_6.setBorder(new TitledBorder(null, "Faltung:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
         panel_6.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
         panel_6.add(getBtn_keine());
         panel_6.add(getRdbtnTag());
         panel_6.add(getRdbtnWoche());
         panel_6.add(getRdbtnMonat());
         panel_6.add(getRdbtnJahr());
      }
      return panel_6;
   }
   private JLabel getLblNewLabel() {
      if (lblNewLabel == null) {
         lblNewLabel=new JLabel("von");
         lblNewLabel.setHorizontalAlignment(SwingConstants.TRAILING);
      }
      return lblNewLabel;
   }
   private TagSpinner getTagSpinner1() {
      if (tagSpinner1 == null) {
         tagSpinner1=new TagSpinner();
      }
      return tagSpinner1;
   }
   private JSpinner getSpinner_1() {
      if (spinner_1 == null) {
         spinner_1=new JSpinner(new SpinnerListModel(STUNDEN));
      }
      return spinner_1;
   }
   private JLabel getLblNewLabel_1() {
      if (lblNewLabel_1 == null) {
         lblNewLabel_1=new JLabel("bis");
         lblNewLabel_1.setHorizontalAlignment(SwingConstants.TRAILING);
      }
      return lblNewLabel_1;
   }
   private TagSpinner getTagSpinner2() {
      if (tagSpinner2 == null) {
         tagSpinner2=new TagSpinner();
      }
      return tagSpinner2;
   }
   final static private List STUNDEN=Arrays.asList("00", "01", "02", "03", "04", "05", "06", "07", "08", "09",   //
            "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",                                          //
            "20", "21", "22", "23");
   private JPanel            panel_7;
   private JRadioButton      btn_keine;
   private JRadioButton      btn_minuten;
   private JRadioButton      btn_stunden;
   private JRadioButton      btn_tage;
   private JRadioButton      btn_wochen;
   private JRadioButton      rdbtnTag;
   private JRadioButton      rdbtnWoche;
   private JRadioButton      rdbtnMonat;
   private JRadioButton      rdbtnJahr;
   private JPanel            panel_8;
   private JPanel            panel_9;
   private JPanel            panel_10;
   private JScrollPane       scrollPane;
   private JLabel            lblX;
   private JLabel            lblY;
   private JPanel            grafik;
   private JSpinner getSpinner_3() {
      if (spinner_3 == null) {
         spinner_3=new JSpinner(new SpinnerListModel(STUNDEN));
      }
      return spinner_3;
   }
   private JLabel getLblNewLabel_2() {
      if (lblNewLabel_2 == null) {
         lblNewLabel_2=new JLabel("max Ticks");
         lblNewLabel_2.setHorizontalAlignment(SwingConstants.TRAILING);
      }
      return lblNewLabel_2;
   }
   private JTextField getTextField() {
      if (textField == null) {
         textField=new JTextField();
         textField.setColumns(10);
      }
      return textField;
   }
   private JButton getBtnNewButton() {
      if (btnNewButton == null) {
         btnNewButton=new JButton("reset");
         btnNewButton.setEnabled(false);
      }
      return btnNewButton;
   }
   private JPanel getPanel_7() {
      if (panel_7 == null) {
         panel_7=new JPanel();
         panel_7.setBorder(new TitledBorder(null, "Anzeige:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
         panel_7.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
         panel_7.add(getBtn_minuten());
         panel_7.add(getBtn_stunden());
         panel_7.add(getBtn_tage());
         panel_7.add(getBtn_wochen());
      }
      return panel_7;
   }
   private JRadioButton getBtn_keine() {
      if (btn_keine == null) {
         btn_keine=new JRadioButton("keine");
         btn_keine.setSelected(true);
      }
      return btn_keine;
   }
   private JRadioButton getBtn_minuten() {
      if (btn_minuten == null) {
         btn_minuten=new JRadioButton("Minuten");
         btn_minuten.setSelected(true);
      }
      return btn_minuten;
   }
   private JRadioButton getBtn_stunden() {
      if (btn_stunden == null) {
         btn_stunden=new JRadioButton("Stunden");
         btn_stunden.setEnabled(false);
      }
      return btn_stunden;
   }
   private JRadioButton getBtn_tage() {
      if (btn_tage == null) {
         btn_tage=new JRadioButton("Tage");
         btn_tage.setEnabled(false);
      }
      return btn_tage;
   }
   private JRadioButton getBtn_wochen() {
      if (btn_wochen == null) {
         btn_wochen=new JRadioButton("Wochen");
         btn_wochen.setEnabled(false);
      }
      return btn_wochen;
   }
   private JRadioButton getRdbtnTag() {
      if (rdbtnTag == null) {
         rdbtnTag=new JRadioButton("Tag");
         rdbtnTag.setEnabled(false);
      }
      return rdbtnTag;
   }
   private JRadioButton getRdbtnWoche() {
      if (rdbtnWoche == null) {
         rdbtnWoche=new JRadioButton("Woche");
         rdbtnWoche.setEnabled(false);
      }
      return rdbtnWoche;
   }
   private JRadioButton getRdbtnMonat() {
      if (rdbtnMonat == null) {
         rdbtnMonat=new JRadioButton("Monat");
         rdbtnMonat.setEnabled(false);
      }
      return rdbtnMonat;
   }
   private JRadioButton getRdbtnJahr() {
      if (rdbtnJahr == null) {
         rdbtnJahr=new JRadioButton("Jahr");
         rdbtnJahr.setEnabled(false);
      }
      return rdbtnJahr;
   }
   private JPanel getPanel_8() {
      if (panel_8 == null) {
         panel_8=new JPanel();
         panel_8.setLayout(new BoxLayout(panel_8, BoxLayout.Y_AXIS));
         panel_8.add(getPanel_6());
         panel_8.add(getPanel_7());
      }
      return panel_8;
   }
   private JPanel getPanel_9() {
      if (panel_9 == null) {
         panel_9=new JPanel();
         panel_9.setLayout(new GridLayout(0, 1, 0, 0));
         panel_9.add(getLblY());
      }
      return panel_9;
   }
   private JPanel getPanel_10() {
      if (panel_10 == null) {
         panel_10=new JPanel();
         panel_10.setLayout(new GridLayout(0, 1, 0, 0));
         panel_10.add(getLblX());
      }
      return panel_10;
   }
   private JScrollPane getScrollPane() {
      if (scrollPane == null) {
         scrollPane=new JScrollPane(getGrafik());
         scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
         scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
      }
      return scrollPane;
   }
   private JLabel getLblX() {
      if (lblX == null) {
         lblX=new JLabel("XX");
         lblX.setOpaque(true);
         lblX.setBackground(new Color(127, 255, 212));
      }
      return lblX;
   }
   private JLabel getLblY() {
      if (lblY == null) {
         lblY=new JLabel("YY");
         lblY.setOpaque(true);
         lblY.setBackground(new Color(135, 206, 235));
      }
      return lblY;
   }
   private JPanel getGrafik() {
      if (grafik == null) {
         grafik=new JPanel();
         grafik.setBackground(new Color(255, 250, 205));
         grafik.setLayout(new BorderLayout(0, 0));
      }
      return grafik;
   }
}
