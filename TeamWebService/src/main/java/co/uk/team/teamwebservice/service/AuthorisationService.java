package co.uk.team.teamwebservice.service;

/**
 *
 * Service to authorise users
 */
public interface AuthorisationService {
    
    boolean isUserAuthorised(String credentials);
    
}
