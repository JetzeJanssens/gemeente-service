package fact.it.gemeente.controller;

import fact.it.gemeente.model.Gemeente;
import fact.it.gemeente.repository.GemeenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class GemeenteController {

    @Autowired
    private GemeenteRepository gemeenteRepository;

    @PostConstruct
    public void fillDB(){
        if(gemeenteRepository.count()==0){
            gemeenteRepository.save(new Gemeente("Ham", "3945"));
            gemeenteRepository.save(new Gemeente("Paal", "3583"));
            gemeenteRepository.save(new Gemeente("Tessenderlo", "3980"));
        }

        System.out.println(gemeenteRepository.findGemeenteByPostcode("3945").getNaam());
    }

    @GetMapping("/gemeentes/name/{name}")
    public List<Gemeente> getGemeenteByName(@PathVariable String naam){
        return gemeenteRepository.findGemeenteByNaamContaining(naam);
    }

    @GetMapping("/gemeentes/{postcode}")
    public Gemeente getGemeenteByPostcode(@PathVariable String postcode){
        return gemeenteRepository.findGemeenteByPostcode(postcode);
    }


}
