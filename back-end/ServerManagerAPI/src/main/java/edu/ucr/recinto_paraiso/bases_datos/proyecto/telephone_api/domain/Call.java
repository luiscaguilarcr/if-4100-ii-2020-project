package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain;

import java.util.Objects;

public class Call {
    private int telephone_Number;
    private int destination_Telephone_Number;
    private String start_Date;
    private String end_tDate;

    public Call() {
    }

    public Call(int telephone_Number, int destination_Telephone_Number, String start_Date, String end_tDate) {
        this.telephone_Number = telephone_Number;
        this.destination_Telephone_Number = destination_Telephone_Number;
        this.start_Date = start_Date;
        this.end_tDate = end_tDate;
    }

    public int getTelephone_Number() {
        return telephone_Number;
    }

    public void setTelephone_Number(int telephone_Number) {
        this.telephone_Number = telephone_Number;
    }

    public int getDestination_Telephone_Number() {
        return destination_Telephone_Number;
    }

    public void setDestination_Telephone_Number(int destination_Telephone_Number) {
        this.destination_Telephone_Number = destination_Telephone_Number;
    }

    public String getStart_Date() {
        return start_Date;
    }

    public void setStart_Date(String start_Date) {
        this.start_Date = start_Date;
    }

    public String getEnd_tDate() {
        return end_tDate;
    }

    public void setEnd_tDate(String end_tDate) {
        this.end_tDate = end_tDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Call call = (Call) o;
        return telephone_Number == call.telephone_Number &&
                destination_Telephone_Number == call.destination_Telephone_Number &&
                Objects.equals(start_Date, call.start_Date) &&
                Objects.equals(end_tDate, call.end_tDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(telephone_Number, destination_Telephone_Number, start_Date, end_tDate);
    }

    @Override
    public String toString() {
        return "Call{" +
                "telephone_Number=" + telephone_Number +
                ", destination_Telephone_Number=" + destination_Telephone_Number +
                ", start_Date='" + start_Date + '\'' +
                ", end_tDate='" + end_tDate + '\'' +
                '}';
    }
}
