package controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import models.DataTag;
import models.TagTyp;
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

	public static ArrayList<DataTag> searchDataTags(String file_content) {
		ArrayList<DataTag> data_tags = new ArrayList<DataTag>();

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

			// System.out.println(start_tag_type.length());

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
				// TODO: perform error handling for tagtype which does not exist
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

			/*
			 * System.out.println("start begin: " +offset_start_begin_tag);
			 * System.out.println("start end: " +offset_start_end_tag);
			 * System.out.println("close begin: " +offset_close_begin_tag);
			 * System.out.println("close end: " +offset_close_end_tag);
			 */
		}

		// file_content.substring(MINDSHARE_OPEN_TAG, ">");
		return data_tags;
	}

	public static void show(Long id) {

		Template selected_template = Template.find("byId", id).first();
		String file_content;
		String name = selected_template.name;
		try {
			file_content = selected_template.getTemplate();
			ArrayList<DataTag> data_tags = new ArrayList<DataTag>();
			data_tags = searchDataTags(file_content);

			// filtere string nach auftreten von tags in ArrayList<DataTag>
			// gib string array an render für ausgabe
			render(id, name, data_tags);
		} catch (IOException e) {

			render(e.getMessage());
		}
	}

	public static void generateFile(Long id, ArrayList<String> data_tags) {

		Template selected_template = Template.find("byId", id).first();
		String file_content;
		try {
			file_content = selected_template.getTemplate();
			ArrayList<DataTag> result_data_tags = new ArrayList<DataTag>();
			result_data_tags = searchDataTags(file_content);

			for (int count = 0; count < data_tags.size(); count++) {
				result_data_tags.get(count).setContent(data_tags.get(count));
			}

			DocumentGenerator doc_gen = new DocumentGenerator(file_content,
					result_data_tags);
			String result = doc_gen.getResult();

			if (selected_template.isMultifile()) {
				ArrayList<NamedString> named_strings = new ArrayList<NamedString>();
				int index = 0;
				while (result.indexOf("?mindshare|fileend", index) > -1) {
					int new_index = result
							.indexOf("?mindshare|fileend*", index);

					int name_index = result.indexOf("*", new_index) + 1;
					int name_end = result.indexOf("*", name_index);

					String name = result.substring(name_index, name_end);

					System.out.println("Name: " + name);

					named_strings.add(new NamedString(name, result.substring(
							index, new_index)));

					// To prevent endless loops
					new_index++;

					index = name_end + 2;

					System.out.println("Index: " + index + " Newindex: "
							+ new_index);
				}

				ZipFactory.Generate(named_strings, selected_template.filename,
						"application/zip", false);
			} else {
				InputStream file_stream = new ByteArrayInputStream(
						result.getBytes());
				response.setContentTypeIfNotSet("text/plain; charset=utf-8");
				renderBinary(file_stream, selected_template.getFilename());
			}
		} catch (IOException e) {

			render(e.getMessage());
		}

		// datatags.content = data_tags.get(0);
		// DocumentGenerator("",data_tags);
	}

	public static void download(Long id) {
		Template selected_template = Template.find("byId", id).first();
		String file_content;

		try {
			file_content = selected_template.getTemplate();
			InputStream file_stream = new ByteArrayInputStream(
					file_content.getBytes());

			response.setContentTypeIfNotSet("text/plain");
			renderBinary(file_stream, selected_template.getFilename());
		} catch (IOException e) {

			render(e.getMessage());
		}

	}

	private static boolean checkDuplicateDataTags(DataTag actual_tag,
			ArrayList<DataTag> data_tags) {
		for (int i = 0; i < data_tags.size(); i++) {
			if (actual_tag.getDescription().equals(
					data_tags.get(i).getDescription())) {
				return true;
			}
		}
		return false;
	}

}