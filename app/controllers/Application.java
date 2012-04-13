package controllers;

import play.*;
import play.mvc.*;
import play.data.*;

import models.*;
import views.html.*;

public class Application extends Controller {

  public static Result index() {
    return ok(index.render(Category.allFront(), Event.all()));
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