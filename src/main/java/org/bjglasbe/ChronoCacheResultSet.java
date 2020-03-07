import java.util.List;
import java.util.Map;


public class ChronoCacheResultSet implements ResultSet {

    private List<Map<String,Object>> resultSet;
    private Map<String, Integer> colNameToId;
    private Map<Integer, String> idToColName;
    private Integer currentRowId;

    public class ChronoCacheResultSet(
        List<Map<String, Object>> resultSet
    ) {
        this.resultSet = resultSet;
        this.currentRowId = 0;

        idToColName = new HashMap<>();
        if( !idToColName.isEmpty() ) {
            Map<String, Object> row = resultSet.get( 0 );
            Integer i = 0;
            for( String colName : row.keySet() ) {
               colNameToId.put( colName, i ); 
               idToColName.put( i, colName );
            }
        }
    }

    @Override
    public boolean absolute( int row ) {
        //Move to this row number
        this.currentRowId = row;
    }

    @Override
    public void afterLast() {
        // Move to the end
        this.currentRowId = resultSet.size();
    } 
    @Override
    public void beforeFirst() {
        //Move to the first
        this.currentRowId = 0;
    }

    @Override
    public void cancelRowUpdates() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clearWarnings() {
    }

    @Override
    public void close() {
    }

    @Override
    public void deleteRow() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int findColumn( String columnLabel ) {
        if( !colNameToId.containsKey( columnName ) ) {
            return -1;
        }
        return colNameToId.get( columnName );
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

    @Override
    public BigDecimal getBigDecimal( int columnIndex ) {
        String colName = idToColName.get( columnIndex );
        if( colname == null ) {
            throw new SQLException();
        }
        Map<String,Object> row = resultSet.get( currentRowId );
        Object val = row.get( colName );
        if( val == null || !(val instanceof String) ) {
            throw new SQLException();
        }
        String strVal = (String) val;
        return new BigDecimal( strVal );
    }

    @Override
    public BigDecimal getBigDecimal( int columnIndex, int scale ) {
        throw new UnsupportedOperationException();
    }

    @Override
    public BigDecimal getBigDecimal( String columnLabel ) {
        Object val = row.get( columnLabel );
        if( val == null || !(val instanceof String) ) {
            throw new SQLException();
        }
        String strVal = (String) val;
        return new BigDecimal( strVal );
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
    public boolean getBoolean( int columnIndex ) {
        String colName = idToColName.get( columnIndex );
        if( colname == null ) {
            throw new SQLException();
        }
        Map<String,Object> row = resultSet.get( currentRowId );
        Object val = row.get( colName );
        if( val == null || !(val instanceof Boolean) ) {
            throw new SQLException();
        }
        return (Boolean) val;
    }
    @Override
    public boolean getBoolean( String columnLabel ) {
        Object val = row.get( columnLabel );
        if( val == null || !(val instanceof Boolean) ) {
            throw new SQLException();
        }
        return (Boolean) val;
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
    public Date getDate(int columnIndex)
    @Override
    public Date getDate(int columnIndex, Calendar cal)
    @Override
    public Date getDate(String columnLabel)
    @Override
    public Date getDate(String columnLabel, Calendar cal)
    @Override
    public double getDouble(int columnIndex)
    @Override
    public double getDouble(String columnLabel)
    @Override
    public int getFetchDirection()
    @Override
    public int getFetchSize()
    @Override
    public float getFloat(int columnIndex)
    @Override
    public float getFloat(String columnLabel)
    @Override
    public int getHoldability()
    @Override
    public int getInt(int columnIndex)
    @Override
    public int getInt(String columnLabel)
    @Override
    public long getLong(int columnIndex)
    @Override
    public long getLong(String columnLabel)
    @Override
    public ResultSetMetaData getMetaData()
    @Override
    public Reader getNCharacterStream(int columnIndex)
    @Override
    public Reader getNCharacterStream(String columnLabel)
    @Override
    public NClob getNClob(int columnIndex)
    @Override
    public NClob getNClob(String columnLabel)
    @Override
    public String getNString(int columnIndex)
    @Override
    public String getNString(String columnLabel)
    @Override
    public Object getObject(int columnIndex)
    @Override
    public <T> T getObject(int columnIndex, Class<T> type)
    @Override
    public Object getObject(int columnIndex, Map<String,Class<?>> map)
    @Override
    public Object getObject(String columnLabel)
    @Override
    public <T> T getObject(String columnLabel, Class<T> type)
    @Override
    public Object getObject(String columnLabel, Map<String,Class<?>> map)
    @Override
    public Ref getRef(int columnIndex)
    @Override
    public Ref getRef(String columnLabel)
    @Override
    public int getRow()
    @Override
    public RowId getRowId(int columnIndex)
    @Override
    public RowId getRowId(String columnLabel)
    @Override
    public short getShort(int columnIndex)
    @Override
    public short getShort(String columnLabel)
    @Override
    public SQLXML getSQLXML(int columnIndex)
    @Override
    public SQLXML getSQLXML(String columnLabel)
    @Override
    public Statement getStatement()
    @Override
    public String getString(int columnIndex)
    @Override
    public String getString(String columnLabel)
    @Override
    public Time getTime(int columnIndex)
    @Override
    public Time getTime(int columnIndex, Calendar cal)
    @Override
    public Time getTime(String columnLabel)
    @Override
    public Time getTime(String columnLabel, Calendar cal)
    @Override
    public Timestamp getTimestamp(int columnIndex)
    @Override
    public Timestamp getTimestamp(int columnIndex, Calendar cal)
    @Override
    public Timestamp getTimestamp(String columnLabel)
    @Override
    public Timestamp getTimestamp(String columnLabel, Calendar cal)
    @Override
    public int getType()
    @Override
    public InputStream getUnicodeStream(int columnIndex)
    @Override
    public InputStream getUnicodeStream(String columnLabel)
    @Override
    public URL getURL(int columnIndex)
    @Override
    public URL getURL(String columnLabel)
    @Override
    public SQLWarning getWarnings()
    @Override
    public void insertRow()
    @Override
    public boolean isAfterLast()
    @Override
    public boolean isBeforeFirst()
    @Override
    public boolean isClosed()
    @Override
    public boolean isFirst()
    @Override
    public boolean isLast()
    @Override
    public boolean last()
    @Override
    public void moveToCurrentRow()
    @Override
    public void moveToInsertRow()
    @Override
    public boolean next()
    @Override
    public boolean previous()
    @Override
    public void refreshRow()
    @Override
    public boolean relative(int rows)
    @Override
    public boolean rowDeleted()
    @Override
    public boolean rowInserted()
    @Override
    public boolean rowUpdated()
    @Override
    public void setFetchDirection(int direction)
    @Override
    public void setFetchSize(int rows)
    @Override
    public void updateArray(int columnIndex, Array x)
    @Override
    public void updateArray(String columnLabel, Array x)
    @Override
    public void updateAsciiStream(int columnIndex, InputStream x)
    @Override
    public void updateAsciiStream(int columnIndex, InputStream x, int length)
    @Override
    public void updateAsciiStream(int columnIndex, InputStream x, long length)
    @Override
    public void updateAsciiStream(String columnLabel, InputStream x)
    @Override
    public void updateAsciiStream(String columnLabel, InputStream x, int length)
    @Override
    public void updateAsciiStream(String columnLabel, InputStream x, long length)
    @Override
    public void updateBigDecimal(int columnIndex, BigDecimal x)
    @Override
    public void updateBigDecimal(String columnLabel, BigDecimal x)
    @Override
    public void updateBinaryStream(int columnIndex, InputStream x)
    @Override
    public void updateBinaryStream(int columnIndex, InputStream x, int length)
    @Override
    public void updateBinaryStream(int columnIndex, InputStream x, long length)
    @Override
    public void updateBinaryStream(String columnLabel, InputStream x)
    @Override
    public void updateBinaryStream(String columnLabel, InputStream x, int length)
    @Override
    public void updateBinaryStream(String columnLabel, InputStream x, long length)
    @Override
    public void updateBlob(int columnIndex, Blob x)
    @Override
    public void updateBlob(int columnIndex, InputStream inputStream)
    @Override
    public void updateBlob(int columnIndex, InputStream inputStream, long length)
    @Override
    public void updateBlob(String columnLabel, Blob x)
    @Override
    public void updateBlob(String columnLabel, InputStream inputStream)
    @Override
    public void updateBlob(String columnLabel, InputStream inputStream, long length)
    @Override
    public void updateBoolean(int columnIndex, boolean x)
    @Override
    public void updateBoolean(String columnLabel, boolean x)
    @Override
    public void updateByte(int columnIndex, byte x)
    @Override
    public void updateByte(String columnLabel, byte x)
    @Override
    public void updateBytes(int columnIndex, byte[] x)
    @Override
    public void updateBytes(String columnLabel, byte[] x)
    @Override
    public void updateCharacterStream(int columnIndex, Reader x)
    @Override
    public void updateCharacterStream(int columnIndex, Reader x, int length)
    @Override
    public void updateCharacterStream(int columnIndex, Reader x, long length)
    @Override
    public void updateCharacterStream(String columnLabel, Reader reader)
    @Override
    public void updateCharacterStream(String columnLabel, Reader reader, int length)
    @Override
    public void updateCharacterStream(String columnLabel, Reader reader, long length)
    @Override
    public void updateClob(int columnIndex, Clob x)
    @Override
    public void updateClob(int columnIndex, Reader reader)
    @Override
    public void updateClob(int columnIndex, Reader reader, long length)
    @Override
    public void updateClob(String columnLabel, Clob x)
    @Override
    public void updateClob(String columnLabel, Reader reader)
    @Override
    public void updateClob(String columnLabel, Reader reader, long length)
    @Override
    public void updateDate(int columnIndex, Date x)
    @Override
    public void updateDate(String columnLabel, Date x)
    @Override
    public void updateDouble(int columnIndex, double x)
    @Override
    public void updateDouble(String columnLabel, double x)
    @Override
    public void updateFloat(int columnIndex, float x)
    @Override
    public void updateFloat(String columnLabel, float x)
    @Override
    public void updateInt(int columnIndex, int x)
    @Override
    public void updateInt(String columnLabel, int x)
    @Override
    public void updateLong(int columnIndex, long x)
    @Override
    public void updateLong(String columnLabel, long x)
    @Override
    public void updateNCharacterStream(int columnIndex, Reader x)
    @Override
    public void updateNCharacterStream(int columnIndex, Reader x, long length)
    @Override
    public void updateNCharacterStream(String columnLabel, Reader reader)
    @Override
    public void updateNCharacterStream(String columnLabel, Reader reader, long length)
    @Override
    public void updateNClob(int columnIndex, NClob nClob)
    @Override
    public void updateNClob(int columnIndex, Reader reader)
    @Override
    public void updateNClob(int columnIndex, Reader reader, long length)
    @Override
    public void updateNClob(String columnLabel, NClob nClob)
    @Override
    public void updateNClob(String columnLabel, Reader reader)
    @Override
    public void updateNClob(String columnLabel, Reader reader, long length)
    @Override
    public void updateNString(int columnIndex, String nString)
    @Override
    public void updateNString(String columnLabel, String nString)
    @Override
    public void updateNull(int columnIndex)
    @Override
    public void updateNull(String columnLabel)
    @Override
    public void updateObject(int columnIndex, Object x)
    @Override
    public void updateObject(int columnIndex, Object x, int scaleOrLength)
    @Override
    public void updateObject(String columnLabel, Object x)
    @Override
    public void updateObject(String columnLabel, Object x, int scaleOrLength)
    @Override
    public void updateRef(int columnIndex, Ref x)
    @Override
    public void updateRef(String columnLabel, Ref x)
    @Override
    public void updateRow()
    @Override
    public void updateRowId(int columnIndex, RowId x)
    @Override
    public void updateRowId(String columnLabel, RowId x)
    @Override
    public void updateShort(int columnIndex, short x)
    @Override
    public void updateShort(String columnLabel, short x)
    @Override
    public void updateSQLXML(int columnIndex, SQLXML xmlObject)
    @Override
    public void updateSQLXML(String columnLabel, SQLXML xmlObject)
    @Override
    public void updateString(int columnIndex, String x)
    @Override
    public void updateString(String columnLabel, String x)
    @Override
    public void updateTime(int columnIndex, Time x)
    @Override
    public void updateTime(String columnLabel, Time x)
    @Override
    public void updateTimestamp(int columnIndex, Timestamp x)
    @Override
    public void updateTimestamp(String columnLabel, Timestamp x)
    @Override
    public boolean wasNull()
}
