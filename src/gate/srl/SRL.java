/*
 * SRL.java
 *
 * Jan Rybak, 23/2/2013
 *
 * Part of Semantic Role Labeling tagger, plugin for GATE.
 *
 */

package gate.srl;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import conll2009.parser.Argument;
import conll2009.parser.IWord;
import conll2009.parser.Parser;
import conll2009.parser.Predicate;
import conll2009.parser.Sentence;

import gate.*;
import gate.creole.*;
import gate.creole.metadata.*;
import gate.util.*;




/** 
 * <p>Semantic Role Label annotator using mate-tools SRL tool 
 *  (https://code.google.com/p/mate-tools/). This plugin requires 
 *  mate tools running in the server mode.</p> 
 *  
 *  <p>This GATE Processing resource was written as a part of
 *  Jan Rybak's Master's thesis at the University of West Bohemia, 
 *  Czech Rep. and Norwegian University of Science and Technology.</p>
 * 
 *  @version 0.1  
 *  @author jendarybak@gmail.com
 *  @link http://github.com/jendarybak/GATE-SRL
 */
@CreoleResource(name = "Semantic Role Labeling", comment = "Mate-tools wrapper for GATE.")
public class SRL extends AbstractLanguageAnalyser
  implements ProcessingResource {
	  
	/** 
	 * First version of this plugin
	 */
	private static final long serialVersionUID = 1L;

	/** Code  */
	private static final String OUTPUT_LABEL = "SRL";
		  
	  
	/** The name of the annotation set used for input */
	private String outputASName;  

	/** Address of SRL server */
	private String srlServerUrlString;
	  
	/**
	 * Initializes this resource
	 * 
	 * @return Resource
	 * @throws ResourceInstantiationException
	 */
	public Resource init() throws ResourceInstantiationException {
		// DBpedia Spotlight URL is mandatory
	    if (srlServerUrlString == null || srlServerUrlString.length() == 0) {
	      throw new ResourceInstantiationException("SLR server URL is missing!");
	    }
	    
	    // test format of DS url
	    try {
	      new URL(srlServerUrlString);
	      
	    } catch(MalformedURLException e) {
	      throw new ResourceInstantiationException("Wrong URL format.");
	    }
	    
	    return this;
	}
	  
	/**
	 * Method is executed after the init() method has finished its
	 * execution. <BR>
	 * 
	 * @throws ExecutionException
	 */	  
	public void execute() {
		// init progress bar
		fireProgressChanged(0);

	    // If no document provided to process throw an exception
	    if (document == null) {
	      fireProcessFinished();
	      throw new GateRuntimeException("No document to process!");
	    }

	    // document in plain text
	    String sentence = document.getContent().toString();

	    SrlPOSTRequest request = new SrlPOSTRequest(srlServerUrlString);
	    String response = request.query(sentence);
	    
	    //System.out.println("RESPONSE:\n" + response);
	      
	    if (response == null) {
	    	fireProcessFinished();
	    	throw new GateRuntimeException("No result returned from DBpedia Spotlight!");
	    }
	    
	    Parser p = new Parser();
	    Sentence sent = p.parse(response);
	    sent.processPredArguments();
	    
	    // get the annotationSet name provided by the user, or otherwise use
	    //the default method
	    AnnotationSet outputAs = (outputASName == null || outputASName.length() == 0) 
	    		? document.getAnnotations() : document.getAnnotations(outputASName);

	    // iterate over all 'Resource' entities given by DBpedia Spotlight
	    // and annotate current document
	    for (Predicate pred : sent.getPredicates()) {
		    for (Argument arg : pred.getArguments()) {
		    	
	            String patternString = "";
	            // prepare regex pattern: ((word_a) (\s)* (word_b))
	            for (Iterator<IWord> i = arg.getWords().iterator(); i.hasNext();) {
	            	
	            	IWord word = (IWord) i.next();
	            	patternString += "(";
	            	patternString += "("+regexSafe(word.getForm())+")";
	            	if (i.hasNext()) { patternString +="(\\s)*"; }
	            	patternString +=")";
	            	
				}

	            // specify features
		    	FeatureMap fm = gate.Factory.newFeatureMap();
		    	// argument type
	            fm.put("APRED", arg.getArgType());
		    	// predicate surface form
	            fm.put("PRED", arg.getPredicateString());

	            
	            Pattern pattern = Pattern.compile(patternString);
	            Matcher matcher = pattern.matcher(sentence);
	            while (matcher.find()) {
	                // add feature
		            try {
			            
		            	// argument surface form
			            fm.put("string", matcher.group());
			            
						outputAs.add((long)matcher.start(), (long)matcher.end(), OUTPUT_LABEL, fm);
					} catch (InvalidOffsetException e) {
						e.printStackTrace();
					}
	            }	
			}			
		}
	    
	    // process is done, nice!
	    fireProcessFinished();
	}  
	
	/**
	 * Conversion of Regular expression special characters
	 * that may occur in the text to safe notation.
	 * 
	 * @param string to check for unsafe characters
	 * @return regex safe string
	 */
	public static String regexSafe(String aRegexFragment){
	    final StringBuilder result = new StringBuilder();

	    final StringCharacterIterator iterator = 
	      new StringCharacterIterator(aRegexFragment)
	    ;
	    char character =  iterator.current();
	    while (character != CharacterIterator.DONE ){
	      /*
	       All literals need to have backslashes doubled.
	      */
	      if (character == '.') {
	        result.append("\\.");
	      }
	      else if (character == '\\') {
	        result.append("\\\\");
	      }
	      else if (character == '?') {
	        result.append("\\?");
	      }
	      else if (character == '*') {
	        result.append("\\*");
	      }
	      else if (character == '+') {
	        result.append("\\+");
	      }
	      else if (character == '&') {
	        result.append("\\&");
	      }
	      else if (character == ':') {
	        result.append("\\:");
	      }
	      else if (character == '{') {
	        result.append("\\{");
	      }
	      else if (character == '}') {
	        result.append("\\}");
	      }
	      else if (character == '[') {
	        result.append("\\[");
	      }
	      else if (character == ']') {
	        result.append("\\]");
	      }
	      else if (character == '(') {
	        result.append("\\(");
	      }
	      else if (character == ')') {
	        result.append("\\)");
	      }
	      else if (character == '^') {
	        result.append("\\^");
	      }
	      else if (character == '$') {
	        result.append("\\$");
	      }
	      else {
	        //the char is not a special one
	        //add it to the result as is
	        result.append(character);
	      }
	      character = iterator.next();
	    }
	    return result.toString();
	  }	
	  
	  
	  
	/** 
	 * Getters and setters
	 * 
	 **/
	  
	  
	/**
	 * Returns the name of the AnnotationSet that has been provided to
	 * create the AnnotationSet
	 */
	public String getOutputASName() {
		return this.outputASName;
	}
	  
	/**
	 * Sets the AnnonationSet name, that is used to create the
	 * AnnotationSet
	 * 
	 * @param outputAS
	 */
	public void setOutputASName(String outputASName) {
		this.outputASName = outputASName;
	}

	/**
	 * Returns the URL of server where mate-tools are running. 
	 * @return url of server
	 */
	public String getSrlServerUrlString() {
		return this.srlServerUrlString;
	}

	/**
	 * Sets the URL of server where mate-tools are running. 
	 * @param url of server
	 */
	public void setSrlServerUrlString(String srlServerUrlString) {
		this.srlServerUrlString = srlServerUrlString.trim();
	}

} // class SRL
