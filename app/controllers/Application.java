package controllers;

import play.*;
import play.db.jpa.JPA;
import play.mvc.*;

import java.util.*;

import javax.persistence.EntityManager;

import models.*;

public class Application extends Controller {

    public static void index(Integer id) {

    	EntityManager em = JPA.em();

    	List<Template> list_out = em.createQuery("from Template").getResultList();
    	
    	render(list_out);
    }
    
    public static void AddDatei(){
    	
    }

}