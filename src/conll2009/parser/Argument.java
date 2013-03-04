/*
 * Argument.java
 *
 * Jan Rybak, 27/2/2013
 * www.rybak.in
 */
package conll2009.parser;

import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

/** 
 * <p>Class representing an argument of a predicate.</p> 
 * 
 *  @version 0.1  
 *  @author jendarybak@gmail.com
 *  @link http://github.com/jendarybak/CoNLL2009_DataParser
 */
public class Argument {
	
	/* List of words */
	private SortedSet<IWord> words;

	/* Row of data belonging to one word */
	private Predicate predicate;
	
	/* Type of the argument */
	private String argType;
	
	
	/**
	 * Constructor - with comperator by word id
	 * */
	public Argument(Predicate predicate, String argType) {
		this.words = new TreeSet<IWord>(new SortByWord_ID());
		this.argType = argType;
		this.predicate = predicate;
	}

	/**
	 * Addition of a word
	 * */
	public void addWord(IWord word) {
		this.words.add(word);
	}
	
	/**
	 * Comparator implementation which sorts IDataFormat objects on id field
	 */
	private class SortByWord_ID implements Comparator<IWord>{

	    public int compare(IWord w1, IWord w2) {
	        return Integer.parseInt(w1.getId()) - Integer.parseInt(w2.getId());
	    }
	}
	
	/**
	 * Custom toString method.
	 * */
	public String toString() {
		String out = "Type: " + argType +" | Form: ";
		for (Iterator<IWord> i = words.iterator(); i.hasNext();) {
			out += i.next().getForm() +" ";
		}
		out +="\n";
		return out;	
	}

	
	/*
	 * Getters and setters
	 * */
	
	public SortedSet<IWord> getWords() {
		return words;
	}

	public void setWords(SortedSet<IWord> words) {
		this.words = words;
	}

	public Predicate getPredicate() {
		return predicate;
	}

	public String getPredicateString() {
		return predicate.getPredWord().getForm();
	}
	
	public String getPredicateLemma() {
		return predicate.getPredWord().getLemma();
	}		

	public void setPredicate(Predicate predicate) {
		this.predicate = predicate;
	}

	public String getArgType() {
		return argType;
	}

	public void setArgType(String argType) {
		this.argType = argType;
	}

}
