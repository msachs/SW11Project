import org.junit.*;
import org.junit.Before;

import play.test.*;
import play.mvc.*;
import play.mvc.Http.*;
import models.*;

public class ApplicationTest extends FunctionalTest {
	@Before
	public void setUp() {
	    Fixtures.deleteAll();
	    Fixtures.load("fixtures.yml");
	}
	
    @Test
    public void testThatIndexPageWorks() {
        Response response = GET("/");
        assertStatus(200, response);
        assertContentType("text/html", response);
        assertCharset("utf-8", response);
    }
    
}