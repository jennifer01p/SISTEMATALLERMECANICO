package co.edu.uptc.domain.model;

public class Client extends Person {

    public Client() {
        super();
    }

    public Client(int id, String name, String phone) {
        super(id, name, phone);
    }

    @Override
    public String toString() {
        return "Cliente\n" +
           "ID:        " + getId() + "\n" +
           "Nombre:    " + getName() + "\n" +
           "Teléfono:  " + getPhone() + "\n" ;
           
    }

    



    

}
