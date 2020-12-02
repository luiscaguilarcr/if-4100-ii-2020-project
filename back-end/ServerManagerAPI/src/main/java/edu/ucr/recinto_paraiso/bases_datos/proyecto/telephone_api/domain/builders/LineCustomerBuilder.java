package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.builders;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.LineCustomer;

public class LineCustomerBuilder {
    private int telephoneNumber;
    private int id;
    private String firstName;
    private String lastName;
    private String address;
    private String email;

    public LineCustomerBuilder setTelephoneNumber(int telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
        return this;
    }

    public LineCustomerBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public LineCustomerBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public LineCustomerBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public LineCustomerBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public LineCustomerBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public LineCustomer build(){
        return new LineCustomer(telephoneNumber, id, firstName, lastName, address, email);
    }
}
