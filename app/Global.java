import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import models.Category;

import com.avaje.ebean.Ebean;

import play.*;
import play.libs.Yaml;
import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.Http.Request;

public class Global extends GlobalSettings {
  
  @Override
  public void onStart(Application app) {
    Logger.info("Load test data");
    InitialData.insert(app);
  }

  @Override
  public Action onRequest(Request request, Method actionMethod) {
    if (Play.isDev()) {
      System.out.println("Request: " + request.toString());
      Ebean.getServer(null).getAdminLogging().setDebugGeneratedSql(true);
    }
    return super.onRequest(request, actionMethod);
  }

  static class InitialData {
    
    public static void insert(Application app) {
      if (Ebean.find(Category.class).findRowCount() == 0) {

        Map<String,List<Object>> all = (Map<String,List<Object>>)Yaml.load("fixtures.yml");

        // Insert categories first
        Ebean.save(all.get("categories"));

        Ebean.save(all.get("cities"));

        Ebean.save(all.get("places"));
        
        Ebean.save(all.get("events"));

        Ebean.save(all.get("users"));
      }
    }
  }
}