package message.extractor.analyzer;

public class StructureAnalysis implements Analyzer {

    private Variable[] S = new Variable[6];
    private Variable C1;
    private String globalInput = "";
    private boolean thSentenceMode = true;

    // constructor
    public StructureAnalysis() {
        initVariables();
    }
    
    public StructureAnalysis(boolean stm) {
        initVariables();
        thSentenceMode = stm;      
    }
    
    @Override
    public void analyze(String input) {
        S[1].addValue();

        if(input.length() == 0)
            S[2].addValue();
        else
            //S[3].addValue(input.split("(\\s){1,}").length);
            globalInput = globalInput + input;
    }
    
    public void analyze() {
        if (thSentenceMode)
            S[3].addValue(globalInput.split("(\\s){1,}").length);   
        else
            S[3].addValue(globalInput.split("[!?.:;]+").length); 
    }

    public void calculate(Variable C1) {
        this.C1 = C1;
        S[4].setValue( C1.getValue() / (S[1].getValue()-S[2].getValue()) );
        S[5].setValue( C1.getValue() / S[3].getValue() );
    }
    
 public void calWeightedValues(Variable C1){
     S[2].calFinalValue(S[1]);
     S[4].calFinalValue2(C1, S[1], S[2]);
     S[5].calFinalValue1(C1, S[3]);
 }
    @Override
    public void clear() {

    }

    private void initVariables() {
        S[1] = Variable.getInstance("S1")
                .setDescriptionEN("ENG Des")
                .setDescriptionTH("THAI Des");
        S[2] = Variable.getInstance("S2")
                .setDescriptionEN("ENG Des")
                .setDescriptionTH("THAI Des");
        S[3] = Variable.getInstance("S3")
                .setDescriptionEN("ENG Des")
                .setDescriptionTH("THAI Des");
        S[4] = Variable.getInstance("S4")
                .setDescriptionEN("ENG Des")
                .setDescriptionTH("THAI Des");
        S[5] = Variable.getInstance("S5")
                .setDescriptionEN("ENG Des")
                .setDescriptionTH("THAI Des");
    }
  
    public Variable[] getVariables() {
       return S;
    }

    public void DEBUG() {
        for(int i=1; i<=5; i++) {
            System.out.printf("S[%d]=" + S[i].getValue(), i);
            System.out.println();
            System.out.printf("Weighted S[%d]=" + S[i].getweightedValue(), i);
            System.out.println();
            System.out.println("---------------------------------------");
        }
    }
}
