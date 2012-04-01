package models;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.format.*;
import play.db.ebean.*;

@Entity
public class Category extends Model {

    @Id
    public Long id;

    public String title;
    
    public Long test;
    
    public static Finder<Long, Category> find = new Finder(Long.class, Category.class);
    
    public static List<Category> all() {
        return find.all();
    }
    
    public static void create(Category category) {
        category.save();
    }
}
