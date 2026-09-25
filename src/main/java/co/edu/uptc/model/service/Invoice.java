package co.edu.uptc.model.service;

public class Invoice {
    private int orderId;
    private double laborCost;
    private double materialsCost;
    private double taxes;
    private double discount;
    private double total;

    public Invoice() {}

    public Invoice(int orderId, double laborCost, double materialsCost, double taxes, double discount, double total) {
        this.orderId = orderId;
        this.laborCost = laborCost;
        this.materialsCost = materialsCost;
        this.taxes = taxes;
        this.discount = discount;
        this.total = total;
    }

    public int getOrderId() {
        return orderId;
    }

    public double getLaborCost() {
        return laborCost;
    }

    public double getMaterialsCost() {
        return materialsCost;
    }

    public double getTaxes() {
        return taxes;
    }

    public double getDiscount() {
        return discount;
    }

    public double getTotal() {
        return total;
    }

}
