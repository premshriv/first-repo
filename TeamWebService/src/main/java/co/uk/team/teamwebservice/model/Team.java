package co.uk.team.teamwebservice.model;

import java.util.Arrays;
import java.util.Date;

public class Team {

	private long id;
	
	private String name;
	
	private String city;
	
	private String  owner;
        
        private String competition;
        
        private String [] players;
        
        private Date creationDate;

	public Team(){
		id=0;
	}

    public Team(long id, String name, String city, String owner, String competition, String[] players, Date creationDate) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.owner = owner;
        this.competition = competition;
        this.players = players;
        this.creationDate = creationDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String[] getPlayers() {
        return players;
    }

    public void setPlayers(String[] players) {
        this.players = players;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getCompetition() {
        return competition;
    }

    public void setCompetition(String competition) {
        this.competition = competition;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 89 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 89 * hash + (this.city != null ? this.city.hashCode() : 0);
        hash = 89 * hash + (this.owner != null ? this.owner.hashCode() : 0);
        hash = 89 * hash + (this.competition != null ? this.competition.hashCode() : 0);
        hash = 89 * hash + Arrays.deepHashCode(this.players);
        hash = 89 * hash + (this.creationDate != null ? this.creationDate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Team other = (Team) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Team{" + "id=" + id + ", name=" + name + ", city=" + city + ", owner=" + owner + ", competition=" + competition + ", players=" + players + ", creationDate=" + creationDate + '}';
    }
       
}