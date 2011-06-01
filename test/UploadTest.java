import org.junit.*;
import play.test.*;
import play.mvc.*;
import play.mvc.Http.*;
import models.*;

import java.io.*;

public class UploadTest extends FunctionalTest {
    
    @Test
    public void testFileName() {
    	Upload upload = new Upload();
    	assertEquals(upload.getName(), "");
    }
    
}