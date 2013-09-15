package controllers;

import models.Credentials;
import models.User;
import org.apache.commons.codec.digest.DigestUtils;
import play.Play;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;
import java.util.Map;

public class Login extends Controller {

    public static final String AUTH_USER = "user";
    public static final String AUTH_TOKEN = "token";

    public static Result getLogin() {
        return ok(views.html.login.render("Bitte loggen sie sich ein"));
    }


    public static Result login() {
        Map<String, String[]> values = request().body().asFormUrlEncoded();
        String[] passwords = values.get("password");
        String[] emails = values.get("email");
        if (passwords == null || passwords.length != 1 ||  emails == null || emails.length != 1) {
            return unsuccessfulLogin();
        }
        Credentials credentials = new Credentials(emails[0], passwords[0]);
        List<User> all = User.FINDER.all();
        for (User user : all) {
            if(user.isUser(credentials)) {
                session(AUTH_USER, String.valueOf(user.getId()));
                session(AUTH_TOKEN, computeAuthenticationToken(user));
                return ok(views.html.indexTeamlead.render());
            }
        }
        return unsuccessfulLogin();
    }

    public static String computeAuthenticationToken(User user) {
        return DigestUtils.md5Hex(user.getFirstName() + user.getLastName() + Play.application().configuration().getString("application.secret"));
    }

    private static Status unsuccessfulLogin() {
        return ok(views.html.login.render("Email oder Passwort ist falsch, bitte erneut versuchen"));
    }

}
