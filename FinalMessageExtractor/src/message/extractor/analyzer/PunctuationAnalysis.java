package message.extractor.analyzer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Todo: finish p10 [true/false]

public  class PunctuationAnalysis implements Analyzer {

    // members
    private Variable[] p = new Variable[11];
    private String globalInput;
    private int yamokNearbyCount = 0;
    private int yamokWithSpaceCount = 0;

    // construtor
    public PunctuationAnalysis() {
        initVariables();
    }
    
    @Override
    public void analyze(String input) {
        globalInput = input;
        p[1].addValue(Count('.'));
        p[2].addValue(Count(','));
        p[3].addValue(Count(':'));
        p[4].addValue(Count(';'));
        p[5].addValue(Count('-','_'));
        p[6].addValue(Count('?'));
        p[7].addValue(Count('!'));
        p[8].addValue(Count('(',')'));
        p[9].addValue(Count('ๆ'));
        p[10].setValue(CountYamok());
        globalInput = null;
    }
  public void calWeightedValues(Variable C1) {
         //this.C1=C1;
    p[1].calFinalValue1(p[1], C1);
    p[2].calFinalValue1(p[2], C1);
    p[3].calFinalValue1(p[3], C1);
    p[4].calFinalValue1(p[4], C1);
    p[5].calFinalValue1(p[5], C1);
    p[6].calFinalValue1(p[6], C1);
    p[7].calFinalValue1(p[7], C1);
    p[8].calFinalValue1(p[8], C1);
    p[9].calFinalValue1(p[9], C1);
 }
public Variable getP1(){
        return p[1];
    }
public Variable getP6(){
        return p[6];
    }
public Variable getP7(){
        return p[7];
    }
public Variable getP9(){
        return p[9];
    }

    @Override
    public void clear() {
        for(int i=1; i<=10; i++) {
            p[i] = null;
        }
        initVariables();
    }

    public void DEBUG() {
        for(int i=1; i<=10; i++) {
            System.out.printf("P[%d]=" + p[i].getValue(), i);
            System.out.println();
            System.out.printf("Weighted P[%d]=" + p[i].getweightedValue(), i);
            System.out.println();
            System.out.println("---------------------------------------");
        }
    }

    private int Count(char ch) {
        Pattern p = Pattern.compile(String.format("[%c]",ch));
        Matcher m = p.matcher(globalInput);
        int counter = 0;
        while(m.find()) {
            counter++;
        }
        return counter;
    }
    
    private int CountYamok() {
        
        Pattern p = Pattern.compile("([^ๆ ]+)ๆ");
        Matcher m = p.matcher(globalInput);
        while(m.find()) {
            yamokNearbyCount++;
        }
        
        p = Pattern.compile(" ๆ");
        m = p.matcher(globalInput);
        while(m.find())  {
            yamokWithSpaceCount++;
        }
        
        // Compare
        if(yamokNearbyCount > yamokWithSpaceCount)
            return 1;
        else
            return 0;
        
    }

    private int Count(char ch, char ch2) {
        Pattern p = Pattern.compile(String.format("[%c%c]",ch,ch2));
        Matcher m = p.matcher(globalInput);
        int counter = 0;
        while(m.find()) {
            counter++;
        }
        return counter;
    }

    private void initVariables() {
        p[1] = Variable.getInstance("P1")
                .setDescriptionEN("Description in English")
                .setDescriptionTH("Description in Thai.");
        p[2] = Variable.getInstance("P2")
                .setDescriptionEN("Description in English")
                .setDescriptionTH("Description in Thai.");
        p[3] = Variable.getInstance("P3")
                .setDescriptionEN("Description in English")
                .setDescriptionTH("Description in Thai.");
        p[4] = Variable.getInstance("P4")
                .setDescriptionEN("Description in English")
                .setDescriptionTH("Description in Thai.");
        p[5] = Variable.getInstance("P5")
                .setDescriptionEN("Description in English")
                .setDescriptionTH("Description in Thai.");
        p[6] = Variable.getInstance("P6")
                .setDescriptionEN("Description in English")
                .setDescriptionTH("Description in Thai.");
        p[7] = Variable.getInstance("P7")
                .setDescriptionEN("Description in English")
                .setDescriptionTH("Description in Thai.");
        p[8] = Variable.getInstance("P8")
                .setDescriptionEN("Description in English")
                .setDescriptionTH("Description in Thai.");
        p[9] = Variable.getInstance("P9")
                .setDescriptionEN("Description in English")
                .setDescriptionTH("Description in Thai.");
        p[10] = Variable.getInstance("P10")
                .setDescriptionEN("Description in English")
                .setDescriptionTH("Description in Thai.");
    }

    public Variable[] getVariables() {
        return p;
    }
    
}
