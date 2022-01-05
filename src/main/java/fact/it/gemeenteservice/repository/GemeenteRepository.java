package fact.it.gemeenteservice.repository;

import fact.it.gemeenteservice.model.Gemeente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GemeenteRepository extends MongoRepository<Gemeente, String> {
    List<Gemeente> findGemeenteByNaam(String naam);
    Gemeente findGemeenteByPostcode(String postcode);

}
