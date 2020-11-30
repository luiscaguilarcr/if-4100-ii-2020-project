package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.builders;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.Call;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.Line;

public class CallBuilder {
    private int telephone_Number;
    private int destination_Telephone_Number;
    private String start_Date;
    private String end_tDate;

    public void setTelephone_Number(int telephone_Number) {
        this.telephone_Number = telephone_Number;
    }

    public void setDestination_Telephone_Number(int destination_Telephone_Number) {
        this.destination_Telephone_Number = destination_Telephone_Number;
    }

    public void setStart_Date(String start_Date) {
        this.start_Date = start_Date;
    }

    public void setEnd_tDate(String end_tDate) {
        this.end_tDate = end_tDate;
    }

    public Call build(){
        return new Call(this.telephone_Number, this.destination_Telephone_Number, this.start_Date, this.end_tDate);
    }

}
