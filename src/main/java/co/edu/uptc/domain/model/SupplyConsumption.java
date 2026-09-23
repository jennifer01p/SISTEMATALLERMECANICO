package co.edu.uptc.domain.model;

public class SupplyConsumption {

    private int quantity;
    private double unitPrice;
    private double subtotal;


    public SupplyConsumption() {
    }

    public SupplyConsumption(int quantity, double unitPrice) {
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subtotal = quantity * unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }


    public void calculateSubtotal() {
        this.subtotal = this.quantity * this.unitPrice;
    }

    @Override
    public String toString() {
        return "SupplyConsumption [quantity=" + quantity + ", unitPrice=" + unitPrice + ", subtotal=" + subtotal + "]";
    }

    
    

    
}
