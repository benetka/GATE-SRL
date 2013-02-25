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
	  


} // class SRL
