package co.edu.uptc.domain.model;
import co.edu.uptc.enums.MechanicSpecialty;

public class Mechanic extends Person {

    private MechanicSpecialty specialty;
    private double hourlyRate;

    public Mechanic() {
        super();
    }

    public Mechanic(int id, String name, String phone, MechanicSpecialty specialty, double hourlyRate) {
        super(id, name, phone);
        this.specialty = specialty;
        this.hourlyRate = hourlyRate;
    }

    public MechanicSpecialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(MechanicSpecialty specialty) {
        this.specialty = specialty;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    @Override
    public String toString() {
        return "Mechanic [specialty=" + specialty + ", hourlyRate=" + hourlyRate + "]";
    }

    

}