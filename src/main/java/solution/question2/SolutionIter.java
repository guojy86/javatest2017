package solution.question2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Q2 solution. Using regex to filter out invalid integer string
 * @author Jiayuan
 *
 */
public class SolutionIter implements Iterable<Integer> {

	private BufferedReader reader;
	private static final Pattern pattern = Pattern.compile("(-|\\+)?(0|1?[0-9]{0,9})");
	
	
	public SolutionIter(Reader inp) {
		this.reader = new BufferedReader(inp);
	}
	
	@Override
	public Iterator<Integer> iterator() {
		List<Integer> list = new ArrayList<Integer>();
		
		try {
			String line = null;
			while((line = reader.readLine()) != null) {
				if (pattern.matcher(line.trim()).matches()) {
					list.add(Integer.valueOf(line));
				}
			}
			
		} catch (IOException e) {
			System.out.println("Error reading file! " + e.getMessage());
			
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				System.out.println("Error closing reader! " + e.getMessage());
			}
		}
		
		
		return list.iterator();
	}
	
	

}
