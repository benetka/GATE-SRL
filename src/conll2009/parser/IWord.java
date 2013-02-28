package conll2009.parser;

public interface IWord {

	public String getId();
	public String getForm();
	public String getLemma();
	public String getPos();
	public IWord parseArray(String[] columns);
}
