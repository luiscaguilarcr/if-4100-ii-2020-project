package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain;

import java.util.Objects;

public class Service {

    private  int service_Code ;
    private  String name ;
    private  String description ;
    private  int cost ;
    private  String status;


    public Service() {
    }

    public Service(int service_Code, String name, String description, int cost, String status) {
        this.service_Code = service_Code;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.status = status;
    }

    public int getService_Code() {
        return service_Code;
    }

    public void setService_Code(int service_Code) {
        this.service_Code = service_Code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
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
        Service service = (Service) o;
        return service_Code == service.service_Code;
    }

    @Override
    public int hashCode() {
        return Objects.hash(service_Code);
    }

    @Override
    public String toString() {
        return "Service{" +
                "service_Code='" + service_Code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", cost='" + cost + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

