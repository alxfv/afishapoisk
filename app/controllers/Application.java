package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.validation.Validation;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.lowagie.text.pdf.codec.Base64.InputStream;
import com.typesafe.plugin.*;

import play.Logger;
import play.mvc.*;
import play.api.templates.Html;
import play.data.*;

import models.*;
import util.pdf.PDF;
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

  public static Result test() {
    Logger.info(play.Play.application().path().toString());
    
//    File srcFile = new File("/var/www/afishapoisk/public/uploads/images/events/big/new.jpg");
//    File dstfile = new File("/var/www/afishapoisk/public/uploads/images/events/big/super.jpg");
//    try {
//      FileOutputStream dstFos = new FileOutputStream(dstfile);
//      FileInputStream srcFos = new FileInputStream(srcFile);
//      
//      byte[] buffer = new byte[256];
//      int bytesRead;
//      
//      do {
//        bytesRead = srcFos.read(buffer, 0, 256);
//        dstFos.write(buffer, 0, bytesRead);
//      } while (bytesRead > 0);
//      dstFos.close();
//    } catch (Exception e) {
//      Logger.info(e.getMessage());
//    }
//    
//    try {
//      Connection conn = DriverManager.getConnection("sdf");
//      PreparedStatement pstmt = conn.prepareStatement("UPDATE asdfsdf ");
//    } catch (Exception e) {
//      
//    }
    
    Event event = Event.find.byId(Long.valueOf(4));
    
    return ok(event.place.name);
  }
  
  public static Result mail() {
    MailerPlugin plugin = play.Play.application().plugin(MailerPlugin.class);
    MailerAPI mail = plugin.email();
    
    mail.setSubject("mailer");
    mail.addRecipient("faost@mail.ru", "Aleksandr <faostb@gmail.com>");
    mail.addFrom("AfishaPoisk <info@afishapoisk.ru>");
    //sends html
//    mail.sendHtml("<html>html</html>" );
    
    return ok("has been sent");
  }
  
  public static Result index() {
    Form<Search> searchForm = form(Search.class);
    return ok(
      index.render(Event.all(), searchForm.fill(new Search("2", 3)))
    );
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
  
  public static Result signup() {
    return ok(
      signup.render(form(User.class))
    );
  }
  
  public static Result signupSubmit() {
    Form<User> filledForm = form(User.class).bindFromRequest();
    
//      // Check accept conditions
//      if(!"true".equals(filledForm.field("accept").value())) {
//          filledForm.reject("accept", "You must accept the terms and conditions");
//      }
    
    // Check repeated password
    if(!filledForm.field("password").valueOr("").isEmpty()) {
      if(!filledForm.field("password").valueOr("").equals(filledForm.field("repeatPassword").value())) {
        filledForm.reject("repeatPassword", "Password don't match");
      }
    }
    
    if (!filledForm.field("name").valueOr("").isEmpty()) {
      String name = filledForm.field("name").value();
      String email = filledForm.field("email").value();
      
      if (!User.isUniqueName(name)) {
        filledForm.reject("name", "Это имя уже занято");
      }
      if (!User.isUniqueEmail(email)) {
        filledForm.reject("email", "Этот email уже используется");
      }
    }
    
//      // Check if the username is valid
//      if(!filledForm.hasErrors()) {
//          if(filledForm.get().name.equals("admin") || filledForm.get().username.equals("guest")) {
//              filledForm.reject("username", "This username is already taken");
//          }
//      }
    
    if (filledForm.hasErrors()) {
      return badRequest(signup.render(filledForm));
    } else {
      User.create(filledForm.get());
      return redirect(routes.Users.settings());
    }
  }
}