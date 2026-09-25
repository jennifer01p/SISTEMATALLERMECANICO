package co.edu.uptc.model.service;

import co.edu.uptc.domain.model.ServiceOrder;
import co.edu.uptc.model.repository.IServiceOrderRepository;
import co.edu.uptc.model.repository.ISparePartRepository;

public class BillingService {

    private final IServiceOrderRepository orderRepository;
    private final ISparePartRepository spareRepository;

    public BillingService(IServiceOrderRepository orderRepository, ISparePartRepository spareRepository) {
        this.orderRepository = orderRepository;
        this.spareRepository = spareRepository;
    }

    public Invoice generateInvoice(int orderId, int clientCompletedServices) {
        ServiceOrder order = orderRepository.findById(orderId);
        if (order == null) {
            return null;
        }

        double labor = order.calculateLaborCost();
        double materials = order.calculateMaterialsCost();
        double taxes = order.calculateTaxes();
        double discount = order.calculateDiscount(clientCompletedServices);
        double total = order.calculateTotal(clientCompletedServices);

        return new Invoice(orderId, labor, materials, taxes, discount, total);
    }

}
