package controllers;

import play.*;
import play.db.jpa.JPA;
import play.mvc.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import javax.persistence.EntityManager;

import models.*;

public class Application extends Controller {

    public static void index(Integer id) {

    	EntityManager em = JPA.em();

    	List<Template> list_out = em.createQuery("from Template").getResultList();
    	
    	render(list_out);
    }
    
    public static void AddDatei(Upload upload) {
		try {
			String content = upload.getContent();
			
			ArrayList<DataTag> data_tags = Templates.searchDataTags(content);
			upload.save();
			System.out.println("\nAfter Save!");
			render(content, data_tags);

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		index(0);

  }
}