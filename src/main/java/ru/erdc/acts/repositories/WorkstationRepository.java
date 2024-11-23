package ru.erdc.acts.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.erdc.acts.entities.Workstation;

//public interface WorkstationRepository extends PagingAndSortingRepository<Workstation, Long>, JpaSpecificationExecutor<Workstation> {
//}
@Repository
public interface WorkstationRepository extends CrudRepository<Workstation, Long>, JpaSpecificationExecutor<Workstation> {
}

