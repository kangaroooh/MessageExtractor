package message.extractor.graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;
import weka.core.Instances;

public class GraphData {
    
    final private Integer[] nValues = new Integer[8];
    final private File arffFile;
    final private HashMap<String, Double[]> HM = new HashMap<>();
    private Object[] keys;    
    
    public GraphData(File arffFile) {
        this.arffFile = arffFile;
        
        initObjects();
        loadDataFromArff();
        loadNValues();
    }
    
    public Object[] getKeys() {
        return keys;
    }
    
    private void loadDataFromArff() {
        try 
        {
            Instances data = new Instances(new FileReader(arffFile));
            System.out.println(data);


            if (data.classIndex() == -1) {
                data.setClassIndex(data.numAttributes() - 1);
            }

            // Load data into HashMap
            for (int i = 0; i < data.numInstances(); i++) {
                if (HM.isEmpty()) {
                    Double[] temp = new Double[51];
                    for (int o = 1; o <= 50; o++) {
                        temp[o] = new Double(0);
                        temp[o] += data.instance(i).value(o);
                    }
                    temp[0] = new Double(0);
                    temp[0]++;
                    HM.put(data.instance(i).stringValue(0), temp);
                } else {
                    Double[] temp = new Double[51];
                    for (int o = 1; o <= 50; o++) {
                        temp[o] = new Double(0);
                        temp[o] += data.instance(i).value(o);
                    }
                    temp[0] = new Double(0);

                    if (HM.containsKey(data.instance(i).stringValue(0))) {
                        temp[0] = HM.get(data.instance(i).stringValue(0))[0];
                        temp[0]++;
                        for (int o = 1; o <= 50; o++) {
                            temp[o] += HM.get(data.instance(i).stringValue(0))[o];
                        }
                    } else {
                        temp[0]++;
                    }
                    HM.put(data.instance(i).stringValue(0), temp);
                }
            }

            TreeSet<String> TS = new TreeSet<String>(HM.keySet());
            keys = TS.toArray();           

            // Find average values for ploting graph
            for(int i = 0; i < keys.length; i++) {
                for (int j = 1; j <= 50; j++) {
                    HM.get((String)keys[i])[j] /= HM.get((String)keys[i])[0];
                }
            }

            // Just display result
            Iterator<String> i = HM.keySet().iterator();
            while (i.hasNext()) {
                String key = i.next();
                for (int j = 0; j < 51; j++) {
                    System.out.println(HM.get(key)[j]);
                }
            }
          
        } catch (IOException e) { System.err.println(e); }
    } 
    
    private void initObjects() {
        for(Integer current: nValues) {
            current = new Integer(0);
        }
    }
    
    private void loadNValues() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(arffFile));
            String line;

            String[] nValuesTemp = new String[8];
            String[] splitedValues = new String[8];
            
            while ((line = reader.readLine()) != null) {
                // If this line is comment, then process
                if (line.startsWith("%")) {
                    line = line.substring(line.indexOf("-")).trim();
                    splitedValues = line.split(",");
                    break;
                }
            }
            
            // Process splitedValue
            for (int i = 0; i < splitedValues.length; i++) {
                nValuesTemp[i] = splitedValues[i].substring(splitedValues[i].indexOf("=")+1).trim();
            }

            // Save nValues from file to the class member as Integer
            for(int i=0; i<nValues.length; i++) {
                nValues[i] = Integer.parseInt(nValuesTemp[i]);
            }
        
        } catch (IOException e) {
            System.err.println(e);
        }
        
    }
    
    
    public Integer[] getNValues() {
        return nValues;
    }
    
    public Double getValueFromKeyAndCode(String key,String code) {
        Double[] variableValues = HM.get(key);
        return variableValues[Integer.parseInt(code.substring(1))];
    }    
    
}