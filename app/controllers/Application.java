package controllers;

import models.*;

import play.*;
import play.data.Form;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

  static Form<Category> categoryForm = form(Category.class);

  public static Result index() {
    return ok(views.html.index.render(Category.all(), categoryForm));
  }
  
  public static Result showCategory(Long id) {
      return ok("Category");
  }
  
  public static Result newCategory() {
      Form<Category> filledForm = categoryForm.bindFromRequest();
      if(filledForm.hasErrors()) {
        return badRequest(
          views.html.index.render(Category.all(), filledForm)
        );
      } else {
        Category.create(filledForm.get());
        return ok("OK");
        //return redirect(routes.Application.index());  
      }
  }
}