package org.bjglasbe;

import java.io.InputStream;
import java.io.Reader;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.SQLWarning;
import java.sql.Time;
import java.sql.Timestamp;

public class ChronoCacheResultSet implements ResultSet {

    private List<Map<String,Object>> resultSet;
    private Map<String, Integer> colNameToId;
    private Map<Integer, String> idToColName;
    private Integer currentRowId;

    public ChronoCacheResultSet(
        List<Map<String, Object>> resultSet
    ) {
        this.resultSet = resultSet;
        this.currentRowId = -1;

        idToColName = new HashMap<>();
        colNameToId = new HashMap<>();
        if( !resultSet.isEmpty() ) {
            Map<String, Object> row = resultSet.get( 0 );
            Integer i = 1;
            for( String colName : row.keySet() ) {
               colNameToId.put( colName, i ); 
               idToColName.put( i, colName );
            }
        }
    }

    @Override
    public boolean absolute( int row ) throws SQLException {
        //Move to this row number
        if( row >= 0 && row < resultSet.size() ) {
            this.currentRowId = row;
            return true;
        }
        return false;
    }

    @Override
    public void afterLast() throws SQLException {
        this.currentRowId = resultSet.size();
    } 
    @Override
    public void beforeFirst() throws SQLException {
        //Move to the first
        this.currentRowId = -1;
    }

    @Override
    public void cancelRowUpdates() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clearWarnings() throws SQLException {
    }

    @Override
    public void close() throws SQLException {
    }

    @Override
    public void deleteRow() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int findColumn( String columnLabel ) throws SQLException {
        return colNameToId.get( columnLabel );
    }

    @Override
    public boolean first() {
        if( resultSet.isEmpty() ) {
            return false;
        }
        currentRowId = 0;
        return true;
    }

    @Override
    public Array getArray( int columnIndex ) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Array getArray( String columnLabel ) {
        throw new UnsupportedOperationException();
    }

    @Override
    public InputStream getAsciiStream( int columnIndex ) {
        throw new UnsupportedOperationException();
    }

    @Override
    public InputStream getAsciiStream( String columnLabel ) {
        throw new UnsupportedOperationException();
    }

    private <T> T getColumnValueAsType( String colName, Class<T> type ) throws SQLException {
        Map<String,Object> row = resultSet.get( currentRowId );
        System.out.println("We got row: " + row );
        Object val = row.get( colName );
        System.out.println("We got value: " + val );
        System.out.println( val.getClass().getName() );
        if( val == null ) {
            throw new SQLException(
                    String.format( "No val for column %s in row %d\n",
                        colName, currentRowId ) );
        }
        if( !type.isInstance(val) ) {
            throw new SQLException(
                    String.format( "val in column %s is not of type %s",
                        colName, type ) );
        }
        return (T) val;
    }

    private <T> T getColumnValueAsType( int columnIndex, Class<T> type ) throws SQLException {
        String colName = idToColName.get( columnIndex );
        System.out.println( "ColInd: " + columnIndex + " " + idToColName );
        if( colName == null ) {
            throw new SQLException(
                    String.format( "ColumnName %s not found", colName ) );
        }
        return getColumnValueAsType( colName, type );
    }

    @Override
    public BigDecimal getBigDecimal( int columnIndex ) throws SQLException {
        Double d = getColumnValueAsType( columnIndex, Double.class );
        return new BigDecimal( d );
    }

    @Override
    public BigDecimal getBigDecimal( int columnIndex, int scale ) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public BigDecimal getBigDecimal( String columnLabel ) throws SQLException {
        Double d = getColumnValueAsType( columnLabel, Double.class );
        return new BigDecimal( d );
    }

    @Override
    public BigDecimal getBigDecimal( String columnLabel, int scale ) {
        throw new UnsupportedOperationException();
    }

    @Override
    public InputStream getBinaryStream( int columnIndex ) {
        throw new UnsupportedOperationException();
    }

