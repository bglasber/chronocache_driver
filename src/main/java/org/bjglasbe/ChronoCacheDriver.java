package org.bjglasbe;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.logging.Logger;
import java.util.Properties;

public class ChronoCacheDriver implements Driver {

    public static ChronoCacheDriver registeredDriver = null; 

    public ChronoCacheDriver() {
    }
    
    static {
        try {
            register();
        } catch( SQLException e ) {
        }
    }

    public static void register() throws SQLException {
        ChronoCacheDriver driver = new ChronoCacheDriver();
        DriverManager.registerDriver( driver );
        ChronoCacheDriver.registeredDriver = driver;
    }

    public static boolean isRegistered() {
        return registeredDriver != null;
    }


    @Override
    public boolean acceptsURL( String url ) {
        if( !url.startsWith( "jdbc:cc://" ) ) {
            return false;
        }
        return true;
    }

    @Override
    public Connection connect( String url, Properties info ) throws SQLException {
        return new ChronoCacheConnection( url, info );
    }

    @Override
    public int getMajorVersion() {
        return 1;
    }

    @Override
    public int getMinorVersion() {
        return 0;
    }

    @Override
    public Logger getParentLogger() {
        return Logger.getLogger( ChronoCacheDriver.class.getName() );
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(
            String url,
            Properties info
    ) {
        return null;
    }

    @Override
    public boolean jdbcCompliant() {
        return false;
    }

}
