package actions;

import play.Logger;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

public class VerboseAction extends Action.Simple {

  public Result call(Http.Context ctx) throws Throwable {
    Logger.info("Calling action for " + ctx);
    ctx.args.put("test", "test");
    return delegate.call(ctx);
  }

  public static String current() {
    return (String)Http.Context.current().args.get("test");
  }
}
