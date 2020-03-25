package org.bjglasbe;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Clob;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;

import java.util.concurrent.Executor;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.client.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

public class ChronoCacheConnection implements Connection {

    private String jdbcUrl;
    private String hostname;
    private String username;
    private String password;

    private int port;
    private Properties info;

    public ChronoCacheConnection( String url, Properties info ) throws SQLException {
        this.jdbcUrl = url;
        this.info = info;

        this.username = info.getProperty( "username" );
        this.password = info.getProperty( "password" );

        // Check if we can connect with this URL
        String[] chunks = url.split( ":" );

        // Checks just in case
        if( chunks.length < 4 ||
            !(chunks[0].equals( "jdbc" )) ||
            !(chunks[1].equals( "cc" )) ||
            !(chunks[2].startsWith( "//" ))
        ) { 
            throw new SQLException( "Invalid URL: " + url );
        }

        this.port = -1;
        try {
            this.port =  Integer.parseInt( chunks[3] );
        } catch( NumberFormatException e ) {
            throw new SQLException( "Invalid port: " + chunks[3] );
        }

        this.hostname = chunks[2].substring( 2, chunks[2].length() );

        // Try connect
        connectOrThrow();
    }

    public Invocation.Builder makeClientRequestBuilder( int clientId ) {
		Client client = ClientBuilder.newClient();
		String strTarget = "http://" + hostname + ":" + port +"/kronos/rest/query/" + clientId;
        WebTarget target = client.target( strTarget );
        return target.request( MediaType.APPLICATION_JSON );
    }

    private String makeJsonQuery( String query ) {
        StringBuilder sb = new StringBuilder();
        sb.append( "{ \"query\": \"" );
        sb.append( query );
        sb.append( "\" }" );
        return sb.toString();
    }

    private void connectOrThrow() throws SQLException { 
        
        Invocation.Builder builder = makeClientRequestBuilder( 0 );

        String queryString = makeJsonQuery( "SELECT 1" );
		// Make a post query
        try {
            long queryStart = System.nanoTime()/1000;
            Response response = builder.post(Entity.json( queryString ));
            String queryResponse = response.readEntity( String.class );
            long queryEnd = System.nanoTime()/1000;
        } catch( Exception e ) {
            throw new SQLException( e );
        }
    }

    public String getURL() {
        return jdbcUrl;
    }

    public String getUserName() {
        return this.getUserName();
    }
	public static String quoteAndSanitize( String dirty ) {
		return "'" + dirty.replace("'", "\\'") + "'";
	}

	public ResultSet restReadQuery( String queryString, int clientId ) throws SQLException {
		// Make a new client pointing at Apollo/the rest service
        Invocation.Builder builder = makeClientRequestBuilder( clientId );
		// Make the post query
		long queryStart = System.nanoTime()/1000;
        Response response = builder.post(Entity.json( makeJsonQuery( queryString ) ));
        String queryResponse = response.readEntity( String.class );
		long queryEnd = System.nanoTime()/1000;

		// Deparse the result
		ObjectMapper mapper = new ObjectMapper();
		List<Map<String, Object>> data = null;
		try
		{
			data = mapper.readValue( queryResponse, new TypeReference<List<Map<String, Object>>>() {} );
		}
		catch (Exception e)
		{
            throw new SQLException( e );
		}

		assert data != null;

		return new ChronoCacheResultSet( data );
	}

	public int restOtherQuery( String queryString, int clientId ) throws SQLException {
		// Make a new client pointing at Apollo/the rest service
        Invocation.Builder builder = makeClientRequestBuilder( clientId );
		// Make the post query
		long queryStart = System.nanoTime()/1000;
        Response response = builder.post(Entity.json( makeJsonQuery( queryString ) ));
        String queryResponse = response.readEntity( String.class );
		long queryEnd = System.nanoTime()/1000;

		// Deparse the result
		ObjectMapper mapper = new ObjectMapper();
		int data = -1;
		try
		{
			data = mapper.readValue(queryResponse, Integer.class);
		}
		catch (Exception e)
		{
            //FIXME
            // Do something here
		}

		assert data != -1;

		return data;
	}

    @Override
    public void abort( Executor executor ) {
    }

    @Override
    public void clearWarnings() {
    }

