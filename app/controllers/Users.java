package controllers;

import com.avaje.ebean.Ebean;
import models.Credentials;
import models.Role;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;

import java.util.List;
import java.util.Map;

@With(SecuredAction.class)
public class Users extends Controller{

    public static Result index() {
        List<User> all = User.FINDER.all();
        return ok(views.html.userIndex.render(all));
    }

    public static Result getUserCreationForm() {
        return ok(views.html.userNew.render("Neuen User anlegen"));
    }

    public static Result addUser() {
        Map<String,String[]> values = request().body().asFormUrlEncoded();
        String[] firstnames = values.get("firstname");
        String[] lastnames = values.get("lastname");
        String[] emails = values.get("email");

        if(firstnames == null || firstnames.length != 1 || lastnames == null || lastnames.length != 1 || emails == null || emails.length != 1) {
            return ok(views.html.userNew.render("Bitte alle Felder ausfüllen!"));
        }

        User newUser = new User(lastnames[0], firstnames[0], new Credentials(emails[0], "start"), Role.TEAMMEMBER);
        Ebean.save(newUser);
        List<User> all = User.FINDER.all();
        return redirect(routes.Users.index());
    }

    public static Result updateUser(long id) {
        return TODO;
    }

    public static Result deleteUser(long id) {
        User user = User.FINDER.byId(id);
        Ebean.delete(user);
        return TODO;
    }

    public static Result getUser(long id) {
        return TODO;
    }

}
