package controllers;

import play.*;
import play.db.jpa.JPA;
import play.mvc.*;
import play.vfs.VirtualFile;

import java.util.*;
import java.io.*;
import java.lang.Object.*;

public class Download extends Controller {

	public static void download(ByteArrayOutputStream content, String filename, String content_type) {

		
		//System.out.println(content.length);
		InputStream file_stream = new ByteArrayInputStream(content.toByteArray());

		response.setContentTypeIfNotSet(content_type);
		renderBinary(file_stream, filename);
	}
}
