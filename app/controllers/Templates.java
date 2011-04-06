package controllers;

import play.*;
import play.db.jpa.JPA;
import play.mvc.*;

import java.util.*;

import javax.persistence.EntityManager;

import models.*;

public class Templates extends Controller {
	
	public static void show(Integer id) {
		//Template.find(query, params)
    	// file suchen mit id und filename
    	// gib methode filename und suche alle strings
    	// gib string array an render für ausgabe
    	render(id);
    }

}