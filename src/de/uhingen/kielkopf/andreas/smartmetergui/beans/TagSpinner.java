/**
 * 
 */
package de.uhingen.kielkopf.andreas.smartmetergui.beans;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.swing.*;

import de.uhingen.kielkopf.andreas.smartmetergui.data.Smartmeter;

/**
 * @author Andreas Kielkopf
 *
 */
public class TagSpinner extends JSpinner {
   private static final long serialVersionUID=4005368656680150115L;
   private TagSpinnerModel   tsm;
   public class TagSpinnerModel extends AbstractSpinnerModel {
      private static final long          serialVersionUID=-3090469076858295794L;
      static public NavigableSet<String> tage;
      private String                     value;
      public void setSmartmeter(Smartmeter smartmeter) {
         if (smartmeter instanceof Smartmeter sm //
                  && sm.getTagesListe() instanceof NavigableSet<String> set) {
            tage=set;
         }
      }
      public TagSpinnerModel() {
         tage=new ConcurrentSkipListSet<>(Arrays.asList("0 vorgestern", "1 gestern", "2 heute", "3 morgen"));
         value=tage.first();
      }
      @Override
      public Object getValue() {
         return value;
      }
      @Override
      public void setValue(Object value_) {
         if (value_ instanceof String s) {
            if (tage.contains(s)) {
               value=s;
               this.fireStateChanged();
            }
            if (!tage.isEmpty() && !tage.contains(value)) {
               value=tage.first();
               this.fireStateChanged();
            }
         }
      }
      @Override
      public Object getNextValue() {
         return tage.higher(value);
      }
      @Override
      public Object getPreviousValue() {
         return tage.lower(value);
      }
   }
   public TagSpinner() {
      super();
      setModel(getTSM());
   }
   public TagSpinnerModel getTSM() {
      if (tsm == null)
         tsm=new TagSpinnerModel();
      return tsm;
   }
}
