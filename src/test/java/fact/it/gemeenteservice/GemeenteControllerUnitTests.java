package fact.it.gemeenteservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import fact.it.gemeenteservice.model.Gemeente;
import fact.it.gemeenteservice.repository.GemeenteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;



@SpringBootTest
@AutoConfigureMockMvc
public class GemeenteControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GemeenteRepository gemeenteRepository;

    private Gemeente gemeente1 = new Gemeente("Tessenderlo", "3980");
    private Gemeente gemeente2 = new Gemeente("Ham", "3945");

    private List<Gemeente> allGemeentes = Arrays.asList(gemeente1, gemeente2);

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void givenGemeent_FindByPostcode() throws Exception {

        given(gemeenteRepository.findGemeenteByPostcode("3945")).willReturn(gemeente2);

        mockMvc.perform(get("/gemeentes/postcode/{postcode}","3945"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.naam",is("Ham")))
                .andExpect(jsonPath("$.postcode",is("3945")));
    }

    @Test
    public void givenGemeentes_GetAllGemeentes() throws Exception {

        given(gemeenteRepository.findGemeenteByNaam("Gemeente")).willReturn(allGemeentes);

        mockMvc.perform(get("/gemeentes/naam/{naam}","Gemeente"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].naam",is("Tessenderlo")))
                .andExpect(jsonPath("$[0].postcode",is("3980")))
                .andExpect(jsonPath("$[1].naam",is("Ham")))
                .andExpect(jsonPath("$[1].postcode",is("3945")));
    }

    @Test
    public void getAllGemmeents() throws Exception {
         Gemeente gemeente1 = new Gemeente("Tessenderlo", "3980");
         Gemeente gemeente2 = new Gemeente("Ham", "3945");

        List<Gemeente> gemeenteList = new ArrayList<>();
        gemeenteList.add(gemeente1);
        gemeenteList.add(gemeente2);

        given(gemeenteRepository.findAll()).willReturn(gemeenteList);

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
    public void PostGemeente() throws Exception{
        Gemeente monument1 = new Gemeente("Leopoldsburg", "3970");

        mockMvc.perform(post("/gemeente/add")
                .content(mapper.writeValueAsString(monument1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.naam",is("Leopoldsburg")))
                .andExpect(jsonPath("$.postcode",is("3970")));
    }


}

