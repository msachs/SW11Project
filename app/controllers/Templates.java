package controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import models.DataTag;
import models.TagTyp;
import models.WordPosRep;
import models.Template;
import play.mvc.Controller;

import static org.junit.Assert.*;

import java.io.*;
import java.util.*;
import java.util.zip.*;

import play.test.*;
import org.junit.*;

import models.*;
import Support.*;

public class Templates extends Controller {

	// get all tags out of file and same them into a list
	public static ArrayList<DataTag> searchDataTags(String file_content) {
		ArrayList<DataTag> data_tags = new ArrayList<DataTag>();

		String check_word_schema = "xmlns:o=\"urn:schemas-microsoft-com:office:office\"";
		if (file_content.contains(check_word_schema)) {
			file_content = wordPreCompileToPlainText(file_content);
		}
		
		// init tags and offsets
		String MINDSHARE_BEGIN_TAG = "<mindshare:";
		String MINDSHARE_END_TAG = "</mindshare:";
		String MINDSHARE_CLOSE_TAG = ">";
		Integer offset_start_begin_tag = 0;
		Integer offset_start_end_tag = 0;
		Integer offset_close_begin_tag = 0;
		Integer offset_close_end_tag = 0;

		offset_start_begin_tag = file_content.indexOf(MINDSHARE_BEGIN_TAG,
				offset_start_end_tag);
		offset_start_end_tag = file_content.indexOf(MINDSHARE_CLOSE_TAG,
				offset_start_begin_tag);
		offset_close_begin_tag = file_content.indexOf(MINDSHARE_END_TAG,
				offset_close_end_tag);
		offset_close_end_tag = file_content.indexOf(MINDSHARE_CLOSE_TAG,
				offset_close_begin_tag);

		// run through the whole string and search for tags
		while (offset_start_begin_tag != -1
				&& (offset_close_begin_tag > offset_start_end_tag)) {
			String start_tag_type = file_content.substring(
					offset_start_begin_tag + MINDSHARE_BEGIN_TAG.length(),
					offset_start_end_tag);
			while (start_tag_type.length() <= 0) {

				offset_start_begin_tag = file_content.indexOf(
						MINDSHARE_BEGIN_TAG, offset_start_end_tag);
				offset_start_end_tag = file_content.indexOf(
						MINDSHARE_CLOSE_TAG, offset_start_begin_tag);
				start_tag_type = file_content.substring(offset_start_begin_tag
						+ MINDSHARE_BEGIN_TAG.length(), offset_start_end_tag);
			}

			String end_tag_type = file_content.substring(offset_close_begin_tag
					+ MINDSHARE_END_TAG.length(), offset_close_end_tag);
			String description = file_content.substring(
					offset_start_end_tag + 1, offset_close_begin_tag);


			// create tags and save them into an array list
			if ((start_tag_type.equals(end_tag_type))) {

				if (start_tag_type.equals("content")) {
					DataTag actual_tag = new DataTag(TagTyp.CONTENT,
							description);

					if (!checkDuplicateDataTags(actual_tag, data_tags)) {
						data_tags.add(actual_tag);
					}

				} else if (start_tag_type.equals("date")) {
					DataTag actual_tag = new DataTag(TagTyp.DATE, description);

					if (!checkDuplicateDataTags(actual_tag, data_tags)) {
						data_tags.add(actual_tag);
					}
				} else if (start_tag_type.equals("text_short")) {
					DataTag actual_tag = new DataTag(TagTyp.TEXT_SHORT,
							description);

					if (!checkDuplicateDataTags(actual_tag, data_tags)) {
						data_tags.add(actual_tag);
					}

				} else if (start_tag_type.equals("text_long")) {
					DataTag actual_tag = new DataTag(TagTyp.TEXT_LONG,
							description);

					if (!checkDuplicateDataTags(actual_tag, data_tags)) {
						data_tags.add(actual_tag);
					}
				}
			}

			// increment offset for tags
			offset_start_begin_tag = file_content.indexOf(MINDSHARE_BEGIN_TAG,
					offset_close_end_tag);
			offset_start_end_tag = file_content.indexOf(MINDSHARE_CLOSE_TAG,
					offset_start_begin_tag);
			offset_close_begin_tag = file_content.indexOf(MINDSHARE_END_TAG,
					offset_close_end_tag);
			offset_close_end_tag = file_content.indexOf(MINDSHARE_CLOSE_TAG,
					offset_close_begin_tag);
		}

		return data_tags;
	}

