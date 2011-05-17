package controllers;

import play.*;

import play.db.jpa.JPA;
import play.jobs.OnApplicationStart;
import play.mvc.*;

import java.io.IOException;
import java.util.*;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

import models.*;

public class Application extends Controller {

    public static void index(Integer id) {
    	
    	
    	EntityManager em = JPA.em();
    	List <Template> list_out = em.createQuery("from Template").getResultList();
    	
    	render(list_out);
    }
    
	public static void AddDatei(Upload upload) {
		System.out.println("Hellooo");
		
		try {
			System.out.println(upload.getContent());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		upload.save();
		index(0);

 }

}