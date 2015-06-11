package message.extractor.analyzer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CharacterAnalysis implements Analyzer {
    // members
    private Variable[] c = new Variable[10];
    private String globalInput;

    public CharacterAnalysis() {
        initVariables();
    }

    private void initVariables() {
        c[1] = Variable.getInstance("C1")
                .setDescriptionEN("Description in English")
                .setDescriptionTH("Description in Thai.");
        c[2] = Variable.getInstance("C2")
                .setDescriptionEN("Description in English")
                .setDescriptionTH("Description in Thai.");
        c[3] = Variable.getInstance("C3")
                .setDescriptionEN("Description in English")
                .setDescriptionTH("Description in Thai.");
        c[4] = Variable.getInstance("C4")
                .setDescriptionEN("Description in English")
                .setDescriptionTH("Description in Thai.");
        c[5] = Variable.getInstance("C5")
                .setDescriptionEN("Description in English")
                .setDescriptionTH("Description in Thai.");
        c[6] = Variable.getInstance("C6")
                .setDescriptionEN("Description in English")
                .setDescriptionTH("Description in Thai.");
        c[7] = Variable.getInstance("C7")
                .setDescriptionEN("Description in English")
                .setDescriptionTH("Description in Thai.");
        c[8] = Variable.getInstance("C8")
                .setDescriptionEN("Description in English")
                .setDescriptionTH("Description in Thai.");
        c[9] = Variable.getInstance("C9")
                .setDescriptionEN("Description in English")
                .setDescriptionTH("Description in Thai.");
    }

    @Override
    public void clear() {
        // clear object data for reusing objects
    }

    @Override
    public void analyze(String input) {
        globalInput = input;
        c[1].addValue(input.length()); // total character
        c[2].addValue(Count("[\\u0E01-\\u0E5B]")); //total Thai character
        c[3].addValue(Count("([\\u0041-\\u005A])|([\\u0061-\\u007A])"));//total English
        c[4].addValue(Count("([\\u0041-\\u005A])"));//total CapEnglish
        c[5].addValue(Count("([\\u0030-\\u0039])")); // total digit
        c[6].addValue(Count(" ")); // total whitespace
        c[7].addValue(Count("\\u0009")); //total tab character
        c[8].addValue();//line ending
        c[9].addValue(Count("([\\u0021-\\u002F])|([\\u003A-\\u0040])|([\\u005B-\\u0060])|([\\u007B-\\u007E])"));//special characters
        globalInput = null;
    }
        public void calWeightedValues() {
        
        c[2].calFinalValue(c[1]);
        //c[3].calFinalValue(c[1]);
        c[4].calFinalValue(c[3]);
        c[4].calFinalValue(c[1]);
        c[5].calFinalValue(c[1]);
        c[6].calFinalValue(c[1]);
        c[7].calFinalValue(c[6]);
        //c[7].calFinalValue(c[1]);
        c[8].calFinalValue(c[1]);
        c[9].calFinalValue(c[1]);
        
    }
    
    public Variable getC1(){
    	return c[1];
    }
     public Variable getC5(){
        return c[5];
    }
    public void DEBUG() {
        for(int i=1; i<=9; i++) {
             System.out.printf("C[%d]="+c[i].getValue(),i);
            System.out.println();
            System.out.printf("Weighted C[%d]="+c[i].getweightedValue(),i);
            System.out.println();
            System.out.println("---------------------------------------");
        }
        
    }

    private int Count(String str) {
        Pattern p = Pattern.compile(String.format("%s", str));
        Matcher m = p.matcher(globalInput);
        int counter = 0;
        while(m.find()) {
            counter++;
        }
        return counter;
    }

    public Variable[] getVariables() {
        return c;
    }

}