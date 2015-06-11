package message.extractor.analyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContentAnalysis implements Analyzer {

    // members
    private Variable[] t = new Variable[7];
    private String globalInput;

    private String inefficientWordSet;
    private String endFemaleWordSet;
    private String endMaleWordSet;
    private String shortenWordSet;
    private String sabotWordSet;
    private String slangWordSet;

    public ContentAnalysis() {
        initVariables();
        readWords();
    }

    private void initVariables() {
        t[1] = Variable.getInstance("T1")
                .setDescriptionEN("Description in English")
                .setDescriptionTH("Description in Thai.");
        t[2] = Variable.getInstance("T2")
                .setDescriptionEN("Description in English")
                .setDescriptionTH("Description in Thai.");
        t[3] = Variable.getInstance("T3")
                .setDescriptionEN("Description in English")
                .setDescriptionTH("Description in Thai.");
        t[4] = Variable.getInstance("T4")
                .setDescriptionEN("Description in English")
                .setDescriptionTH("Description in Thai.");
        t[5] = Variable.getInstance("T5")
                .setDescriptionEN("Description in English")
                .setDescriptionTH("Description in Thai.");
        t[6] = Variable.getInstance("T6")
                .setDescriptionEN("Description in English")
                .setDescriptionTH("Description in Thai.");
    }

    @Override
    public void analyze(String input) {
        globalInput = input;

        t[1].addValue(Count(inefficientWordSet)); // ฟุ่มเฟือย
        t[2].addValue(Count(endFemaleWordSet)); //ลงท้ายหญิง
        t[3].addValue(Count(endMaleWordSet));//ลงท้ายชาย
        t[4].addValue(Count(shortenWordSet)); //ลดรูป
        t[5].addValue(Count(sabotWordSet)); //สบถ
        t[6].addValue(Count(slangWordSet));//สแลง

        globalInput = null;
    }
   public void calWeightedValues(Variable W1){
     t[1].calFinalValue(W1);
     t[2].calFinalValue(W1);
     t[3].calFinalValue(W1);
     t[4].calFinalValue(W1);
     t[5].calFinalValue(W1);
     t[6].calFinalValue(W1);
     
 }
    private void readWords() {
        final File inefficientWordFile = new File("ฟุ่มเฟือย.txt");
        final File endFemaleWordFile = new File("ลงท้ายเพศหญิง.txt");
        final File endMaleWordFile = new File("ลงท้ายเพศชาย.txt");
        final File shortenWordFile = new File("คำลดรูป.txt");
        final File sabotWordFile = new File("คำสบถ.txt");
        final File slangWordFile = new File("คำสแลง.txt");

        inefficientWordSet = buildWordSet(inefficientWordFile);
        endFemaleWordSet = buildWordSet(endFemaleWordFile);
        endMaleWordSet = buildWordSet(endMaleWordFile);
        shortenWordSet = buildWordSet(shortenWordFile);
        sabotWordSet = buildWordSet(sabotWordFile);
        slangWordSet = buildWordSet(slangWordFile);

    }

    private String buildWordSet(File file) {
        StringBuilder sb = null;
        BufferedReader reader = null;
        String line = null;

        try {
            sb = new StringBuilder();
            reader = new BufferedReader(new FileReader(file));

            while ((line = reader.readLine()) != null) {
                sb.append("(");
                sb.append(line);
                sb.append(")");
                sb.append("|");
            }

            line = sb.toString().substring(0, sb.toString().length() - 1);

        } catch (IOException e) {
            System.out.println(e);
        }
        return line;
    }

    private int Count(String str) {
        Pattern p = Pattern.compile(String.format("%s", str));
        Matcher m = p.matcher(globalInput);
        int counter = 0;
        while (m.find()) {
            counter++;
        }
        return counter;
    }

    public void DEBUG() {
        for (int i = 1; i <= 5; i++) {
            System.out.printf("T[%d]="+t[i].getValue(),i);
            System.out.println();
            System.out.printf("Weighted T[%d]="+t[i].getweightedValue(),i);
            System.out.println();
            System.out.println("---------------------------------------");
        }
    }

    @Override
    public void clear() {

    }

    public Variable[] getVariables() {
        return t;
    }
}
