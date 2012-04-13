package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.validation.*;

@Entity
public class Category extends Model {

    @Id
    public Long id;

    @Constraints.Required
    public String title;
    
    public Boolean front;
    
    @OneToMany(mappedBy="category")
    public List<Event> events;
    
    public static Model.Finder<Long,Category> find = new Model.Finder(Long.class, Category.class);
    
    public static List<Category> all() {
      return find.all();
    }
    
    public static List<Category> allFront() {
      return find.where().eq("front", 1).findList();
    }
    
    public static void create(Category category) {
      category.save();
    }
    
    public static void delete(Long id) {
      find.ref(id).delete();
    }
}
