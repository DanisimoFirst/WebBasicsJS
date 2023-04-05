package itstep.learning.ioc;

import com.google.inject.AbstractModule;
import itstep.learning.service.DbService;
import itstep.learning.service.HashService;
import itstep.learning.service.LocalDbService;
import itstep.learning.service.Md5HashService;
import itstep.learning.service.auth.AuthService;
import itstep.learning.service.auth.SessionAuthService;

public class ServiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind( DbService.class   ).to( LocalDbService.class ) ;
        bind( HashService.class ).to( Md5HashService.class ) ;
        bind( AuthService.class ).to( SessionAuthService.class ) ;
    }
}
