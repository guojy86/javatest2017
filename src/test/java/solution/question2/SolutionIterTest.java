package solution.question2;

import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class SolutionIterTest {
	
	File inputFile;
	
	@Before
	public void setup() {
		try {
			inputFile = File.createTempFile("tempFile", ".tmp");
			String [] contents = new String [] {"137", "-104", "2 58", "+0", "++3", "+1", 
					"23.9", "2000000000", "-0", "five", "-1"};
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(inputFile));
			for(String s : contents) {
				bw.write(s);
				bw.newLine();
			}
			bw.close();
			
		} catch (IOException e) {
			System.out.println("Error creating tmp file!");
		}
	}
	
	@Test
	public void iterShouldReturnOnlyIntegerFromFile() throws FileNotFoundException {
		Reader inp = new FileReader(inputFile);
		List<Integer> expectedList = new ArrayList<Integer>();
		expectedList.add(137);
		expectedList.add(-104);
		expectedList.add(0);
		expectedList.add(1);
		expectedList.add(0);
		expectedList.add(-1);
		
		List<Integer> actualList = new ArrayList<Integer>();
		
		for(Integer x : new SolutionIter(inp)) {
			actualList.add(x);
		}
		
		assertTrue(actualList.equals(expectedList));
	}

}
