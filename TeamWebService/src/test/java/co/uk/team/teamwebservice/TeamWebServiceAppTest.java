package co.uk.team.teamwebservice;

import co.uk.team.teamwebservice.configuration.TeamConfiguration;
import org.apache.commons.codec.binary.Base64;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TeamConfiguration.class})
@WebAppConfiguration
public class TeamWebServiceAppTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private String authorisation;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
        String plainCreds = "user@password";
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);
        authorisation = "Basic " + base64Creds;

    }

    @Test
    public void shouldReturnAllTeams() throws Exception {

        mockMvc.perform(get("/team/")
                .header("Authorization", authorisation))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE)))
                .andExpect(jsonPath("$", Matchers.hasSize(4)));

    }

    @Test
    public void shouldReturnOneTeam() throws Exception {

        mockMvc.perform(get("/team/1")
                .header("Authorization", authorisation))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE)))
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.name", Matchers.is("Team1")))
                .andExpect(jsonPath("$.city", Matchers.is("city1")))
                .andExpect(jsonPath("$.owner", Matchers.is("owner1")))
                .andExpect(jsonPath("$.competition", Matchers.is("competition1"))
                );

    }

}

