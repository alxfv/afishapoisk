package controllers;

import play.mvc.*;
import play.data.*;

import models.*;
import views.html.*;

public class Application extends Controller {

  public static class Login {
    
    public String name;
    public String password;
    
    public String validate() {
        if(User.authenticate(name, password) == null) {
            return "Invalid user or password" + name + password;
        }
        return null;
    }
  }
  
  public static Result index() {
    Form<Search> searchForm = form(Search.class);
    searchForm.fill(new Search("2", 3));
    return ok(index.render(Event.all(), searchForm));
  }

  /**
   * Login page.
   */
  public static Result login() {
    return ok(
      login.render(form(Login.class))
    );
  }
  
  /**
   * Handle login form submission.
   */
  public static Result authenticate() {
    Form<Login> loginForm = form(Login.class).bindFromRequest();
    if(loginForm.hasErrors()) {
      return badRequest(login.render(loginForm));
    } else {
      session("name", loginForm.get().name);
      return redirect(
        routes.Application.index()
      );
    }
  }

  /**
   * Logout and clean the session.
   */
  public static Result logout() {
    session().clear();
    flash("success", "You've been logged out");
    return redirect(
      routes.Application.login()
    );
  }
  
  public static Result search() {
    Form<Search> searchForm = form(Search.class).bindFromRequest();
//    Logger.info(searchForm.get().activity.toString());
    if (searchForm.hasErrors()) {
      return badRequest(index.render(Event.all(), searchForm));
    }
    return ok(search.render(Event.all(), searchForm));
  }
  
  public static String bar() {
    String name = session("name");
//    if (name != null) {
      return logged.render(name).body();
//    }
//    return anonymous.render().body();
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