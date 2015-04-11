package tbrown.com.woodbuffalotransitmockup.datamodels;

/**
 * Created by tmast_000 on 4/5/2015.
 */
public class StopOverview {
    protected String route;
    protected String name;

    protected static final String ROUTE_PREFIX = "Route_";
    protected static final String NAME_PREFIX = "Name_";

    public String getName() {
        return name;
    }

    public StopOverview(String name) {
        this.name = name;
    }
}
