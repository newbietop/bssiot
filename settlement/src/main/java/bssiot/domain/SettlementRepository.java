package bssiot.domain;

import bssiot.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(
    collectionResourceRel = "settlements",
    path = "settlements"
)
public interface SettlementRepository
    extends PagingAndSortingRepository<Settlement, Long> {}
