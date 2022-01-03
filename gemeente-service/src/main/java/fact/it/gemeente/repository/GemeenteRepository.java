package fact.it.gemeente.repository;
import fact.it.gemeente.model.Gemeente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GemeenteRepository extends JpaRepository<Gemeente, String> {
    Gemeente findGemeenteByPostcode(String postcode);
    List<Gemeente> findGemeenteByNaamContaining(String naam);

}
