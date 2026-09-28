package serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.edu.uptc.application.service.SparePartService;
import co.edu.uptc.domain.exception.DuplicateCodeException;
import co.edu.uptc.domain.exception.InsufficientStockException;
import co.edu.uptc.domain.exception.SparePartNotFoundException;
import co.edu.uptc.domain.model.SparePart;
import co.edu.uptc.domain.repository.ISparePartRepository;
import repositoryTest.TestSparePartRepository;

class SparePartServiceTest {

    private ISparePartRepository repository;
    private SparePartService service;

    @BeforeEach
    void setUp() {
        repository = new TestSparePartRepository();
        service = new SparePartService(repository);
    }

    @Test
    void registerNewSparePartSavesItCorrectly() {
        SparePart part = service.registerNewSparePart("R1", "Filtro de aceite", 15000, 10);

        assertEquals("R1", part.getCode());
        assertEquals(1, service.findAll().size());
    }

    @Test
    void registerSparePartWithDuplicateCodeThrowsException() {
        service.registerNewSparePart("R1", "Filtro de aceite", 15000, 10);

        assertThrows(DuplicateCodeException.class,
                () -> service.registerNewSparePart("R1", "Otro filtro", 20000, 5));
    }

    @Test
    void restockExistingSparePartIncreasesStock() {
        service.registerNewSparePart("R1", "Filtro de aceite", 15000, 10);

        SparePart updated = service.restockSparePart("R1", 5);

        assertEquals(15, updated.getStock());
    }

    @Test
    void restockNonExistingSparePartThrowsException() {
        assertThrows(SparePartNotFoundException.class, () -> service.restockSparePart("R99", 5));
    }

    @Test
    void hasEnoughStockReturnsTrueWhenStockIsSufficient() {
        service.registerNewSparePart("R1", "Filtro de aceite", 15000, 10);

        assertTrue(service.hasEnoughStock("R1", 5));
    }

    @Test
    void hasEnoughStockReturnsFalseWhenStockIsInsufficient() {
        service.registerNewSparePart("R1", "Filtro de aceite", 15000, 3);

        assertFalse(service.hasEnoughStock("R1", 5));
    }

    @Test
    void discountStockReducesStockWhenSufficient() {
        service.registerNewSparePart("R1", "Filtro de aceite", 15000, 10);

        service.discountStock("R1", 4);

        assertEquals(6, service.findByCode("R1").getStock());
    }

    @Test
    void discountStockWithInsufficientStockThrowsException() {
        service.registerNewSparePart("R1", "Filtro de aceite", 15000, 2);

        assertThrows(InsufficientStockException.class, () -> service.discountStock("R1", 5));
    }

    @Test
    void findByCodeNonExistingThrowsException() {
        assertThrows(SparePartNotFoundException.class, () -> service.findByCode("R99"));
    }

    @Test
    void findAllReturnsAllRegisteredParts() {
        service.registerNewSparePart("R1", "Filtro de aceite", 15000, 10);
        service.registerNewSparePart("R2", "Pastillas de freno", 30000, 8);

        assertEquals(2, service.findAll().size());
    }
}