package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.Constraint;

import ch.qos.logback.core.joran.spi.DefaultClass;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.validation.Length;

import play.Logger;
import play.data.format.*;
import play.data.validation.*;
import play.data.validation.Constraints.MaxLength;
import play.db.ebean.Model;

@Entity
public class Event extends Model {

  @Id
  public Long id;
  
  @Constraints.Required
  @Constraints.MaxLength(value=64)
  @Length(max=64)
  public String title;
  
  @Length(max=128)
  @Constraints.MaxLength(value=128)
  public String teaser;
  
  @Lob
  @Basic(fetch=FetchType.EAGER)
  public String info;
  
  public Boolean front;
  
  public Boolean publish;
  
  @Formats.DateTime(pattern="dd.MM.yyyy HH:mm")
  public Date start;
  
  public Date created;
  
  public Date updated;
    
  @ManyToOne
  @Constraints.Required
  public Category category;
  
  @ManyToOne
  public Place place;
  
//  @Constraints.Required
  public String image;
  
  public static Model.Finder<Long, Event> find = new Model.Finder<Long, Event>(Long.class, Event.class);
  
  public static List<Event> all() {
    return find.all();
  }
  
  public static void create(Event event) {
    event.created = new Date();
    event.save();
  }
  
  public static void update(Event event) {
    event.updated = new Date();
    event.update();   
  }
  
  public static void delete(Event event) {
    event.delete();
  }
}
