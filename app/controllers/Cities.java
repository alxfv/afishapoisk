package controllers;

import models.City;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;

import views.html.cities.*;

public class Cities extends Controller {

  public static String list() {
    String cityId = session("city");
    if (cityId == null) {
      cityId = "1";
    }
    Long id = Long.parseLong(cityId);
    
    City city = City.find.byId(id);
    
    return list.render(city, City.findAll()).body();
  }
  
  public static Result change(Long id) {
    if (City.find.byId(id) != null) {
      session("city", id.toString());
    }
    return redirect(routes.Application.index());
  }
}
