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

@Entity
public class Template extends Model {
	@Lob
	@MaxSize(1000)
	@Required
	public String filename;

	@Required
	public String name;
	
	@Required
	private
	boolean multifile;

	public Template(String name, String filename, boolean multifile) {
		this.name = name;
		this.filename = filename;
		this.setMultifile(multifile);
	}

	private String readFileAsString(String filePath)
			throws java.io.IOException {
		
		if (multifile){
			VirtualFile vf = VirtualFile.fromRelativePath(filePath);
			File realFile = vf.getRealFile();
			ArrayList<String> zipoutput;
			String output = "";
			FileInputStream zipfilestream = new FileInputStream(realFile);
			
			byte fileContent[] = new byte[(int)realFile.length()];

			zipfilestream.read(fileContent);

			zipoutput = ZipFactory.DecompressZip(fileContent);
			
			for(int i=0; i < zipoutput.size();i++){
				output += zipoutput.get(i);
				output += "<mindshare:fileend>";
				//System.out.println(output);
			}
			return output;
		}
		
		StringBuffer fileData = new StringBuffer(1000);
		VirtualFile vf = VirtualFile.fromRelativePath(filePath);
		File realFile = vf.getRealFile();
		FileReader fr = new FileReader(realFile);
		BufferedReader reader = new BufferedReader(fr);

		char[] buf = new char[1024];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
		reader.close();
		return fileData.toString();
	}

	public String getTemplate() throws IOException {

		String filepath = "/public/templates/" + filename;
		try {
			return readFileAsString(filepath);

		} catch (IOException exc) {
			throw new IOException(exc);
		}
	}

	public String getFilename() {
		
		return this.filename;
	}

	public void setMultifile(boolean multifile) {
		this.multifile = multifile;
	}

	public boolean isMultifile() {
		return multifile;
	}
}