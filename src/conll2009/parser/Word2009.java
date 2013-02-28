/*
 * Word.java
 *
 * Jan Rybak, 27/2/2013
 * www.rybak.in
 */
package conll2009.parser;

import java.util.Arrays;

/** 
 * <p>Class representing a single word in a sentence, together
 * with all it's features described by CoNLL 2009 data format:
 * http://ufal.mff.cuni.cz/conll2009-st/task-description.html#Dataformat</p> 
 * 
 *  @version 0.1  
 *  @author jendarybak@gmail.com
 *  @link http://github.com/jendarybak/CoNLL2009_DataParser
 */
public class Word2009 implements IWord {
	/*
	Data format fields:
	ID, FORM, LEMMA, PLEMMA, POS, PPOS, FEAT, PFEAT, HEAD, PHEAD, DEPREL, PDEPREL,
	FILLPRED, PRED, APRED1 - APRED16
	*/
	
	/* Row of data belonging to one word */
	String dataRow[];

	/* Array of predicate arguments */
	String argsArray[];	
	
	/* Number of data columns (word features) */
	private final int COLUMNS = 30;

	/* Number of data columns (word features) */
	private final int ARG_COLUMNS = 16;
	
	/**
	 * Constructor, init of array and prefill with '_'
	 * 
	 * */
	public Word2009() {
		super();
		this.dataRow = new String[COLUMNS];
		Arrays.fill(this.dataRow, "_");
	}
	
	/**
	 * Constructor with all possible parameters
	 * 
	 * */
	public Word2009(String id, String form, String lemma, String plemma,
			String pos, String ppos, String feat, String pfeat, String head,
			String phead, String deprel, String pdeprel, String fillpred,
			String pred, String apred1, String apred2, String apred3, 
			String apred4, String apred5, String apred6, String apred7, String apred8, 
			String apred9, String apred10, String apred11, String apred12, String apred13, 
			String apred14, String apred15, String apred16) {
		super();
		this.dataRow[0] = id;
		this.dataRow[1] = form;
		this.dataRow[2] = lemma;
		this.dataRow[3] = plemma;
		this.dataRow[4] = pos;
		this.dataRow[5] = ppos;
		this.dataRow[6] = feat;
		this.dataRow[7] = pfeat;
		this.dataRow[8] = head;
		this.dataRow[9] = phead;
		this.dataRow[10] = deprel;
		this.dataRow[11] = pdeprel;
		this.dataRow[12] = fillpred;
		this.dataRow[13] = pred;
		
		this.dataRow[14] = apred1;
		this.dataRow[15] = apred2;
		this.dataRow[16] = apred3;
		this.dataRow[17] = apred4;
		this.dataRow[18] = apred5;
		this.dataRow[19] = apred6;
		this.dataRow[20] = apred7;
		this.dataRow[21] = apred8;
		this.dataRow[22] = apred9;
		this.dataRow[23] = apred10;
		this.dataRow[24] = apred11;
		this.dataRow[25] = apred12;
		this.dataRow[26] = apred13;
		this.dataRow[27] = apred14;
		this.dataRow[28] = apred15;
		this.dataRow[29] = apred16;
	}
	
	/**
	 * Parses one row of data (= one word).
	 * 
	 * @param array of word features
	 * */
	public Word2009 parseArray(String[] dataArray) {
		
		for (int i = 0; i < dataArray.length && i < dataRow.length; i++) {
			this.dataRow[i] = dataArray[i];
		}
		
		this.argsArray = Arrays.copyOfRange(this.dataRow, 
				COLUMNS-ARG_COLUMNS, COLUMNS-1);
		
		return this;
	}
		

	/**
	 * Getters & Setters 4 all variables
	 * 
	 * */
	
	/* Returns word position in the sentence */
	public String getId() {
		return this.dataRow[0];
	}

	public void setId(String id) {
		this.dataRow[0] = id;
	}
		
	/* Word's surface form (string) */	
	public String getForm() {
		return this.dataRow[1];
	}

