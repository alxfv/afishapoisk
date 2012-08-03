package controllers;

import models.Place;
import play.mvc.Controller;
import play.mvc.Result;

import views.html.places.*;

public class Places extends Controller {

  public static Result list() {
    return TODO;
  }
  
  public static Result show(Long id) {
    Place place = Place.find.byId(id);
    return ok(show.render(place));
  }
  
  public static Result add() {
    return TODO;
  }
  
  public static Result create() {
    return TODO;
  }
  
  public static Result edit(Long id) {
    return TODO;
  }

  public static Result update(Long id) {
    return TODO;
  }
  
  public static Result delete(Long id) {
    return TODO;
  }
}
