package co.edu.uptc.application.service;

import co.edu.uptc.application.dto.Invoice;

public interface IBillingService {

    Invoice generateInvoice(int orderId, int clientCompletedServices);

}