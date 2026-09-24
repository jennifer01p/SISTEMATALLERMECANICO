package co.edu.uptc.domain.model;
import java.time.LocalDate;
import java.util.List;

import co.edu.uptc.enums.OrderStatus;

public class ServiceOrder {
    
    private int id ; 
    private Vehicle vehicle;
    private Mechanic mechanic;
    private List<SupplyConsumption> supplyConsumptions;
    private LocalDate entryDate;
    private String diagnosis; 
    private double workHours;
    private OrderStatus status;
    private double discount ; 
    private double total;

    public ServiceOrder() {
    }
    public ServiceOrder(int id, LocalDate entryDate, String diagnosis, double workHours, OrderStatus status,
            double discount, double total) {
        this.id = id;
        this.entryDate = entryDate;
        this.diagnosis = diagnosis;
        this.workHours = workHours;
        this.status = status;
        this.discount = discount;
        this.total = total;
    }
    public String getDiagnosis() {
        return diagnosis;
    }
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
    public OrderStatus getStatus() {
        return status;
    }
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    public double getDiscount() {
        return discount;
    }
    public void setDiscount(double discount) {
        this.discount = discount;
    }
    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }

    public double calculateLaborCost(){
        double laborCost = workHours * mechanic.getHourlyRate();
        return laborCost;
        
    }

    public double calculateMaterialsCost(){
        double materialsCost = 0;
        for (SupplyConsumption consumption : supplyConsumptions) {
            materialsCost += consumption.getUnitPrice() * consumption.getQuantity();
        }
        return materialsCost;
    }

    public double calculateSubtotal(){
        double subtotal = calculateLaborCost() + calculateMaterialsCost();
        return subtotal;
    }

    public double calculateTaxes(){
        double taxes = calculateSubtotal() * 0.13; 
        return taxes; 
    }

    public double calculateTotal(int completedService){
        total = calculateSubtotal()+calculateTaxes()-applyDiscount(completedService);
        return total;
    }

    public double applyDiscount(int completedService){
        if (completedService < 3 ) {
            discount = calculateSubtotal() * 0.05;
            return discount;   
        }
        return 0 ; 
    }




    @Override
    public String toString() {
        return "ServiceOrder [id=" + id + ", entryDate=" + entryDate + ", diagnosis=" + diagnosis + ", workHours="
                + workHours + ", status=" + status + ", discount=" + discount + ", total=" + total + "]";
    } 

    

    


}
