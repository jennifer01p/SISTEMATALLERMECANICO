package co.edu.uptc.application.service;

import java.util.List;

import co.edu.uptc.domain.model.Vehicle;
import co.edu.uptc.domain.repository.IVehicleRepository;


public class VehicleService implements IVehicleService {

    private final IVehicleRepository vehicleRepository;

    public VehicleService (IVehicleRepository vehicleRepository){
        this.vehicleRepository = vehicleRepository;
    }

    @Override 
    public boolean register(Vehicle vehicle ){
    validate(vehicle);
    return vehicleRepository.save(vehicle);
    }

    @Override 
    public Vehicle findByLicensePlate(String licensePlate){
        return vehicleRepository.findByLicensePlate(licensePlate);
    }
     @Override
    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    @Override 
    public Vehicle update(Vehicle vehicle) {
        validate(vehicle);
        return vehicleRepository.update(vehicle);
    }

    @Override 
    public boolean delete(String licensePlate) {
        return vehicleRepository.delete(licensePlate);
    }

    private void validate(Vehicle vehicle) {
    if (vehicle.getLicensePlate() == null || !vehicle.getLicensePlate().matches("[A-Za-z]{3}\\d{3}")) {
        throw new IllegalArgumentException("La placa debe tener el formato AAA123.");
    }
    if (vehicle.getBrand() == null || vehicle.getBrand().trim().isEmpty()) {
        throw new IllegalArgumentException("La marca no puede estar vacia.");
    }
    if (vehicle.getMileage() < 0) {
        throw new IllegalArgumentException("El kilometraje no puede ser negativo.");
    }
}



}
