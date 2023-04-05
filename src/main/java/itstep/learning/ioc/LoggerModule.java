package itstep.learning.ioc;

import com.google.inject.AbstractModule;

import java.io.InputStream;
import java.util.logging.LogManager;

public class LoggerModule extends AbstractModule {
    @Override
    protected void configure() {
        try( InputStream conf =
                 this.getClass()
                     .getClassLoader()
                     .getResourceAsStream( "logging.properties" ) )
        {
            LogManager logManager = LogManager.getLogManager() ;
            logManager.reset() ;
            logManager.readConfiguration( conf ) ;
        }
        catch( Exception ex ) {
            System.err.println( "LoggerModule::configure: " + ex.getMessage() ) ;
        }
    }
}
// LogManager.getLogManager().getProperty("java.util.logging.SimpleFormatter.format")