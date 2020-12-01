package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.builders;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.Line;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.Service;

public class ServiceBuilder {

    private  int service_Code ;
    private  String name ;
    private  String description ;
    private  int cost ;
    private  String status;

    public void setService_Code(int service_Code) {
        this.service_Code = service_Code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Service build(){
        return new Service(this.service_Code, this.name, this.description, this.cost, this.status);
    }
}
