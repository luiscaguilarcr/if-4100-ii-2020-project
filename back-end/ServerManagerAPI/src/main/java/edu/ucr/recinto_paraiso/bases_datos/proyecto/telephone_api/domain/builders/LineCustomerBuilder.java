package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.builders;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.LineCustomer;

public class LineCustomerBuilder {
    private int telephone_Number;
    private int id;
    private String first_Name;
    private String last_Name;
    private String address;
    private String email;

    public LineCustomerBuilder setTelephone_Number(int telephone_Number) {
        this.telephone_Number = telephone_Number;
        return this;
    }

    public LineCustomerBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public LineCustomerBuilder setFirst_Name(String first_Name) {
        this.first_Name = first_Name;
        return this;
    }

    public LineCustomerBuilder setLast_Name(String last_Name) {
        this.last_Name = last_Name;
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
        return new LineCustomer(telephone_Number, id, first_Name, last_Name, address, email);
    }
}
