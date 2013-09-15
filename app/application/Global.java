package application;

import com.avaje.ebean.Ebean;
import models.Credentials;
import models.Role;
import models.User;
import play.Application;
import play.GlobalSettings;

import java.util.List;

public class Global extends GlobalSettings {

    @Override
    public void onStart(Application application) {
        super.onStart(application);
        List<User> all = User.FINDER.all();
        Ebean.delete(all);

        Credentials credentials = new Credentials("test@example.com", "start123");
        Ebean.save(new User("Klaus", "Kleber", credentials, Role.TEAMLEAD));
    }
}
