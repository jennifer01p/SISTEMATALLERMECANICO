package co.edu.uptc.domain.model;

public class SupplyConsumption {
    private String code;
    private String name;
    private int quantity;
    private double unitPrice;
    private double subtotal;


    public SupplyConsumption() {
    }

    public SupplyConsumption(String code, String name, int quantity, double unitPrice, double subtotal) {
        this.code = code;
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subtotal = subtotal;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "SupplyConsumption [code=" + code + ", name=" + name + ", quantity=" + quantity + ", unitPrice="
                + unitPrice + ", subtotal=" + subtotal + "]";
    }
}
