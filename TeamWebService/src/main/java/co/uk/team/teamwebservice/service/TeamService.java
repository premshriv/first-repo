package co.uk.team.teamwebservice.service;

import co.uk.team.teamwebservice.model.Team;
import java.util.List;





public interface TeamService {
	
	Team findById(long id);
	
	Team findByName(String name);
	
	void saveTeam(Team team);
	
	void updateTeam(Team team);
	
	void deleteTeamById(long id);

	List<Team> findAllTeams(); 
	
	void deleteAllTeams();
	
	public boolean isTeamExist(Team team);
	
}
