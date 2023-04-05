package itstep.learning.ioc;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class StringModule extends AbstractModule {
    @Override
    protected void configure() {
        bind( String.class )
            .annotatedWith( Names.named( "AvatarFolder" ) )
            .toInstance( "../avatars/" ) ;
    }
}
