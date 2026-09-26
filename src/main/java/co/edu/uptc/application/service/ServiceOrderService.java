package co.edu.uptc.application.service;

import java.time.LocalDate;
import java.util.List;

import co.edu.uptc.domain.exception.MechanicNotFoundException;
import co.edu.uptc.domain.exception.ServiceOrderNotFoundException;
import co.edu.uptc.domain.exception.VehicleNotFoundException;
import co.edu.uptc.domain.model.Mechanic;
import co.edu.uptc.domain.model.ServiceOrder;
import co.edu.uptc.domain.model.SparePart;
import co.edu.uptc.domain.model.SupplyConsumption;
import co.edu.uptc.domain.model.Vehicle;
import co.edu.uptc.domain.repository.IServiceOrderRepository;
import co.edu.uptc.enums.OrderStatus;

public class ServiceOrderService implements IServiceOrderService{

    private final IServiceOrderRepository repository;
    private final ISparePartService sparePartService;
    private final IVehicleRepository vehicleRepository;
    private final IMechanicRepository mechanicRepository;

    public ServiceOrderService(IServiceOrderRepository repository, ISparePartService sparePartService,
            IVehicleRepository vehicleRepository, IMechanicRepository mechanicRepository) {
        this.repository = repository;
        this.sparePartService = sparePartService;
        this.vehicleRepository = vehicleRepository;
        this.mechanicRepository = mechanicRepository;
    }

    @Override
    public ServiceOrder createOrder(int id, String vehiclePlate, int mechanicId, String diagnosis) {
        Vehicle vehicle = vehicleRepository.findByLicensePlate(vehiclePlate);
        Mechanic mechanic = mechanicRepository.findById(mechanicId);

        if(vehicle == null){
            throw new VehicleNotFoundException("No hay ningún vehículo con esa Placa");
        }
        if(mechanic == null){
            throw new MechanicNotFoundException("No existe ningun mecánico con ese Id");
        }

        ServiceOrder newServiceOrder = new ServiceOrder(id, vehicle, mechanic, LocalDate.now(), diagnosis, 0, OrderStatus.ENTERED, 0, 0);

        repository.save(newServiceOrder);
        return newServiceOrder;

    }

    @Override
    public boolean addSparePart(int orderId, String sparePartCode, int quantity) {
        ServiceOrder order = findById(orderId);
        sparePartService.discountStock(sparePartCode, quantity);
        SparePart part = sparePartService.findByCode(sparePartCode);
        SupplyConsumption consumption = new SupplyConsumption(part.getCode(), part.getName(), quantity, part.getUnitPrice(), part.getUnitPrice()*quantity);
        order.getSupplyConsumptions().add(consumption);
        repository.update(order);
        return true;
    }

    @Override
    public boolean changeStatus(int orderId, OrderStatus newStatus) {
        ServiceOrder order = findById(orderId);
        order.setStatus(newStatus);
        repository.update(order);
        return true;
    }

    @Override
    public boolean registerWorkedHours(int orderId, double hours) {
        ServiceOrder order = findById(orderId);

    }

    @Override
    public ServiceOrder closeOrder(int orderId, int clientCompletedServices) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'closeOrder'");
    }

    @Override
    public List<ServiceOrder> findAll() {
        return repository.findAll();
    }

    @Override
    public ServiceOrder findById(int id) {
        ServiceOrder order = repository.findById(id);
        if(order == null){
            throw new ServiceOrderNotFoundException("No hay ordenes de servicio con ese id");
        }
        return order;
    }

}
