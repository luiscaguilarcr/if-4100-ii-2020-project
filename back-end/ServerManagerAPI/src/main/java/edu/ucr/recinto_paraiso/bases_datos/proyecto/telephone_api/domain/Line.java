package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain;

import java.util.Objects;

public class Line {
    private int telephoneNumber;
    private int pointsQuantity;
    private int type;
    private String status;

    public Line() { }

    public Line(int telephoneNumber, int pointsQuantity, int type, String status) {
        this.telephoneNumber = telephoneNumber;
        this.pointsQuantity = pointsQuantity;
        this.type = type;
        this.status = status;
    }

    public int getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(int telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public int getPointsQuantity() {
        return pointsQuantity;
    }

    public void setPointsQuantity(int pointsQuantity) {
        this.pointsQuantity = pointsQuantity;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return telephoneNumber == line.telephoneNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(telephoneNumber);
    }

    @Override
    public String toString() {
        return "Line{" +
                "telephone_Number='" + telephoneNumber + '\'' +
                ", points_Quantity='" + pointsQuantity + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