    @Override
    public InputStream getBinaryStream( String columnLabel ) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Blob getBlob( int columnIndex ) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Blob getBlob( String columnLabel ) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getBoolean( int columnIndex ) throws SQLException {
        Boolean b = getColumnValueAsType( columnIndex, Boolean.class );
        return b;
    }
    @Override
    public boolean getBoolean( String columnLabel ) throws SQLException {
        Boolean b = getColumnValueAsType( columnLabel, Boolean.class );
        return b;
    }

    @Override
    public byte getByte( int columnIndex ) {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte getByte( String columnLabel ) {
        throw new UnsupportedOperationException();
    }
    @Override
    public byte[] getBytes( int columnIndex ) {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte[] getBytes( String columnLabel ) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Reader getCharacterStream( int columnIndex ) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Reader getCharacterStream( String columnLabel ) {
        throw new UnsupportedOperationException();
    }
    @Override
    public Clob getClob( int columnIndex ) {
        throw new UnsupportedOperationException();
    }
    @Override
    public Clob getClob( String columnLabel ) {
        throw new UnsupportedOperationException();
    }
    @Override
    public int getConcurrency() {
        return ResultSet.CONCUR_READ_ONLY;
    }

    @Override
    public String getCursorName() {
        return null;
    }
    
    // Continue from here.
    @Override
    public Date getDate( int columnIndex ) throws SQLException {
        Date val = getColumnValueAsType( columnIndex, Date.class );
        // FIXME: Probably need to Strformat correctly.
        return val;
    }

    @Override
    public Date getDate(int columnIndex, Calendar cal) throws SQLException {
        Date val = getColumnValueAsType( columnIndex, Date.class );
        // FIXME: Probably need to Strformat correctly.
        return val;
    }

    @Override
    public Date getDate(String columnLabel) throws SQLException {
        Date val = getColumnValueAsType( columnLabel, Date.class );
        // FIXME: Probably need to Strformat correctly.
        return val;
    }

    @Override
    public Date getDate(String columnLabel, Calendar cal) throws SQLException {
        Date val = getColumnValueAsType( columnLabel, Date.class );
        // FIXME: Probably need to Strformat correctly.
        return val;
    }

    @Override
    public double getDouble(int columnIndex) throws SQLException {
        Double val = getColumnValueAsType( columnIndex, Double.class );
        return val;
    }

    @Override
    public double getDouble(String columnLabel) throws SQLException {
        Double val = getColumnValueAsType( columnLabel, Double.class );
        return val;
    }

    @Override
    public int getFetchDirection() {
        return ResultSet.FETCH_FORWARD;
    }

    @Override
    public int getFetchSize() {
        return 0;
    }

    @Override
    public float getFloat(int columnIndex) throws SQLException {
        Double d = getColumnValueAsType( columnIndex, Double.class );
        return d.floatValue();
    }

    @Override
    public float getFloat(String columnLabel) throws SQLException {
        Double d = getColumnValueAsType( columnLabel, Double.class );
        return d.floatValue();
    }

    @Override
    public int getHoldability() {
        return ResultSet.HOLD_CURSORS_OVER_COMMIT;
    }

    @Override
    public int getInt(int columnIndex) throws SQLException {
        Integer i = getColumnValueAsType( columnIndex, Integer.class );
        return i;
    }

    @Override
    public int getInt(String columnLabel) throws SQLException {
        Integer i = getColumnValueAsType( columnLabel, Integer.class );
        return i;
    }

    @Override
    public long getLong(int columnIndex) throws SQLException {
        Long l = getColumnValueAsType( columnIndex, Long.class );
        return l;
    }

    @Override
    public long getLong(String columnLabel) throws SQLException {
        Long l = getColumnValueAsType( columnLabel, Long.class );
        return l;
    }

    @Override
    public ResultSetMetaData getMetaData() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Reader getNCharacterStream(int columnIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Reader getNCharacterStream(String columnLabel) {
        throw new UnsupportedOperationException();
    }
    @Override
    public NClob getNClob(int columnIndex) {
        throw new UnsupportedOperationException();
    }
    @Override
    public NClob getNClob(String columnLabel) {
        throw new UnsupportedOperationException();
    }
    @Override
    public String getNString(int columnIndex) throws SQLException {
        String s = getColumnValueAsType( columnIndex, String.class );
        return s;
    }
    @Override
    public String getNString(String columnLabel) throws SQLException {
        String s = getColumnValueAsType( columnLabel, String.class );
        return s;
    }
    @Override
    public Object getObject(int columnIndex) throws SQLException {
        Object o = getColumnValueAsType( columnIndex, Object.class );
        return o;
    }
    @Override
    public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
        T t = getColumnValueAsType( columnIndex, type );
        return t;
    }
    @Override
    public Object getObject(int columnIndex, Map<String,Class<?>> map) {
        throw new UnsupportedOperationException();
    }
    @Override
    public Object getObject(String columnLabel) throws SQLException {
        Object o = getColumnValueAsType( columnLabel, Object.class );
        return o;
    }
    @Override
    public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
        T t = getColumnValueAsType( columnLabel, type );
        return t;
    }
    @Override
    public Object getObject(
        String columnLabel,
        Map<String,Class<?>> map
    ) {
        throw new UnsupportedOperationException();
    }
    @Override
    public Ref getRef(int columnIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Ref getRef(String columnLabel) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getRow() {
        return currentRowId;
    }

    @Override
    public RowId getRowId(int columnIndex) {
        throw new UnsupportedOperationException();
    }
    @Override
    public RowId getRowId(String columnLabel) {
        throw new UnsupportedOperationException();
    }
    @Override
    public short getShort(int columnIndex) throws SQLException {
        Integer i = getColumnValueAsType( columnIndex, Integer.class );
        return i.shortValue();
    }
    @Override
    public short getShort(String columnLabel) throws SQLException {
        Integer i = getColumnValueAsType( columnLabel, Integer.class );
        return i.shortValue();
    }
        
    @Override
    public SQLXML getSQLXML(int columnIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SQLXML getSQLXML(String columnLabel) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Statement getStatement() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getString(int columnIndex) throws SQLException {
        String s = getColumnValueAsType( columnIndex, String.class );
        return s;
    }
    
    @Override
    public String getString(String columnLabel) throws SQLException {
        String s = getColumnValueAsType( columnLabel, String.class );
        return s;
    }

    @Override
    public Time getTime(int columnIndex) throws SQLException {
        Time s = getColumnValueAsType( columnIndex, Time.class );
        // FIXME: Properly convert time.
        return s;
    }

    @Override
    public Time getTime(int columnIndex, Calendar cal) throws SQLException {
        Time s = getColumnValueAsType( columnIndex, Time.class );
        // FIXME: Properly convert time.
        return s;
    }

    @Override
    public Time getTime(String columnLabel) throws SQLException {
        Time s = getColumnValueAsType( columnLabel, Time.class );
        // FIXME: Properly convert time.
        return s;

    }

    @Override
    public Time getTime(String columnLabel, Calendar cal) throws SQLException {
        Time s = getColumnValueAsType( columnLabel, Time.class );
        // FIXME: Properly convert time.
        return s;
    }
    @Override
    public Timestamp getTimestamp(int columnIndex) throws SQLException {
        Timestamp s = getColumnValueAsType( columnIndex, Timestamp.class );
        // FIXME: Properly convert time.
        return s;
    }

    @Override
    public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
        Timestamp s = getColumnValueAsType( columnIndex, Timestamp.class );
        // FIXME: Properly convert time.
        return s;
    }

    @Override
    public Timestamp getTimestamp(String columnLabel) throws SQLException {
        Timestamp s = getColumnValueAsType( columnLabel, Timestamp.class );
        // FIXME: Properly convert time.
        return s;
    }

    @Override
    public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
        Timestamp s = getColumnValueAsType( columnLabel, Timestamp.class );
        // FIXME: Properly convert time.
        return s;
    }

    @Override
    public int getType() {
        return ResultSet.TYPE_SCROLL_INSENSITIVE;
    }

    @Override
    public InputStream getUnicodeStream(int columnIndex) {
        throw new UnsupportedOperationException();
    }
    @Override
    public InputStream getUnicodeStream(String columnLabel) {
        throw new UnsupportedOperationException();
    }

    @Override
    public URL getURL(int columnIndex) throws SQLException {
        String s = getColumnValueAsType( columnIndex, String.class );
        try {
            URL url = new URL( s );
            return url;
        } catch( MalformedURLException e ) {
            throw new SQLException( e );
        }
    }
    @Override
    public URL getURL(String columnLabel) throws SQLException {
        String s = getColumnValueAsType( columnLabel, String.class );
        try {
            URL url = new URL( s );
            return url;
        } catch( MalformedURLException e ) {
            throw new SQLException( e );
        }
    }
    @Override
    public SQLWarning getWarnings() {
        return null;
    }
    @Override
    public void insertRow() {
        throw new UnsupportedOperationException();
    }
    @Override
    public boolean isAfterLast() {
        return currentRowId >= resultSet.size();
    }
    @Override
    public boolean isBeforeFirst() {
        return currentRowId == -1;
    }
    @Override
    public boolean isClosed() {
        return false;
    }
    @Override
    public boolean isFirst() {
        return currentRowId == 0;
    }
    @Override
    public boolean isLast() {
        return currentRowId == resultSet.size()-1;
    }
    @Override
    public boolean last() {
        currentRowId = resultSet.size()-1;
        return true;
    }
    @Override
    public void moveToCurrentRow() {
    }
    @Override
    public void moveToInsertRow() {
    }
    @Override
    public boolean next() {
        currentRowId = Math.min( currentRowId+1, resultSet.size() );
        if( currentRowId >= resultSet.size() ) {
            return false;
        }
        return true;
    }
    @Override
    public boolean previous() {
        if( currentRowId == 0 ) {
            return false;
        } else {
            currentRowId--;
        }
        return true;
    }
    @Override
    public void refreshRow() {
    }
    @Override
    public boolean relative(int rows) {
        if( rows < 0 ) {
            if( currentRowId >= rows ) {
                currentRowId = currentRowId - rows;
                return true;
            } else {
                return false;
            }
        } else {
            if( currentRowId + rows > resultSet.size()-1 ) {
                return false;
            } else {
                currentRowId += rows;
                return false;
            }
        }
    }

    @Override
    public boolean rowDeleted() {
        return false;
    }
    @Override
    public boolean rowInserted() {
        return false;
    }
    @Override
    public boolean rowUpdated() {
        return false;
    }
    @Override
    public void setFetchDirection(int direction) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void setFetchSize(int rows) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateArray(int columnIndex, Array x) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateArray(String columnLabel, Array x) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateAsciiStream(int columnIndex, InputStream x) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateAsciiStream( 
        int columnIndex,
        InputStream x,
        int length
    ) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateAsciiStream(
        int columnIndex,
        InputStream x,
        long length
    ) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateAsciiStream(String columnLabel, InputStream x) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateAsciiStream(
        String columnLabel,
        InputStream x,
        int length
    ) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateAsciiStream(
        String columnLabel,
        InputStream x,
        long length
    ) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateBigDecimal(int columnIndex, BigDecimal x) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateBigDecimal(String columnLabel, BigDecimal x) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateBinaryStream(int columnIndex, InputStream x) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateBinaryStream(
        int columnIndex,
        InputStream x,
        int length
    ) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateBinaryStream(
        int columnIndex,
        InputStream x,
        long length
    ) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateBinaryStream(String columnLabel, InputStream x) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateBinaryStream(
            String columnLabel,
            InputStream x,
            int length
    ) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateBinaryStream(
        String columnLabel,
        InputStream x,
        long length
    ) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateBlob(int columnIndex, Blob x) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateBlob(int columnIndex, InputStream inputStream) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateBlob(
        int columnIndex,
        InputStream inputStream,
        long length
    ) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateBlob(String columnLabel, Blob x) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateBlob(String columnLabel, InputStream inputStream)
    {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateBlob(
        String columnLabel,
        InputStream inputStream,
        long length
     ) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateBoolean(int columnIndex, boolean x) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateBoolean(String columnLabel, boolean x) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateByte(int columnIndex, byte x) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateByte(String columnLabel, byte x) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateBytes(int columnIndex, byte[] x) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateBytes(String columnLabel, byte[] x) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateCharacterStream(int columnIndex, Reader x) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateCharacterStream(
        int columnIndex,
        Reader x,
        int length
    ) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateCharacterStream(
        int columnIndex,
        Reader x,
        long length
    ) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateCharacterStream(String columnLabel, Reader reader)
    {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateCharacterStream(
        String columnLabel,
        Reader reader,
        int length
    ) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateCharacterStream(
        String columnLabel,
        Reader reader,
        long length
    ) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateClob(int columnIndex, Clob x) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateClob(int columnIndex, Reader reader) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateClob(int columnIndex, Reader reader, long length)
    {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateClob(String columnLabel, Clob x) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateClob(String columnLabel, Reader reader) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateClob(String columnLabel, Reader reader, long
            length) {
        throw new UnsupportedOperationException();
            }
    @Override
    public void updateDate(int columnIndex, Date x) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateDate(String columnLabel, Date x) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateDouble(int columnIndex, double x) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateDouble(String columnLabel, double x) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateFloat(int columnIndex, float x) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateFloat(String columnLabel, float x) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateInt(int columnIndex, int x) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateInt(String columnLabel, int x) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateLong(int columnIndex, long x) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateLong(String columnLabel, long x) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateNCharacterStream(int columnIndex, Reader x) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateNCharacterStream(
        int columnIndex,
        Reader x,
        long length
    ) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateNCharacterStream(
        String columnLabel,
        Reader reader
    ) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateNCharacterStream(
        String columnLabel,
        Reader reader,
        long length
    ) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateNClob(int columnIndex, NClob nClob) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateNClob(int columnIndex, Reader reader) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateNClob(int columnIndex, Reader reader, long length)
    {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateNClob(String columnLabel, NClob nClob) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateNClob(String columnLabel, Reader reader) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateNClob(
        String columnLabel,
        Reader reader,
        long length
     ) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateNString(int columnIndex, String nString) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateNString(String columnLabel, String nString) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateNull(int columnIndex) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateNull(String columnLabel) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateObject(int columnIndex, Object x) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateObject(
        int columnIndex,
        Object x,
        int scaleOrLength
    ) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateObject(String columnLabel, Object x) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateObject(
            String columnLabel,
            Object x,
            int scaleOrLength
    ) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateRef(int columnIndex, Ref x) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateRef(String columnLabel, Ref x) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateRow() {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateRowId(int columnIndex, RowId x) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateRowId(String columnLabel, RowId x) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateShort(int columnIndex, short x) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateShort(String columnLabel, short x) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateSQLXML(int columnIndex, SQLXML xmlObject) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateSQLXML(String columnLabel, SQLXML xmlObject) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateString(int columnIndex, String x) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateString(String columnLabel, String x) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateTime(int columnIndex, Time x) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateTime(String columnLabel, Time x) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateTimestamp(int columnIndex, Timestamp x) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void updateTimestamp(String columnLabel, Timestamp x) {
        throw new UnsupportedOperationException();
    }
    @Override
    public boolean wasNull() {
        return false;
    }

    @Override
    public boolean isWrapperFor( Class<?> iface ) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T unwrap( Class<T> iface ) {
        throw new UnsupportedOperationException();
    }

}
