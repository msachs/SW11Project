package controllers;

import java.io.*;
import java.util.*;
import java.util.zip.*;

import play.*;
import play.mvc.Controller;

import models.Template;

import org.xml.sax.InputSource;
import Support.*;

public class ZipFactory extends Controller{
	static final int BUFFER = 2048;
	static final String ENCODING = "UTF-8";

	public static byte[] Generate(
			ArrayList<NamedString> named_strings, String filename, 
			String content_type, boolean test_mode) throws IOException {
		ByteArrayOutputStream dest = new ByteArrayOutputStream();
		ZipOutputStream out = new ZipOutputStream(
				new BufferedOutputStream(dest));

		for (int i = 0; i < named_strings.size(); i++) {

			ZipEntry entry = new ZipEntry(named_strings.get(i).FileName);
			out.putNextEntry(entry);

			InputStream origin = new ByteArrayInputStream(
					named_strings.get(i).Content
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
	
	
	public static ArrayList<NamedString> DecompressZip(byte[] zipfilepath)
			throws IOException {
		ArrayList<NamedString> dec_out = new ArrayList<NamedString>();
		BufferedOutputStream dest = null;
		ByteArrayInputStream fis = new ByteArrayInputStream(zipfilepath);
		ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
		ZipEntry entry;
		while ((entry = zis.getNextEntry()) != null) {
			System.out.println(entry.getName());
			int count;
			byte data[] = new byte[BUFFER];
			// write the files to the disk
			ByteArrayOutputStream fos = new ByteArrayOutputStream();
			dest = new BufferedOutputStream(fos, BUFFER);
			while ((count = zis.read(data, 0, BUFFER)) != -1) {
				dest.write(data, 0, count);
			}
			dest.flush();
			dest.close();
			dec_out.add(new NamedString(entry.getName(), 
					new String(fos.toByteArray(), ENCODING)));
		}
		zis.close();

		return dec_out;
	}
}