    @Override
    public void close() {
    }

    @Override
    public void commit() {
    }

    @Override
    public Array createArrayOf(String typeName, Object[] elements) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Blob createBlob() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Clob createClob() {
        throw new UnsupportedOperationException();
    }

    @Override
    public NClob createNClob() {
        throw new UnsupportedOperationException();
    }

    @Override
    public SQLXML createSQLXML() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Statement createStatement() {
        return new ChronoCacheStatement( this );
    }

    @Override
    public Statement createStatement(
        int resultSetType,
        int resultSetConcurrency
    ) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Statement createStatement(
        int resultSetType,
        int resultSetConcurrency,
        int resultSetHoldability
    ) {
        return null;
    }
    
    @Override
    public Struct createStruct( String typeName, Object[] attributes ) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getAutoCommit() {
        return true;
    }

    @Override
    public String getCatalog() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Properties getClientInfo() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getClientInfo(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getHoldability() {
        throw new UnsupportedOperationException();
    }

    @Override
    public DatabaseMetaData getMetaData() {
        return new ChronoCacheDatabaseMetaData( this );
    }

    @Override
    public int getNetworkTimeout() {
        //FIXME?
        return -1;
    }
    @Override
    public String getSchema() {
        throw new UnsupportedOperationException();
    }
    @Override
    public int getTransactionIsolation() {
        //SNAPSHOT ISOLATION
        return Connection.TRANSACTION_NONE;
    }

    @Override
    public Map<String,Class<?>> getTypeMap() {
        throw new UnsupportedOperationException(); 
    }

    @Override
    public SQLWarning getWarnings() {
        throw new UnsupportedOperationException();
    }
    @Override
    public boolean isClosed() {
        return false;
    }
    @Override
    public boolean isReadOnly() {
        return false;
    }
    @Override
    public boolean isValid(int timeout) {
        return true;
    }
    @Override
    public String nativeSQL(String sql) {
        return sql;
    }
    @Override
    public CallableStatement prepareCall(String sql) {
        throw new UnsupportedOperationException();
    }
    @Override
    public CallableStatement prepareCall(
        String sql,
        int resultSetType,
        int resultSetConcurrency
    ) {
        throw new UnsupportedOperationException();
    }
    @Override
    public CallableStatement prepareCall(
        String sql,
        int resultSetType,
        int resultSetConcurrency,
        int resultSetHoldability
    ) {
        throw new UnsupportedOperationException();
    }
    @Override
    public PreparedStatement prepareStatement(String sql) {
        throw new UnsupportedOperationException();
    }
    @Override
    public PreparedStatement prepareStatement(
        String sql,
        int autoGeneratedKeys
    ) {
        throw new UnsupportedOperationException();
    }
    @Override 
    public PreparedStatement prepareStatement(
        String sql,
        int[] columnIndexes
    ) {
        throw new UnsupportedOperationException();
    }
    @Override
    public PreparedStatement prepareStatement(
        String sql,
        int resultSetType,
        int resultSetConcurrency
    ) {
        throw new UnsupportedOperationException();
    }

    @Override
    public PreparedStatement prepareStatement(
        String sql,
        int resultSetType,
        int resultSetConcurrency,
        int resultSetHoldability
    ) {
        throw new UnsupportedOperationException();
    }

    @Override 
    public PreparedStatement prepareStatement(
        String sql, 
        String[] columnNames 
    ) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void releaseSavepoint(Savepoint savepoint) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void rollback() {
        throw new UnsupportedOperationException();
    }
    @Override
    public void rollback(Savepoint savepoint) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void setAutoCommit(boolean autoCommit) {
        if( autoCommit != true ) {
            throw new UnsupportedOperationException();
        }
    }
    @Override
    public void setCatalog(String catalog) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void setClientInfo(Properties properties) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void setClientInfo(String name, String value) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void setHoldability(int holdability) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void setReadOnly( boolean readOnly ) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public Savepoint setSavepoint() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Savepoint setSavepoint( String name ) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setSchema( String schema ) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setTransactionIsolation( int level ) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setTypeMap( Map<String,Class<?>> map ) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T unwrap(Class<T> iface ) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isWrapperFor( Class<?> iface ) throws SQLException {
        return false;
    }
}
