package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain;

import java.util.Objects;

public class Call {
    private int telephone_Number;
    private int destinationTelephoneNumber;
    private String startDate;
    private String endDate;

    public Call() {
    }

    public Call(int telephone_Number, int destinationTelephoneNumber, String startDate, String endDate) {
        this.telephone_Number = telephone_Number;
        this.destinationTelephoneNumber = destinationTelephoneNumber;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getTelephone_Number() {
        return telephone_Number;
    }

    public void setTelephone_Number(int telephone_Number) {
        this.telephone_Number = telephone_Number;
    }

    public int getDestinationTelephoneNumber() {
        return destinationTelephoneNumber;
    }

    public void setDestinationTelephoneNumber(int destinationTelephoneNumber) {
        this.destinationTelephoneNumber = destinationTelephoneNumber;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEnd_tDate(String end_tDate) {
        this.endDate = end_tDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Call call = (Call) o;
        return telephone_Number == call.telephone_Number &&
                destinationTelephoneNumber == call.destinationTelephoneNumber &&
                Objects.equals(startDate, call.startDate) &&
                Objects.equals(endDate, call.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(telephone_Number, destinationTelephoneNumber, startDate, endDate);
    }

    @Override
    public String toString() {
        return "Call{" +
                "telephone_Number=" + telephone_Number +
                ", destination_Telephone_Number=" + destinationTelephoneNumber +
                ", start_Date='" + startDate + '\'' +
                ", end_tDate='" + endDate + '\'' +
                '}';
    }
}
