package co.edu.uptc.model.repository;

import java.util.List;

import co.edu.uptc.domain.model.ServiceOrder;
import co.edu.uptc.enums.OrderStatus;

public interface IServiceOrderRepository {

    ServiceOrder createOrder(int id, String vehiclePlate, int mechanicId, String diagnosis);

    boolean addSparePart(int orderId, String sparePartCode, int quantity);

    boolean changeStatus(int orderId, OrderStatus newStatus);

    void registerWorkedHours(int orderId, double hours);

    ServiceOrder closeOrder(int orderId, int clientCompletedServices);

    List<ServiceOrder> findAll();

    ServiceOrder findById(int id);
    
}
