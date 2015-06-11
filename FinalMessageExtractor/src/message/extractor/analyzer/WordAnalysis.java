package message.extractor.analyzer;

import java.util.*;


public class WordAnalysis implements Analyzer {

	private Variable[] W = new Variable[11];
	private HashMap<String, Integer> HM = new HashMap<String, Integer>();
	private TreeSet<String> TS;
	private Object [] keys;
	public Variable C1;
	public int nLess;
	public int nMore;

	public WordAnalysis(int nLess,int nMore) {
		this.nLess=nLess;
		this.nMore=nMore;
		initVariables();
	}

	@Override
	public void analyze (String input){
		String word = input.toLowerCase();
		Integer count = HM.get(word);
		if (count != null)
		{
			count++;
			HM.put(word, count);
		}
		else HM.put(word, 1);

	}

	@Override
	public void clear() {

	}

	public void calculate(Variable C1){
		this.C1=C1;
		TS = new TreeSet<String>( HM.keySet() );
		keys = TS.toArray();
		for (int k=0; k < keys.length; k++)
		{
			W[1].addValue(HM.get(keys[k]));
			
			if (keys[k].toString().length()>nMore) W[4].addValue();
			if (keys[k].toString().length()<nLess) W[5].addValue();
		}
		W[3].addValue(HM.size());
		W[2].setValue(C1.getValue()/HM.size());
		W[6].addValue(getYule());
		W[7].addValue(getSimp());
		W[8].addValue(getSichels());
		W[9].addValue(getHonore());
		W[10].addValue(getEntro());
	}
    public void calWeightedValues() {
     W[2].calFinalValue1(C1, W[1]);
     W[3].calFinalValue(W[1]);
     W[4].calFinalValue(W[1]);
     W[5].calFinalValue(W[1]);
 }
 public Variable getW1(){
        return W[1];
    }

    public int  getIoccur(int i){
        int count=0;
        for (int k=0; k < keys.length; k++)
        {
            if (HM.get(keys[k]) == i) count++;
        }
        return count;
    }

	private double getYule(){
		double power=Math.pow(10, 4);
		double sum=0,vi=0,check=0;
		for(int i=0;i<W[3].getValue();i++){
			vi=getIoccur(i+1);
			double keep = Math.pow(((i+1)/(W[1].getValue())), 2);
			sum=vi*keep+sum;
		}
		check=((-1)*(1/W[1].getValue()));
		 return power*(check+sum);
	}

	private double getSimp(){
		double vi=0,d=0;
		for (int i = 1; i <= W[3].getValue(); i++) {
			vi=getIoccur(i);
			d+=(double)vi*((i/W[1].getValue())*((i-1)/(W[1].getValue()-1)));
		}
		return d;
	}

	private double getSichels(){
		double eq = getIoccur(2);
		eq/=W[3].getValue();
		return eq;
	}

	private double getHonore(){
		double eq= 100*Math.log10(W[1].getValue());
		eq/=(1-getIoccur(1)/W[3].getValue());
		return eq;
	}

	private double getEntro(){
		double eq=0;
		for (int i = 1; i <= W[3].getValue(); i++) {
			eq+=getIoccur(i)*((-1)*Math.log10(i/W[1].getValue()))*(i/W[1].getValue());
		}
		return eq;
	}

	private void initVariables() {
		W[1] = Variable.getInstance("W1") //total word
				.setDescriptionEN("Description in English")
				.setDescriptionTH("Description in Thai.");
		W[2] = Variable.getInstance("W2") //mean length
				.setDescriptionEN("Description in English")
				.setDescriptionTH("Description in Thai.");
		W[3] = Variable.getInstance("W3") //unrepeat words
				.setDescriptionEN("Description in English")
				.setDescriptionTH("Description in Thai.");
		W[4] = Variable.getInstance("W4") //morethanN
				.setDescriptionEN("Description in English")
				.setDescriptionTH("Description in Thai.");
		W[5] = Variable.getInstance("W5") //lessthanN
				.setDescriptionEN("Description in English")
				.setDescriptionTH("Description in Thai.");        
		W[6] = Variable.getInstance("W6") //Yule
				.setDescriptionEN("Description in English")
				.setDescriptionTH("Description in Thai.");
		W[7] = Variable.getInstance("W7") //Simpson
				.setDescriptionEN("Description in English")
				.setDescriptionTH("Description in Thai.");
		W[8] = Variable.getInstance("W8") //Sichel's
				.setDescriptionEN("Description in English")
				.setDescriptionTH("Description in Thai.");
		W[9] = Variable.getInstance("W9") //Honore's R
				.setDescriptionEN("Description in English")
				.setDescriptionTH("Description in Thai.");
		W[10] = Variable.getInstance("W10") //Entropy
				.setDescriptionEN("Description in English")
				.setDescriptionTH("Description in Thai.");
	}

	public void DEBUG() {
		for(int i=1; i<=10; i++) {
                    System.out.printf("W[%d]=" + W[i].getValue(), i);
                    System.out.println();
                    System.out.printf("Weighted W[%d]=" + W[i].getweightedValue(), i);
                    System.out.println();
                    System.out.println("---------------------------------------");
		}
	}

    public Variable[] getVariables() {
        return W;
    }
}