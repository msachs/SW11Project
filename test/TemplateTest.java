import static org.junit.Assert.*;
import play.test.*;
import org.junit.*;
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
    
    
}
