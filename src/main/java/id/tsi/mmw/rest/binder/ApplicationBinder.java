package id.tsi.mmw.rest.binder;

import id.tsi.mmw.controller.Controller;
import org.atteo.classindex.ClassIndex;
import org.glassfish.jersey.internal.inject.AbstractBinder;

public class ApplicationBinder extends AbstractBinder {

    /**
     * Configures the application bindings.
     */
    @Override
    protected void configure() {
        // Retrieve all classes annotated with @Controller using ClassIndex
        ClassIndex.getAnnotated(Controller.class)
                // For each class found, bind it as a contract
                .forEach(this::bindAsContract);
    }

}
