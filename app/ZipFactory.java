import java.io.*;
import java.util.*;
import java.util.zip.*;

import org.xml.sax.InputSource;

public class ZipFactory {
	static final int BUFFER = 2048;
	static final String ENCODING = "UTF-8";

	public static String Generate(ArrayList<String> strings) throws IOException {
		// PipedOutputStream dest = new PipedOutputStream();
		FileOutputStream dest = new FileOutputStream(
				"c:\\play\\mindshare\\public\\test.zip");
		ZipOutputStream out = new ZipOutputStream(
				new BufferedOutputStream(dest));

		for (int i = 0; i < strings.size(); i++) {

			ZipEntry entry = new ZipEntry("test_document" + i + ".txt");
			out.putNextEntry(entry);

			InputStream origin = new ByteArrayInputStream(strings.get(i)
					.getBytes(ENCODING));
			byte data[] = new byte[BUFFER];
			int count;
			while ((count = origin.read(data, 0, BUFFER)) != -1) {
				out.write(data, 0, count);
			}
			origin.close();
		}

		out.close();

		return "c:\\play\\mindshare\\test.zip";
	}
}
