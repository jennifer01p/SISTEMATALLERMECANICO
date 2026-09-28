package repositoryTest;

import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.domain.model.ServiceOrder;
import co.edu.uptc.domain.repository.IServiceOrderRepository;

public class TestOrderRepository implements IServiceOrderRepository {

    private final List<ServiceOrder> orders = new ArrayList<>();

    @Override
    public boolean save(ServiceOrder order) {
        return orders.add(order);
    }

    @Override
    public ServiceOrder findById(int id) {
        return orders.stream().filter(o -> o.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<ServiceOrder> findAll() {
        return new ArrayList<>(orders);
    }

    @Override
    public ServiceOrder update(ServiceOrder order) {
        delete(order.getId());
        save(order);
        return order;
    }

    @Override
    public boolean delete(int id) {
        return orders.removeIf(o -> o.getId() == id);
    }

}
