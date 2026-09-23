package co.edu.uptc.domain.model;

public class SparePart {

    private String code;
    private String name;
    private double unitPrice;
    private int stock;


    public SparePart() {
    }

    public SparePart(String code, String name, double unitPrice, int stock) {
        this.code = code;
        this.name = name;
        this.unitPrice = unitPrice;
        this.stock = stock;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean ishasStock(int quantity) {
        if(stock >= quantity) {
            return true;
        } else {
            return false;

        }
    }

    public boolean isUpdateStock(int quantity) {
        if(stock >= quantity) {
            stock -= quantity;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "SparePart [code=" + code + ", name=" + name + ", unitPrice=" + unitPrice + ", stock=" + stock + "]";
    }

    

}
