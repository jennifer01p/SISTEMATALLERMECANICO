package serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.edu.uptc.application.service.MechanicService;
import co.edu.uptc.domain.model.Mechanic;
import co.edu.uptc.domain.repository.IMechanicRepository;
import co.edu.uptc.enums.MechanicSpecialty;

import repositoryTest.TestMechanicRepository;

class MechanicServiceTest {

    private IMechanicRepository repository;
    private MechanicService service;

    @BeforeEach
    void setUp() {
        repository = new TestMechanicRepository();
        service = new MechanicService(repository);
    }

    @Test
    void registerValidMechanicReturnsTrue() {
        Mechanic mechanic = new Mechanic(0, "Fernando Castillo", "3101234567", MechanicSpecialty.BRAKES, 3000);

        boolean result = service.register(mechanic);

        assertTrue(result);
        assertEquals(1, service.findAll().size());
    }

    @Test
    void registerMechanicWithNullSpecialtyThrowsException() {
        Mechanic mechanic = new Mechanic(0, "Fernando Castillo", "3101234567", null, 3000);

        assertThrows(IllegalArgumentException.class, () -> service.register(mechanic));
    }

    @Test
    void registerMechanicWithZeroHourlyRateThrowsException() {
        Mechanic mechanic = new Mechanic(0, "Fernando Castillo", "3101234567", MechanicSpecialty.ENGINE, 0);

        assertThrows(IllegalArgumentException.class, () -> service.register(mechanic));
    }

    @Test
    void registerMechanicWithNegativeHourlyRateThrowsException() {
        Mechanic mechanic = new Mechanic(0, "Fernando Castillo", "3101234567", MechanicSpecialty.ENGINE, -500);

        assertThrows(IllegalArgumentException.class, () -> service.register(mechanic));
    }

    @Test
    void findByIdExistingMechanicReturnsMechanic() {
        repository.save(new Mechanic(1, "Andres Corredor", "3101234345", MechanicSpecialty.ENGINE, 5000));

        Mechanic found = service.findById(1);

        assertEquals("Andres Corredor", found.getName());
    }

    @Test
    void findByIdNonExistingMechanicReturnsNull() {
        Mechanic found = service.findById(99);

        assertNull(found);
    }

    @Test
    void updateExistingMechanicChangesHourlyRate() {
        Mechanic mechanic = new Mechanic(1, "Jose Hernandez", "3101334223", MechanicSpecialty.ELECTRICAL, 7000);
        repository.save(mechanic);

        mechanic.setHourlyRate(8500);
        service.update(mechanic);

        Mechanic updated = service.findById(1);
        assertEquals(8500, updated.getHourlyRate());
    }

    @Test
    void deleteExistingMechanicReturnsTrue() {
        repository.save(new Mechanic(1, "Luis Rojas", "3121234567", MechanicSpecialty.SUSPENSION, 4000));

        boolean deleted = service.delete(1);

        assertTrue(deleted);
        assertNull(service.findById(1));
    }

    @Test
    void deleteNonExistingMechanicReturnsFalse() {
        boolean deleted = service.delete(99);

        assertFalse(deleted);
    }

}