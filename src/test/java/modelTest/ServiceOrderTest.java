package modelTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.edu.uptc.domain.model.Mechanic;
import co.edu.uptc.domain.model.ServiceOrder;
import co.edu.uptc.domain.model.SupplyConsumption;
import co.edu.uptc.domain.model.Vehicle;
import co.edu.uptc.enums.MechanicSpecialty;
import co.edu.uptc.enums.OrderStatus;

class ServiceOrderTest {

    private ServiceOrder order;

    @BeforeEach
    void setUp() {
        Mechanic mechanic = new Mechanic(1, "Carlos Ruiz", "3001112233", MechanicSpecialty.ENGINE, 20000);
        Vehicle vehicle = new Vehicle("ABC123", "Mazda", "3", "2020", 45000);

        order = new ServiceOrder(1, vehicle, mechanic, LocalDate.now(), "Falla de motor", 3, OrderStatus.ENTERED, 0, 0);
    }

    @Test
    void calculateLaborCostMultipliesHoursByHourlyRate() {
        double result = order.calculateLaborCost();

        assertEquals(60000, result);
    }

    @Test
    void calculateMaterialsCostWithNoConsumptionsReturnsZero() {
        double result = order.calculateMaterialsCost();

        assertEquals(0, result);
    }

    @Test
    void calculateMaterialsCostSumsAllConsumptions() {
        order.getSupplyConsumptions().add(new SupplyConsumption("R1", "Filtro", 2, 15000, 30000));
        order.getSupplyConsumptions().add(new SupplyConsumption("R2", "Aceite", 1, 25000, 25000));

        double result = order.calculateMaterialsCost();

        assertEquals(55000, result);
    }

    @Test
    void calculateSubtotalAddsLaborAndMaterials() {
        order.getSupplyConsumptions().add(new SupplyConsumption("R1", "Filtro", 2, 15000, 30000));

        double result = order.calculateSubtotal();

        assertEquals(90000, result);
    }

    @Test
    void calculateTaxesIsThirteenPercentOfSubtotal() {
        double result = order.calculateTaxes();

        assertEquals(7800, result, 0.01);
    }

    @Test
    void calculateDiscountWithThreeOrFewerCompletedServicesReturnsZero() {
        double result = order.calculateDiscount(3);

        assertEquals(0, result);
    }

    @Test
    void calculateDiscountWithMoreThanThreeCompletedServicesReturnsFivePercentOfLabor() {
        double result = order.calculateDiscount(4);

        assertEquals(3000, result, 0.01);
    }

    @Test
    void calculateTotalWithoutDiscountAddsSubtotalAndTaxes() {
        double result = order.calculateTotal(2);

        assertEquals(67800, result, 0.01);
    }

    @Test
    void calculateTotalWithDiscountSubtractsFivePercentOfLabor() {
        double result = order.calculateTotal(5);

        assertEquals(64800, result, 0.01);
    }
}