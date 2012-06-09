package models;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.yaml.snakeyaml.constructor.Construct;

import play.data.format.Formats;
import play.data.validation.*;

public class Search {
  
  public String people;
  
  @Formats.DateTime(pattern="dd.mm.YYYY")
  public Date date;
  
  @Constraints.Min(value=0)
  @Constraints.Max(value=6)
  public Integer activity;
  
  public Search() {
    
  }

//  public String validate() {
//    return "Invalid! Activity: ";
//  }
  
  public Search(String initPeople, Integer initActivity) {
    people = initPeople;
    activity = initActivity;
  }
  
  public static Map<String, String> options() {
    LinkedHashMap<String, String> options = new LinkedHashMap<String, String>();
    
    options.put("1", "Один");
    options.put("2", "С друзьями");
    
    return options;
  }
}
