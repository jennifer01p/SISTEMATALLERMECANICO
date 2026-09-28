package co.edu.uptc.application.dto;

public class SparePartUsage {
    private String code;
    private String name;
    private int quantity;

    public SparePartUsage() {
    }

    public SparePartUsage(String code, String name, int quantity) {
        this.code = code;
        this.name = name;
        this.quantity = quantity;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return code + " - " + name + " | Cantidad usada: " + quantity;
    }
}