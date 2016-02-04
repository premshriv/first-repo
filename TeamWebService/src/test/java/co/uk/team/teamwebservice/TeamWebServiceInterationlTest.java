package co.uk.team.teamwebservice;

import co.uk.team.teamwebservice.configuration.TeamConfiguration;
import co.uk.team.teamwebservice.model.Team;
import org.apache.commons.codec.binary.Base64;

import java.util.Date;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import org.junit.Before;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.HttpClientErrorException;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TeamConfiguration.class})
@WebAppConfiguration
public class TeamWebServiceInterationlTest {

    public static final String REST_SERVICE_URI = "http://localhost:8080/TeamWebService";

    private final RestTemplate restTemplate = new RestTemplate();
    
    private final HttpHeaders headers = new HttpHeaders();
    
    @Before
    public void init(){
        
        String plainCreds = "user@password";
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);
        headers.add("Authorization", "Basic " + base64Creds );    
    }

    @Test
    public void shouldGetAllTeams() {
        System.out.println("Testing getAllTeams API----------"); 
        
        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity<Team[]> response = restTemplate.exchange(REST_SERVICE_URI + "/team/",HttpMethod.GET, request, Team[].class);
        Team[] teams = response.getBody();

        assertNotNull(teams);
        assertEquals(4, teams.length);
        assertEquals(1L, teams[0].getId());
        assertEquals("Team1", teams[0].getName());
        assertEquals("owner1", teams[0].getOwner());
        assertEquals("city1", teams[0].getCity());
        assertEquals(2L, teams[1].getId());
        assertEquals("Team2", teams[1].getName());
        assertEquals("owner2", teams[1].getOwner());
        assertEquals("city2", teams[1].getCity());

    }
    
   @Test
    public void shouldGetTeam(){
        System.out.println("Testing getUser API----------");       
        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity<Team> response = restTemplate.exchange(REST_SERVICE_URI + "/team/1",HttpMethod.GET, request, Team.class);
        Team team = response.getBody();
        assertEquals(1L, team.getId());
        assertEquals("Team1", team.getName());
        assertEquals("owner1", team.getOwner());
        assertEquals("city1", team.getCity());
    }
     
    @Test
    public void shouldCreateTeam() {
        System.out.println("Testing create Team API----------");    
        String [] players1 = {"player7","player8"};
        Team team = new Team(5l, "Team11", "city1", "owner1","competition5", players1, new Date());
        HttpEntity<Team> request = new HttpEntity<Team>(team, headers);
        ResponseEntity<String> response = restTemplate.exchange(REST_SERVICE_URI + "/team/",HttpMethod.POST, request, String.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
 
    @Test
    public void  ShouldUpdateTeam() {
        System.out.println("Testing update team API----------");       
        String [] players1 = {"player9","player10"};
        Team team = new Team(5l, "Team10", "city1", "owner1","competition6", players1, new Date());
        HttpEntity<Team> request = new HttpEntity<Team>(team, headers);
        ResponseEntity<Team> response = restTemplate.exchange(REST_SERVICE_URI + "/team/1",HttpMethod.PUT, request, Team.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Team updatedTeam = response.getBody();
        assertEquals(1L, updatedTeam.getId());
        assertEquals("Team10", updatedTeam.getName());
        assertEquals("owner1", updatedTeam.getOwner());
        assertEquals("city1", updatedTeam.getCity());        
    }
 
    @Test
    public void  shouldDeleteTeam() {
        System.out.println("Testing delete Team API----------");       
        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity<String> response = restTemplate.exchange(REST_SERVICE_URI + "/team/3",HttpMethod.DELETE, request, String.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
 
 
   @Test
    public void shouldDeleteAllTeams() {
        System.out.println("Testing all delete team API----------");        
        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity<String> response = restTemplate.exchange(REST_SERVICE_URI + "/team/",HttpMethod.DELETE, request, String.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }   
    
   
    @Test
    public void shouldReturnUnauthorized() {
        System.out.println("Testing Unauthorised request ----------");
        HttpHeaders incorrectHeaders = new HttpHeaders();
        String plainCreds = "somename";
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);
        incorrectHeaders.add("Authorization", "Basic " + base64Creds);
        HttpEntity<String> request = new HttpEntity<String>(incorrectHeaders);

        try {

            restTemplate.exchange(REST_SERVICE_URI + "/team/1", HttpMethod.GET, request, Team.class);
        } catch (HttpClientErrorException ex) {
          
            assertEquals("401 Unauthorized", ex.getMessage());
        }

    }

}
