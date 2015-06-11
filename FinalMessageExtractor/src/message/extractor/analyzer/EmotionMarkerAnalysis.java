package message.extractor.analyzer;

import java.util.regex.*;

public class EmotionMarkerAnalysis implements Analyzer
{
    // members
    private Variable[] e = new Variable[11];
    private int nWordendPat=0;
    private Matcher m;
    private String globalInput;

    private Pattern DotPat;
    private Pattern QueMarkPat;
    private Pattern ExclaMarkPat;
    private Pattern YamokPat;
    private Pattern FivePat;
    private Pattern WordendPat;

    private final String LcheekLeb = "(\\()";
    private final String LcheekNoLeb = "(?<!\\()";
    private final String Leye   = "[@\\^+*\\-=oO0;T>:'.\"?_]";
    private final String nose   = "[=+o_\".O<>\\^\\-]*";
    private final String Reye   = "[@\\^+*\\-=oO0;T<:'.\"?_]" + "[\"';*]?";
    private final String RcheekLeb = "(\\))";
    private final String RcheekNoLeb = "(?!\\))";
    private final String Veye = "[:;]";
    private final String Vnose = "[\\-]";
    private final String Vmouth = "[PDpdczv)(@o|\\/]";

    private Pattern PatNoNose = Pattern.compile(Veye+Vmouth);
    private Pattern PatWithNose = Pattern.compile(Veye+Vnose+Vmouth);
    private Pattern PatVertical = Pattern.compile(LcheekNoLeb+Leye+nose+Reye+RcheekNoLeb);
    private Pattern PatVerticalLeb = Pattern.compile(LcheekLeb+Leye+nose+Reye+RcheekLeb);

    @Override
    public void clear() {
        // Clear values for reusing the object
    }

    public EmotionMarkerAnalysis(int nDotPat,int nQueMarkPat,int nExclaMarkPat,int nYamokPat, int nFivePat,int nWordendPat){

        this.nWordendPat = nWordendPat;

        DotPat = Pattern.compile(String.format("[.]{%d,}", nDotPat));
        QueMarkPat = Pattern.compile(String.format("[?]{%d,}", nQueMarkPat));
        ExclaMarkPat = Pattern.compile(String.format("[!]{%d,}", nExclaMarkPat));
        YamokPat = Pattern.compile(String.format("[ๆ]{%d,}", nYamokPat));
        FivePat = Pattern.compile(String.format("[5]{%d,}", nFivePat));

        initVariables();
    }

    private void initVariables() {
        e[1] = Variable.getInstance("E1")
                .setDescriptionEN("Description in English")
                .setDescriptionTH("Description in Thai.");
        e[2] = Variable.getInstance("E2")
                .setDescriptionEN("Description in English")
                .setDescriptionTH("Description in Thai.");
        e[3] = Variable.getInstance("E3")
                .setDescriptionEN("Description in English")
                .setDescriptionTH("Description in Thai.");
        e[4] = Variable.getInstance("E4")
                .setDescriptionEN("Description in English")
                .setDescriptionTH("Description in Thai.");
        e[5] = Variable.getInstance("E5")
                .setDescriptionEN("Description in English")
                .setDescriptionTH("Description in Thai.");
        e[6] = Variable.getInstance("E6")
                .setDescriptionEN("Description in English")
                .setDescriptionTH("Description in Thai.");
        e[7] = Variable.getInstance("E7")
                .setDescriptionEN("Description in English")
                .setDescriptionTH("Description in Thai.");
        e[8] = Variable.getInstance("E8")
                .setDescriptionEN("Description in English")
                .setDescriptionTH("Description in Thai.");
        e[9] = Variable.getInstance("E9")
                .setDescriptionEN("Description in English")
                .setDescriptionTH("Description in Thai.");
        e[10] = Variable.getInstance("E10")
                .setDescriptionEN("Description in English")
                .setDescriptionTH("Description in Thai.");
    }

    @Override
    public void analyze(String input) {
        globalInput = input;
        e[1].addValue(Count(DotPat));
        e[2].addValue(Count(QueMarkPat));
        e[3].addValue(Count(ExclaMarkPat));
        e[4].addValue(Count(YamokPat));
        e[5].addValue(Count(FivePat));
        e[7].addValue(Count(PatWithNose));
        e[8].addValue(Count(PatNoNose));
        e[9].addValue(Count(PatVerticalLeb));
        e[10].addValue(Count(PatVertical));
        globalInput = null;
    }
    public void calWeightedValues(Variable P1, Variable P6, Variable P7, Variable P9, Variable C5, Variable W1, Variable C1) {
    //e[1].calFinalValue1(e[1], P1);
    e[1].calFinalValue1(e[1], C1);
    //e[2].calFinalValue1(e[2], P6);
    e[2].calFinalValue1(e[2], C1);
    //e[3].calFinalValue1(e[3], P7);
    e[3].calFinalValue1(e[3], C1);
    //e[4].calFinalValue1(e[4], P9);
    e[4].calFinalValue1(e[4], C1);
    //e[5].calFinalValue1(e[5], C5);
    e[5].calFinalValue1(e[5], C1);
    e[6].calFinalValue1(e[6], W1);
    e[7].calFinalValue1(e[7], C1);
    e[8].calFinalValue1(e[8], C1);
    e[9].calFinalValue1(e[9], C1);
    e[10].calFinalValue1(e[10], C1);
}

    private int Count(Pattern p) {
        int counter = 0;
        m = p.matcher(globalInput);
        while(m.find()) {
            counter++;
        }
        return counter;
    }

    public Variable[] getVariables() {
        return e;
    }


    public void DEBUG() {
        for(int i=1; i<=10; i++) {
              System.out.printf("E[%d]=" + e[i].getValue(), i);
            System.out.println();
            System.out.printf("Weighted E[%d]=" + e[i].getweightedValue(), i);
            System.out.println();
            System.out.println("---------------------------------------");
        }
    }

}