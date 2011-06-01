package models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import javax.persistence.*;

import controllers.ZipFactory;

import play.data.validation.*;
import play.db.jpa.*;
import play.vfs.VirtualFile;
import Support.*;

@Entity
public class Template extends Model {
	@Lob
	@MaxSize(1000)
	@Required
	public String filename;

	@Required
	public String name;
	
	@Required
	private boolean multifile;

	public Template(String name, String filename, boolean multifile) {
		this.name = name;
		this.filename = filename;
		this.setMultifile(multifile);
	}

	// returns a string which contains the content of one or more files
	private String readFileAsString(String filePath)
			throws java.io.IOException 
	{
		VirtualFile vf = VirtualFile.fromRelativePath(filePath);
		File realFile = vf.getRealFile();

		if (multifile)
		{
			ArrayList<NamedString> zipoutput;
			String output = ""; // contains the content of more than one file
			FileInputStream zipfilestream = new FileInputStream(realFile);
			
			byte fileContent[] = new byte[(int)realFile.length()];

			zipfilestream.read(fileContent);

			// returns a list of NamedStrings which represents different files
			zipoutput = ZipFactory.DecompressZip(fileContent);
			
			// run over all files and add them to the output string
			for(int i=0; i < zipoutput.size();i++)
			{
				output += zipoutput.get(i).content;
				output += "?mindshare|fileend*" + zipoutput.get(i).filename + "*?";
			}
			return output;
		}
		
		StringBuffer fileData = new StringBuffer(1000);
		FileReader fr = new FileReader(realFile);
		BufferedReader reader = new BufferedReader(fr);

		char[] buf = new char[1024];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) 
		{
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
		reader.close();
		return fileData.toString(); // if there is just one file
	}

	// returns a template using our readFile function
	public String getTemplate() throws IOException 
	{
		String filepath = "/public/templates/" + filename;
		try 
		{
			return readFileAsString(filepath);
		} 
		catch (IOException exc) 
		{
			throw new IOException(exc);
		}
	}

	public void setMultifile(boolean multifile) {
		this.multifile = multifile;
	}

	public boolean isMultifile() {
		return multifile;
	}
}