package uk.gov.ons.hornet.reader;

import java.util.Scanner;

public class FileReader {
    public String readFile(String folder, String fileName) {
        return new Scanner(
                Thread.currentThread().getContextClassLoader().getResourceAsStream(folder + "/" + fileName), "UTF-8")
                .useDelimiter("\\A")
                .next();
    }

}