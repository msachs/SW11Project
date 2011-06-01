import java.util.ArrayList;

import models.DataTag;
import models.Template;

import org.junit.Before;
import org.junit.Test;

import Support.NamedString;

import controllers.Templates;
import play.test.Fixtures;
import play.test.UnitTest;

public class TestNamedString extends UnitTest{
	@Before
	public void setUp() {
	    Fixtures.deleteAll();
	    Fixtures.load("fixtures.yml");
	}
	
	@Test
	public void createNamedString(){
		NamedString named_string = new NamedString("filename", "content");
		assertEquals(named_string.filename, "filename");
		assertEquals(named_string.content, "content");
	}

}
