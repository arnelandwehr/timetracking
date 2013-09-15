package models;

import play.db.ebean.Model;

import javax.persistence.*;

@Entity
public class User extends Model {

    public static Finder<Long, User> FINDER = new Finder<Long, User>(Long.class, User.class);
    @Id
    @GeneratedValue
    private long id;
    private String firstName;
    private String lastName;
    @OneToOne(cascade = CascadeType.ALL)
    private Credentials credentials;
    private Role role;


    public User() {
    }

    public User(String lastName, String firstName, Credentials credentials, Role role) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.credentials = credentials;
        this.role = role;
    }

    public final boolean isUser(Credentials credentials) {
        return this.credentials.equals(credentials);
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public Role getRole() {
        return role;
    }
}
