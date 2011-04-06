import static org.junit.Assert.*;
import play.test.*;
import org.junit.*;
import models.*;

public class ZipFactoryTest extends UnitTest {

    @Test
    public void testCall() {
        assertEquals(ZipFactory.Generate(null), "Test");
    }
}
