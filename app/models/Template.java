package models;

import java.util.*;
import javax.persistence.*;
 
import play.data.validation.*;
import play.db.jpa.*;


@Entity
public class Template extends Model {

	@Lob
	@MaxSize(1000)
	@Required
    public String filename;
    
	@Required
	public String name;
    
    public Template(String name, String filename) {
        this.name = name;
        this.filename = filename;
    }
    
}