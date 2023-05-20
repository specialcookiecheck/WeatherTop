package models;

import com.sun.tools.javac.comp.Todo;
import play.db.jpa.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class Member extends Model {
    public String firstname;
    public String lastname;
    public String email;
    public String password;

    @OneToMany(cascade = CascadeType.ALL)
    public List<Station> stations = new ArrayList<Station>();

    // constructor for Member class
    public Member(String firstname, String lastname, String email, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    // finds and returns a specific member based on their email address
    public static Member findByEmail(String email) {
        return find("email", email).first();
    }

    // checks whether a password (parameter) is equal to the existing password for the member
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}