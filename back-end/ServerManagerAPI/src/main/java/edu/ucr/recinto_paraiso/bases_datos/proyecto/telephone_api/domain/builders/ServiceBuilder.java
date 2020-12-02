package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.builders;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.Service;

public class ServiceBuilder {

    private  int service_Code ;
    private  String name ;
    private  String description ;
    private  int cost ;
    private  String status;

    public ServiceBuilder setService_Code(int service_Code) {
        this.service_Code = service_Code;
        return this;
    }

    public ServiceBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ServiceBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public ServiceBuilder setCost(int cost) {
        this.cost = cost;
        return this;
    }

    public ServiceBuilder setStatus(String status) {
        this.status = status;
        return this;
    }

    public Service build(){
        return new Service(this.service_Code, this.name, this.description, this.cost, this.status);
    }
}
