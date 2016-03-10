package experimental_data;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * IO used by experiments
 * 
 * @author braemen
 * @version 1.0
 */
public class ExperimentIO {
	private final static Charset ENCODING = StandardCharsets.UTF_8;
	static boolean writeToFile(List<String> lines, String filename) {
		Path path = Paths.get(filename);
		try {
			Files.write(path, lines, ENCODING);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
}
