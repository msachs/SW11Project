import static org.junit.Assert.*;

import java.util.ArrayList;

import play.test.*;
import org.junit.*;
import models.*;

public class DocumentGeneratorTest extends UnitTest{

	@Before
	public void setUp() {
	    Fixtures.deleteAll();
	    Fixtures.load("fixtures.yml");
	}

    @Test
    public void testGenerateDocument() {
        //Template template = new Template("Name", "Filename");
    	DataTag dataTag_Short = new DataTag (models.TagTyp.TEXT_SHORT , "blah input", "Juhu");
    	ArrayList <DataTag> tags = new ArrayList<DataTag>();
    	tags.add(dataTag_Short);
        DocumentGenerator generator = new DocumentGenerator("a<mindshare:content>blah input</mindshare:content> dshf", null);
        assertEquals("aJuhu dshf", generator.getResult());
        //assertEquals(template.filename, "Filename");
    }
    
    
}




