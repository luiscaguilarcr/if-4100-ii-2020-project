package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain;

import java.util.Objects;

/**
 * User class represents the an user of the application.
 */
public class User {
    private final Integer id;
    private final String email;
    private final String password;
    private final String firstName;
    private final String lastName;

    /**
     * Constructor to be used by one UserBuilder
     * @param id the unique id of the user.
     * @param email of the user.
     * @param password of the user.
     * @param firstName of the user.
     * @param lastName of the user.
     */
    public User(Integer id, String email, String password, String firstName, String lastName) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    /**
     * The password is not included.
     *
     * @return User in JSON format.
     */
    public String toJSON(){
        return "{\"id\":\"" + id + "\", \"email\":\"" + email + "\", \"first_name\":\"" + firstName + "\", \"last_name\":\""+lastName+"\"}";
    }
}
