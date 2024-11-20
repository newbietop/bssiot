package bssiot.infra;

import bssiot.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class SettlementHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Settlement>> {

    @Override
    public EntityModel<Settlement> process(EntityModel<Settlement> model) {
        return model;
    }
}
