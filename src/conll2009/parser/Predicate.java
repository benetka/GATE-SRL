/*
 * Predicate.java
 *
 * Jan Rybak, 27/2/2013
 * www.rybak.in
 */
package conll2009.parser;

import java.util.ArrayList;
import java.util.Iterator;

/** 
 * <p>Class representing a predicate of a sentence. Each predicate 
 * can have multiple arguments.</p> 
 * 
 *  @version 0.1  
 *  @author jendarybak@gmail.com
 *  @link http://github.com/jendarybak/CoNLL2009_DataParser
 */
public class Predicate {
	
	/* List of words */
	private ArrayList<Argument> arguments;
	
	/* Type of the argument */
	private IWord predWord;
	
	/* Number of the predicate in the sentece */
	private int orderInSentence;
	
	/**
	 * Constructor - with comperator by word id
	 * */
	public Predicate(IWord predWord, int order) {
		this.arguments = new ArrayList<Argument>();
		this.predWord = predWord;
		this.orderInSentence = order;
	}	
	
	/**
	 * Addition of argument to the list.
	 * 
	 * @param argument
	 * */
	public void addArgument(Argument argument) {
		arguments.add(argument);
	}	
	
	/**
	 * Custom toString method.
	 * 
	 * */
	public String toString() {
		String out = "\nPred:" + predWord.getForm();
		out +="\nArguments:\n";
		for (Iterator<Argument> i = arguments.iterator(); i.hasNext();) {
			out += i.next().toString();
		}
		return out;	
	}	
	
	/**
	 * Getters & Setters
	 * 
	 * */

	public ArrayList<Argument> getArguments() {
		return arguments;
	}

	public void setArguments(ArrayList<Argument> arguments) {
		this.arguments = arguments;
	}

	public IWord getPredWord() {
		return predWord;
	}

	public void setPredWord(IWord predWord) {
		this.predWord = predWord;
	}
	
	public int getOrderInSentence() {
		return orderInSentence;
	}

	public void setOrderInSentence(int orderInSentence) {
		this.orderInSentence = orderInSentence;
	}	
	

}
