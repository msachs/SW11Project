import static org.junit.Assert.*;
import play.test.*;
import org.junit.*;

import models.*;

public class DataTagTest extends UnitTest
{
	  @Test
	    public void testCreateDataTag() {
	        DataTag dataTag_Short = new DataTag (models.TagTyp.TEXT_SHORT , "Test-Description", "Test-Context");
	        assertEquals(dataTag_Short.getTag_type(), models.TagTyp.TEXT_SHORT);
	        assertEquals(dataTag_Short.getDescription(), "Test-Description");
	        assertEquals(dataTag_Short.getContent(), "Test-Context");
	  }	  
}
