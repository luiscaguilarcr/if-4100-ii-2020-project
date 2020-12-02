package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.builders;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.Call;

public class CallBuilder {
    private int id;
    private int telephoneNumber;
    private int destinationTelephoneNumber;
    private String start_Date;
    private String end_Date;

    public CallBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public CallBuilder setTelephoneNumber(int telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
        return this;
    }

    public CallBuilder setDestinationTelephoneNumber(int destinationTelephoneNumber) {
        this.destinationTelephoneNumber = destinationTelephoneNumber;
        return this;
    }

    public CallBuilder setStart_Date(String start_Date) {
        this.start_Date = start_Date;
        return this;
    }

    public CallBuilder setEnd_Date(String end_Date) {
        this.end_Date = end_Date;
        return this;
    }

    public Call build(){
        return new Call(this.id, this.telephoneNumber, this.destinationTelephoneNumber, this.start_Date, this.end_Date);
    }

}
