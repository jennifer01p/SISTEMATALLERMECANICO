package co.edu.uptc.application.dto;

public class ProductivityEntry {
    private int mechanicId;
    private String mechanicName;
    private double hoursWorked;

    public ProductivityEntry() {
    }

    public ProductivityEntry(int mechanicId, String mechanicName, double hoursWorked) {
        this.mechanicId = mechanicId;
        this.mechanicName = mechanicName;
        this.hoursWorked = hoursWorked;
    }

    public void addHours(double hours) {
        this.hoursWorked += hours;
    }

    public int getMechanicId() {
        return mechanicId;
    }

    public String getMechanicName() {
        return mechanicName;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    @Override
    public String toString() {
        return "Mecánico " + mechanicId + " - " + mechanicName + " | Horas trabajadas: " + hoursWorked;
    }
}