	//compile MindsharetTags in *.docx to plain-txt for parser
	private static String wordPreCompileToPlainText(String file_content) {
		
		boolean end = false;
		int main_offset = 0;
		ArrayList<WordPosRep> replace_list = new ArrayList<WordPosRep>();
		
		String docx_owt_tag = "<w:t>";
		String docx_cwt_tag = "</w:t>";
		String docx_lt_sign = "&lt;";
		String docx_gt_sign = "&gt;";
		String mindshare_tag = "mindshare:";
		String slash_sign = "/";
		
		int pos_wt;
		int pos_cwt;
		String cur_TagType = "";
		String cur_TagDesc = "";
		
		do {
			
			if (file_content.indexOf(mindshare_tag, main_offset) < 0) {
				end = true;
			} else {
				WordPosRep wpr1 = new WordPosRep(0,docx_lt_sign.length(),"");
				wpr1.setPosition(file_content.indexOf(docx_lt_sign, main_offset));
				pos_wt = file_content.indexOf(docx_owt_tag, main_offset);
				if(wpr1.getPosition() > pos_wt)
				{
					wpr1.addToPosition(-docx_owt_tag.length());
					wpr1.addToLenght(docx_owt_tag.length());
				}
				main_offset = wpr1.getPosition();
				WordPosRep wpr2 = new WordPosRep(0,mindshare_tag.length(),"");
				wpr2.setPosition(file_content.indexOf(mindshare_tag, main_offset));
				pos_cwt = file_content.indexOf(docx_cwt_tag, main_offset);
				
				if (wpr2.getPosition() > pos_cwt) {
					wpr1.addToLenght(docx_cwt_tag.length());
					wpr2.addToPosition(-docx_owt_tag.length());
					wpr2.addToLenght(docx_owt_tag.length());
				}
				
				replace_list.add(wpr1);
				
				main_offset = wpr2.getPosition();
				WordPosRep wpr3 = new WordPosRep(0,docx_gt_sign.length(),"");
				wpr3.setPosition(file_content.indexOf(docx_gt_sign, main_offset));
				pos_cwt = file_content.indexOf(docx_cwt_tag, main_offset);
				
				if (wpr3.getPosition() > pos_cwt) {
					cur_TagType = file_content.substring(wpr2.getPosition()+wpr2.getLength(), pos_cwt);
					wpr2.addToLenght(cur_TagType.length()+docx_cwt_tag.length());
					wpr3.addToPosition(-docx_owt_tag.length());
					wpr3.addToLenght(docx_owt_tag.length());
				} else {
					cur_TagType = file_content.substring(wpr2.getPosition()+wpr2.getLength(), wpr3.getPosition());
					wpr2.addToLenght(cur_TagType.length());
				}
				replace_list.add(wpr2);
				main_offset = wpr3.getPosition();
				WordPosRep wpr_TR = new WordPosRep();
				WordPosRep wpr_desc = new WordPosRep();
				pos_cwt = file_content.indexOf(docx_cwt_tag, main_offset);
				
				if (pos_cwt == wpr3.getPosition() + wpr3.getLength()) {
					wpr3.addToLenght(docx_cwt_tag.length());
					pos_wt = file_content.indexOf(docx_owt_tag, pos_cwt);
					main_offset = pos_wt;
					wpr_TR.setPosition(pos_wt);
					wpr_desc.setPosition(wpr_TR.getPosition()+docx_owt_tag.length());
					wpr_TR.setLength(docx_owt_tag.length());
				} else {
					wpr_TR.setPosition(wpr3.getPosition() + wpr3.getLength());
					wpr_desc.setPosition(wpr_TR.getPosition());
				}
				replace_list.add(wpr3);
				
				WordPosRep wpr4 = new WordPosRep(0,docx_lt_sign.length(),"");
				wpr4.setPosition(file_content.indexOf(docx_lt_sign, main_offset));
				pos_cwt = file_content.indexOf(docx_cwt_tag, main_offset);
				
				if(wpr4.getPosition() > pos_cwt)
				{
					cur_TagDesc = file_content.substring(wpr_desc.getPosition(), pos_cwt);
					wpr_TR.addToLenght(docx_cwt_tag.length());
					wpr4.addToPosition(-docx_owt_tag.length());
					wpr4.addToLenght(docx_owt_tag.length());
				}
				else
				{
					cur_TagDesc = file_content.substring(wpr_desc.getPosition(), wpr4.getPosition() );
				}
				
				wpr_TR.addToLenght(cur_TagDesc.length());
				wpr_TR.setReplacer(docx_owt_tag + "<" + mindshare_tag + cur_TagType + ">" + cur_TagDesc
									+ "<" + slash_sign + mindshare_tag + cur_TagType + ">" + docx_cwt_tag);
				
				replace_list.add(wpr_TR);
				
				main_offset = wpr4.getPosition();
				WordPosRep wpr5 = new WordPosRep(0,slash_sign.length(),"");
				wpr5.setPosition(file_content.indexOf(slash_sign, main_offset));
				pos_cwt = file_content.indexOf(docx_cwt_tag, main_offset);
				
				if (wpr5.getPosition() > pos_cwt) {
					wpr4.addToLenght(docx_cwt_tag.length());
					pos_wt = file_content.indexOf(docx_owt_tag, main_offset);
					wpr5.setPosition(file_content.indexOf(slash_sign, pos_wt));
					wpr5.addToPosition(-docx_owt_tag.length());
					wpr5.addToLenght(docx_owt_tag.length());
				}
				
				replace_list.add(wpr4);
				
				main_offset = wpr5.getPosition();
				WordPosRep wpr6 = new WordPosRep(0,mindshare_tag.length(),"");
				wpr6.setPosition(file_content.indexOf(mindshare_tag, main_offset));
				pos_cwt = file_content.indexOf(docx_cwt_tag, main_offset);
				
				if (wpr6.getPosition() > pos_cwt) {
					wpr5.addToLenght(docx_cwt_tag.length());
					wpr6.addToPosition(-docx_owt_tag.length());
					wpr6.addToLenght(docx_owt_tag.length());
				}
				
				replace_list.add(wpr5);
				
				main_offset = wpr6.getPosition();
				WordPosRep wpr7 = new WordPosRep(0,cur_TagType.length(),"");
				wpr7.setPosition(file_content.indexOf(cur_TagType, main_offset));
				pos_cwt = file_content.indexOf(docx_cwt_tag, main_offset);
				
				if (wpr7.getPosition() > pos_cwt) {
					wpr6.addToLenght(docx_cwt_tag.length());
					wpr7.addToPosition(-docx_owt_tag.length());
					wpr7.addToLenght(docx_owt_tag.length());
				}
				
				replace_list.add(wpr6);
				
				main_offset = wpr7.getPosition();
				WordPosRep wpr8 = new WordPosRep(0,docx_gt_sign.length(),"");
				wpr8.setPosition(file_content.indexOf(docx_gt_sign, main_offset));
				pos_cwt = file_content.indexOf(docx_cwt_tag, main_offset);
				
				if (wpr8.getPosition() > pos_cwt) {
					wpr7.addToLenght(docx_cwt_tag.length());
					wpr8.addToPosition(-docx_owt_tag.length());
					wpr8.addToLenght(docx_owt_tag.length());
				}
				
				replace_list.add(wpr7);
				
				main_offset = wpr8.getPosition();
				pos_cwt = file_content.indexOf(docx_cwt_tag, main_offset);
				
				if(pos_cwt == wpr8.getPosition() + wpr8.getLength())
					wpr8.addToLenght(docx_cwt_tag.length());
				
				main_offset = wpr8.getPosition() + wpr8.getLength();
				
				replace_list.add(wpr8);
			}
		}
		while (!end);
	
		main_offset = 0;
		
		WordPosRep temp_replacer;
		
		System.out.println("---------------------");
		for (int i = 0; i < replace_list.size(); i++) 
		{
			temp_replacer = replace_list.get(i);
			System.out.println(temp_replacer);
			file_content = replaceInString(file_content, temp_replacer, main_offset);
			main_offset += temp_replacer.getLength() - temp_replacer.getReplacer().length();
		}
		
		return file_content;
	}

