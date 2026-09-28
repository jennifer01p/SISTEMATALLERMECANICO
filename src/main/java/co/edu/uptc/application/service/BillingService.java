package co.edu.uptc.application.service;

import co.edu.uptc.application.dto.Invoice;
import co.edu.uptc.domain.exception.ServiceOrderNotFoundException;
import co.edu.uptc.domain.model.ServiceOrder;
import co.edu.uptc.domain.repository.IServiceOrderRepository;

public class BillingService implements IBillingService {

    private final IServiceOrderRepository orderRepository;

    public BillingService(IServiceOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Invoice generateInvoice(int orderId, int clientCompletedServices) {
        ServiceOrder order = orderRepository.findById(orderId);
        if (order == null) {
            throw new ServiceOrderNotFoundException("No hay ordenes de servicio con ese id");
        }

        double labor = order.calculateLaborCost();
        double materials = order.calculateMaterialsCost();
        double taxes = order.calculateTaxes();
        double discount = order.calculateDiscount(clientCompletedServices);
        double total = order.calculateTotal(clientCompletedServices);

        return new Invoice(orderId, labor, materials, taxes, discount, total);
    }

}