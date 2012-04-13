package controllers;

import play.*;
import play.mvc.*;
import play.data.*;

import java.util.*;

import models.*;

import views.html.categories.*;

public class Categories extends Controller {

  public static Result show(Long id) {
    Category category = Category.find.byId(id);
    return ok(show.render(category));
  }
  
  public static Result list() {
    return ok(list.render(Category.all()));
  }
  
  public static Result add() {
    Form<Category> categoryForm = form(Category.class);
    return ok(add.render(categoryForm));
  }

  public static Result create() {
    Form<Category> categoryForm = form(Category.class).bindFromRequest();
    if(categoryForm.hasErrors()) {
      return badRequest(add.render(categoryForm));
    } 
    Category.create(categoryForm.get());
    return redirect(routes.Categories.list());  
  }
  
  public static Result delete(Long id) {
    Category.delete(id);
    return redirect(routes.Application.index());
  }
}
