/*
 * Parser.java
 *
 * Jan Rybak, 27/2/2013
 * www.rybak.in
 */
package conll2009.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

/** 
 * <p>Basic parser for CoNLL 2009 text format (each word with it's features on new line, tab 
 * separated).</p> 
 *  
 *  <p>This parser was written as a part of Jan Rybak's Master's thesis 
 *  at the University of West Bohemia, Czech Rep. and Norwegian University 
 *  of Science and Technology.</p>
 * 
 *  @version 0.1  
 *  @author jendarybak@gmail.com
 *  @link http://github.com/jendarybak/CoNLL2009_DataParser
 */
public class Parser {
	
	/* If parsing multiple sentences */
	private Sentence sentence;

	public Parser() {
		this.sentence = new Sentence();		
	}
	
	/**
	 * Parses input String in CoNLL 2009 format and saves the result
	 * in Sentence object, where all words are represented by Word object
	 * and the structure between predicates and arguments is captured.
	 * 
	 * @param text to parse
	 * @return sentence
	 * */
	public Sentence parse(String input) {
		String[] columns;	/* Temporary array for each parameter of the word */
		String row;			/* One word in CoNLL format - text string */
		IWord data; 		/* One word in CoNLL format - object */		
		
		BufferedReader reader = new BufferedReader(new StringReader(input));
		
		try {
			while ((row = reader.readLine()) != null) {
				// devide columns by TAB symbol
				columns = row.trim().split("\t");
				
				// load data and parse CoNLL2009 format
				data = new Word2009();
				data = data.parseArray(columns);
				sentence.addWord((Word2009) data);
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return sentence;
	}
	
	/**
	 * Main - for testing purposes.
	 * */
	public static void main(String[] args) {
		// TESTING
		String str = "1	In	in	in	IN	IN	_	_	5	5	TMP	TMP	_	_	AM-TMP	_	_	_	_\n"
				+"2	October	october	october	NNP	NNP	_	_	1	1	PMOD	PMOD	_	_	_	_	_	_	_\n"
				+"3	President	president	president	NN	NN	_	_	4	4	TITLE	TITLE	_	_	_	_	_	_	_\n"
				+"4	Bush	bush	bush	NNP	NNP	_	_	5	5	SBJ	SBJ	_	_	A0	A0	_	A0	_\n"
				+"5	signed	sign	sign	VBD	VBD	_	_	0	0	ROOT	ROOT	Y	sign.02	_	_	_	_	_\n"
				+"6	the	the	the	DT	DT	_	_	9	9	NMOD	NMOD	_	_	_	_	_	_	_\n"
				+"7	Safe	safe	safe	JJ	JJ	_	_	9	9	NMOD	NMOD	_	_	_	_	_	_	_\n"
				+"8	Port	port	port	NN	NN	_	_	9	9	NMOD	NMOD	_	_	_	_	_	_	_\n"
				+"9	Act	act	act	NN	NN	_	_	5	5	OBJ	OBJ	_	_	A1	_	_	_	_\n"
				+"10	,	,	,	,	,	_	_	5	5	P	P	_	_	_	_	_	_	_\n"
				+"11	authorizing	authorize	authorize	VBG	VBG	_	_	5	5	ADV	ADV	Y	authorize.01	AM-ADV	_	_	_	_\n"
				+"12	an	an	an	DT	DT	_	_	13	13	NMOD	NMOD	_	_	_	_	_	_	_\n"
				+"13	investment	investment	investment	NN	NN	_	_	11	11	OBJ	OBJ	Y	investment.01	_	A1	_	A0	_\n"
				+"14	of	of	of	IN	IN	_	_	13	13	NMOD	NMOD	_	_	_	_	A1	_	_\n"
				+"15	$	$	$	$	$	_	_	14	14	PMOD	PMOD	_	_	_	_	_	_	_\n"
				+"16	6.7	6.7	6.7	CD	CD	_	_	15	15	DEP	DEP	_	_	_	_	_	_	_\n"
				+"17	billion	billion	billion	CD	CD	_	_	15	15	DEP	DEP	_	_	_	_	_	_	_\n"
				+"18	to	to	to	TO	TO	_	_	11	11	OPRD	OPRD	_	_	_	A1	_	_	_\n"
				+"19	tighten	tighten	tighten	VB	VB	_	_	18	18	IM	IM	Y	tighten.01	_	_	_	_	_\n"
				+"20	security	security	security	NN	NN	_	_	19	19	OBJ	OBJ	Y	security.02	_	_	_	A1	_\n"
				+"21	at	at	at	IN	IN	_	_	19	19	LOC	LOC	_	_	_	_	_	AM-LOC	_\n"
				+"22	American	american	american	JJ	JJ	_	_	23	23	NMOD	NMOD	_	_	_	_	_	_	_\n"
				+"23	ports	port	port	NNS	NNS	_	_	21	21	PMOD	PMOD	_	_	_	_	_	_	_\n"
				+"24	.	.	.	.	.	_	_	5	5	P	P	_	_	_	_	_	_	_";
		Parser p = new Parser();
		Sentence sent = p.parse(str);
		sent.processPredArguments();

		System.out.println(sent.toString());
	}

}
