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
		String file_content;
		try {
			file_content = selected_template.getTemplate();
			ArrayList<DataTag> data_tags = new ArrayList<DataTag>();
	        data_tags = searchDataTags(file_content);
			System.out.println(data_tags.size());

			// filtere string nach auftreten von tags in ArrayList<DataTag>
	    	// gib string array an render für ausgabe
	    	render(id, data_tags);
		} catch (IOException e) {
			
			render(e.getMessage());
		}
    }
	
	public static void generateFile(Long id, ArrayList<String> data_tags){
		
		System.out.println("Generate");
		System.out.println(id);
		
		//System.out.println(data_tags.size());
		System.out.println(data_tags.size());
		System.out.println(data_tags.get(0));/*
	System.out.println(content_list.get(1));
		System.out.println(content_list.get(2));
		System.out.println(content_list.get(3));
		System.out.println(content_list.get(4));
		System.out.println(content_list.get(5));*/
		
		//datatags.content = data_tags.get(0);
		//DocumentGenerator("",data_tags);
	}


	
}