/*
 * SRL.java
 *
 * Jan Rybak, 23/2/2013
 *
 * Part of Semantic Role Labeling tagger, plugin for GATE.
 *
 */

package gate.srl;

import java.net.MalformedURLException;
import java.net.URL;

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
	      String documentText = document.getContent().toString();

	    // process is done, nice!
	    fireProcessFinished();
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
		  return outputASName;
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
		  return srlServerUrlString;
	  }

	  /**
	   * Sets the URL of server where mate-tools are running. 
	   * @param url of server
	   */
	  public void setSrlServerUrlString(String srlServerUrlString) {
		  this.srlServerUrlString = srlServerUrlString.trim();
	  }
	

} // class SRL
