package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Constraint;

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
  @Column(unique=true, nullable=false)
  public String name;
  
  @Constraints.Required
  @Constraints.Email
  @Formats.NonEmpty
  @Column(unique=true, nullable=false)
  public String email;
  
  @Constraints.Required
  @Column(nullable=false)
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
  
  public static boolean isUniqueEmail(String email) {
    int rowCount =  find.where().eq("email", email).findRowCount();
    if (rowCount > 0) {
      return false;
    }
    return true;
  }
  
  public static boolean isUniqueName(String name) {
//    User user = find.where()
//      .eq("name", name)
//      .findUnique();
//    
//    if (user == null) {
//      return true;
//    }
//    return false;
    
    int rowCount =  find.where().eq("name", name).findRowCount();
    if (rowCount > 0) {
      return false;
    }
    return true;
  }
  
  public String toString() {
    return name;
  }
  
  public static void create(User user) {
    user.save();
  }
}
