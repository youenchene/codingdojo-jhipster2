package youen.dojo.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import youen.dojo.domain.Fake;

/**
 * Spring Data MongoDB repository for the Fake entity.
 */
public interface FakeRepository extends MongoRepository<Fake, String> {

}
