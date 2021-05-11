import example.BPlusTree;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author I-Chung, Wang
 * @date 2021/5/3 下午 03:57
 */
public class Bootstrap {
    public static void main(String[] args) {

        // Ensure correct number of arguments
        if (args.length != 1) {
            System.err.println("usage: java BPlusTree <file_name>");
            System.exit(-1);
        }

        // Read from file
        String fileName = args[0];
        try {

            // Prepare to read input file
            File file = new File(System.getProperty("user.dir") + "/" + fileName);
            Scanner sc = new Scanner(file);

            // Create output file in which search results will be stored
            FileWriter logger = new FileWriter("output_file.txt", false);
            boolean firstLine = true;

            // Create initial B+ tree
            BPlusTree bpt = null;

            // Perform an operation for each line in the input file
            while (sc.hasNextLine()) {
                String line = sc.nextLine().replace(" ", "");
                String[] tokens = line.split("[(,)]");

                switch (tokens[0]) {

                    // Initializes an m-order B+ tree
                    case "Initialize":
                        bpt = new BPlusTree(Integer.parseInt(tokens[1]));
                        break;

                    // Insert a dictionary pair into the B+ tree
                    case "Insert":
                        bpt.insert(Integer.parseInt(tokens[1]), Double.parseDouble(tokens[2]));
                        break;

                    // Delete a dictionary pair from the B+ tree
                    case "Delete":
                        bpt.delete(Integer.parseInt(tokens[1]));
                        break;

                    // Perform a search or search operation on the B+ tree
                    case "Search":
                        String result = "";

                        // Perform search (across a range) operation
                        if (tokens.length == 3) {
                            ArrayList<Double> values = bpt.search(
                                    Integer.parseInt(tokens[1]),
                                    Integer.parseInt(tokens[2]));

                            // Record search result as a String
                            if (values.size() != 0) {
                                for (double v : values) { result += v + ", "; }
                                result = result.substring(0, result.length() - 2);
                            } else {
                                result = "Null";
                            }

                        }

                        // Perform search operation
                        else {

							/* Perform search for key, if resulting value is
							   null, then the key could not be found */
                            Double value = bpt.search(Integer.parseInt(tokens[1]));
                            result = (value == null) ? "Null" :
                                    Double.toString(value);
                        }

                        // Output search result in .txt file
                        if (firstLine) {
                            logger.write(result);
                            firstLine = false;
                        } else {
                            logger.write("\n" + result);
                        }
                        logger.flush();

                        break;
                    default:
                        throw new IllegalArgumentException("\"" + tokens[0] +
                                "\"" + " is an unacceptable input.");
                }
            }

            // Close output file
            logger.close();

        } catch (FileNotFoundException e) {
            System.err.println(e);
        } catch (IllegalArgumentException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
