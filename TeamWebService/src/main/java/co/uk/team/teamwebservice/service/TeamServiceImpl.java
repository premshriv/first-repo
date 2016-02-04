package co.uk.team.teamwebservice.service;

import co.uk.team.teamwebservice.model.Team;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service("teamService")
@Transactional
public class TeamServiceImpl implements TeamService{
	
	private static final AtomicLong counter = new AtomicLong();
	
	private static List<Team> teams;
	
	static{
		teams= populateDummyTeams();
	}

	public List<Team> findAllTeams() {
		return teams;
	}
	
	public Team findById(long id) {
		for(Team team : teams){
			if(team.getId() == id){
				return team;
			}
		}
		return null;
	}
	
	public Team findByName(String name) {
		for(Team team : teams){
			if(team.getName().equalsIgnoreCase(name)){
				return team;
			}
		}
		return null;
	}
	
	public void saveTeam(Team team) {
		team.setId(counter.incrementAndGet());
		teams.add(team);
	}

	public void updateTeam(Team team) {
		int index = teams.indexOf(team);
		teams.set(index, team);
	}

	public void deleteTeamById(long id) {
		
		for (Iterator<Team> iterator = teams.iterator(); iterator.hasNext(); ) {
		    Team team = iterator.next();
		    if (team.getId() == id) {
		        iterator.remove();
		    }
		}
	}

	public boolean isTeamExist(Team team) {
		return findByName(team.getName())!=null;
	}
	
	public void deleteAllTeams(){
		teams.clear();
	}

	private static List<Team> populateDummyTeams(){
		List<Team> teams = new ArrayList<Team>();
                String [] players1 = {"player1","player2"};
                String [] players2 = {"player3","player4"};
                String [] players3 = {"player5","player6"};
                String [] players4 = {"player7","player8"};
                teams.add(new Team(counter.incrementAndGet(), "Team1", "city1", "owner1", "competition1", players1, new Date()));               
		teams.add(new Team(counter.incrementAndGet(), "Team2", "city2", "owner2", "competition2", players2, new Date()));   
		teams.add(new Team(counter.incrementAndGet(), "Team3", "city3", "owner3", "competition3", players3, new Date()));   
		teams.add(new Team(counter.incrementAndGet(), "Team4", "city4", "owner4", "competition4", players4, new Date()));   
		return teams;
	}
   
}
