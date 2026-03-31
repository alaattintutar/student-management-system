import java.util.*;
import java.io.*;

// Utility class for file operations
public class FileManager {

    // Reads a text file and returns a list of string arrays, splitting each line by comma
    public List<String[]> readTxt(String input) {
        List<String[]> dataList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(input))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                dataList.add(parts);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataList;
    }

    // Writes a list of strings to a file, each string as a separate line
    public void writeToFile(String outputFile, List<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
