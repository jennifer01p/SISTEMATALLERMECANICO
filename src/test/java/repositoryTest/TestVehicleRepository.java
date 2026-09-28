package repositoryTest;

import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.domain.model.Vehicle;
import co.edu.uptc.domain.repository.IVehicleRepository;

public class TestVehicleRepository implements IVehicleRepository {

    private List<Vehicle> vehicles = new ArrayList<>();

    @Override
    public boolean save(Vehicle vehicle) {

        vehicles.add(vehicle);

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

        return vehicles;
    }

    @Override
    public Vehicle update(Vehicle vehicle) {

        for (int i = 0; i < vehicles.size(); i++) {

            if (vehicles.get(i).getLicensePlate()
                    .equals(vehicle.getLicensePlate())) {

                vehicles.set(i, vehicle);

                return vehicle;
            }
        }

        return null;
    }

    @Override
    public boolean delete(String licensePlate) {

        return vehicles.removeIf(
                v -> v.getLicensePlate().equals(licensePlate)
        );
    }
}