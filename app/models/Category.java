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
    
    public static Model.Finder<Long,Category> find = new Model.Finder(Long.class, Category.class);
    
    public static List<Category> all() {
        return find.all();
    }
    
    public static void create(Category category) {
        category.save();
    }
    
    public static void delete(Long id) {
        find.ref(id).delete();
    }
}
