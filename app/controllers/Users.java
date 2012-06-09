package controllers;

import play.mvc.*;
import views.html.users.*;

public class Users extends Controller {

  public static Result settings() {
    String name = session("name");
    
    if (name != null) {
      return ok(settings.render(name));
    }
    return redirect(controllers.routes.Application.login());
  }
}
