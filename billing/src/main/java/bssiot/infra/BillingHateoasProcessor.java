package bssiot.infra;

import bssiot.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class BillingHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Billing>> {

    @Override
    public EntityModel<Billing> process(EntityModel<Billing> model) {
        return model;
    }
}
