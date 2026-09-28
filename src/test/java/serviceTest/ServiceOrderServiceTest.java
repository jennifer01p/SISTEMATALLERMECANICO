package serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.edu.uptc.application.service.ISparePartService;
import co.edu.uptc.application.service.ServiceOrderService;
import co.edu.uptc.application.service.SparePartService;
import co.edu.uptc.domain.exception.InsufficientStockException;
import co.edu.uptc.domain.exception.MechanicNotFoundException;
import co.edu.uptc.domain.exception.ServiceOrderNotFoundException;
import co.edu.uptc.domain.exception.VehicleNotFoundException;
import co.edu.uptc.domain.model.Mechanic;
import co.edu.uptc.domain.model.ServiceOrder;
import co.edu.uptc.domain.model.Vehicle;
import co.edu.uptc.domain.repository.IMechanicRepository;
import co.edu.uptc.domain.repository.IServiceOrderRepository;
import co.edu.uptc.domain.repository.ISparePartRepository;
import co.edu.uptc.domain.repository.IVehicleRepository;
import co.edu.uptc.enums.MechanicSpecialty;
import co.edu.uptc.enums.OrderStatus;
import repositoryTest.TestServiceOrderRepository;
import repositoryTest.TestSparePartRepository;

class ServiceOrderServiceTest {

    private ServiceOrderService service;
    private ISparePartService sparePartService;
    private static final String PLATE = "ABC123";
    private static final int MECHANIC_ID = 1;

    @BeforeEach
    void setUp() {
        IServiceOrderRepository orderRepository = new TestServiceOrderRepository();
        ISparePartRepository sparePartRepository = new TestSparePartRepository();
        sparePartService = new SparePartService(sparePartRepository);

        IVehicleRepository vehicleRepository = new IVehicleRepository() {
            private final List<Vehicle> vehicles = new ArrayList<>();

            @Override
            public boolean save(Vehicle vehicle) {
                vehicles.add(vehicle);
                return true;
            }

            @Override
            public Vehicle findByLicensePlate(String licensePlate) {
                return vehicles.stream()
                        .filter(v -> v.getLicensePlate().equals(licensePlate))
                        .findFirst().orElse(null);
            }

            @Override
            public List<Vehicle> findAll() {
                return vehicles;
            }

            @Override
            public Vehicle update(Vehicle vehicle) {
                return vehicle;
            }

            @Override
            public boolean delete(String licensePlate) {
                return vehicles.removeIf(v -> v.getLicensePlate().equals(licensePlate));
            }
        };

        IMechanicRepository mechanicRepository = new IMechanicRepository() {
            private final List<Mechanic> mechanics = new ArrayList<>();

            @Override
            public boolean save(Mechanic mechanic) {
                mechanics.add(mechanic);
                return true;
            }

            @Override
            public Mechanic findById(int id) {
                return mechanics.stream()
                        .filter(m -> m.getId() == id)
                        .findFirst().orElse(null);
            }

            @Override
            public List<Mechanic> findAll() {
                return mechanics;
            }

            @Override
            public Mechanic update(Mechanic mechanic) {
                return mechanic;
            }

            @Override
            public boolean delete(int id) {
                return mechanics.removeIf(m -> m.getId() == id);
            }
        };

        vehicleRepository.save(new Vehicle(PLATE, "Mazda", "3", "2020", 45000));
        mechanicRepository.save(new Mechanic(MECHANIC_ID, "Carlos Ruiz", "3001112233", MechanicSpecialty.ENGINE, 20000));

        service = new ServiceOrderService(orderRepository, sparePartService, vehicleRepository, mechanicRepository);
    }

    @Test
    void createOrderWithValidDataReturnsOrderWithEnteredStatus() {
        ServiceOrder order = service.createOrder(PLATE, MECHANIC_ID, "Falla de motor");

        assertEquals(OrderStatus.ENTERED, order.getStatus());
        assertEquals(PLATE, order.getVehicle().getLicensePlate());
    }

    @Test
    void createOrderWithNonExistingVehicleThrowsException() {
        assertThrows(VehicleNotFoundException.class,
                () -> service.createOrder("ZZZ999", MECHANIC_ID, "Falla de motor"));
    }

    @Test
    void createOrderWithNonExistingMechanicThrowsException() {
        assertThrows(MechanicNotFoundException.class,
                () -> service.createOrder(PLATE, 99, "Falla de motor"));
    }

    @Test
    void addSparePartDiscountsStockAndAddsConsumption() {
        sparePartService.registerNewSparePart("R1", "Filtro de aceite", 15000, 10);
        ServiceOrder order = service.createOrder(PLATE, MECHANIC_ID, "Falla de motor");

        boolean result = service.addSparePart(order.getId(), "R1", 3);

        assertTrue(result);
        assertEquals(7, sparePartService.findByCode("R1").getStock());
        assertEquals(1, service.findById(order.getId()).getSupplyConsumptions().size());
    }

    @Test
    void addSparePartWithInsufficientStockThrowsException() {
        sparePartService.registerNewSparePart("R1", "Filtro de aceite", 15000, 2);
        ServiceOrder order = service.createOrder(PLATE, MECHANIC_ID, "Falla de motor");

        assertThrows(InsufficientStockException.class,
                () -> service.addSparePart(order.getId(), "R1", 5));
    }

    @Test
    void changeStatusUpdatesOrderStatus() {
        ServiceOrder order = service.createOrder(PLATE, MECHANIC_ID, "Falla de motor");

        service.changeStatus(order.getId(), OrderStatus.UNDER_REPAIR);

        assertEquals(OrderStatus.UNDER_REPAIR, service.findById(order.getId()).getStatus());
    }

    @Test
    void registerWorkedHoursUpdatesOrderHours() {
        ServiceOrder order = service.createOrder(PLATE, MECHANIC_ID, "Falla de motor");

        service.registerWorkedHours(order.getId(), 4.5);

        assertEquals(4.5, service.findById(order.getId()).getWorkHours());
    }

    @Test
    void closeOrderWithoutDiscountCalculatesCorrectTotal() {
        ServiceOrder order = service.createOrder(PLATE, MECHANIC_ID, "Falla de motor");
        service.registerWorkedHours(order.getId(), 2);

        ServiceOrder closed = service.closeOrder(order.getId(), 1);

        assertEquals(OrderStatus.READY_FOR_DELIVERY, closed.getStatus());
        assertEquals(45200, closed.getTotal(), 0.01);
    }

    @Test
    void closeOrderWithDiscountAppliesFivePercentOnLabor() {
        ServiceOrder order = service.createOrder(PLATE, MECHANIC_ID, "Falla de motor");
        service.registerWorkedHours(order.getId(), 2);

        ServiceOrder closed = service.closeOrder(order.getId(), 4);

        assertEquals(43200, closed.getTotal(), 0.01);
    }

    @Test
    void findByIdWithNonExistingIdThrowsException() {
        assertThrows(ServiceOrderNotFoundException.class, () -> service.findById(99));
    }
}
