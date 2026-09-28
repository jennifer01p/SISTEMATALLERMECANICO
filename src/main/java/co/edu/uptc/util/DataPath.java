package co.edu.uptc.util;

import java.nio.file.Files;
import java.nio.file.Paths;

public class DataPath {
    private static final String DIR = "src/main/resources/data/";

    public static String of(String fileName) {
        if (Files.isDirectory(Paths.get(DIR))) {
            return DIR + fileName;
        }
        return "sistematallermecanico/" + DIR + fileName;
    }
}