package controllers;

import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import models.Event;
import play.Logger;
import play.mvc.*;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.data.*;
import play.data.validation.Validation;

import views.html.events.*;

public class Events extends Controller {

  public static Result show(Long id) {
    Event event = Event.find.byId(id);
    return ok(show.render(event));
  }

  public static Result add() {
    Event event = new Event();
    event.publish = true;
    event.start = new Date();
    Form<Event> eventForm = form(Event.class).fill(event);
    return ok(add.render(eventForm));
  }
  
  public static Result edit(Long id) {
    Event event = Event.find.byId(id);
    Form<Event> eventForm = form(Event.class).fill(event);
    return ok(edit.render(id, eventForm));
  }

  public static Result create() {
    Form<Event> eventForm = form(Event.class).bindFromRequest();
    if (eventForm.hasErrors() || validate(eventForm, null) == false) {
      return badRequest(add.render(eventForm));
    }
    Event event = eventForm.get();
    Event.create(event);
    return redirect(routes.Events.show(event.id));
  }
  
  public static Result update(Long id) {
    Form<Event> eventForm = form(Event.class).bindFromRequest();
    if (eventForm.hasErrors() || validate(eventForm, id) == false) {
      return badRequest(edit.render(id, eventForm));
    }
    Event event = eventForm.get();
    event.id = id;
    Whitelist whiteList = new Whitelist();
    event.title = Jsoup.clean(event.title, whiteList);
    whiteList.addTags("p", "ul", "ol", "li");
    event.info = Jsoup.clean(event.info, whiteList);
    Event.update(event);
    return redirect(routes.Events.show(event.id));
  }
  
  private static Boolean validate(Form<Event> eventForm, Long id) {
    Event event = eventForm.get();
    
    MultipartFormData body = request().body().asMultipartFormData();
    FilePart filePart = body.getFile("image");
    
    if (filePart != null) {
      String fileName = filePart.getFilename();
      Logger.info(filePart.getContentType());
      File file = filePart.getFile();

      Logger.info(file.getPath());
      
      BufferedImage image = null;
      try {
        image = ImageIO.read(file);
      } catch (IOException e) {
        Logger.info(e.getMessage());
      }
      
//      BufferedImage thumb = Scalr.resize(image, 1000);
      
      Integer width = play.Play.application().configuration().getInt("presets.big.width");
      Integer height = play.Play.application().configuration().getInt("presets.big.height");
      
      if (image.getWidth() < width || image.getHeight() < height) {
        eventForm.reject("image", String.format("Картинка должна быть не менее %d пикселей по ширине и %d пикселей по высоте", width, height));
        return false;
      }
      
      Integer defaultWidth = play.Play.application().configuration().getInt("images.default.width");
      Integer defaultHeight = play.Play.application().configuration().getInt("images.default.height");
      
      BufferedImage thumbImage = image;
      BufferedImage defaultImage = image;
      try {
        defaultImage = Scalr.resize(defaultImage, Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC, defaultWidth, defaultHeight);
        thumbImage = Scalr.resize(thumbImage, Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC, width, height);
        thumbImage = Scalr.crop(thumbImage, width, height);
      } catch (Exception e) {
        Logger.info(e.getMessage());
        eventForm.reject("image", "Ошибка при обработке изображения, загрузить другое изображение");
        return false;
      } 
      
      // random name
      // Example: 53c08e69d9a54ab89d190dde16b8dba4.jpg
      String imageName = UUID.randomUUID().toString().replaceAll("[-]", "") + ".jpg";
      
      // Example: big
      String presetName = play.Play.application().configuration().getString("presets.big.name");
      String categoryName = "events";
      
      // Example: /var/www/afishapoisk
      String rootPath = play.Play.application().path().toString();
      
      // Example: /events/presets/big
      String presetPath = "/" + categoryName + "/presets/" + presetName;
      
      // Example: /public/uploads/images
      String imagesPath = rootPath + play.Play.application().configuration().getString("images.uploads.path");
      
      // Example: /public/uploads/images/events/presets/big
      String fullPresetPath = imagesPath + presetPath;
      
      // Example: /public/uploads/images/events/default
      String fullDefaultPath = imagesPath + "/" + categoryName + "/default";

      File dir = new File(fullPresetPath);
      if (dir.exists() == false) {
        if (dir.mkdirs() == false) {
          Logger.info("Error while create directories " + fullPresetPath);
          eventForm.reject("image", "Ошибка при сохранении картинки. Попробуйте еще раз");
          return false;
        }
      }
      
      // delete old image
      if (id != null) {
        Event oldEvent = Event.find.byId(id);
        if (oldEvent.image != null) {
          File oldFile = new File(fullPresetPath + "/" + oldEvent.image);
          if (oldFile.exists()) {
            oldFile.delete();
          }
          oldFile = new File(fullDefaultPath + "/" + oldEvent.image);
          if (oldFile.exists()) {
            oldFile.delete();
          }
        }
      }
      
      String fullPresetName = fullPresetPath + "/" + imageName;
      String fullDefaultName = fullDefaultPath + "/" + imageName;
      
      File outputPreset = new File(fullPresetName);
      File outputDefault = new File(fullDefaultName);
      
      event.image = imageName;
      
//      for (String name : ImageIO.getWriterFormatNames()) {
//        Logger.info(name);
//      }
      
      try {
        ImageIO.write(thumbImage, "jpg", outputPreset);
        ImageIO.write(defaultImage, "jpg", outputDefault);
      } catch (IOException e) {
        Logger.info(e.getMessage());
        eventForm.reject("image", "Ошибка при сохранении картинки.");
        return false;
      }
      
      
//      Path source = Paths.get(file.getPath());
//      Path target = Paths.get("/home/faost/tmp/ok/new.jpg");
//      
//      try {
//        Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
//      } catch (IOException e) {
//        Logger.info(e.getMessage());
//      }
      
      
      // @see http://stackoverflow.com/questions/244164/resize-an-image-in-java-any-open-source-library
      // @see http://docs.oracle.com/javase/7/docs/api/java/awt/image/BufferedImage.html
      // resize
    }
    return true;
  }
  
  public static Result delete(Long id) {
    Event.find.ref(id).delete();
    flash("success", "Событие удалено");
    return redirect(routes.Application.index());
  }
}
