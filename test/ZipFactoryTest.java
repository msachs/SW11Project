import static org.junit.Assert.*;

import java.io.*;
import java.util.*;
import java.util.zip.*;

import play.test.*;
import org.junit.*;
import models.*;

public class ZipFactoryTest extends UnitTest {
	static final int BUFFER = 2048;
	static final String ENCODING = "UTF-8";

	public ArrayList<String> DecompressZip(String zipfilepath)
			throws IOException {

		ArrayList<String> dec_out = new ArrayList<String>();
		BufferedOutputStream dest = null;
		FileInputStream fis = new FileInputStream(zipfilepath);
		ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
		ZipEntry entry;
		while ((entry = zis.getNextEntry()) != null) {
			System.out.println("Extracting: " + entry);
			int count;
			byte data[] = new byte[BUFFER];
			// write the files to the disk
			ByteArrayOutputStream fos = new ByteArrayOutputStream();
			dest = new BufferedOutputStream(fos, BUFFER);
			while ((count = zis.read(data, 0, BUFFER)) != -1) {
				dest.write(data, 0, count);
			}
			dest.flush();
			dest.close();
			dec_out.add(new String(fos.toByteArray(), ENCODING));
		}
		zis.close();

		return dec_out;
	}

	@Test
	public void testCall() {
		ArrayList<String> strings = new ArrayList<String>();
		strings.add("Testö!1");
		strings.add("Testö!2");

		try {
			ArrayList<String> output = DecompressZip(ZipFactory
					.Generate(strings));
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
