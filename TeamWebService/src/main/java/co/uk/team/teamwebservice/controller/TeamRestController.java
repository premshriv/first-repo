package co.uk.team.teamwebservice.controller;
 
import co.uk.team.teamwebservice.model.Team;
import co.uk.team.teamwebservice.service.AuthorisationService;
import co.uk.team.teamwebservice.service.TeamService;
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestHeader;
 

 
@RestController
public class TeamRestController {
 
    @Autowired
    TeamService teamService;  //Service which will do all data retrieval/manipulation work
    
    @Autowired
    AuthorisationService authorisationService;  //Service which will do user authorisation
    
   private static final Logger logger = Logger.getLogger(TeamRestController.class);

 
     
    //-------------------Retrieve All Teams--------------------------------------------------------
     
    @RequestMapping(value = "/team/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Team>> listAllTeams(@RequestHeader(value = "Authorization")String authString) {
        
         logger.info("Retrieving All Teams " );
        if(!isUserAuthorised(authString)){
            logger.error("user can not be authorised" );
            return new ResponseEntity<List<Team>>(HttpStatus.UNAUTHORIZED);
        }
        List<Team> teams = teamService.findAllTeams();
        if(teams.isEmpty()){
            logger.error("requested content not available" );
            return new ResponseEntity<List<Team>>(HttpStatus.NO_CONTENT);//many return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Team>>(teams, HttpStatus.OK);
    }
 
 
    //-------------------Retrieve Single Team--------------------------------------------------------
     
    @RequestMapping(value = "/team/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Team> getTeam(@PathVariable("id") long id, @RequestHeader(value = "Authorization")String authString) {
        logger.info("Fetching User with id " + id);
        if(!isUserAuthorised(authString)){
            logger.error("user can not be authorised" );
            return new ResponseEntity<Team>(HttpStatus.UNAUTHORIZED);
        }        
        Team team = teamService.findById(id);
        if (team == null) {
            logger.error("Team with id " + id + " not found");
            return new ResponseEntity<Team>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Team>(team, HttpStatus.OK);
    }
 
     
     
    //-------------------Create a team--------------------------------------------------------
     
    @RequestMapping(value = "/team/", method = RequestMethod.POST)
    public ResponseEntity<Void> createTeam(@RequestBody Team team, UriComponentsBuilder ucBuilder, @RequestHeader(value = "Authorization")String authString) {
        logger.info("Creating Team " + team.getName());
        
        if(!isUserAuthorised(authString)){
            logger.error("user can not be authorised" );
            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
        }  
 
        if (teamService.isTeamExist(team)) {
            logger.error("A Team with name " + team.getName() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
 
        teamService.saveTeam(team);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(team.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
 
     
    //------------------- Update a team --------------------------------------------------------
     
    @RequestMapping(value = "/team/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Team> updateTeam(@PathVariable("id") long id, @RequestBody Team team, @RequestHeader(value = "Authorization")String authString) {
        logger.info("Updating Team " + id);
        
        if(!isUserAuthorised(authString)){
            logger.error("user can not be authorised" );
            return new ResponseEntity<Team>(HttpStatus.UNAUTHORIZED);
        }  
         
        Team currentTeam = teamService.findById(id);
         
        if (currentTeam==null) {
            logger.error("Team with id " + id + " not found");
            return new ResponseEntity<Team>(HttpStatus.NOT_FOUND);
        }
 
        currentTeam.setName(team.getName());
        currentTeam.setCity(team.getCity());
        currentTeam.setOwner(team.getOwner());
        currentTeam.setCreationDate(team.getCreationDate());
        currentTeam.setPlayers(team.getPlayers());
         
        teamService.updateTeam(currentTeam);
        return new ResponseEntity<Team>(currentTeam, HttpStatus.OK);
    }
 
    //------------------- Delete a Team --------------------------------------------------------
     
    @RequestMapping(value = "/team/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Team> deleteTeam(@PathVariable("id") long id, @RequestHeader(value = "Authorization")String authString) {
        logger.info("Fetching & Deleting team with id " + id);
        
         if(!isUserAuthorised(authString)){
            logger.error("user can not be authorised" );
            return new ResponseEntity<Team>(HttpStatus.UNAUTHORIZED);
        } 
 
        Team team = teamService.findById(id);
        if (team == null) {
            logger.error("Unable to delete. team with id " + id + " not found");
            return new ResponseEntity<Team>(HttpStatus.NOT_FOUND);
        }
 
        teamService.deleteTeamById(id);
        return new ResponseEntity<Team>(HttpStatus.NO_CONTENT);
    }
 
     
    //------------------- Delete All Teams --------------------------------------------------------
     
    @RequestMapping(value = "/team/", method = RequestMethod.DELETE)
    public ResponseEntity<Team> deleteAllTeams(@RequestHeader(value = "Authorization")String authString) {
        logger.info("Deleting All Teams");
 
         if(!isUserAuthorised(authString)){
            logger.error("user can not be authorised" );
            return new ResponseEntity<Team>(HttpStatus.UNAUTHORIZED);
        } 
        teamService.deleteAllTeams();
        return new ResponseEntity<Team>(HttpStatus.NO_CONTENT);
    }
    
    private boolean isUserAuthorised(String authString){
    
        return authorisationService.isUserAuthorised(authString);
    }
 
}