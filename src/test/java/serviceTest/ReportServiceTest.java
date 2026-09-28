package serviceTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.edu.uptc.application.dto.ProductivityEntry;
import co.edu.uptc.application.dto.SparePartUsage;
import co.edu.uptc.application.service.ReportService;
import co.edu.uptc.domain.model.Mechanic;
import co.edu.uptc.domain.model.ServiceOrder;
import co.edu.uptc.domain.model.Vehicle;
import co.edu.uptc.enums.MechanicSpecialty;
import co.edu.uptc.enums.OrderStatus;
import repositoryTest.TestOrderRepository;
import repositoryTest.TestSparePartRepository;

class ReportServiceTest {

    private TestOrderRepository orderRepo;
    private TestSparePartRepository spareRepo;
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        orderRepo = new TestOrderRepository();
        spareRepo = new TestSparePartRepository();
        reportService = new ReportService(orderRepo, spareRepo);
    }

    @Test
    void incomeBetweenSumsOnlyReadyForDeliveryOrdersInRange() {
        Mechanic mech = new Mechanic(1, "Ana", "111", MechanicSpecialty.BRAKES, 40.0);
        Vehicle v = new Vehicle("AAA111", "Ford", "Ka", "2019", 50000);

        ServiceOrder o1 = new ServiceOrder(1, v, mech, LocalDate.of(2026, 9, 1), "d", 1.0, OrderStatus.READY_FOR_DELIVERY, 0, 100.0);
        ServiceOrder o2 = new ServiceOrder(2, v, mech, LocalDate.of(2026, 8, 1), "d", 1.0, OrderStatus.READY_FOR_DELIVERY, 0, 200.0);
        ServiceOrder o3 = new ServiceOrder(3, v, mech, LocalDate.of(2026, 9, 10), "d", 1.0, OrderStatus.UNDER_REPAIR, 0, 300.0);

        orderRepo.save(o1);
        orderRepo.save(o2);
        orderRepo.save(o3);

        double sum = reportService.incomeBetween(LocalDate.of(2026, 9, 1), LocalDate.of(2026, 9, 30));
        assertEquals(100.0, sum, 0.001);
    }

    @Test
    void topSparePartsReturnsTopN() {
        Mechanic mech = new Mechanic(1, "Ana", "111", MechanicSpecialty.BRAKES, 40.0);
        Vehicle v = new Vehicle("AAA111", "Ford", "Ka", "2019", 50000);

        ServiceOrder o1 = new ServiceOrder(1, v, mech, LocalDate.now(), "d", 1.0, OrderStatus.READY_FOR_DELIVERY, 0, 0.0);
        o1.getSupplyConsumptions().add(new co.edu.uptc.domain.model.SupplyConsumption("C1", "Filtro", 3, 10.0, 30.0));
        o1.getSupplyConsumptions().add(new co.edu.uptc.domain.model.SupplyConsumption("C2", "Bujia", 1, 5.0, 5.0));

        ServiceOrder o2 = new ServiceOrder(2, v, mech, LocalDate.now(), "d", 1.0, OrderStatus.READY_FOR_DELIVERY, 0, 0.0);
        o2.getSupplyConsumptions().add(new co.edu.uptc.domain.model.SupplyConsumption("C1", "Filtro", 2, 10.0, 20.0));

        orderRepo.save(o1);
        orderRepo.save(o2);

        spareRepo.save(new co.edu.uptc.domain.model.SparePart("C1", "Filtro", 10.0, 10));
        spareRepo.save(new co.edu.uptc.domain.model.SparePart("C2", "Bujia", 5.0, 5));

        List<SparePartUsage> top = reportService.topSpareParts(2);

        assertEquals(2, top.size());
        assertEquals("C1", top.get(0).getCode());
        assertEquals(5, top.get(0).getQuantity());
    }

    @Test
    void productivityPerMechanicAggregatesHours() {
        Mechanic m1 = new Mechanic(1, "Ana", "111", MechanicSpecialty.BRAKES, 40.0);
        Mechanic m2 = new Mechanic(2, "Luis", "222", MechanicSpecialty.ENGINE, 50.0);
        Vehicle v = new Vehicle("AAA111", "Ford", "Ka", "2019", 50000);

        ServiceOrder o1 = new ServiceOrder(1, v, m1, LocalDate.now(), "d", 2.0, OrderStatus.READY_FOR_DELIVERY, 0, 0.0);
        ServiceOrder o2 = new ServiceOrder(2, v, m1, LocalDate.now(), "d", 3.0, OrderStatus.READY_FOR_DELIVERY, 0, 0.0);
        ServiceOrder o3 = new ServiceOrder(3, v, m2, LocalDate.now(), "d", 4.0, OrderStatus.READY_FOR_DELIVERY, 0, 0.0);

        orderRepo.save(o1);
        orderRepo.save(o2);
        orderRepo.save(o3);

        List<ProductivityEntry> list = reportService.productivityPerMechanic();
        assertEquals(2, list.size());
        ProductivityEntry e1 = list.stream().filter(e -> e.getMechanicId() == 1).findFirst().orElse(null);
        assertEquals(5.0, e1.getHoursWorked(), 0.001);
    }

    @Test
    void exportMethodsCreateFiles() throws IOException {
        
        Mechanic mech = new Mechanic(1, "Ana", "111", MechanicSpecialty.BRAKES, 40.0);
        Vehicle v = new Vehicle("AAA111", "Ford", "Ka", "2019", 50000);
        ServiceOrder o1 = new ServiceOrder(1, v, mech, LocalDate.now(), "d", 2.0, OrderStatus.READY_FOR_DELIVERY, 0, 123.45);
        orderRepo.save(o1);

        Path jsonFile = Files.createTempFile("report", ".json");
        Path csvFile = Files.createTempFile("report", ".csv");

        co.edu.uptc.application.dto.SparePartUsage usage = new co.edu.uptc.application.dto.SparePartUsage("C1", "Filtro", 3);
        reportService.exportToJson(usage, jsonFile.toString());
        reportService.exportIncomeToCsv(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1), csvFile.toString());

        assertTrue(Files.size(jsonFile) > 0);
        assertTrue(Files.size(csvFile) > 0);
    }

}