	public void setForm(String form) {
		this.dataRow[1] = form;
	}
	
	/* Canonical form of the word */
	public String getLemma() {
		return this.dataRow[2];
	}

	public void setLemma(String lemma) {
		this.dataRow[2] = lemma;
	}

	/* Automatically pre-analyzed lemma */
	public String getPlemma() {
		return this.dataRow[3];
	}

	public void setPlemma(String plemma) {
		this.dataRow[3] = plemma;
	}

	/* Part of speech category */	
	public String getPos() {
		return this.dataRow[4];
	}

	public void setPos(String pos) {
		this.dataRow[4] = pos;
	}

	/* Automatically pre-analyzed 
	 * part of speech category */	
	public String getPpos() {
		return this.dataRow[5];
	}

	public void setPpos(String ppos) {
		this.dataRow[5] = ppos;
	}

	/* Morphological and lexical features */		
	public String getFeat() {
		return this.dataRow[6];
	}

	public void setFeat(String feat) {
		this.dataRow[6] = feat;
	}
	
	/* Automatically pre-analyzed 
	 * morphological and lexical features */
	public String getPfeat() {
		return this.dataRow[7];
	}

	public void setPfeat(String pfeat) {
		this.dataRow[7] = pfeat;
	}

	/* Head node for semantic role labeling */	
	public String getHead() {
		return this.dataRow[8];
	}

	public void setHead(String head) {
		this.dataRow[8] = head;
	}

	/* Automatically predicted head node */	
	public String getPhead() {
		return this.dataRow[9];
	}

	public void setPhead(String phead) {
		this.dataRow[9] = phead;
	}

	/* Dependency relation */		
	public String getDeprel() {
		return this.dataRow[10];
	}

	public void setDeprel(String deprel) {
		this.dataRow[10] = deprel;
	}

	/* Automatically inferred dependency relation */		
	public String getPdeprel() {
		return this.dataRow[11];
	}

	public void setPdeprel(String pdeprel) {
		this.dataRow[11] = pdeprel;
	}

	/* Contains Y for lines where 
	 * PRED is/should be filled */
	public String getFillpred() {
		return this.dataRow[12];
	}

	public void setFillpred(String fillpred) {
		this.dataRow[12] = fillpred;
	}

	/* Predicate */	
	public String getPred() {
		return this.dataRow[13];
	}

	public void setPred(String pred) {
		this.dataRow[13] = pred;
	}
	
	/* Returns true if this word is predicate*/
	public boolean isPred() {
		if (!getPred().equals("_") 
				&& !getPred().trim().equals("")){
			return true;
		}
		return false;
	}
	
	/* Array of PREDs' argument dependencies and labels */
	public String getApred_01() {
		return this.dataRow[14];
	}

	public void setApred_01(String apred) {
		this.dataRow[14] = apred;
	}	
	
	public String getApred_02() {
		return this.dataRow[15];
	}

	public void setApred_02(String apred) {
		this.dataRow[15] = apred;
	}	
	
	public String getApred_03() {
		return this.dataRow[16];
	}

	public void setApred_03(String apred) {
		this.dataRow[16] = apred;
	}	
	
	public String getApred_04() {
		return this.dataRow[17];
	}

	public void setApred_04(String apred) {
		this.dataRow[17] = apred;
	}	
	
	public String getApred_05() {
		return this.dataRow[18];
	}

	public void setApred_05(String apred) {
		this.dataRow[18] = apred;
	}	
	
	public String getApred_06() {
		return this.dataRow[19];
	}

	public void setApred_06(String apred) {
		this.dataRow[19] = apred;
	}	
	
	public String getApred_07() {
		return this.dataRow[20];
	}

	public void setApred_07(String apred) {
		this.dataRow[20] = apred;
	}	
	
	public String getApred_08() {
		return this.dataRow[21];
	}

	public void setApred_08(String apred) {
		this.dataRow[21] = apred;
	}	
	
