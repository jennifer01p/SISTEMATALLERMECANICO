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
 
import co.edu.uptc.domain.model.Client;
import co.edu.uptc.domain.repository.IClientRepository;
 
public class JsonClientRepository implements IClientRepository {
 
    private static final String FILE_PATH = "sistematallermecanico\\src\\main\\resources\\data\\clientes.json";
    private final Gson gson;
    private List<Client> clients;
    private int nextId;
 
    public JsonClientRepository() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.clients = loadData();
        this.nextId = calculateNextId();
    }
 
    private int calculateNextId() {
        return clients.stream()
                .mapToInt(Client::getId)
                .max()
                .orElse(0) + 1;
    }
 
    private List<Client> loadData() {
        if (!Files.exists(Paths.get(FILE_PATH))) {
            List<Client> preloaded = createPreloadedData();
            writeToFile(preloaded);
            return preloaded;
        }
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<ArrayList<Client>>() {}.getType();
            List<Client> loaded = gson.fromJson(reader, listType);
            return loaded != null ? loaded : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
 
    private List<Client> createPreloadedData() {
        List<Client> preloaded = new ArrayList<>();
        preloaded.add(new Client(1, "Carlos Ramirez", "3101234567"));
        preloaded.add(new Client(2, "Laura Gomez", "3129876543"));
        preloaded.add(new Client(3, "Andres Torres", "3115558899"));
        return preloaded;
    }
 
    private void writeToFile(List<Client> data) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    @Override
    public boolean save(Client client) {
        client.setId(nextId);
        clients.add(client);
        writeToFile(clients);
        nextId++;
        return true;
    }
 
    @Override
    public Client findById(int id) {
        for (Client c : clients) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }
 
    @Override
    public List<Client> findAll() {
        return clients;
    }
 
    @Override
    public Client update(Client client) {
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getId() == client.getId()) {
                clients.set(i, client);
                writeToFile(clients);
                return client;
            }
        }
        return null;
    }
 
    @Override
    public boolean delete(int id) {
        boolean removed = clients.removeIf(c -> c.getId() == id);
        if (removed) {
            writeToFile(clients);
        }
        return removed;
    }
 
}
 