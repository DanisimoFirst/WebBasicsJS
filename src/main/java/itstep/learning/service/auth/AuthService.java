package itstep.learning.service.auth;

import itstep.learning.data.entity.User;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {
    void authorize( HttpServletRequest request ) ;
    User getAuthUser() ;   // authUser | null
    void logout( HttpServletRequest request ) ;
}
