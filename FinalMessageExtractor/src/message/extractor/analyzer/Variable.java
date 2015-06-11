package message.extractor.analyzer;

import java.text.DecimalFormat;

public class Variable {

    // private fields
    private String CODE;
    private String descriptionEN, descriptionTH;
    private double value;
    private double weightedvalue;
    private boolean boolValue;
    private boolean isBoolValueSet = false;
    private DecimalFormat df = new DecimalFormat("###.###");

    // constructor
    protected Variable(String code) { CODE = code; }

    // getter and setter
    public double getValue() {return value;}
    public double getweightedValue() {return weightedvalue;}
    public void setValue(double value) {this.value = value;}
    public boolean getBoolValue() {return boolValue;}
    public void setBoolValue(boolean boolValue) {
        isBoolValueSet = true;
        this.boolValue = boolValue;
    }
    public String getCODE() {return CODE;}
    public String getDescriptionEN() {return descriptionEN;}
    public String getDescriptionTH() {return descriptionTH;}
    public void addValue() {value += 1;}
    public void addValue(int value) {this.value += value;}
    public void addValue(double value) {this.value += value;}

    public void calFinalValue(Variable weight) {
        if(weight.getValue()==0){
            weightedvalue=9999;
        }
        else{
        weightedvalue = value / weight.getValue();
    }}
      public void calFinalValue1(Variable weight1, Variable weight) {
          if(weight.getValue()==0){
              weightedvalue=9999; 
          }
          else{
        weightedvalue = weight1.getValue() / weight.getValue();
          }
    }
    public void calFinalValue2(Variable weight1, Variable weight, Variable weight2) {
        if(weight.getValue()-weight2.getValue()==0){
            weightedvalue=9999; 
        }
        else{
        weightedvalue = weight1.getValue() / (weight.getValue()-weight2.getValue());
        }
    }

    // methods for chaining

    @Override
    public String toString() {
           return String.format("%s = %-5s",CODE, df.format(value));
    }

    public static Variable getInstance(String code) {
        return new Variable(code);
    }

    public Variable setDescriptionEN(String DesEN) {
        descriptionEN = DesEN;
        return this;
    }

    public Variable setDescriptionTH(String DesTH) {
        descriptionTH = DesTH;
        return this;
    }
}
