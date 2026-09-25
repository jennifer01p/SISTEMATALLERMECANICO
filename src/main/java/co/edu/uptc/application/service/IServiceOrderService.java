package co.edu.uptc.application.service;

import java.util.List;

import co.edu.uptc.domain.model.ServiceOrder;
import co.edu.uptc.enums.OrderStatus;

public interface IServiceOrderService {

    ServiceOrder createOrder(int id, String vehiclePlate, int mechanicId, String diagnosis);

    boolean addSparePart(int orderId, String sparePartCode, int quantity);

    boolean changeStatus(int orderId, OrderStatus newStatus);

    boolean registerWorkedHours(int orderId, double hours);

    ServiceOrder closeOrder(int orderId, int clientCompletedServices);

    List<ServiceOrder> findAll();

    ServiceOrder findById(int id);

}
