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
    
	public static String readFileAsString(String filePath)
      throws java.io.IOException{
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
    
	public static ArrayList<DataTag> searchDataTags(String file_content)
	{
		ArrayList<DataTag> data_tags = new ArrayList<DataTag>();
		
		// init tags and offsets
		String MINDSHARE_BEGIN_TAG = "<mindshare:";
	    String MINDSHARE_END_TAG = "</mindshare:";
	    String MINDSHARE_CLOSE_TAG = ">";
		Integer offset_start_begin_tag = 0;
		Integer offset_start_end_tag = 0;
		Integer offset_close_begin_tag = 0;
		Integer offset_close_end_tag = 0;
		
		offset_start_begin_tag = file_content.indexOf(MINDSHARE_BEGIN_TAG, offset_start_end_tag);
		offset_start_end_tag = file_content.indexOf(MINDSHARE_CLOSE_TAG, offset_start_begin_tag);
		offset_close_begin_tag = file_content.indexOf(MINDSHARE_END_TAG, offset_close_end_tag);
		offset_close_end_tag = file_content.indexOf(MINDSHARE_CLOSE_TAG, offset_close_begin_tag);

		// run through the whole string and search for tags
		while (offset_start_begin_tag != -1 && (offset_close_begin_tag > offset_start_end_tag))
		{
			String start_tag_type = file_content.substring(offset_start_begin_tag+MINDSHARE_BEGIN_TAG.length(), offset_start_end_tag);
			while(start_tag_type.length() <= 0)
			{
				offset_start_begin_tag = file_content.indexOf(MINDSHARE_BEGIN_TAG, offset_start_end_tag);
				offset_start_end_tag = file_content.indexOf(MINDSHARE_CLOSE_TAG, offset_start_begin_tag);
				start_tag_type = file_content.substring(offset_start_begin_tag+MINDSHARE_BEGIN_TAG.length(), offset_start_end_tag);
			}
			
			String end_tag_type = file_content.substring(offset_close_begin_tag+MINDSHARE_END_TAG.length(),offset_close_end_tag);
			String description = file_content.substring(offset_start_end_tag+1, offset_close_begin_tag);
			
			
			//System.out.println(start_tag_type.length());
			
			// create tags and save them into an array list
			if ((start_tag_type.equals(end_tag_type)))
			{	
				if (start_tag_type.equals("content"))
				{
					DataTag actual_tag = new DataTag(TagTyp.CONTENT, description);
					data_tags.add(actual_tag);	
				}
				else if(start_tag_type.equals("date"))
				{
					DataTag actual_tag = new DataTag(TagTyp.DATE, description);
					data_tags.add(actual_tag);	
				}
				else if(start_tag_type.equals("text_short"))
				{
					DataTag actual_tag = new DataTag(TagTyp.TEXT_SHORT, description);
					data_tags.add(actual_tag);	
				}
				else if(start_tag_type.equals("text_long"))
				{
					DataTag actual_tag = new DataTag(TagTyp.TEXT_LONG, description);
					data_tags.add(actual_tag);	
				}
				//TODO: perform error handling for tagtype which does not exist
			}
			
			// increment offset for tags
			offset_start_begin_tag = file_content.indexOf(MINDSHARE_BEGIN_TAG, offset_close_end_tag);
			offset_start_end_tag = file_content.indexOf(MINDSHARE_CLOSE_TAG, offset_start_begin_tag);
			offset_close_begin_tag = file_content.indexOf(MINDSHARE_END_TAG, offset_close_end_tag);
			offset_close_end_tag = file_content.indexOf(MINDSHARE_CLOSE_TAG, offset_close_begin_tag);

/*			System.out.println("start begin: " +offset_start_begin_tag);
			System.out.println("start end: " +offset_start_end_tag);
			System.out.println("close begin: " +offset_close_begin_tag);
			System.out.println("close end: " +offset_close_end_tag);*/	
		}
		  
	    //file_content.substring(MINDSHARE_OPEN_TAG, ">");			
		return data_tags;
	}
	
	public static void show(Long id) {
		System.out.println(id);

		Template selected_template = Template.find("byId", id).first();
		String filepath = "/public/templates/" + selected_template.filename;
		String file_content = "";
		try
		{
			file_content = readFileAsString(filepath);
		}
		catch(IOException exc)
		{
			String error = "IO-Error: Please contact your System-Admin";
			render(error);
		}
		
		ArrayList<DataTag> data_tags = new ArrayList<DataTag>();
        data_tags = searchDataTags(file_content);
		System.out.println(data_tags.size());

		// filtere string nach auftreten von tags in ArrayList<DataTag>
    	// gib string array an render für ausgabe
    	render(id, data_tags);
    }


	
}