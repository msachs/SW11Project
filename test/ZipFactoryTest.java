import static org.junit.Assert.*;

import java.io.*;
import java.util.*;
import java.util.zip.*;

import play.test.*;
import org.junit.*;

import controllers.ZipFactory;

import models.*;
import Support.*;

public class ZipFactoryTest extends UnitTest {
	@Test
	public void testCall() {
		ArrayList<NamedString> strings = new ArrayList<NamedString>();
		strings.add(new NamedString("Test1.txt", "Testö!1"));
		strings.add(new NamedString("Test2.txt", "Testö!2"));

		try {
			ArrayList<NamedString> output = ZipFactory.DecompressZip(ZipFactory
					.Generate(strings, "Test.zip", "application/zip", true));
			for (int i = 0; i < output.size(); i++) {
				assertEquals(strings.get(i).Content, output.get(i).Content);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
	}
}
