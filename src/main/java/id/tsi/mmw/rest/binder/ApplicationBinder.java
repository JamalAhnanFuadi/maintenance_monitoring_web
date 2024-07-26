package id.tsi.mmw.rest.binder;

import id.tsi.mmw.controller.Controller;
import org.atteo.classindex.ClassIndex;
import org.glassfish.jersey.internal.inject.AbstractBinder;

public class ApplicationBinder extends AbstractBinder {

    @Override
    protected void configure() {

        ClassIndex.getAnnotated(Controller.class).forEach(this::bindAsContract);

    }

}
