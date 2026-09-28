package co.edu.uptc.application.service;

import co.edu.uptc.application.dto.Invoice;
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
			throw new IllegalArgumentException("Orden de servicio no encontrada: " + orderId);
		}

		double labor = order.calculateLaborCost();
		double materials = order.calculateMaterialsCost();
		double discount = order.calculateDiscount(clientCompletedServices);
		double taxes = (labor + materials) * 0.13;
		double total = order.calculateSubtotal() + taxes - discount;

		Invoice invoice = new Invoice(orderId, labor, materials, taxes, discount, total);
		return invoice;
	}

}
