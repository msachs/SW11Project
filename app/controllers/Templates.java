package controllers;

import play.*;
import play.db.jpa.JPA;
import play.mvc.*;
import play.vfs.VirtualFile;

import java.util.*;
import java.io.*;
import java.lang.Object.*;

import javax.persistence.EntityManager;

import models.*;

public class Templates extends Controller {
    private static String readFileAsString(String filePath)
    throws java.io.IOException{
        StringBuffer fileData = new StringBuffer(1000);
        
        VirtualFile vf = VirtualFile.fromRelativePath(filePath);
        File realFile = vf.getRealFile();
        FileReader fr = new FileReader(realFile);
        
        BufferedReader reader = new BufferedReader(
                fr);
        

        
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
    
	public static void show(Long id) throws IOException {
		System.out.println(id);

		Template selected_template = Template.find("byId", id).first();
		String filepath = "/public/templates/" + selected_template.filename;
		String text = readFileAsString(filepath);
		
		//File template_file = new File(filepath);
		//String text = FileFunctions.readTextFile("C:\\temp\\xmas.html");
		
		System.out.println(text);

		
		//System.out.println(selected_template.filename);
    	// file suchen mit id und filename
    	// gib methode filename und suche alle strings
    	// gib string array an render für ausgabe
    	render(id);
    }

}