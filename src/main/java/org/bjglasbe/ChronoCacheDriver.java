package org.bjglasbe;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;

import java.util.logging.Logger;
import java.util.Properties;

public class ChronoCacheDriver implements Driver {

    public ChronoCacheDriver() {
    }
    
    // TODO: Static DriverManager register goes here.

    @Override
    public boolean acceptsURL( String url ) {
        if( !url.startsWith( "jdbc:cc://" ) ) {
            return false;
        }
        return true;
    }

    @Override
    public Connection connect( String url, Properties info ) throws SQLException {
        return new ChronoCacheConnection( url );
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