	public String getApred_09() {
		return this.dataRow[22];
	}

	public void setApred_09(String apred) {
		this.dataRow[22] = apred;
	}	
	
	public String getApred_10() {
		return this.dataRow[23];
	}

	public void setApred_10(String apred) {
		this.dataRow[23] = apred;
	}	
	
	public String getApred_11() {
		return this.dataRow[24];
	}

	public void setApred_11(String apred) {
		this.dataRow[24] = apred;
	}	
	
	public String getApred_12() {
		return this.dataRow[25];
	}

	public void setApred_12(String apred) {
		this.dataRow[25] = apred;
	}	
	
	public String getApred_13() {
		return this.dataRow[26];
	}

	public void setApred_13(String apred) {
		this.dataRow[26] = apred;
	}	
	
	public String getApred_14() {
		return this.dataRow[27];
	}

	public void setApred_14(String apred) {
		this.dataRow[27] = apred;
	}	
	
	public String getApred_15() {
		return this.dataRow[28];
	}

	public void setApred_15(String apred) {
		this.dataRow[28] = apred;
	}	
	
	public String getApred_16() {
		return this.dataRow[29];
	}

	public void setApred_16(String apred) {
		this.dataRow[29] = apred;
	}

	public String[] getArgsArray(){
		return argsArray;
	}
	
	public int getFormatColumns() {
		return COLUMNS;
	}
	
	public String toString() {
		String out = "\n\nWORD:\n";
		out += "\nId:\t\t" + this.getId();
		out += "\nForm:\t\t" + this.getForm();
		out += "\nLemma:\t\t" + this.getLemma();
		out += "\nPLemma:\t\t" + this.getPlemma();
		out += "\nPOS:\t\t" + this.getPos();
		out += "\nPPOS:\t\t" + this.getPpos();
		out += "\nFeat:\t\t" + this.getFeat();
		out += "\nPFeat:\t\t" + this.getPfeat();
		out += "\nHead:\t\t" + this.getHead();
		out += "\nPHead:\t\t" + this.getPhead();
		out += "\nDeprel:\t\t" + this.getDeprel();
		out += "\nPDeprel:\t" + this.getPdeprel();
		out += "\nFillpred:\t" + this.getFillpred();
		out += "\nPred:\t\t" + this.getPred();
		
		if(!getApred_01().equals("_")) out += "\nApred 01:\t\t" + this.getApred_01();
		if(!getApred_02().equals("_")) out += "\nApred 02:\t\t" + this.getApred_02();
		if(!getApred_03().equals("_")) out += "\nApred 03:\t\t" + this.getApred_03();
		if(!getApred_04().equals("_")) out += "\nApred 04:\t\t" + this.getApred_04();
		if(!getApred_05().equals("_")) out += "\nApred 05:\t\t" + this.getApred_05();
		if(!getApred_06().equals("_")) out += "\nApred 06:\t\t" + this.getApred_06();
		if(!getApred_07().equals("_")) out += "\nApred 07:\t\t" + this.getApred_07();
		if(!getApred_08().equals("_")) out += "\nApred 08:\t\t" + this.getApred_08();
		if(!getApred_09().equals("_")) out += "\nApred 09:\t\t" + this.getApred_09();
		if(!getApred_10().equals("_")) out += "\nApred 10:\t\t" + this.getApred_10();
		if(!getApred_11().equals("_")) out += "\nApred 11:\t\t" + this.getApred_11();
		if(!getApred_12().equals("_")) out += "\nApred 12:\t\t" + this.getApred_12();
		if(!getApred_13().equals("_")) out += "\nApred 13:\t\t" + this.getApred_13();
		if(!getApred_14().equals("_")) out += "\nApred 14:\t\t" + this.getApred_14();
		if(!getApred_15().equals("_")) out += "\nApred 15:\t\t" + this.getApred_15();
		if(!getApred_16().equals("_")) out += "\nApred 16:\t\t" + this.getApred_16();
		
		
		
		return out;
	}
	
}
