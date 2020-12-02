package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain;

import java.util.Objects;

public class Call {
    private int noCall;
    private int telephoneNumber;
    private int destinationTelephoneNumber;
    private String startDate;
    private String endDate;

    public Call() {
    }

    public Call(int noCall, int telephoneNumber, int destinationTelephoneNumber, String startDate, String endDate) {
        this.noCall = noCall;
        this.telephoneNumber = telephoneNumber;
        this.destinationTelephoneNumber = destinationTelephoneNumber;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(int telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
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

    public void setEndDate(String end_tDate) {
        this.endDate = end_tDate;
    }

    public void setNoCall(int noCall) {
        this.noCall = noCall;
    }

    public int getNoCall() {
        return noCall;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Call call = (Call) o;
        return noCall == call.noCall;
    }

    @Override
    public int hashCode() {
        return Objects.hash(noCall);
    }
}
