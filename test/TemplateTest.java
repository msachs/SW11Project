import static org.junit.Assert.*;

import java.io.IOException;

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
    public void testReadFileAsString(){
		try 
		{
			Template no_file_template = Template.find("byName", "no-file").first();
			String tmp = no_file_template.getTemplate();
			fail("Should have gotten IOException: Bad filename");
		} 
		
		catch (java.io.IOException expected) 
		{
		}
    }
	
    @Test
    public void testCreateTemplate() {
        Template template = new Template("Name", "Filename", false);
        assertEquals(template.name, "Name");
        assertEquals(template.filename, "Filename");
        assertEquals(template.isMultifile(), false);
    }
    
    @Test
    public void testLoadTemplate(){
    	// save template into database
    	Template template = new Template("Name", "Filename",false);
    	template.save();
    	
    	// load the same template out of database
		Template database_template = Template.find("byName", "Name").first();
        assertEquals(template.name, database_template.name);
    }
    
    @Test
    public void testChangeTemplate(){
    	// save template into database
    	Template template = new Template("Name", "Filename",false);
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
    
    @Test
    public void testGetTemplate(){
    	// create template with actual file
    	Template template = new Template("Test", "simple.txt",false);

    	// save content using getTemplate()
    	String content = "";
		try 
		{
			content = template.getTemplate();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
        assertEquals(content, "<mindshare:content>Receiver Firstname</mindshare:content>");
    }
    
    @Test
    public void testGetTemplateMultifile(){
    	// create template with actual file
    	Template template = new Template("Test", "simple.zip", true);

    	// save content using getTemplate()
    	String content = "";
		try 
		{
			content = template.getTemplate();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
        assertEquals("<mindshare:content>Receiver Firstname</mindshare:content>?mindshare|fileend*simple.txt*?", content);
    }
    
    @Test
    public void testGetTemplateMultifileWith2(){
    	// create template with actual file
    	Template template = new Template("Test", "simple2.zip", true);

    	// save content using getTemplate()
    	String content = "";
		try 
		{
			content = template.getTemplate();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
	    assertEquals(content, "<mindshare:content>Receiver Firstname</mindshare:content>?mindshare|fileend*simple.txt*?" +
	    		              "<mindshare:content>Zweites</mindshare:content>?mindshare|fileend*simple2.txt*?");
    }
}
