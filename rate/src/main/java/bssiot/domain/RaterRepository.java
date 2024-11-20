package bssiot.domain;

import bssiot.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "raters", path = "raters")
public interface RaterRepository
    extends PagingAndSortingRepository<Rater, Long> {}
