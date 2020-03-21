package org.bjglasbe;

import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.equalTo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ChronoCacheResultSetTest {

    public static final String dbUrl = "jdbc:derby:memory:demo;create=true";

    @After
    public void teardown() throws SQLException {
        Connection conn = DriverManager.getConnection( dbUrl );
        Statement stmt = conn.createStatement();
        stmt.executeUpdate( "DROP TABLE test" );
        stmt.close();
        conn.close();
    }

    @Test
    public void testOneRowOfInts() throws SQLException {
        String dbUrl = "jdbc:derby:memory:demo;create=true";
        Connection conn = DriverManager.getConnection( dbUrl );

        Statement stmt = conn.createStatement();
        stmt.executeUpdate( "CREATE TABLE TEST( A int )" );
        stmt.executeUpdate( "INSERT INTO TEST VALUES (1)" );
        stmt.close();


        Statement getValsStmt = conn.createStatement();
        ResultSet resultSet = getValsStmt.executeQuery( "SELECT A FROM TEST" );
        List<Map<String,Object>> jsonResultSet =
            ResultSetConverter.getEntitiesFromResultSet( resultSet );
        ChronoCacheResultSet ccResultSet = new ChronoCacheResultSet(
                jsonResultSet );
        getValsStmt.close();
        conn.close();

        assertThat( ccResultSet.getRow(), equalTo( -1 ) );
        assertThat( ccResultSet.next(), equalTo( true ) );
        Integer aVal = ccResultSet.getInt( "A" );
        assertThat( aVal, equalTo( 1 ) );
        aVal = ccResultSet.getInt( 1 );
        assertThat( aVal, equalTo( 1 ) );
        assertThat( ccResultSet.next(), equalTo( false ) );

    }

    @Test
    public void testOneRowOfLongs() throws SQLException {
        String dbUrl = "jdbc:derby:memory:demo;create=true";
        Connection conn = DriverManager.getConnection( dbUrl );

        Statement stmt = conn.createStatement();
        stmt.executeUpdate( "CREATE TABLE TEST( A bigint )" );
        stmt.executeUpdate( "INSERT INTO TEST VALUES (1)" );
        stmt.close();


        Statement getValsStmt = conn.createStatement();
        ResultSet resultSet = getValsStmt.executeQuery( "SELECT A FROM TEST" );
        List<Map<String,Object>> jsonResultSet =
            ResultSetConverter.getEntitiesFromResultSet( resultSet );
        ChronoCacheResultSet ccResultSet = new ChronoCacheResultSet(
                jsonResultSet );
        getValsStmt.close();
        conn.close();

        assertThat( ccResultSet.getRow(), equalTo( -1 ) );
        assertThat( ccResultSet.next(), equalTo( true ) );
        Long aVal = ccResultSet.getLong( "A" );
        assertThat( aVal, equalTo( 1L ) );
        aVal = ccResultSet.getLong( 1 );
        assertThat( aVal, equalTo( 1L ) );
        assertThat( ccResultSet.next(), equalTo( false ) );

    }

    @Test
    public void testOneRowOfFloats() throws SQLException {
        String dbUrl = "jdbc:derby:memory:demo;create=true";
        Connection conn = DriverManager.getConnection( dbUrl );

        Statement stmt = conn.createStatement();
        stmt.executeUpdate( "CREATE TABLE TEST( A float )" );
        stmt.executeUpdate( "INSERT INTO TEST VALUES (1.0)" );
        stmt.close();


        Statement getValsStmt = conn.createStatement();
        ResultSet resultSet = getValsStmt.executeQuery( "SELECT A FROM TEST" );
        List<Map<String,Object>> jsonResultSet =
            ResultSetConverter.getEntitiesFromResultSet( resultSet );
        ChronoCacheResultSet ccResultSet = new ChronoCacheResultSet(
                jsonResultSet );
        getValsStmt.close();
        conn.close();

        assertThat( ccResultSet.getRow(), equalTo( -1 ) );
        assertThat( ccResultSet.next(), equalTo( true ) );
        Float aVal = ccResultSet.getFloat( "A" );
        assertThat( aVal, equalTo( 1.0f ) );
        aVal = ccResultSet.getFloat( 1 );
        assertThat( aVal, equalTo( 1.0f ) );
        assertThat( ccResultSet.next(), equalTo( false ) );
    }

    @Test
    public void testOneRowOfDoubles() throws SQLException {
        String dbUrl = "jdbc:derby:memory:demo;create=true";
        Connection conn = DriverManager.getConnection( dbUrl );

        Statement stmt = conn.createStatement();
        stmt.executeUpdate( "CREATE TABLE TEST( A double )" );
        stmt.executeUpdate( "INSERT INTO TEST VALUES (1.0)" );
        stmt.close();


        Statement getValsStmt = conn.createStatement();
        ResultSet resultSet = getValsStmt.executeQuery( "SELECT A FROM TEST" );
        List<Map<String,Object>> jsonResultSet =
            ResultSetConverter.getEntitiesFromResultSet( resultSet );
        ChronoCacheResultSet ccResultSet = new ChronoCacheResultSet(
                jsonResultSet );
        getValsStmt.close();
        conn.close();

        assertThat( ccResultSet.getRow(), equalTo( -1 ) );
        assertThat( ccResultSet.next(), equalTo( true ) );
        Double aVal = ccResultSet.getDouble( "A" );
        assertThat( aVal, equalTo( 1.0 ) );
        aVal = ccResultSet.getDouble( 1 );
        assertThat( aVal, equalTo( 1.0 ) );

        BigDecimal aVal2 =  ccResultSet.getBigDecimal( "A" );
        assertThat( aVal2.doubleValue(), equalTo( 1.0 ) );
        aVal2 = ccResultSet.getBigDecimal( 1 );
        assertThat( aVal2.doubleValue(), equalTo( 1.0 ) );
        assertThat( ccResultSet.next(), equalTo( false ) );
    }

    @Test
    public void testOneRowOfStrings() throws SQLException {
        String dbUrl = "jdbc:derby:memory:demo;create=true";
        Connection conn = DriverManager.getConnection( dbUrl );

        Statement stmt = conn.createStatement();
        stmt.executeUpdate( "CREATE TABLE TEST( A varchar(30))" );
        stmt.executeUpdate( "INSERT INTO TEST VALUES ('asdf')" );
        stmt.close();

        Statement getValsStmt = conn.createStatement();
        ResultSet resultSet = getValsStmt.executeQuery( "SELECT A FROM TEST" );
        List<Map<String,Object>> jsonResultSet =
            ResultSetConverter.getEntitiesFromResultSet( resultSet );
        ChronoCacheResultSet ccResultSet = new ChronoCacheResultSet(
                jsonResultSet );
        getValsStmt.close();
        conn.close();

        assertThat( ccResultSet.getRow(), equalTo( -1 ) );
        assertThat( ccResultSet.next(), equalTo( true ) );
        String aVal = ccResultSet.getString( "A" );
        assertThat( aVal, equalTo( "asdf" ) );
        aVal = ccResultSet.getString( 1 );
        assertThat( aVal, equalTo( "asdf" ) );
    }


    @Test
    public void testOneRowOfDates() throws SQLException {
        String dbUrl = "jdbc:derby:memory:demo;create=true";
        Connection conn = DriverManager.getConnection( dbUrl );

        Statement stmt = conn.createStatement();
        stmt.executeUpdate( "CREATE TABLE TEST( A date, B date)" );
        stmt.executeUpdate( "INSERT INTO TEST VALUES ('2020-03-01', '2012-02-08')" );
        stmt.close();

        Statement getValsStmt = conn.createStatement();
        ResultSet resultSet = getValsStmt.executeQuery( "SELECT A, B FROM TEST" );
        List<Map<String,Object>> jsonResultSet =
            ResultSetConverter.getEntitiesFromResultSet( resultSet );
        ChronoCacheResultSet ccResultSet = new ChronoCacheResultSet(
                jsonResultSet );
        getValsStmt.close();
        conn.close();

        assertThat( ccResultSet.getRow(), equalTo( -1 ) );
        assertThat( ccResultSet.next(), equalTo( true ) );
        Date aVal = ccResultSet.getDate( "A" );
        Calendar cal = Calendar.getInstance();
        cal.setTime( aVal );
        assertThat( cal.get( Calendar.YEAR ), equalTo( 2020 ) );
        assertThat( cal.get( Calendar.MONTH ), equalTo( 3 - 1 /* MONTH OFFSET */ ) );
        assertThat( cal.get( Calendar.DAY_OF_MONTH ), equalTo( 1 ) );
        Date bVal = ccResultSet.getDate( "B" );
        cal.setTime( bVal );
        assertThat( cal.get( Calendar.YEAR ), equalTo( 2012 ) );
        assertThat( cal.get( Calendar.MONTH ), equalTo( 2 - 1 /* MONTH OFFSET */ ) );
        assertThat( cal.get( Calendar.DAY_OF_MONTH ), equalTo( 8 ) );
    }

    @Test
    public void testOneRowOfTimestamps() throws SQLException {
        String dbUrl = "jdbc:derby:memory:demo;create=true";
        Connection conn = DriverManager.getConnection( dbUrl );

        Statement stmt = conn.createStatement();
        stmt.executeUpdate( "CREATE TABLE TEST( A Timestamp )" );
        stmt.executeUpdate( "INSERT INTO TEST VALUES ('2020-03-01 12:12:12')");
        stmt.close();

        Statement getValsStmt = conn.createStatement();
        ResultSet resultSet = getValsStmt.executeQuery( "SELECT A FROM TEST" );
        List<Map<String,Object>> jsonResultSet =
            ResultSetConverter.getEntitiesFromResultSet( resultSet );
        ChronoCacheResultSet ccResultSet = new ChronoCacheResultSet(
                jsonResultSet );
        getValsStmt.close();
        conn.close();

        assertThat( ccResultSet.getRow(), equalTo( -1 ) );
        assertThat( ccResultSet.next(), equalTo( true ) );
        Timestamp aVal = ccResultSet.getTimestamp( "A" );
        Calendar cal = Calendar.getInstance();
        cal.setTime( aVal );
        assertThat( cal.get( Calendar.YEAR ), equalTo( 2020 ) );
        assertThat( cal.get( Calendar.MONTH ), equalTo( 3-1 ) );
        assertThat( cal.get( Calendar.DAY_OF_MONTH ), equalTo( 1 ) );
        assertThat( cal.get( Calendar.HOUR_OF_DAY ), equalTo( 12 ) );
        assertThat( cal.get( Calendar.MINUTE ), equalTo( 12 ) );
        assertThat( cal.get( Calendar.SECOND ), equalTo( 12 ) );
    }

    @Test
    public void testOneRowOfTime() throws SQLException {
        String dbUrl = "jdbc:derby:memory:demo;create=true";
        Connection conn = DriverManager.getConnection( dbUrl );

        Statement stmt = conn.createStatement();
        stmt.executeUpdate( "CREATE TABLE TEST( A Time )" );
        stmt.executeUpdate( "INSERT INTO TEST VALUES ('12:12:12')");
        stmt.close();

        Statement getValsStmt = conn.createStatement();
        ResultSet resultSet = getValsStmt.executeQuery( "SELECT A FROM TEST" );
        List<Map<String,Object>> jsonResultSet =
            ResultSetConverter.getEntitiesFromResultSet( resultSet );
        ChronoCacheResultSet ccResultSet = new ChronoCacheResultSet(
                jsonResultSet );
        getValsStmt.close();
        conn.close();

        assertThat( ccResultSet.getRow(), equalTo( -1 ) );
        assertThat( ccResultSet.next(), equalTo( true ) );
        Time aVal = ccResultSet.getTime( "A" );
        Calendar cal = Calendar.getInstance();
        cal.setTime( aVal );
        assertThat( cal.get( Calendar.HOUR_OF_DAY ), equalTo( 12 ) );
        assertThat( cal.get( Calendar.MINUTE ), equalTo( 12 ) );
        assertThat( cal.get( Calendar.SECOND ), equalTo( 12 ) );
    }

    @Test
    public void testTwoRows() throws SQLException {
        String dbUrl = "jdbc:derby:memory:demo;create=true";
        Connection conn = DriverManager.getConnection( dbUrl );

        Statement stmt = conn.createStatement();
        stmt.executeUpdate( "CREATE TABLE TEST( A int )" );
        stmt.executeUpdate( "INSERT INTO TEST VALUES (1)");
        stmt.executeUpdate( "INSERT INTO TEST VALUES (2)");
        stmt.close();

        Statement getValsStmt = conn.createStatement();
        ResultSet resultSet = getValsStmt.executeQuery( "SELECT A FROM TEST" );
        List<Map<String,Object>> jsonResultSet =
            ResultSetConverter.getEntitiesFromResultSet( resultSet );
        ChronoCacheResultSet ccResultSet = new ChronoCacheResultSet(
                jsonResultSet );
        getValsStmt.close();
        conn.close();

        assertThat( ccResultSet.isBeforeFirst(), equalTo( true ) );
        assertThat( ccResultSet.next(), equalTo( true ) );
        assertThat( ccResultSet.getInt(1), equalTo(1) );
        assertThat( ccResultSet.next(), equalTo( true ) );
        assertThat( ccResultSet.getInt(1), equalTo(2) );
        assertThat( ccResultSet.isLast(), equalTo(true) );
        assertThat( ccResultSet.next(), equalTo( false) );
        assertThat( ccResultSet.isAfterLast(), equalTo(true) );
    }

    public ChronoCacheResultSet createTwoRowResultSet( Connection conn ) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate( "CREATE TABLE TEST( A int, B varchar(20) )" );
        stmt.executeUpdate( "INSERT INTO TEST VALUES (1, 'brad')");
        stmt.executeUpdate( "INSERT INTO TEST VALUES (2, 'michael')");
        stmt.close();

        Statement getValsStmt = conn.createStatement();
        ResultSet resultSet = getValsStmt.executeQuery( "SELECT A,B FROM TEST" );
        List<Map<String,Object>> jsonResultSet =
            ResultSetConverter.getEntitiesFromResultSet( resultSet );
        ChronoCacheResultSet ccResultSet = new ChronoCacheResultSet(
                jsonResultSet );
        getValsStmt.close();
        conn.close();

        return ccResultSet;
    }

    @Test
    public void testMultiRowMultiTypes() throws SQLException {
        String dbUrl = "jdbc:derby:memory:demo;create=true";
        Connection conn = DriverManager.getConnection( dbUrl );

        ChronoCacheResultSet ccResultSet = createTwoRowResultSet( conn );
        assertThat( ccResultSet.isBeforeFirst(), equalTo( true ) );
        assertThat( ccResultSet.next(), equalTo( true ) );
        assertThat( ccResultSet.getInt(1), equalTo(1) );
        assertThat( ccResultSet.getString(2), equalTo("brad") );
        assertThat( ccResultSet.next(), equalTo( true ) );
        assertThat( ccResultSet.getInt(1), equalTo(2) );
        assertThat( ccResultSet.getString(2), equalTo("michael") );
        assertThat( ccResultSet.isLast(), equalTo(true) );
        assertThat( ccResultSet.next(), equalTo( false) );
        assertThat( ccResultSet.isAfterLast(), equalTo(true) );
    }

    @Test
    public void testAbsoluteRowPositioning() throws SQLException {
        String dbUrl = "jdbc:derby:memory:demo;create=true";
        Connection conn = DriverManager.getConnection( dbUrl );

        ChronoCacheResultSet ccResultSet = createTwoRowResultSet( conn );
        assertThat( ccResultSet.absolute(1), equalTo( true ) );
        assertThat( ccResultSet.getInt(1), equalTo(1) );
        assertThat( ccResultSet.getString(2), equalTo("brad") );
        assertThat( ccResultSet.absolute(2), equalTo( true ) );
        assertThat( ccResultSet.getInt(1), equalTo(2) );
        assertThat( ccResultSet.getString(2), equalTo("michael") );
        assertThat( ccResultSet.absolute(3), equalTo( false ) );
    }

    @Test
    public void testRelativeRowPositioning() throws SQLException {
        String dbUrl = "jdbc:derby:memory:demo;create=true";
        Connection conn = DriverManager.getConnection( dbUrl );

        ChronoCacheResultSet ccResultSet = createTwoRowResultSet( conn );
        assertThat( ccResultSet.absolute(2), equalTo( true ) );
        assertThat( ccResultSet.getInt(1), equalTo(2) );
        assertThat( ccResultSet.getString(2), equalTo("michael") );
        assertThat( ccResultSet.relative(-1), equalTo( true ) );
        assertThat( ccResultSet.getInt(1), equalTo(1) );
        assertThat( ccResultSet.getString(2), equalTo("brad") );

        assertThat( ccResultSet.relative(1), equalTo( true ) );
        assertThat( ccResultSet.getInt(1), equalTo(2) );
        assertThat( ccResultSet.getString(2), equalTo("michael") );
        assertThat( ccResultSet.relative(1), equalTo( false ) );
        assertThat( ccResultSet.relative(-2), equalTo( false ) );
    }

    @Test
    public void testPreviousNextCalls() throws SQLException {
        String dbUrl = "jdbc:derby:memory:demo;create=true";
        Connection conn = DriverManager.getConnection( dbUrl );

        ChronoCacheResultSet ccResultSet = createTwoRowResultSet( conn );
        assertThat( ccResultSet.next(), equalTo( true ) ); //first row
        assertThat( ccResultSet.previous(), equalTo( false ) ); // before first
        assertThat( ccResultSet.next(), equalTo( true ) ); //second
        assertThat( ccResultSet.previous(), equalTo( true ) ); //first
    }

}
