package id.tsi.mmw.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import id.tsi.mmw.manager.*;
import id.tsi.mmw.util.log.AppLogger;

@WebListener
public class ContextListener implements ServletContextListener {

    public final AppLogger log;

    public ContextListener() {
        log = new AppLogger(this.getClass());
    }

    @Override
    public void contextInitialized(ServletContextEvent evt) {
        log.info("Application Init Started");
        PropertyManager.getInstance();
        EncryptionManager.init();
        ConnectionManager.getInstance();
        log.info("Application Init Completed");
    }

    @Override
    public void contextDestroyed(ServletContextEvent evt) {
        log.info("Application Shutdown Started");
        ConnectionManager.getInstance().shutdown();
        EncryptionManager.shutdown();
        log.info("Application Shutdown Completed");
    }

}
