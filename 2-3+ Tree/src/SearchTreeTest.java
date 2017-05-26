import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import student.TestCase;

/**
 * @author Randy Liang (randy8)
 * @author Patrick McKee (bludevil)
 * @version October 03, 2016
 */

public class SearchTreeTest extends TestCase {
    /**
     * Sets up the tests that follow. In general, used for initialization.
     */
    public void setUp() {
        // Nothing Here
    }

    /**
     * Method to read file
     * 
     * @param path
     *            of what you want to read
     * @return the history of a file
     * @throws IOException
     */
    static String readFile(String path) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded);
    }

    /**
     * Get code coverage of the class declaration.
     * 
     * @throws Exception
     */
    public void testMInit() throws Exception {
        ///*
        SearchTree sT = new SearchTree();
        assertNotNull(sT);
        String[] args = new String[] {"10", "32", "P2_Input1_Sample.txt" };
        String theOutput = readFile("P2_Output1_Sample.txt");
        SearchTree.main(args);
        assertFuzzyEquals(theOutput, systemOut().getHistory());
        assertNotNull(sT);
        try {
            SearchTree.main(new String[] {"10", "32", "P2_Input1_Sample.txt"});
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}