	// call html page for specific template
	public static void show(Long id) {

		Template selected_template = Template.find("byId", id).first();
		String file_content;
		String name = selected_template.name;
		try 
		{
			file_content = selected_template.getTemplate();
			ArrayList<DataTag> data_tags = new ArrayList<DataTag>();
			data_tags = searchDataTags(file_content);
			render(id, name, data_tags);
		} 
		catch (IOException e) {

			render(e.getMessage());
		}
    }
	
	// generates a user uploaded file which has been filled
	public static void generateFileUpload(String content_template, ArrayList<String> data_tags){
		String file_content = content_template;

		// search data tags in user template
		ArrayList<DataTag> result_data_tags = new ArrayList<DataTag>();
		result_data_tags = searchDataTags(file_content);
		
		// fill data tags object with content of input fields
		for(int count = 0; count < data_tags.size(); count++)
		{
			result_data_tags.get(count).setContent(data_tags.get(count));
		}
		
		// generate output file
		DocumentGenerator doc_gen = new DocumentGenerator(file_content, result_data_tags);
		String result = doc_gen.getResult();
		
		// send user a output file with .txt extension
		InputStream file_stream = new ByteArrayInputStream(result.getBytes());
		response.setContentTypeIfNotSet("text/plain; charset=utf-8");
		renderBinary(file_stream, "own_template.txt");
	}
	
