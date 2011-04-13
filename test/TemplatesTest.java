import java.util.ArrayList;

import models.DataTag;
import models.Template;

import org.junit.Before;
import org.junit.Test;

import controllers.Templates;
import play.test.Fixtures;
import play.test.UnitTest;


public class TemplatesTest extends UnitTest {
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
			Templates.readFileAsString("/public/templates/" + no_file_template.filename);
			fail("Should have gotten IOException: Bad filename");
		} 
		
		catch (java.io.IOException expected) 
		{
		}
    }
	
	@Test
	public void SearchEmptyTagsTest(){
		Template no_file_template = Template.find("byName", "C.V.").first();
		String file_content = "";
		
		try
		{
		  file_content =
           Templates.readFileAsString("/public/templates/" + no_file_template.filename);
		
		}
		catch (java.io.IOException expected) 
		{
		}
		
		ArrayList<DataTag> data_tags = new ArrayList<DataTag>();
		data_tags = Templates.searchDataTags(file_content);
		assertEquals(data_tags.size(), 0);
	}
	
	@Test
	public void SearchTagsSizeTest(){
		Template no_file_template = Template.find("byName", "C.V.").first();
		String file_content = "";
		
		try
		{
		  file_content =
           Templates.readFileAsString("/public/templates/" + no_file_template.filename);
		
		}
		catch (java.io.IOException expected) 
		{
		}
		
		ArrayList<DataTag> data_tags = new ArrayList<DataTag>();
		data_tags = Templates.searchDataTags(file_content);
		assertEquals(data_tags.size(), 4);
	}
}
