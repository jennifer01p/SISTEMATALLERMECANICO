package co.edu.uptc.util;

import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonExporter {

    private final Gson gson;

    public JsonExporter() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void write(Object obj, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(obj, writer);
        }
    }

}
