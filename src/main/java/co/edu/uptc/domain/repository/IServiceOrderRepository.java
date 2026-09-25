package co.edu.uptc.domain.repository;

import java.util.List;

import co.edu.uptc.domain.model.ServiceOrder;

public interface IServiceOrderRepository {

    boolean save(ServiceOrder order);

    ServiceOrder findById(int id);

    List<ServiceOrder> findAll();

    ServiceOrder update(ServiceOrder order);

    boolean delete(int id);

}
