package co.edu.uptc.domain.model;


public class Vehicle {
    private String licensePlate;
    private String brand;
    private String model;
    private String year;
    private double mileage;


    public Vehicle() {
    }


    public Vehicle(String licensePlate, String brand, String model, String year, double mileage) {
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
    }


    public double getMileage() {
        return mileage;
    }


    public void setMileage(double mileage) {
        this.mileage = mileage;
    }


    @Override
    public String toString() {
        return "Vehicle [licensePlate=" + licensePlate + ", brand=" + brand + ", model=" + model + ", year=" + year
                + ", mileage=" + mileage + "]";
    }

    

    

    
    
}
