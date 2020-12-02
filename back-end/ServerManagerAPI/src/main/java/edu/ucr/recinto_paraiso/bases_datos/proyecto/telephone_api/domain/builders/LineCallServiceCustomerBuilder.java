package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.builders;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.LineCallServiceCustomer;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.LineCustomer;

public class LineCallServiceCustomerBuilder {
    private int telephoneNumber;
    private String customerId;
    private String customerFirstName;
    private String customerLastName;
    private String status;
    private String name;

    public LineCallServiceCustomerBuilder setTelephoneNumber(int telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
        return this;
    }

    public LineCallServiceCustomerBuilder setCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public LineCallServiceCustomerBuilder setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
        return this;
    }

    public LineCallServiceCustomerBuilder setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
        return this;
    }

    public LineCallServiceCustomerBuilder setStatus(String status) {
        this.status = status;
        return this;
    }

    public LineCallServiceCustomerBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public LineCallServiceCustomer build(){
        return new LineCallServiceCustomer(telephoneNumber, customerId, customerFirstName, customerLastName, status, name);
    }
}
