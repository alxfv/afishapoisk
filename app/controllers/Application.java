package controllers;

import play.*;
import play.mvc.*;
import play.data.*;

import models.*;
import views.html.*;

public class Application extends Controller {

  public static Result index() {
    return ok(index.render(Event.all(), form(Search.class)));
  }
  
  public static Result search() {
    Form<Search> searchForm = form(Search.class).bindFromRequest();
    if (searchForm.hasErrors()) {
      return badRequest(search.render(Event.all(), searchForm));
    }
    Search s = searchForm.get();
    return ok(search.render(Event.all(), searchForm));
  }
  
  /**
   * TODO: Using actions composition
   * http://stackoverflow.com/questions/9629250/how-to-avoid-passing-parameters-everywhere-in-play2
   * 
   * @return String html  
   */
  public static String nav() {
    return nav.render(Category.allFront()).body();
  }
}