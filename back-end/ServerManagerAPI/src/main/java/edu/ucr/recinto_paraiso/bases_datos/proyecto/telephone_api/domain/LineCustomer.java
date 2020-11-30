package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain;

import java.util.Objects;

public class LineCustomer {
     private int telephone_Number;
     private int id;
     private String firstName;
     private String lastName;
     private String address;
     private String email;

    public LineCustomer() {
    }

    public LineCustomer(int telephone_Number, int id, String firstName, String last_Name, String address, String email) {
        this.telephone_Number = telephone_Number;
        this.id = id;
        this.firstName = firstName;
        this.lastName = last_Name;
        this.address = address;
        this.email = email;
    }

    public int getTelephone_Number() {
        return telephone_Number;
    }

    public void setTelephone_Number(int telephone_Number) {
        this.telephone_Number = telephone_Number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineCustomer that = (LineCustomer) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "LineCustomer{" +
                "telephone_Number=" + telephone_Number +
                ", id=" + id +
                ", first_Name='" + firstName + '\'' +
                ", last_Name='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
