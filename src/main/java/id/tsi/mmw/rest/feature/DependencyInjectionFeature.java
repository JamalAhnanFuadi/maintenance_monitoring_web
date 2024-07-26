package id.tsi.mmw.rest.feature;

import id.tsi.mmw.rest.binder.ApplicationBinder;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

@Provider
public class DependencyInjectionFeature implements Feature {

    /**
     * This method configures the feature context by registering the ApplicationBinder.
     *
     * @param context the feature context to be configured
     * @return true after successfully registering the ApplicationBinder
     */
    @Override
    public boolean configure(FeatureContext context) {
        // Create a new instance of ApplicationBinder
        ApplicationBinder applicationBinder = new ApplicationBinder();

        // Register the ApplicationBinder with the provided feature context
        context.register(applicationBinder);

        // Return true indicating successful configuration
        return true;
    }

}

