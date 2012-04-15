package controllers;

import models.Category;
import models.Event;
import play.*;
import play.mvc.*;
import play.data.*;

import views.html.events.*;

public class Events extends Controller {

  public static Result show(Long id) {
    return TODO;
  }
  
  public static Result add() {
    Form<Event> eventForm = form(Event.class);
    return ok(add.render(eventForm));
  }

  public static Result create() {
    Form<Event> eventForm = form(Event.class).bindFromRequest();
    if (eventForm.hasErrors()) {
      return badRequest(add.render(eventForm));
    }
    Event.create(eventForm.get());
    return redirect(routes.Application.index());
  }
}
