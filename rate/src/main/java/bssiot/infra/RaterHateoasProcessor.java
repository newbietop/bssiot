package bssiot.infra;

import bssiot.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class RaterHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Rater>> {

    @Override
    public EntityModel<Rater> process(EntityModel<Rater> model) {
        return model;
    }
}
