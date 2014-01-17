package test;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class MainClass {

	
	public static void main(String[] args) {
		String path = MainClass.class.getResource("../taggers/english-bidirectional-distsim.tagger").toString();
		System.out.println(path);
				
		MaxentTagger tagger = new MaxentTagger(path);
		
		
		//String sample = "people say that van and other cars are running on 4 weels";
		String sample = "to either i/o devices such as disk tape or optical drives or i/o cards";
		ParserClass parser = new ParserClass(tagger);
		parser.parse(sample);		
	}	
	
}
