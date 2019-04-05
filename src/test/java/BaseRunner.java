import org.junit.After;
import org.junit.Before;

public class BaseRunner {

    public static ThreadLocal<Application> threadApp = new ThreadLocal<>();
    public Application app;

    @Before
    public void start() {
        if (threadApp.get() != null) {
            app = threadApp.get();
            return;
        }
        app = new Application();
        threadApp.set(app);
    }

    @After
    public void tearDown() {
        app.quit();
    }
}
