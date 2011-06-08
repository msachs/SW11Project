package controllers;

import play.*;
import play.db.jpa.JPA;
import play.mvc.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import javax.persistence.EntityManager;

import models.*;

public class Application extends Controller {

	// generate index page
    public static void index(Integer id) {
    	EntityManager em = JPA.em();
    	List<Template> list_out = em.createQuery("from Template").getResultList();
    	render(list_out);
    }
    
    // upload own template using predefined play functionality
    public static void addDatei(Upload upload) {
		try 
		{
			String content = upload.getContent();
			ArrayList<DataTag> data_tags = Templates.searchDataTags(content);
			upload.save();
			render(content, data_tags);			
		} 
		catch (IOException ex) 
		{
			ex.printStackTrace();
		}
		
		index(0);
    }
    
    
}