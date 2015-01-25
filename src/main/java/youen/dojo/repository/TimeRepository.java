package youen.dojo.repository;

import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.Query;
import youen.dojo.domain.Time;
import org.springframework.data.jpa.repository.JpaRepository;
import youen.dojo.domain.User;

import java.util.List;

/**
 * Spring Data JPA repository for the Time entity.
 */
public interface TimeRepository extends JpaRepository<Time, Long> {


    @Query("select t from Time t inner join t.user u where u.login = ?1 order by t.date desc")
    public List<Time> findLastLoggerByUser(String login);

    @Query("select t from Time t inner join t.user u where u.login = ?1")
    public List<Time> findAllLoggerByUser(String login);

}
