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

    public static Result add() {
        Form<Category> filledForm = form(Category.class).bindFromRequest();
        if(filledForm.hasErrors()) {
            return badRequest(views.html.index.render(Category.all(), filledForm));
        } else {
            Category.create(filledForm.get());
            return redirect(routes.Application.index());  
        }
    }
    
    public static Result delete(Long id) {
        Category.delete(id);
        return redirect(routes.Application.index());
    }
}
