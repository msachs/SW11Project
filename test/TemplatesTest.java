import java.util.ArrayList;

import models.DataTag;
import models.TagTyp;
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
	public void searchEmptyTagsTest(){
		Template no_file_template = Template.find("byName", "C.V.").first();
		String file_content = "";
		
		try
		{
		  file_content = no_file_template.getTemplate();
		}
		catch (java.io.IOException expected) 
		{
		}
		
		ArrayList<DataTag> data_tags = new ArrayList<DataTag>();
		data_tags = Templates.searchDataTags(file_content);
		assertEquals(17, data_tags.size());
	}

	@Test
	public void duplicateDataTagExisting(){
		// generate the same tag twice
		DataTag actual_tag = new DataTag(models.TagTyp.TEXT_SHORT, "Test", "Test");
		DataTag actual_tag_dup = new DataTag(models.TagTyp.TEXT_SHORT, "Test", "Test2");
		
		// generate data_tag list
		ArrayList<DataTag> data_tag_list = new ArrayList<DataTag>();
		data_tag_list.add(0, actual_tag_dup);
	
		assertEquals(Templates.checkDuplicateDataTags(actual_tag, data_tag_list), true);
	}
	
	@Test
	public void duplicateDataTagNotExisting(){
		// generate different tags
		DataTag actual_tag = new DataTag(models.TagTyp.TEXT_SHORT, "Test", "Test");
		DataTag actual_tag_dup = new DataTag(models.TagTyp.TEXT_SHORT, "Test2", "Test2");
		
		// generate data_tag list
		ArrayList<DataTag> data_tag_list = new ArrayList<DataTag>();
		data_tag_list.add(0, actual_tag_dup);
	
		assertEquals(Templates.checkDuplicateDataTags(actual_tag, data_tag_list), false);
	}
}
