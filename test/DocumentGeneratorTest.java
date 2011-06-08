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
    public void testShortGenerateDocument() {
    	DataTag dataTag_Short = new DataTag (models.TagTyp.TEXT_SHORT , "blah input", "Juhu");
    	ArrayList <DataTag> tags = new ArrayList<DataTag>();
    	tags.add(dataTag_Short);
        DocumentGenerator generator = new DocumentGenerator("a<mindshare:content>blah input</mindshare:content> dshf", tags);
        assertEquals("aJuhu dshf", generator.getResult());
    }
    
    @Test
    public void testLongGenerateDocument() {
    	DataTag dataTag_first = new DataTag (models.TagTyp.TEXT_SHORT , "Vorname", "Hans");
    	DataTag dataTag_last = new DataTag (models.TagTyp.TEXT_SHORT , "Nachname", "Huber");
    	ArrayList <DataTag> tags = new ArrayList<DataTag>();
    	tags.add(dataTag_first);
    	tags.add(dataTag_last);
        DocumentGenerator generator = new DocumentGenerator("Lieber <mindshare:content>Vorname</mindshare:content>!! FYI: Dein ganzer Name lauetet <mindshare:content>Vorname</mindshare:content> <mindshare:content>Nachname</mindshare:content>.", tags);
        assertEquals("Lieber Hans!! FYI: Dein ganzer Name lauetet Hans Huber.", generator.getResult());
    }
    
    @Test
    public void testFalseTagGenerateDocument() {
    	DataTag dataTag_first = new DataTag (models.TagTyp.TEXT_SHORT , "Vorname", "Hans");
    	DataTag dataTag_last = new DataTag (models.TagTyp.TEXT_SHORT , "Nachname", "Huber");
    	ArrayList <DataTag> tags = new ArrayList<DataTag>();
    	tags.add(dataTag_first);
    	tags.add(dataTag_last);
        DocumentGenerator generator = new DocumentGenerator("Lieber <mindshare:content>Vorname</mindshare:content>!! FYI: Dein ganzer Name lauetet <mindshare:content>Vorname</mindshare:content <mindshare:content>Nachname</mindshare:content>.", tags);
        assertNotSame("Lieber Hans!! FYI: Dein ganzer Name lauetet Hans Huber.", generator.getResult());
    }
    
    
}




