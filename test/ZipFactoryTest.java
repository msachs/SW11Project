import static org.junit.Assert.*;

import java.io.*;
import java.util.*;
import java.util.zip.*;

import play.test.*;
import org.junit.*;

import controllers.ZipFactory;

import models.*;

public class ZipFactoryTest extends UnitTest {
	@Test
	public void testCall() {
		ArrayList<String> strings = new ArrayList<String>();
		strings.add("Testö!1");
		strings.add("Testö!2");

		try {
			ArrayList<String> output = ZipFactory.DecompressZip(ZipFactory
					.Generate(strings,"Test.zip", "application/zip",true));
			for (int i = 0; i < output.size(); i++) {
				assertEquals(strings.get(i), output.get(i));
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
	}
}
