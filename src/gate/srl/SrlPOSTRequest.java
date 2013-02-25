/*
 * DsResource.java
 *
 * Jan Rybak, 23/1/2013
 *
 * Part of DBpedia Spotlight tagger, plugin for GATE.
 *
 */

package gate.srl;

import gate.util.GateRuntimeException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/** 
 * Requesting DBpedia Spotlight endpoint with GET method
 */
public class SrlPOSTRequest{
	
	/** Encoding */
	private final String CHARSET = "UTF-8";
	
	/** This class uses POST method, GET method is used by DsGETRequest */
    private final String METHOD = "POST";    
    
    /** This content type is necessary when using POST method. */
    private final String CONTENT_TYPE = "application/x-www-form-urlencoded";
    
	/** Return method (available: raw text, html, rdf) */
	private final String RETURN_METHOD = "RDF";    
    
    /**
     * Primarily we parse XML documents.<br /> 
     * 
     * Other possible formats (but not in this plugin yet):
     * <ul>
     * <li>text/html</li>
     * <li>application/xhtml+xml</li>
     * <li>application/json</li>
     * </ul>
     *  
     * */
    private final String ACCEPT = "text/xml";
    
    /** Default language */
    private final String LANGUAGE = "en-US";
    
    /** DBpedia Spotlight endpoints URL, 
     * for example: http://spotlight.dbpedia.org/rest/annotate/ */
    private URL srlUrl;

    /**
	 * Constructor method. 
	 */
    public SrlPOSTRequest(String srlServerUrlString) {
    	srlServerUrlString = srlServerUrlString.trim();
			
    	try {
			this.srlUrl = new URL(srlServerUrlString);
		
    	} catch (MalformedURLException e) {
    		e.printStackTrace();
    		throw new GateRuntimeException("Wrong URL format. \n");
    	}
    }
    
    /**
	 * Establishes connection with SRL server, sends request
	 * and returns String response.
	 * 
	 *  @param 	sentence			String of the text to annotate.
	 *  
	 *  @return	string			Annotated text.
	 */
    public String query(String sentence) {
	  	
		HttpURLConnection connection = null;
	
		try {
		    
			//Establish connection
			String urlParameters =
					"sentence=" + URLEncoder.encode(sentence, CHARSET) +
			        "&returnType=" + RETURN_METHOD;
	
	    	connection = (HttpURLConnection) srlUrl.openConnection();
		    connection.setRequestMethod(METHOD);
		    connection.setRequestProperty("Content-Type", CONTENT_TYPE);
		    connection.setRequestProperty("Accept", ACCEPT);
		    connection.setRequestProperty("Content-Length", "" + 
		               Integer.toString(urlParameters.getBytes().length));
		    connection.setRequestProperty("Content-Language", LANGUAGE);  
					
		    connection.setUseCaches (false);
		    connection.setDoInput(true);
		    connection.setDoOutput(true);

		    //Send request
		    DataOutputStream wr = new DataOutputStream (
		                  connection.getOutputStream ());
		    wr.writeBytes (urlParameters);
		    wr.flush ();
		    wr.close ();

		    //Get Response	
		    InputStream is = connection.getInputStream();
		    
		    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		    String line;
		    StringBuffer response = new StringBuffer(); 
		    
		    while((line = rd.readLine()) != null) {
		    	response.append(line);
		    	response.append('\r');
		    }
		    rd.close();
		    
		    // return response
		    return response.toString();
	
		} catch (Exception e) {
			e.printStackTrace();
			throw new GateRuntimeException("Error during communication with DBpedia Spotlight server!");
	
		} finally {
			if (connection != null) {
				connection.disconnect(); 
		    }
		}
    }
}