package co.uk.team.teamwebservice.service;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.stereotype.Service;

/**
 *
 * Implementation of AuthorisationService
 */
@Service("authorisationService")
public class AuthorisationServiceImpl implements AuthorisationService {

    public boolean isUserAuthorised(String credentials) {
        
        String[] authParts = credentials.split("\\s+");
        String authInfo = authParts[1];
        
        String decodeAuth = StringUtils.newStringUtf8(Base64.decodeBase64(authInfo));
        return decodeAuth.equalsIgnoreCase("user@password");
       
    }
    
}
