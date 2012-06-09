package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.data.format.*;
import play.data.validation.*;

import play.db.ebean.Model;

@Entity
@Table(name="account")
public class User extends Model {

  @Id
  public Long id;

  @Constraints.Required
  @Formats.NonEmpty
  public String name;
  
  @Constraints.Required
  @Formats.NonEmpty
  public String email;
  
  @Constraints.Required
  public String password;
  
  public static Model.Finder<Long, User> find = new Model.Finder<Long, User>(Long.class, User.class);
  
  public static List<User> findAll() {
    return find.all();
  }
  
  public static User authenticate(String name, String password) {
    return find.where()
        .eq("name", name)
        .eq("password", password)
        .findUnique();
  }
  
  public String toString() {
    return name;
  }
}
