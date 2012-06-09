import java.util.List;
import java.util.Map;

import models.Category;

import com.avaje.ebean.Ebean;

import play.*;
import play.libs.Yaml;
import play.mvc.Http.Context;

public class Global extends GlobalSettings {
  
  @Override
  public void onStart(Application app) {
    Logger.info("Load test data");
    InitialData.insert(app);
  }

  static class InitialData {
    
    public static void insert(Application app) {
      if (Ebean.find(Category.class).findRowCount() == 0) {

        Map<String,List<Object>> all = (Map<String,List<Object>>)Yaml.load("fixtures.yml");

        // Insert categories first
        Ebean.save(all.get("categories"));

        // Insert events
        Ebean.save(all.get("events"));

        // Insert users
        Ebean.save(all.get("users"));
        
        // Insert cities
        Ebean.save(all.get("cities"));
      }
    }
  }
}