package controllers;

import play.*;
import play.db.jpa.JPA;
import play.mvc.*;

import java.util.*;

import javax.persistence.EntityManager;

import models.*;

public class Application extends Controller {

    public static void index() {
    	/*ArrayList<Template> list = new ArrayList<Template>();
    	list.add(new Template("C.V."));
    	list.add(new Template("Makefile"));
    	list.add(new Template("Letter (De)"));
    	list.add(new Template("Letter (En)"));
    	list.add(new Template("Shell"));
        
    	list.get(0).save();
    	list.get(1).save();
    	list.get(2).save();
    	list.get(3).save();
    	list.get(4).save();*/
    	
    	//ArrayList<Template> list_out = new ArrayList<Template>();

    	EntityManager em = JPA.em();
    	//em.persist(list_out);
    	List<Template> list_out = em.createQuery("from Template").getResultList();
    	
    	render(list_out);
    }

}