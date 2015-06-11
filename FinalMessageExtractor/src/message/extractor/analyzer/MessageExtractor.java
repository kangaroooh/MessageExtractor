package message.extractor.analyzer;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.scene.control.TextArea;
import message.extractor.arff.ArffFileWriter;
import message.extractor.lexto.LongLexTo;

public class MessageExtractor {

    private ArffFileWriter arffFileWriter;
    private ArffFileWriter arffweightedFileWriter;
    private int msgExtractionCount = 0;
    private File workingFile;
    private int[] Wn = new int[2];
    private int[] En = new int[6];
    private TextArea resultArea;
    
    private String pendingAuthor = "?";
    private Analyzer[] pendingVars;
    private boolean thSentenceMode = true;

    public MessageExtractor(ArffFileWriter arffWriter, ArffFileWriter arffWriterWeighted) {
        this.arffFileWriter = arffWriter;
        this.arffweightedFileWriter = arffWriterWeighted;
    }

    public int getExtractionCount() {
        return msgExtractionCount;
    }

    public void setUserDefineN(int w4n, int w5n, int e1n, int e2n, int e3n, int e4n, int e5n, int e6n) {
        Wn[0] = w4n;
        Wn[1] = w5n;
        En[0] = e1n;
        En[1] = e2n;
        En[2] = e3n;
        En[3] = e4n;
        En[4] = e5n;
        En[5] = e6n;
    }
    
    public void setSentenceMode(boolean stm) {
        thSentenceMode = stm;
    }

    public void extract(String author, String msg) {

        msgExtractionCount++;

        CharacterAnalysis C = new CharacterAnalysis();
        ContentAnalysis T = new ContentAnalysis();
        EmotionMarkerAnalysis E = new EmotionMarkerAnalysis(En[0], En[1], En[2], En[3], En[4], En[5]);
        PunctuationAnalysis P = new PunctuationAnalysis();
        //StructureAnalysis S = new StructureAnalysis();
        StructureAnalysis S = new StructureAnalysis(thSentenceMode);
        WordAnalysis W = new WordAnalysis(Wn[0], Wn[1]);

        try {
            // Init LongLexTo
            LongLexTo tokenizer = new LongLexTo(new File("lexitron.txt"));
            // Read message from TextArea
            BufferedReader br = new BufferedReader(new StringReader(msg));

            int begin, end;
            String line;

            // Analyze inputs
            while ((line = br.readLine()) != null) {

                //line=line.trim();
                C.analyze(line);
                T.analyze(line);
                E.analyze(line);
                P.analyze(line);
                S.analyze(line);

                if (line.length() > 0) {

                    tokenizer.wordInstance(line);
                    begin = tokenizer.first();

                    while (tokenizer.hasNext()) {
                        end = tokenizer.next();
                        if ( !line.substring(begin, end).matches("\\s+") )
                            W.analyze(line.substring(begin, end)); // counting words, both thai and english
                        begin = end;
                    }

                }
            } // End read
            
            // Analyze the whole input
            S.analyze();
            
        } catch (IOException e) {
            System.err.println(e);
        }

        // Calculate
        S.calculate(C.getC1());
        W.calculate(C.getC1());
        C.calWeightedValues();
        W.calWeightedValues();
        P.calWeightedValues(C.getC1());
        E.calWeightedValues(P.getP1(), P.getP6(), P.getP7(), P.getP9(), C.getC5(), W.getW1(), C.getC1());
        S.calWeightedValues(C.getC1());
        T.calWeightedValues(W.getW1());

        C.DEBUG();
        W.DEBUG();
        T.DEBUG();
        E.DEBUG();
        P.DEBUG();
        S.DEBUG();

        // Show result
        StringBuilder sb = new StringBuilder();
        Variable[] cVars = C.getVariables();
        for (int i = 1; i < cVars.length; i++) {
            sb.append(cVars[i]);
            //sb.append(" ");
            sb.append("\n");
            
        }
        sb.append("\n\n");
        Variable[] wVars = W.getVariables();
        for (int i = 1; i < wVars.length; i++) {
            sb.append(wVars[i]);
            //sb.append(" ");
            sb.append("\n");
        }
        sb.append("\n\n");
        Variable[] tVars = T.getVariables();
        for (int i = 1; i < tVars.length; i++) {
            sb.append(tVars[i]);
            //sb.append(" ");
            sb.append("\n");
        }
        sb.append("\n\n");
        Variable[] eVars = E.getVariables();
        for (int i = 1; i < eVars.length; i++) {
            sb.append(eVars[i]);
            //sb.append(" ");
            sb.append("\n");
        }
        sb.append("\n\n");
        Variable[] pVars = P.getVariables();
        for (int i = 1; i < pVars.length; i++) {
            sb.append(pVars[i]);
            //sb.append(" ");
            sb.append("\n");
        }
        sb.append("\n\n");
        Variable[] sVars = S.getVariables();
        for (int i = 1; i < sVars.length; i++) {
            sb.append(sVars[i]);
            //sb.append(" ");
            sb.append("\n");
        }        

        resultArea.appendText(sb.toString());
        resultArea.appendText("---------------------------\n");
        
        //arffFileWriter.addNewRow(author, C.getVariables(), W.getVariables(), P.getVariables(), E.getVariables(), S.getVariables(), T.getVariables());
        //arffweightedFileWriter.addNewWeightedRow(author, C.getVariables(), W.getVariables(), P.getVariables(), E.getVariables(), S.getVariables(), T.getVariables());
        pendingAuthor = author;
        pendingVars = new Analyzer[6];
        pendingVars[0] = C;
        pendingVars[1] = W;
        pendingVars[2] = P;
        pendingVars[3] = E;
        pendingVars[4] = S;
        pendingVars[5] = T;
    }
    
    public int getNumRecords() {
        return arffFileWriter.getNumRecords();
    }

    public void saveArffRecord() {
 
        if (pendingVars != null) {
            CharacterAnalysis     C = (CharacterAnalysis)pendingVars[0];
            WordAnalysis          W = (WordAnalysis)pendingVars[1]; 
            PunctuationAnalysis   P = (PunctuationAnalysis)pendingVars[2];
            EmotionMarkerAnalysis E = (EmotionMarkerAnalysis)pendingVars[3];
            StructureAnalysis     S = (StructureAnalysis)pendingVars[4];
            ContentAnalysis       T = (ContentAnalysis)pendingVars[5];
        
            arffFileWriter.addNewRow(pendingAuthor, C.getVariables(), W.getVariables(), P.getVariables(), E.getVariables(), S.getVariables(), T.getVariables());
            arffweightedFileWriter.addNewWeightedRow(pendingAuthor, C.getVariables(), W.getVariables(), P.getVariables(), E.getVariables(), S.getVariables(), T.getVariables()); 
            pendingAuthor = "?";
            pendingVars = null;
        }
    }
    
    public File getFile() {
        return workingFile;
    }

    public void close(String fileName, String weightedFileName) {
        // Ask for filename and location to be saved
        SimpleDateFormat dt = new SimpleDateFormat("yyyyy-mm-dd hh-mm-ss");
        if (fileName == null) {
            fileName = dt.format(new Date());
        }
        if (weightedFileName == null) {
            weightedFileName = dt.format(new Date());
        }
        File targetFile = new File(fileName + ".arff");
        File weightedtargetFile = new File(weightedFileName + ".arff");

        workingFile = targetFile;
        arffFileWriter.writeToFile(workingFile);
        arffweightedFileWriter.writeToFile(weightedtargetFile);
    }

    public void setResultTextArea(TextArea textArea) {
        this.resultArea = textArea;
    }
}
