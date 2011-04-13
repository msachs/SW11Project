import static org.junit.Assert.*;
import play.test.*;
import org.junit.*;
import org.junit.Before;

import play.mvc.*;
import controllers.*;
import models.*;

public class TemplateTest extends UnitTest {
	@Before
	public void setUp() {
	    Fixtures.deleteAll();
	    Fixtures.load("fixtures.yml");
	}

    @Test
    public void testCreateTemplate() {
        Template template = new Template("Name", "Filename");
        assertEquals(template.name, "Name");
        assertEquals(template.filename, "Filename");
    }
    
    @Test
    public void testLoadTemplate(){
    	// save template into database
    	Template template = new Template("Name", "Filename");
    	template.save();
    	
    	// load the same template out of database
		Template database_template = Template.find("byName", "Name").first();
        assertEquals(template.name, database_template.name);
    }
    
    @Test
    public void testChangeTemplate(){
    	// save template into database
    	Template template = new Template("Name", "Filename");
    	template.save();
    	
    	// load the same template out of database
		Template database_template = Template.find("byName", "Name").first();
		
		// change the filename and save it
		database_template.filename = "New_Filename";
		database_template.save();
		
    	// load the same template out of database
		Template new_database_template = Template.find("byName", "Name").first();
		
        assertEquals(new_database_template.filename, database_template.filename);
    }
    
}
