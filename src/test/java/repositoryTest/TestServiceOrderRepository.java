package repositoryTest;

import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.domain.model.ServiceOrder;
import co.edu.uptc.domain.repository.IServiceOrderRepository;

public class TestServiceOrderRepository implements IServiceOrderRepository {

    private List<ServiceOrder> orders = new ArrayList<>();
    private int nextId = 1;

    @Override
    public boolean save(ServiceOrder order) {
        order.setId(nextId);
        nextId++;
        orders.add(order);
        return true;
    }

    @Override
    public ServiceOrder findById(int id) {
        for (ServiceOrder o : orders) {
            if (o.getId() == id) {
                return o;
            }
        }
        return null;
    }

    @Override
    public List<ServiceOrder> findAll() {
        return new ArrayList<>(orders);
    }

    @Override
    public ServiceOrder update(ServiceOrder order) {
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getId() == order.getId()) {
                orders.set(i, order);
                return order;
            }
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        return orders.removeIf(o -> o.getId() == id);
    }

}