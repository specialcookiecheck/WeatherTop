import java.util.List;

import controllers.StationCtrl;
import play.*;
import play.jobs.*;
import play.test.*;

import models.*;

@OnApplicationStart
public class Bootstrap extends Job {
    // loads data from the .yml file if the online database is empty
    public void doJob() {
        if (Reading.count() == 0) {
            Fixtures.loadModels("data.yml");
        }
    }
}
