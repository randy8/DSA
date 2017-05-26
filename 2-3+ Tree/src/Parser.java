import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Parses the text file.
 * 
 * @author Randy Liang (randy8)
 * @author Patrick McKee (bludevil)
 * 
 * @version 2016.09.27
 */
public class Parser {

    private Scanner scanner;
    private CommandProcessor cP;

    /**
     * This method is parsing the command line by line and executes the
     * instructions given to it.
     * 
     * @param hashSize
     *            of the hash table
     * @param blockSize
     *            free block size
     * @param fileName
     *            the file
     * @throws IOException
     */
    public Parser(int hashSize, int blockSize, String fileName)
            throws IOException {
        cP = new CommandProcessor(hashSize, blockSize);
        File fp = new File(fileName);
        scanner = new Scanner(fp);
        readFile();
    }

    /**
     * Scans the first word of each line for the command.
     */
    /**
     * Read file.
     * 
     * @throws IOException
     */
    private void readFile() throws IOException {
        while (scanner.hasNextLine()) {
            String command = scanner.next();
            String input = scanner.nextLine().trim();
            if (command.equals("insert")) {
                cP.insert(input);
            }
            else if (command.equals("remove")) {
                cP.remove(input);
            }
            else if (command.equals("list")) {
                cP.list(input);
            }
            else if (command.equals("delete")) {
                cP.delete(input, false, false);
            }
            else {
                cP.print(input);
            }
        }
    }

    /**
     * Prints the string.
     * 
     * @param str
     *            the string that we want to print
     * @return returns true when print is called
     */
    public boolean print(String str) {
        System.out.println(str);
        return true;
    }
}