	// Determines which button has been pressed and calls the right method
	public static void submitDuplexer(Long id, ArrayList<String> data_tags, String submitType)
	{
		// generates a file
		if (submitType.equals("Generate"))
		{
			generateFile(id, data_tags);
		}
		// exports all filled input fields
		else if(submitType.equals("Export"))
		{
			exportInput(id, data_tags);
		}
		
	}
	
	// generates a .csv file with all input data from a given template
	public static void exportInput(Long id, ArrayList<String> data_tags){
		Template selected_template = Template.find("byId", id).first();
		String file_content;
		try 
		{
			file_content = selected_template.getTemplate();
			ArrayList<DataTag> result_data_tags = new ArrayList<DataTag>();
	        result_data_tags = searchDataTags(file_content);  
	        String content = "";
			
	        // generate a comma seperated file with input field and content
			for(int count = 0; count < data_tags.size(); count++)
			{
				content += result_data_tags.get(count).getDescription() + ";" +
					data_tags.get(count) + "\n";
			}
			
			// send user a output file with .csv extension
		    InputStream file_stream = new ByteArrayInputStream(content.getBytes());
			response.setContentTypeIfNotSet("text/plain; charset=utf-8");
			renderBinary(file_stream, "UserInput.csv"); 			
			
		} 
		catch (IOException e) 
		{
			render(e.getMessage());
		}
	}
	
	// generate a output file which represents a user filled template
	public static void generateFile(Long id, ArrayList<String> data_tags){

		Template selected_template = Template.find("byId", id).first();
		String file_content;
		try 
		{
			file_content = selected_template.getTemplate();
			ArrayList<DataTag> result_data_tags = new ArrayList<DataTag>();
			result_data_tags = searchDataTags(file_content);

			for (int count = 0; count < data_tags.size(); count++) 
			{
				result_data_tags.get(count).setContent(data_tags.get(count));
			}

			String check_word_schema = "xmlns:o=\"urn:schemas-microsoft-com:office:office\"";
			if (file_content.contains(check_word_schema)) {
				file_content = wordPreCompileToPlainText(file_content);
			}
			
			DocumentGenerator doc_gen = new DocumentGenerator(file_content,
					result_data_tags);
			String result = doc_gen.getResult();

			// check for multifile input
			if (selected_template.isMultifile()) 
			{
				ArrayList<NamedString> named_strings = new ArrayList<NamedString>();
				int index = 0;
				
				// split filestring into seperate files 
				while (result.indexOf("?mindshare|fileend", index) > -1) 
				{
					int new_index = result
							.indexOf("?mindshare|fileend*", index);

					int name_index = result.indexOf("*", new_index) + 1;
					int name_end = result.indexOf("*", name_index);

					String name = result.substring(name_index, name_end);

					named_strings.add(new NamedString(name, result.substring(
							index, new_index)));

					// To prevent endless loops
					new_index++;
					index = name_end + 2;
				}

				// generate zip-file out of all files
				ZipFactory.Generate(named_strings, selected_template.filename,
						"application/zip", false);
			} 
			else // just one file
			{
				InputStream file_stream = new ByteArrayInputStream(
						result.getBytes());
				response.setContentTypeIfNotSet("text/plain; charset=utf-8");
				renderBinary(file_stream, selected_template.filename);
			}
		} 
		catch (IOException e) 
		{
			render(e.getMessage());
		}
	}

	// just download an empty template
	public static void download(Long id) {
		Template selected_template = Template.find("byId", id).first();
		String file_content;

		try 
		{
			file_content = selected_template.getTemplate();
			InputStream file_stream = new ByteArrayInputStream(
					file_content.getBytes());

			response.setContentTypeIfNotSet("text/plain");
			renderBinary(file_stream, selected_template.filename);
		} 
		catch (IOException e) 
		{

			render(e.getMessage());
		}

	}

	// check if actual data tag already is in the data tags list
	private static boolean checkDuplicateDataTags(DataTag actual_tag,
			ArrayList<DataTag> data_tags) {
		for (int i = 0; i < data_tags.size(); i++) 
		{
			if (actual_tag.getDescription().equals(
					data_tags.get(i).getDescription())) 
			{
				return true;
			}
		}
		return false;
	}
	
	private static String replaceInString(String text, WordPosRep current_replace, int offset)
	{
		String first_part, second_part;
		
		first_part = text.substring(0, current_replace.getPosition()- offset);
		second_part = text.substring(current_replace.getPosition() + current_replace.getLength() - offset, text.length());
		text = first_part + current_replace.getReplacer() + second_part;
		
		return text;
	}
}
