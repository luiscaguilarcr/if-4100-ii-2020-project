package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.builders;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.Service;

public class ServiceBuilder {

    private  int serviceCode;
    private  String name ;
    private  String description ;
    private  int cost ;
    private  String status;

    public ServiceBuilder setServiceCode(int serviceCode) {
        this.serviceCode = serviceCode;
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
        return new Service(this.serviceCode, this.name, this.description, this.cost, this.status);
    }
}
