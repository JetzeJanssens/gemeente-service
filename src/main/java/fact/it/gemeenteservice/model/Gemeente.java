package fact.it.gemeenteservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "gemeentes")
public class Gemeente {
    @Id
    private String id;
    private String naam;
    private String postcode;

    public Gemeente() {
    }

    public Gemeente(String naam, String postcode) {
        this.naam = naam;
        this.postcode = postcode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
