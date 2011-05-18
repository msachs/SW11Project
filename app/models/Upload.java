package models;
 
import java.io.*;
import java.nio.channels.FileChannel;

import groovy.util.FileNameByRegexFinder;
import play.db.jpa.Blob;
import play.db.jpa.Model;
import play.vfs.VirtualFile;
 
import javax.persistence.Entity;
 
@Entity
public class Upload extends Model {
 
   public String name_ = "";
   public Blob file;
   
	public String getName() {
		return name_;
	}
	
	public String toString()
	{
		String temp = file.getFile().toString();
		int zahl_begin = temp.lastIndexOf("\\");
		int zahl_end = temp.length();
		
		String temp_file = temp.substring(zahl_begin, zahl_end);
		return "data\\attachments" + temp_file;
	}
	
	/*public void copyFile() throws IOException
	{
		
		String filePath = toString();
		VirtualFile vf = VirtualFile.fromRelativePath(filePath);
	    File sourceFile = vf.getRealFile();
	    
	    if (!sourceFile.exists()) {
			return;
			}
		
	    filePath = "public\\templates\\hallo.txt";
		VirtualFile vf2 = VirtualFile.fromRelativePath(filePath);
	    File destFile = vf2.getRealFile();
	    
	    
	    FileChannel source = null;
	    FileChannel destination = null;
	    source = new FileInputStream(sourceFile).getChannel();
		destination = new FileOutputStream(destFile).getChannel();
		if (destination != null && source != null) {
			destination.transferFrom(source, 0, source.size());
			}
		if (source != null) {
			source.close();
			}
		if (destination != null) {
			destination.close();
	}

	}*/
	
	
	public String getContent()throws java.io.IOException
	{
		String filePath = toString();
	    StringBuffer fileData = new StringBuffer(1000);
	    VirtualFile vf = VirtualFile.fromRelativePath(filePath);
	    File realFile = vf.getRealFile();
	    FileReader fr = new FileReader(realFile);
	    BufferedReader reader = new BufferedReader(fr);
	          
	    char[] buf = new char[1024];
	    int numRead=0;
	    while((numRead=reader.read(buf)) != -1){
	    	String readData = String.valueOf(buf, 0, numRead);
	    	fileData.append(readData);
	    	buf = new char[1024];
	      	}
	    reader.close();
	    return fileData.toString();
	}
}
