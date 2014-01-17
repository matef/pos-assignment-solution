package test;

import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class ParserClass {

	MaxentTagger tagger;
	

	public ParserClass(MaxentTagger tagger){
		this.tagger = tagger;
	}
	
	/**
	 * main parsing method
	 * @param sample
	 */
	public void parse(String sample){
		String[] sampleArray = sample.split(" such as ",2);
		if(sampleArray.length>1){
			String y = getATokenInv(sampleArray[0]);
			List<String> x = getTokens(sampleArray[1]);
			for (int i = 0; i < x.size(); i++) {
				System.out.println(x.get(i) +" IS-A "+ y);
			}
			
			return;
		}
		
		sampleArray = sample.split(" or other ",2);
		if(sampleArray.length>1){
			String x = getATokenInv(sampleArray[0]);
			String y = getAToken(sampleArray[1]);
			
			System.out.println(x +" IS-A "+ y);
			return;
		}
		sampleArray = sample.split(" and other ",2);
		if(sampleArray.length>1){
			String x = getATokenInv(sampleArray[0]);
			String y = getAToken(sampleArray[1]);
			
			System.out.println(x +" IS-A "+ y);
			return;
		}
		
		sampleArray = sample.split(" including ",2);
		if(sampleArray.length>1){
			String y = getATokenInv(sampleArray[0]);
			String x = getAToken(sampleArray[1]);
			
			System.out.println(x +" IS-A "+ y);
			return;
		}
		
		sampleArray = sample.split(", especially ",2);
		if(sampleArray.length>1){
			String y = getATokenInv(sampleArray[0]);
			String x = getAToken(sampleArray[1]);
			
			System.out.println(x +" IS-A "+ y);
			return;
		}		
		System.out.println("no match found");
		
	}
	
	
	
	/**
	 * search for single token at the beginning of a text
	 * Token can contain more than 2 words like (adjective + noun)
	 * @param part
	 * @return
	 */
	public String getAToken(String part){
		String[] partArray = part.split(" ");
		String tagged = tagger.tagString(part);
		String[] taggedArray = tagged.split(" ");
		
		StringBuffer result = new StringBuffer();
		
		for (int i = 0; i < taggedArray.length;i++) {
			String word = partArray[i];
			String taggedWord = taggedArray[i];
			if(taggedWord.matches("(.)*_NN.{0,2}") || taggedWord.matches("(.)*_JJ.?")){
				String normalizedWord = normalizedWord(word, taggedWord);
				result.append(normalizedWord+" ");				
			}
			else{
				break;
			}
		}
		return result.toString();
	}
	
	/**
	 * search for single token at the end of a text
	 * Token can contain more than 2 words like (adjective + noun)
	 * @param part
	 * @return
	 */
	public String getATokenInv(String part){
		String[] partArray = part.split(" ");
		String tagged = tagger.tagString(part);
		String[] taggedArray = tagged.split(" ");
		
		StringBuffer result = new StringBuffer();
		
		for (int i = taggedArray.length-1; i >=0 ; i--) {
			String word = partArray[i];
			String taggedWord = taggedArray[i];
			if(taggedWord.matches("(.)*_NN.{0,2}") || taggedWord.matches("(.)*_JJ.?")){
				String normalizedWord = normalizedWord(word, taggedWord);
				result.insert(0,normalizedWord+" ");
			}
			else{
				
				break;
			}
		}
		return result.toString();
	}
	
	/**
	 * returns list of tokens for 'such as' case
	 * @param part
	 * @return
	 */
	public List<String> getTokens(String part){		
		List<String> result = new ArrayList<String>();
		String[] partArray = part.split("((,?\\s(and|or)\\s)|(,\\s))");
		for (int i = 0; i < partArray.length; i++) {
			result.add(getAToken(partArray[i]));
		}
		return result;
	}
	
	/**
	 * simple normalization algo
	 * @param word
	 * @param taggedWord
	 * @return
	 */
	public String normalizedWord(String word, String taggedWord){
		if(taggedWord.matches("(.)*_NNPS") ||taggedWord.matches("(.)*_NNS")){
			if(word.endsWith("ies"))
				return word.substring(0,word.length()-3);
			if(word.endsWith("ches")||word.endsWith("xes")||word.endsWith("xes"))
				return word.substring(0,word.length()-2);
			if(word.endsWith("s"))
				return word.substring(0,word.length()-1);			
		}
		return word;
	}
}
