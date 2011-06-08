import static org.junit.Assert.*;
import play.test.*;
import org.junit.*;

import models.*;

public class DataTagTest extends UnitTest
{
	  @Test
	    public void testCreateDataTagShort() {
	        DataTag dataTag_Short = new DataTag (models.TagTyp.TEXT_SHORT , "Test-Description", "Test-Context");
	        assertEquals(dataTag_Short.getTag_type(), models.TagTyp.TEXT_SHORT);
	        assertEquals(dataTag_Short.getDescription(), "Test-Description");
	        assertEquals(dataTag_Short.getContent(), "Test-Context");
	  }	  
	  
	  @Test
	    public void testCreateDataTagLong() {
	        DataTag dataTag_Short = new DataTag (models.TagTyp.TEXT_SHORT , "Test-Description", "Test-Context");
	        
	        // test setter
	        dataTag_Short.setTag_type(models.TagTyp.TEXT_LONG);
	        dataTag_Short.setDescription("Test-Long");
	        dataTag_Short.setContent("Test-Content-Long");
	        
	        assertEquals(dataTag_Short.getTag_type(), models.TagTyp.TEXT_LONG);
	        assertEquals(dataTag_Short.getDescription(), "Test-Long");
	        assertEquals(dataTag_Short.getContent(), "Test-Content-Long");
	  }	  
}
