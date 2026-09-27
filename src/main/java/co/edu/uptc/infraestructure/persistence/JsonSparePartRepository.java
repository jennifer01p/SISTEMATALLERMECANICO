package co.edu.uptc.infraestructure.persistence;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import co.edu.uptc.domain.model.SparePart;
import co.edu.uptc.domain.repository.ISparePartRepository;

public class JsonSparePartRepository implements ISparePartRepository {

    private static final String FILE_PATH = "sistematallermecanico\\src\\main\\resources\\data\\spare_parts.json";

    private final Gson gson;
    private List<SparePart> parts;

    public JsonSparePartRepository() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.parts = loadData();
    }

    private List<SparePart> loadData() {

        if (!Files.exists(Paths.get(FILE_PATH))) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(FILE_PATH)) {

            Type listType = new TypeToken<ArrayList<SparePart>>() {}.getType();

            List<SparePart> loaded = gson.fromJson(reader, listType);

            return loaded != null ? loaded : new ArrayList<>();

        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private void writeToFile(List<SparePart> data) {

        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(data, writer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(SparePart part) {

        parts.add(part);
        writeToFile(parts);
    }

    @Override
    public SparePart findByCode(String code) {

        for (SparePart p : parts) {

            if (p.getCode().equals(code)) {
                return p;
            }
        }

        return null;
    }

    @Override
    public List<SparePart> findAll() {

        return new ArrayList<>(parts);
    }

    @Override
    public SparePart update(SparePart part) {

        for (int i = 0; i < parts.size(); i++) {

            if (parts.get(i).getCode().equals(part.getCode())) {

                parts.set(i, part);
                writeToFile(parts);

                return part;
            }
        }

        return null;
    }

    @Override
    public boolean delete(String code) {

        boolean removed = parts.removeIf(p -> p.getCode().equals(code));

        if (removed) {
            writeToFile(parts);
        }

        return removed;
    }
}
