package serviceTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.edu.uptc.application.dto.Invoice;
import co.edu.uptc.application.service.BillingService;
import co.edu.uptc.domain.model.Mechanic;
import co.edu.uptc.domain.model.ServiceOrder;
import co.edu.uptc.domain.model.Vehicle;
import co.edu.uptc.enums.MechanicSpecialty;
import repositoryTest.TestOrderRepository;

class BillingServiceTest {

    private TestOrderRepository repo;
    private BillingService billingService;

    @BeforeEach
    void setUp() {
        repo = new TestOrderRepository();
        billingService = new BillingService(repo);
    }

    @Test
    void generateInvoiceCalculatesCorrectTotals() {
        Mechanic mech = new Mechanic(1, "Juan", "123", MechanicSpecialty.ENGINE, 50.0);
        Vehicle v = new Vehicle("ABC123", "Mazda", "3", "2021", 10000);
        ServiceOrder order = new ServiceOrder(1, v, mech, LocalDate.now(), "diag", 2.0, null, 0.0, 0.0);
        order.getSupplyConsumptions().add(new co.edu.uptc.domain.model.SupplyConsumption("P1", "Filtro", 2, 20.0, 40.0));

        repo.save(order);

        Invoice invoice = billingService.generateInvoice(1, 0);

        double expectedLabor = mech.getHourlyRate() * 2.0;
        double expectedMaterials = 40.0;
        double expectedTaxes = (expectedLabor + expectedMaterials) * 0.13;
        double expectedTotal = (expectedLabor + expectedMaterials) + expectedTaxes - 0.0;

        assertEquals(expectedLabor, invoice.getLaborCost(), 0.001);
        assertEquals(expectedMaterials, invoice.getMaterialsCost(), 0.001);
        assertEquals(expectedTaxes, invoice.getTaxes(), 0.001);
        assertEquals(expectedTotal, invoice.getTotal(), 0.001);
    }

}
