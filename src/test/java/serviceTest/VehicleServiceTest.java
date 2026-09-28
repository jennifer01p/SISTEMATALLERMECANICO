package serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.edu.uptc.application.service.VehicleService;
import co.edu.uptc.domain.model.Vehicle;
import co.edu.uptc.domain.repository.IVehicleRepository;
import repositoryTest.TestVehicleRepository;

class VehicleServiceTest {

    private IVehicleRepository repository;
    private VehicleService service;

    @BeforeEach
    void setUp() {
        repository = new TestVehicleRepository();
        service = new VehicleService(repository);
    }

    @Test
    void registerValidVehicleReturnsTrue() {

        Vehicle vehicle = new Vehicle(
                "ABC123",
                "Mazda",
                "3",
                "2021",
                15000
        );

        boolean result = service.register(vehicle);

        assertTrue(result);
        assertEquals(1, service.findAll().size());
    }

    @Test
    void registerVehicleWithEmptyBrandThrowsException() {

        Vehicle vehicle = new Vehicle(
                "ABC123",
                "",
                "3",
                "2021",
                15000
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> service.register(vehicle)
        );
    }

    @Test
    void registerVehicleWithNegativeMileageThrowsException() {

        Vehicle vehicle = new Vehicle(
                "ABC123",
                "Mazda",
                "3",
                "2021",
                -5
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> service.register(vehicle)
        );
    }

    @Test
    void findByLicensePlateExistingVehicleReturnsVehicle() {

        Vehicle vehicle = new Vehicle(
                "ABC123",
                "Mazda",
                "3",
                "2021",
                15000
        );

        repository.save(vehicle);

        Vehicle found = service.findByLicensePlate("ABC123");

        assertEquals("Mazda", found.getBrand());
    }

    @Test
    void findByLicensePlateNonExistingVehicleReturnsNull() {

        Vehicle found = service.findByLicensePlate("ZZZ999");

        assertNull(found);
    }

    @Test
    void updateExistingVehicleChangesMileage() {

        Vehicle vehicle = new Vehicle(
                "ABC123",
                "Mazda",
                "3",
                "2021",
                15000
        );

        repository.save(vehicle);

        vehicle.setMileage(20000);
        service.update(vehicle);

        Vehicle updated = service.findByLicensePlate("ABC123");

        assertEquals(20000, updated.getMileage());
    }

    @Test
    void deleteExistingVehicleReturnsTrue() {

        Vehicle vehicle = new Vehicle(
                "ABC123",
                "Mazda",
                "3",
                "2021",
                15000
        );

        repository.save(vehicle);

        boolean deleted = service.delete("ABC123");

        assertTrue(deleted);
        assertNull(service.findByLicensePlate("ABC123"));
    }

    @Test
    void deleteNonExistingVehicleReturnsFalse() {

        boolean deleted = service.delete("ZZZ999");

        assertFalse(deleted);
    }
}