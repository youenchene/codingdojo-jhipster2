package youen.dojo.repository;

import youen.dojo.domain.Time;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Time entity.
 */
public interface TimeRepository extends JpaRepository<Time, Long> {

}
