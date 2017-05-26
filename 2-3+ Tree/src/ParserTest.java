import java.io.IOException;
import student.TestCase;

/**
 * @author Randy Liang (randy8)
 * @author Patrick McKee (bludevil)
 * @version October 03, 2016
 */

public class ParserTest extends TestCase {

    private Parser parser;

    /**
     * Sets up the tests class
     * 
     * @throws IOException
     */
    public void setUp() throws IOException {
        parser = new Parser(10, 32, "P2_Input1_Sample.txt");
    }

    /**
     * This tests the method print
     */
    public void testPrint() {
        String string = "tree";
        assertTrue(parser.print(string));
    }
}