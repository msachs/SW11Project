package controllers;

import play.*;
import play.db.jpa.JPA;
import play.mvc.*;

import java.util.*;

import javax.persistence.EntityManager;

import models.*;

public class Templates extends Controller {

    public static void Show(Integer id) {
    	render(id);
    }

}