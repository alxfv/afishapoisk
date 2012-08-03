package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.data.validation.*;

import play.db.ebean.Model;


@Entity
public class Place extends Model {

  @Id
  public Long id;
  
  @Constraints.Required
  public String name;
  
  @ManyToOne
  public City city;

  @OneToMany(mappedBy="place")
  public List<Event> events;
  
  @Lob
  @Basic(fetch=FetchType.EAGER)
  public String info;
  
  public String phone;
  
  @Constraints.Required
  public String address;
  
  public String url;
  
  public Double lat;
  
  public Double lng;
  
  public Date created;
  
  public Date updated;
  
  public static Model.Finder<Long, Place> find = new Model.Finder<Long, Place>(Long.class, Place.class);
  
  public static List<Place> all() {
    return find.all();
  }
  
  public static void create(Place place) {
    place.created = new Date();
    place.save();
  }
  
  public static void update(Place place) {
    place.updated = new Date();
    place.update();
  }
  
  @Override
  public String toString() {
    return name;
  }
  
  public List<Event> getEvents() {
    return events;
  }
}
