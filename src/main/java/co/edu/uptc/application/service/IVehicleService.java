package co.edu.uptc.application.service;
import java.util.List;

import co.edu.uptc.domain.model.Vehicle;

public interface IVehicleService {
    
    boolean register(Vehicle vehicle);
    Vehicle findByLicensePlate(String licensePlate);
    List<Vehicle> findAll();
    Vehicle update (Vehicle vehicle);
    boolean delete (String licensePlata);

}
