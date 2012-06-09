package models;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.validation.Length;

import play.Logger;
import play.data.validation.*;
import play.data.validation.Constraints.MaxLength;
import play.db.ebean.Model;

@Entity
public class Event extends Model {

  @Id
  public Long id;
  
  @Constraints.Required
  @Length(max=64)
  public String title;
  
  @Lob
  @Basic(fetch=FetchType.EAGER)
  @MaxLength(value=20)
  public String info;
  
  public Boolean front;
    
  @ManyToOne
  @Constraints.Required
  public Category category;
  
  public static Model.Finder<Long, Event> find = new Model.Finder<Long, Event>(Long.class, Event.class);
  
  public static List<Event> all() {
    return find.all();
  }
  
  public static void create(Event event) {
    event.save();
  }
  
  public static void update(Event event) {
    event.update();   
  }
  
  public static void delete(Event event) {
    event.delete();
  }
}
