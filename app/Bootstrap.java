import java.util.List;

import controllers.StationCtrl;
import play.*;
import play.jobs.*;
import play.test.*;

import models.*;

@OnApplicationStart
public class Bootstrap extends Job
{
    public void doJob()
    {
        if (Reading.count() == 0)
        {
            Fixtures.loadModels("data.yml");
        }
    }
}
