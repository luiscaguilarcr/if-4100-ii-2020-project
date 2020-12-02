package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.builders;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.Line;

public class LineBuilder {
    private int telephone_Number;
    private int pointsQuantity;
    private int type;
    private String status;

    public LineBuilder setTelephone_Number(int telephone_Number) {
        this.telephone_Number = telephone_Number;
        return this;
    }

    public LineBuilder setPointsQuantity(int pointsQuantity) {
        this.pointsQuantity = pointsQuantity;
        return this;
    }

    public LineBuilder setType(int type) {
        this.type = type;
        return this;
    }

    public LineBuilder setStatus(String status) {
        this.status = status;
        return this;
    }

    public Line build(){
        return new Line(this.telephone_Number, this.pointsQuantity, this.type, this.status);
    }
}
