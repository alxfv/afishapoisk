package actions;

import play.Logger;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

public class SecuredAction extends Action.Simple {

  public Result call(Http.Context ctx) throws Throwable {
//    ctx.args.put("name", Http.Context.current().session().get("name"));
    ctx.args.put("test", "test");
    return delegate.call(ctx);
  }
  
  public static String current() {
    return (String)Http.Context.current().args.get("test");
  }
}
