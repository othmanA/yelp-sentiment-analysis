import java.io.*;
import java.util.*;
import org.apache.hadoop.io.IOUtils;

/**
 * @class DistributedCacheReadOnlyFiles
 * @description This class is going to handle a file that contains a word in
 *              each line We are going to fill these words into an ArrayList
 */
public class DistributedCacheReadOnlyFiles {

	private ArrayList<String> words = new ArrayList<String>();

	public DistributedCacheReadOnlyFiles(File file) throws IOException {
		initialize(file);
	}

	/**
	 * This method should handle filling the words into the arrayList most of
	 * the code is copied from Tom White's book.
	 * 
	 * @note We are converting the words to lower case
	 * 
	 * @param file
	 * @throws IOException
	 */
	private void initialize(File file) throws IOException {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(
					file)));
			String line;
			while ((line = in.readLine()) != null) {
				words.add(line.toLowerCase());
			}
		} finally {
			IOUtils.closeStream(in);
		}
	}

	/**
	 * return true if the word is an the array list
	 * 
	 * @param word
	 * @return
	 */
	public boolean contains(String word) {
		return words.contains(word);
	}

}
