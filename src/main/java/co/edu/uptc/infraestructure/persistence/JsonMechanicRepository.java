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
 
import co.edu.uptc.domain.model.Mechanic;
import co.edu.uptc.domain.repository.IMechanicRepository;
import co.edu.uptc.enums.MechanicSpecialty;
 
public class JsonMechanicRepository implements IMechanicRepository {
 
    private static final String FILE_PATH = "sistematallermecanico\\src\\main\\resources\\data\\mechanics.json";
    private final Gson gson;
    private List<Mechanic> mechanics;
    private int nextId;
 
    public JsonMechanicRepository() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.mechanics = loadData();
        this.nextId = calculateNextId();
    }
 
    private int calculateNextId() {
        return mechanics.stream()
                .mapToInt(Mechanic::getId)
                .max()
                .orElse(0) + 1;
    }
 
    private List<Mechanic> loadData() {
        if (!Files.exists(Paths.get(FILE_PATH))) {
            List<Mechanic> preloaded = createPreloadedData();
            writeToFile(preloaded);
            return preloaded;
        }
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<ArrayList<Mechanic>>() {}.getType();
            List<Mechanic> loaded = gson.fromJson(reader, listType);
            return loaded != null ? loaded : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
 
    private List<Mechanic> createPreloadedData() {
        List<Mechanic> preloaded = new ArrayList<>();
        preloaded.add(new Mechanic(1, "Fernando Castillo", "3101234567", MechanicSpecialty.BRAKES, 3000.0));
        preloaded.add(new Mechanic(2, "Andres Corredor", "3101234345", MechanicSpecialty.ENGINE, 5000.0));
        preloaded.add(new Mechanic(3, "Jose Hernandez", "3101334223", MechanicSpecialty.ELECTRICAL, 7000.0));
        return preloaded;
    }
 
    private void writeToFile(List<Mechanic> data) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    @Override
    public boolean save(Mechanic mechanic) {
        mechanic.setId(nextId);
        mechanics.add(mechanic);
        writeToFile(mechanics);
        nextId++;
        return true;
    }
 
    @Override
    public Mechanic findById(int id) {
        for (Mechanic m : mechanics) {
            if (m.getId() == id) {
                return m;
            }
        }
        return null;
    }
 
    @Override
    public List<Mechanic> findAll() {
        return mechanics;
    }
 
    @Override
    public Mechanic update(Mechanic mechanic) {
        for (int i = 0; i < mechanics.size(); i++) {
            if (mechanics.get(i).getId() == mechanic.getId()) {
                mechanics.set(i, mechanic);
                writeToFile(mechanics);
                return mechanic;
            }
        }
        return null;
    }
 
    @Override
    public boolean delete(int id) {
        boolean removed = mechanics.removeIf(m -> m.getId() == id);
        if (removed) {
            writeToFile(mechanics);
        }
        return removed;
    }
 
}