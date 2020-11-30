package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain;

import java.util.Objects;

public class Line {
    private int telephone_Number;
    private int points_Quantity;
    private int type;
    private String status;

    public Line() {
    }

    public Line(int telephone_Number, int points_Quantity, int type, String status) {
        this.telephone_Number = telephone_Number;
        this.points_Quantity = points_Quantity;
        this.type = type;
        this.status = status;
    }

    public int getTelephone_Number() {
        return telephone_Number;
    }

    public void setTelephone_Number(int telephone_Number) {
        this.telephone_Number = telephone_Number;
    }

    public int getPoints_Quantity() {
        return points_Quantity;
    }

    public void setPoints_Quantity(int points_Quantity) {
        this.points_Quantity = points_Quantity;
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
        return telephone_Number == line.telephone_Number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(telephone_Number);
    }

    @Override
    public String toString() {
        return "Line{" +
                "telephone_Number='" + telephone_Number + '\'' +
                ", points_Quantity='" + points_Quantity + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

