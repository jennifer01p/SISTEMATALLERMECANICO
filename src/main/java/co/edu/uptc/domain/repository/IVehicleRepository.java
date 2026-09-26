package co.edu.uptc.domain.repository;

import java.util.List;

import co.edu.uptc.domain.model.Vehicle;

public interface IVehicleRepository {
    boolean save (Vehicle  vehicle);
    Vehicle findByLicensePlate(String licensePlate);
    List<Vehicle> findAll();
    Vehicle update(Vehicle vehicle);
    boolean delete(String licensePlate);

}
