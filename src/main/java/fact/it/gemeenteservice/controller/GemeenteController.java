package fact.it.gemeenteservice.controller;

import fact.it.gemeenteservice.model.Gemeente;
import fact.it.gemeenteservice.repository.GemeenteRepository;
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
        if (gemeenteRepository.count()==0){
            gemeenteRepository.save(new Gemeente("Ham", "3945"));
            gemeenteRepository.save(new Gemeente("Tessenderlo","3980" ));
        }
        System.out.println("Gemeentes test:" + gemeenteRepository.findGemeenteByPostcode("3980").getNaam());
    }

    @GetMapping("/gemeentes/naam/{naam}")
    public List<Gemeente> getGemeenteByName(@PathVariable String naam){
        return gemeenteRepository.findGemeenteByNaam(naam);
    }

    @GetMapping("/gemeentes")
    public List<Gemeente> all(){
        return gemeenteRepository.findAll();
    }

    @GetMapping("/gemeentes/postcode/{postcode}")
    public Gemeente getGemeenteByPostcode(@PathVariable String postcode){
        return gemeenteRepository.findGemeenteByPostcode(postcode);
    }
}
