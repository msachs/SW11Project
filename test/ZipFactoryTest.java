import static org.junit.Assert.*;

import java.io.*;
import java.util.*;
import java.util.zip.*;

import play.test.*;
import play.vfs.VirtualFile;

import org.junit.*;

import controllers.ZipFactory;

import models.*;
import Support.*;

public class ZipFactoryTest extends UnitTest {
	@Before
	public void setUp() {
	    Fixtures.deleteAll();
	    Fixtures.load("fixtures.yml");
	}
	
	@Test
	public void testCombinationOfAll() 
	{
		ArrayList<NamedString> strings = new ArrayList<NamedString>();
		strings.add(new NamedString("simple.txt", "<mindshare:content>Receiver Firstname</mindshare:content>"));
		strings.add(new NamedString("simple2.txt", "<mindshare:content>Zweites</mindshare:content>"));

		try 
		{
			ArrayList<NamedString> output = ZipFactory.DecompressZip(ZipFactory
					.Generate(strings, "Test.zip", "application/zip", true));
			for (int i = 0; i < output.size(); i++) 
			{
				assertEquals(strings.get(i).content, output.get(i).content);
			}

		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			assertTrue(false);
		}
	}
}
