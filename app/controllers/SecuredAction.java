package controllers;

import models.User;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

public class SecuredAction extends Action.Simple {

    @Override
    public Result call(Http.Context context) throws Throwable {
        String authUser = context.session().get(Login.AUTH_USER);
        String authToken = context.session().get(Login.AUTH_TOKEN);
        if (authUser == null || authUser.isEmpty() || authToken == null || authToken.isEmpty()) {
            return gotoLoginPage();
        }

        long userId = Long.parseLong(authUser);
        User user = User.FINDER.byId(userId);
        if(user == null) {
            return gotoLoginPage();
        }
        String computedToken = Login.computeAuthenticationToken(user);
        if (computedToken.equals(authToken)) {
            return delegate.call(context);
        }
        return gotoLoginPage();
    }

    private Result gotoLoginPage() {
        return redirect(routes.Login.getLogin());
    }
}
