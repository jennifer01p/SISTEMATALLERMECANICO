package co.edu.uptc.infraestructure.persistence;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import co.edu.uptc.domain.model.ServiceOrder;
import co.edu.uptc.domain.repository.IServiceOrderRepository;

public class JsonServiceOrderRepository implements IServiceOrderRepository{
    private static final String FILE_PATH = "src\\main\\resources\\data\\service_orders.json";
    private final Gson gson;
    private List<ServiceOrder> orders;
    private int nextId;

    public JsonServiceOrderRepository() {
        this.gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (date, type, context) ->
                new JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE)))
            .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, type, context) ->
                LocalDate.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE))
            .create();
        this.orders = loadData();
        this.nextId = calculateNextId();
    }
    
    private List<ServiceOrder> loadData() {

        if (!Files.exists(Paths.get(FILE_PATH))) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(FILE_PATH)) {

            Type listType = new TypeToken<ArrayList<ServiceOrder>>() {}.getType();

            List<ServiceOrder> loaded = gson.fromJson(reader, listType);

            return loaded != null ? loaded : new ArrayList<>();

        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
    
    private void writeToFile(List<ServiceOrder> data) {

        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(data, writer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean save(ServiceOrder order) {
        order.setId(nextId);
        nextId++;
        orders.add(order);
        writeToFile(orders);
        return true;
    }

    private int calculateNextId() {
        return orders.stream().mapToInt(ServiceOrder::getId).max().orElse(0) + 1;
    }

    @Override
    public ServiceOrder findById(int id) {

        for (ServiceOrder o : orders) {

            if (o.getId() == id) {
                return o;
            }
        }

        return null;
    }

    @Override
    public List<ServiceOrder> findAll() {
        return new ArrayList<>(orders);
    }

    @Override
    public ServiceOrder update(ServiceOrder order) {

        for (int i = 0; i < orders.size(); i++) {

            if (orders.get(i).getId() == order.getId()) {

                orders.set(i, order);
                writeToFile(orders);

                return order;
            }
        }

        return null;
    }

    @Override
    public boolean delete(int id) {

        boolean removed = orders.removeIf(o -> o.getId() == id);

        if (removed) {
            writeToFile(orders);
        }

        return removed;
    }
}


