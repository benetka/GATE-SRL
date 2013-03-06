/*
 * Sentence.java
 *
 * Jan Rybak, 27/2/2013
 * www.rybak.in
 */
package conll2009.parser;

import java.util.ArrayList;
import java.util.Iterator;

/** 
 * <p>Class representing a sentence consisting of words.
 * Predicates are stored in a separate array list</p> 
 * 
 *  @version 0.1  
 *  @author jendarybak@gmail.com
 *  @link http://github.com/jendarybak/CoNLL2009_DataParser
 */
public class Sentence {
	
	/* List of words */
	private ArrayList<IWord> words;

	/* Row of data belonging to one word */
	private ArrayList<Predicate> predicates;
	
	
	/**
	 * Constructor - initialization of arraylists
	 * */
	public Sentence() {
		this.words = new ArrayList<IWord>();
		this.predicates = new ArrayList<Predicate>();
	}
	
	/**
	 * Word addition, if the word is predicate, saves it
	 * into the list of sentence predicates.
	 * 
	 * @param word in CoNLL 2009 format
	 * */
	public void addWord(Word2009 word) {
		this.words.add(word);
		
		// if the word is predicate -> list of preds
		if (word.isPred()) {
			predicates.add(new Predicate(word, predicates.size()));
		}
	}
	
	/**
	 * Goes through all argument slots for all words 
	 * and assignes arguments to their predicates.
	 * 
	 * */	
	public void processPredArguments() {
		for (int i = 0; i < predicates.size(); i++) {
			
			// go through all words of a sentence
			for (Iterator<IWord> j = words.iterator(); j.hasNext();) {
				Word2009 word = (Word2009) j.next();
				
				// if there is more predicates than word.ARG_COLUMNS
				// we can't process arguments (there is only space for 16 args)
				if (i == word.maxNumberOfArgs()-1) {
					continue;
				}
				
				// if argument of a word is not empty
				if (!word.getArgsArray()[i].equals("_")) {
					// create argument object
					Argument arg = new Argument(predicates.get(i), word.getArgsArray()[i]);

					// recursively add all the dependent words
					saveArgument(word, arg);
					
					// asign the argument to the predicate 
					predicates.get(i).addArgument(arg);
				}
			}
			
			//System.out.println(predicates.get(i).toString());
		}
	}
	
	/**
	 * Recursively saves dependent words of the argument. 
	 * 
	 * @param argHead First word (head) of the argument.
	 * @param arg Argument object
	 * */
	private void saveArgument(Word2009 argHead, Argument arg) {
		// save dependent word as a part of the argument
		arg.addWord(argHead);
		
		// goes through every word of a given sentence
		for (Iterator<IWord> i = words.iterator(); i.hasNext();) {
			Word2009 word = (Word2009) i.next();
			
			// if the word's id is equeal to other word's phead column,
			// than the word is dependent on the second one
			if (argHead.getId().equals(word.getPhead())) {
				saveArgument(word, arg);
			}
		}
	}
	
	/**
	 * Custom toString method.
	 * */
	public String toString() {
		String out = "";
		for (Iterator<IWord> i = words.iterator(); i.hasNext();) {
			out += i.next().toString();
		}
		return out;
	}
	

	/*
	 * Getters and setters
	 * */
		
	public ArrayList<IWord> getWords() {
		return words;
	}

	public void setWords(ArrayList<IWord> words) {
		this.words = words;
	}

	public ArrayList<Predicate> getPredicates() {
		return predicates;
	}

	public void setPredicates(ArrayList<Predicate> predicates) {
		this.predicates = predicates;
	}

	public int getNumOfPredicates(){
		return this.predicates.size();
	}
	

}
