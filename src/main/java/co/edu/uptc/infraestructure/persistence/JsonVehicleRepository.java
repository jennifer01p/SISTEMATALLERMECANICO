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

import co.edu.uptc.domain.model.Vehicle;
import co.edu.uptc.domain.repository.IVehicleRepository;

public class JsonVehicleRepository implements IVehicleRepository {

    private static final String FILE_PATH = "sistematallermecanico\\src\\main\\resources\\data\\vehicles.json";

    private final Gson gson;
    private List<Vehicle> vehicles;

    public JsonVehicleRepository() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.vehicles = loadData();
    }

    private List<Vehicle> loadData() {

        if (!Files.exists(Paths.get(FILE_PATH))) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(FILE_PATH)) {

            Type listType = new TypeToken<ArrayList<Vehicle>>() {}.getType();

            List<Vehicle> loaded = gson.fromJson(reader, listType);

            return loaded != null ? loaded : new ArrayList<>();

        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private void writeToFile(List<Vehicle> data) {

        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(data, writer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean save(Vehicle vehicle) {

        vehicles.add(vehicle);
        writeToFile(vehicles);

        return true;
    }

    @Override
    public Vehicle findByLicensePlate(String licensePlate) {

        for (Vehicle v : vehicles) {

            if (v.getLicensePlate().equals(licensePlate)) {
                return v;
            }
        }

        return null;
    }


    @Override
    public List<Vehicle> findAll() {

        return new ArrayList<> (vehicles);
    }


    @Override
    public Vehicle update(Vehicle vehicle) {

        for (int i = 0; i < vehicles.size(); i++) {

            if (vehicles.get(i).getLicensePlate()
                    .equals(vehicle.getLicensePlate())) {

                vehicles.set(i, vehicle);
                writeToFile(vehicles);

                return vehicle;
            }
        }

        return null;
    }


    @Override
    public boolean delete(String licensePlate) {

        boolean removed = vehicles.removeIf(
                v -> v.getLicensePlate().equals(licensePlate)
        );

        if (removed) {
            writeToFile(vehicles);
        }

        return removed;
    }
}