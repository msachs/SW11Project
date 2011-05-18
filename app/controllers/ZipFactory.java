package controllers;

import java.io.*;
import java.util.*;
import java.util.zip.*;
import play.*;
import play.mvc.Controller;

import models.Template;

import org.xml.sax.InputSource;

public class ZipFactory extends Controller{
	static final int BUFFER = 2048;
	static final String ENCODING = "UTF-8";

	public static byte[] Generate(ArrayList<String> strings, String filename, 
													String content_type, boolean test_mode) throws IOException {
		ByteArrayOutputStream dest = new ByteArrayOutputStream();
		ZipOutputStream out = new ZipOutputStream(
				new BufferedOutputStream(dest));

		for (int i = 0; i < strings.size(); i++) {

			ZipEntry entry = new ZipEntry("test_document" + i + ".txt");
			out.putNextEntry(entry);

			InputStream origin = new ByteArrayInputStream(strings.get(i)
					.getBytes(ENCODING));
			byte data[] = new byte[BUFFER];
			int count;
			while ((count = origin.read(data, 0, BUFFER)) != -1) {
				out.write(data, 0, count);
			}
			origin.close();
		}
		out.close();
		
		InputStream file_stream = new ByteArrayInputStream(dest.toByteArray());
		response.setContentTypeIfNotSet(content_type);
		if (!test_mode){
			renderBinary(file_stream, filename);
		}
		
		
		return dest.toByteArray();
	}
}
