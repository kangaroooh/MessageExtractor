package message.extractor.arff;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import message.extractor.analyzer.Variable;


public class ArffFileWriter {

    private Instances data;
   
    private final FastVector attr = new FastVector();    
    private final int[] Wn = new int[2];
    private final int[] En = new int[6];
    
    private boolean thSentenceMode = true;
  
    public ArffFileWriter() {        
        initWriter();
    }
    
    public void setUserDefineN(int w4n,int w5n, int e1n,int e2n,int e3n,int e4n,int e5n,int e6n) {
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

    public void initWriter() {

        attr.addElement(new Attribute("author", (FastVector) null));

        attr.addElement(new Attribute("C1"));
        attr.addElement(new Attribute("C2"));
        attr.addElement(new Attribute("C3"));
        attr.addElement(new Attribute("C4"));
        attr.addElement(new Attribute("C5"));
        attr.addElement(new Attribute("C6"));
        attr.addElement(new Attribute("C7"));
        attr.addElement(new Attribute("C8"));
        attr.addElement(new Attribute("C9"));

        attr.addElement(new Attribute("W1"));
        attr.addElement(new Attribute("W2"));
        attr.addElement(new Attribute("W3"));
        attr.addElement(new Attribute("W4"));
        attr.addElement(new Attribute("W5"));
        attr.addElement(new Attribute("W6"));
        attr.addElement(new Attribute("W7"));
        attr.addElement(new Attribute("W8"));
        attr.addElement(new Attribute("W9"));
        attr.addElement(new Attribute("W10"));

        attr.addElement(new Attribute("P1"));
        attr.addElement(new Attribute("P2"));
        attr.addElement(new Attribute("P3"));
        attr.addElement(new Attribute("P4"));
        attr.addElement(new Attribute("P5"));
        attr.addElement(new Attribute("P6"));
        attr.addElement(new Attribute("P7"));
        attr.addElement(new Attribute("P8"));
        attr.addElement(new Attribute("P9"));
        attr.addElement(new Attribute("P10"));

        attr.addElement(new Attribute("E1"));
        attr.addElement(new Attribute("E2"));
        attr.addElement(new Attribute("E3"));
        attr.addElement(new Attribute("E4"));
        attr.addElement(new Attribute("E5"));
        attr.addElement(new Attribute("E6"));
        attr.addElement(new Attribute("E7"));
        attr.addElement(new Attribute("E8"));
        attr.addElement(new Attribute("E9"));
        attr.addElement(new Attribute("E10"));

        attr.addElement(new Attribute("S1"));
        attr.addElement(new Attribute("S2"));
        attr.addElement(new Attribute("S3"));
        attr.addElement(new Attribute("S4"));
        attr.addElement(new Attribute("S5"));

        attr.addElement(new Attribute("T1"));
        attr.addElement(new Attribute("T2"));
        attr.addElement(new Attribute("T3"));
        attr.addElement(new Attribute("T4"));
        attr.addElement(new Attribute("T5"));
        attr.addElement(new Attribute("T6"));

        data = new Instances("DieSoon", attr, 51);  
        
    }
    
    public void addNewWeightedRow(String name, Variable[] C, Variable[] W, Variable[] P, Variable[] E, Variable[] S, Variable[] T){
        Instance weightedInstance = new Instance(51);

        // Set value for each attribute
        weightedInstance.setValue((Attribute) attr.elementAt(0), name);

        weightedInstance.setValue((Attribute) attr.elementAt(1), C[1].getValue());
        weightedInstance.setValue((Attribute) attr.elementAt(2), C[2].getweightedValue());
        weightedInstance.setValue((Attribute) attr.elementAt(3), C[3].getweightedValue());
        weightedInstance.setValue((Attribute) attr.elementAt(4), C[4].getweightedValue());
        weightedInstance.setValue((Attribute) attr.elementAt(5), C[5].getweightedValue());
        weightedInstance.setValue((Attribute) attr.elementAt(6), C[6].getweightedValue());
        weightedInstance.setValue((Attribute) attr.elementAt(7), C[7].getweightedValue());
        weightedInstance.setValue((Attribute) attr.elementAt(8), C[8].getweightedValue());
        weightedInstance.setValue((Attribute) attr.elementAt(9), C[9].getweightedValue());

        weightedInstance.setValue((Attribute)attr.elementAt(10),W[1].getValue());
        weightedInstance.setValue((Attribute)attr.elementAt(11),W[2].getweightedValue());
        weightedInstance.setValue((Attribute)attr.elementAt(12),W[3].getweightedValue());
        weightedInstance.setValue((Attribute)attr.elementAt(13),W[4].getweightedValue());
        weightedInstance.setValue((Attribute)attr.elementAt(14),W[5].getweightedValue());
        weightedInstance.setValue((Attribute)attr.elementAt(15),W[6].getValue());
        weightedInstance.setValue((Attribute)attr.elementAt(16),W[7].getValue());
        weightedInstance.setValue((Attribute)attr.elementAt(17),W[8].getValue());
        weightedInstance.setValue((Attribute)attr.elementAt(18),W[9].getValue());
        weightedInstance.setValue((Attribute)attr.elementAt(19),W[10].getValue());

        weightedInstance.setValue((Attribute)attr.elementAt(20),P[1].getweightedValue());
        weightedInstance.setValue((Attribute)attr.elementAt(21),P[2].getweightedValue());
        weightedInstance.setValue((Attribute)attr.elementAt(22),P[3].getweightedValue());
        weightedInstance.setValue((Attribute)attr.elementAt(23),P[4].getweightedValue());
        weightedInstance.setValue((Attribute)attr.elementAt(24),P[5].getweightedValue());
        weightedInstance.setValue((Attribute)attr.elementAt(25),P[6].getweightedValue());
        weightedInstance.setValue((Attribute)attr.elementAt(26),P[7].getweightedValue());
        weightedInstance.setValue((Attribute)attr.elementAt(27),P[8].getweightedValue());
        weightedInstance.setValue((Attribute)attr.elementAt(28),P[9].getweightedValue());
        weightedInstance.setValue((Attribute)attr.elementAt(29),P[10].getValue());

        weightedInstance.setValue((Attribute)attr.elementAt(30),E[1].getweightedValue());
        weightedInstance.setValue((Attribute)attr.elementAt(31),E[2].getweightedValue());
        weightedInstance.setValue((Attribute)attr.elementAt(32),E[3].getweightedValue());
        weightedInstance.setValue((Attribute)attr.elementAt(33),E[4].getweightedValue());
        weightedInstance.setValue((Attribute)attr.elementAt(34),E[5].getweightedValue());
        weightedInstance.setValue((Attribute)attr.elementAt(35),E[6].getweightedValue());
        weightedInstance.setValue((Attribute)attr.elementAt(36),E[7].getweightedValue());
        weightedInstance.setValue((Attribute)attr.elementAt(37),E[8].getweightedValue());
        weightedInstance.setValue((Attribute)attr.elementAt(38),E[9].getweightedValue());
        weightedInstance.setValue((Attribute)attr.elementAt(39),E[10].getweightedValue());

        weightedInstance.setValue((Attribute)attr.elementAt(40),S[1].getValue());
        weightedInstance.setValue((Attribute)attr.elementAt(41),S[2].getweightedValue());
        weightedInstance.setValue((Attribute)attr.elementAt(42),S[3].getValue());
        weightedInstance.setValue((Attribute)attr.elementAt(43),S[4].getweightedValue());
        weightedInstance.setValue((Attribute)attr.elementAt(44),S[5].getweightedValue());

        weightedInstance.setValue((Attribute)attr.elementAt(45),T[1].getweightedValue());
        weightedInstance.setValue((Attribute)attr.elementAt(46),T[2].getweightedValue());
        weightedInstance.setValue((Attribute)attr.elementAt(47),T[3].getweightedValue());
        weightedInstance.setValue((Attribute)attr.elementAt(48),T[4].getweightedValue());
        weightedInstance.setValue((Attribute)attr.elementAt(49),T[5].getweightedValue());
        weightedInstance.setValue((Attribute)attr.elementAt(50),T[6].getweightedValue());

        // Add to instances
        data.add(weightedInstance);
    }  
    
    public void addNewRow(String name, Variable[] C, Variable[] W, Variable[] P, Variable[] E, Variable[] S, Variable[] T) {

        Instance instance = new Instance(51);

        // Set value for each attribute
        instance.setValue((Attribute) attr.elementAt(0), name);

        instance.setValue((Attribute) attr.elementAt(1), C[1].getValue());
        instance.setValue((Attribute) attr.elementAt(2), C[2].getValue());
        instance.setValue((Attribute) attr.elementAt(3), C[3].getValue());
        instance.setValue((Attribute) attr.elementAt(4), C[4].getValue());
        instance.setValue((Attribute) attr.elementAt(5), C[5].getValue());
        instance.setValue((Attribute) attr.elementAt(6), C[6].getValue());
        instance.setValue((Attribute) attr.elementAt(7), C[7].getValue());
        instance.setValue((Attribute) attr.elementAt(8), C[8].getValue());
        instance.setValue((Attribute) attr.elementAt(9), C[9].getValue());

        instance.setValue((Attribute)attr.elementAt(10),W[1].getValue());
        instance.setValue((Attribute)attr.elementAt(11),W[2].getValue());
        instance.setValue((Attribute)attr.elementAt(12),W[3].getValue());
        instance.setValue((Attribute)attr.elementAt(13),W[4].getValue());
        instance.setValue((Attribute)attr.elementAt(14),W[5].getValue());
        instance.setValue((Attribute)attr.elementAt(15),W[6].getValue());
        instance.setValue((Attribute)attr.elementAt(16),W[7].getValue());
        instance.setValue((Attribute)attr.elementAt(17),W[8].getValue());
        instance.setValue((Attribute)attr.elementAt(18),W[9].getValue());
        instance.setValue((Attribute)attr.elementAt(19),W[10].getValue());

        instance.setValue((Attribute)attr.elementAt(20),P[1].getValue());
        instance.setValue((Attribute)attr.elementAt(21),P[2].getValue());
        instance.setValue((Attribute)attr.elementAt(22),P[3].getValue());
        instance.setValue((Attribute)attr.elementAt(23),P[4].getValue());
        instance.setValue((Attribute)attr.elementAt(24),P[5].getValue());
        instance.setValue((Attribute)attr.elementAt(25),P[6].getValue());
        instance.setValue((Attribute)attr.elementAt(26),P[7].getValue());
        instance.setValue((Attribute)attr.elementAt(27),P[8].getValue());
        instance.setValue((Attribute)attr.elementAt(28),P[9].getValue());
        instance.setValue((Attribute)attr.elementAt(29),P[10].getValue());

        instance.setValue((Attribute)attr.elementAt(30),E[1].getValue());
        instance.setValue((Attribute)attr.elementAt(31),E[2].getValue());
        instance.setValue((Attribute)attr.elementAt(32),E[3].getValue());
        instance.setValue((Attribute)attr.elementAt(33),E[4].getValue());
        instance.setValue((Attribute)attr.elementAt(34),E[5].getValue());
        instance.setValue((Attribute)attr.elementAt(35),E[6].getValue());
        instance.setValue((Attribute)attr.elementAt(36),E[7].getValue());
        instance.setValue((Attribute)attr.elementAt(37),E[8].getValue());
        instance.setValue((Attribute)attr.elementAt(38),E[9].getValue());
        instance.setValue((Attribute)attr.elementAt(39),E[10].getValue());

        instance.setValue((Attribute)attr.elementAt(40),S[1].getValue());
        instance.setValue((Attribute)attr.elementAt(41),S[2].getValue());
        instance.setValue((Attribute)attr.elementAt(42),S[3].getValue());
        instance.setValue((Attribute)attr.elementAt(43),S[4].getValue());
        instance.setValue((Attribute)attr.elementAt(44),S[5].getValue());

        instance.setValue((Attribute)attr.elementAt(45),T[1].getValue());
        instance.setValue((Attribute)attr.elementAt(46),T[2].getValue());
        instance.setValue((Attribute)attr.elementAt(47),T[3].getValue());
        instance.setValue((Attribute)attr.elementAt(48),T[4].getValue());
        instance.setValue((Attribute)attr.elementAt(49),T[5].getValue());
        instance.setValue((Attribute)attr.elementAt(50),T[6].getValue());

        // Add to instances
        data.add(instance);
        
    }

    public void writeToFile(File arffFile) {

        boolean isDataReached = false;

        String dataInArffFormat = data.toString();

        StringBuilder finalOutput = new StringBuilder();

        BufferedReader reader = new BufferedReader(new StringReader(dataInArffFormat));
        String line;
        
        try {
            // Build final output up to @data
            while ((line = reader.readLine()) != null) {

                if (line.equals("@data")) {
                    // Add comments
                    finalOutput.append("%parameters - ");
                    finalOutput.append(String.format("W4=%d,", Wn[0]));
                    finalOutput.append(String.format("W5=%d,", Wn[1]));
                    finalOutput.append(String.format("E1=%d,", En[0]));
                    finalOutput.append(String.format("E2=%d,", En[1]));
                    finalOutput.append(String.format("E3=%d,", En[2]));
                    finalOutput.append(String.format("E4=%d,", En[3]));
                    finalOutput.append(String.format("E5=%d,", En[4]));
                    finalOutput.append(String.format("E6=%d", En[5]));
                    finalOutput.append("\n\n");

                    // Also appen @data
                    finalOutput.append(line);
                    finalOutput.append('\n');

                } else {                    
                    finalOutput.append(line);
                    finalOutput.append('\n');                    
                }
            } // End while

            // Write to file           
            // fix here!!!!!!!!!
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(arffFile),"UTF-8"));
            writer.write(finalOutput.toString());
            
            writer.close();
            
            data.delete(); //fix bug

        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public int getNumRecords() {
        return data.numInstances();
    }
}