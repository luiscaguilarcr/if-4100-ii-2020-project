package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.builders;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.Call;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.Line;

public class CallBuilder {
    private int telephone_Number;
    private int destination_Telephone_Number;
    private String start_Date;
    private String end_Date;

    public CallBuilder setTelephone_Number(int telephone_Number) {
        this.telephone_Number = telephone_Number;
        return this;
    }

    public CallBuilder setDestination_Telephone_Number(int destination_Telephone_Number) {
        this.destination_Telephone_Number = destination_Telephone_Number;
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
        return new Call(this.telephone_Number, this.destination_Telephone_Number, this.start_Date, this.end_Date);
    }

}
