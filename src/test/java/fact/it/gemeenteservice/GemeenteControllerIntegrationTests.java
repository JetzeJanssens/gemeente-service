package fact.it.gemeenteservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import fact.it.gemeenteservice.model.Gemeente;
import fact.it.gemeenteservice.repository.GemeenteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class GemeenteControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GemeenteRepository gemeenteRepository;

    private Gemeente gemeente1 = new Gemeente("Tessenderlo", "3980");
    private Gemeente gemeente2 = new Gemeente("Ham", "3945");

    @BeforeEach
    public void beforeAllTests() {
        gemeenteRepository.deleteAll();
        gemeenteRepository.save(gemeente1);
        gemeenteRepository.save(gemeente2);
    }

//    @AfterEach
//    public void afterAllTests() {
//        gemeenteRepository.deleteAll();
//    }


    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void GetAllGemeentes() throws Exception {

        mockMvc.perform(get("/gemeentes"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].naam",is("Tessenderlo")))
                .andExpect(jsonPath("$[0].postcode",is("3980")))
                .andExpect(jsonPath("$[1].naam",is("Ham")))
                .andExpect(jsonPath("$[1].postcode",is("3945")));
    }

    @Test
    public void givenGemeente_GetGemeenteByPostcode() throws Exception {

        mockMvc.perform(get("/gemeentes/postcode/{postcode}","3945"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.naam",is("Ham")))
                .andExpect(jsonPath("$.postcode",is("3945")));
    }

    @Test
    public void givenGemeente_GetGemeetes() throws Exception {

        mockMvc.perform(get("/gemeentes/","Gemeente"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].naam",is("Tessenderlo")))
                .andExpect(jsonPath("$[0].postcode",is("3980")))
                .andExpect(jsonPath("$[1].naam",is("Ham")))
                .andExpect(jsonPath("$[1].postcode",is("3945")));
    }

    @Test
    public void whenPostMonument_thenReturnJsonMonument() throws Exception {

        Gemeente newGemeente = new Gemeente("Hasselt", "3500");

        mockMvc.perform(post("/gemeente/add")
                .content(mapper.writeValueAsString(newGemeente))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.naam",is("Hasselt")))
                .andExpect(jsonPath("$.postcode",is("3500")));
    }





}