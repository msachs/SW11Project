package Support;

// represents a relation of files and their content, this is required for multifile
public class NamedString {
	
	public String filename;
	public String content;
	
	public NamedString(String filename, String content)
	{
		this.filename = filename;
		this.content = content;
	}

}
