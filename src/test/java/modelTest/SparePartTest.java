package modelTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.edu.uptc.domain.model.SparePart;

class SparePartTest {

    private SparePart part;

    @BeforeEach
    void setUp() {
        part = new SparePart("R1", "Filtro de aceite", 15000, 10);
    }

    @Test
    void constructorSetsAllFieldsCorrectly() {
        assertEquals("R1", part.getCode());
        assertEquals("Filtro de aceite", part.getName());
        assertEquals(15000, part.getUnitPrice());
        assertEquals(10, part.getStock());
    }

    @Test
    void setStockUpdatesStockValue() {
        part.setStock(20);

        assertEquals(20, part.getStock());
    }

    @Test
    void setUnitPriceUpdatesUnitPriceValue() {
        part.setUnitPrice(18000);

        assertEquals(18000, part.getUnitPrice());
    }

    @Test
    void ishasStockReturnsTrueWhenStockIsSufficient() {
        assertTrue(part.ishasStock(5));
    }

    @Test
    void ishasStockReturnsFalseWhenStockIsInsufficient() {
        assertFalse(part.ishasStock(15));
    }

    @Test
    void isUpdateStockReducesStockWhenSufficient() {
        boolean result = part.isUpdateStock(4);

        assertTrue(result);
        assertEquals(6, part.getStock());
    }

    @Test
    void isUpdateStockReturnsFalseWhenInsufficientAndDoesNotChangeStock() {
        boolean result = part.isUpdateStock(15);

        assertFalse(result);
        assertEquals(10, part.getStock());
    }
}