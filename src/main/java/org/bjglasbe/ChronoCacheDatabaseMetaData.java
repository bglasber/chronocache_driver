package org.bjglasbe;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.RowIdLifetime;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

// Most of these are just guesses/defensive, because ChronoCache interacts
// with some underlying database. Will probably need fixing later.

public class ChronoCacheDatabaseMetaData implements DatabaseMetaData {

    private Connection conn;

    public ChronoCacheDatabaseMetaData( Connection conn ) {
        this.conn = conn;
    }

    public ResultSet makeEmptyResultSet() {
        return new ChronoCacheResultSet( new LinkedList<Map<String,Object>>() );
    }

    public boolean allProceduresAreCallable() {
        return false;
    }

    public boolean allTablesAreSelectable() {
        return false;
    }

    public boolean autoCommitFailureClosesAllResultSets() {
        return false;
    }

    public boolean dataDefinitionCausesTransactionCommit() {
        return true;
    }

    public boolean dataDefinitionIgnoredInTransactions() {
        return false;
    }

    public boolean deletesAreDetected(int type) {
        return false;
    }

    public boolean doesMaxRowSizeIncludeBlobs() {
        return true;
    }

    public boolean generatedKeyAlwaysReturned() {
        return false;
    }

    public ResultSet getAttributes(String catalog, String schemaPattern, String typeNamePattern, String attributeNamePattern) {
        return makeEmptyResultSet();
    }

    public ResultSet getBestRowIdentifier(String catalog, String schema, String table, int scope, boolean nullable) {
        return makeEmptyResultSet();
    }

    public ResultSet getCatalogs() {
        return makeEmptyResultSet();
    }

    public String getCatalogSeparator() {
        return ".";
    }

    public String getCatalogTerm() {
        return "database";
    }

    public ResultSet getClientInfoProperties() {
        return makeEmptyResultSet();
    }

    public ResultSet getColumnPrivileges(String catalog, String schema, String table, String columnNamePattern) {
        return makeEmptyResultSet();
    }

    public ResultSet getColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern) {
        return makeEmptyResultSet();
    }

    public Connection getConnection() {
        return conn;
    }

    public ResultSet getCrossReference(String parentCatalog, String parentSchema, String parentTable, String foreignCatalog, String foreignSchema, String foreignTable) {
        return makeEmptyResultSet();
    }

    // PG 12.1
    public int getDatabaseMajorVersion() {
        return 12;
    }

    public int getDatabaseMinorVersion() {
        return 1;
    }

    public String getDatabaseProductName() {
        return "PostgreSQL";
    }

    public String getDatabaseProductVersion() {
        return "12.1";
    }

    public int getDefaultTransactionIsolation() {
        // Technically this is SSSI over submitted queries.
        return Connection.TRANSACTION_READ_COMMITTED;
    }

    public int getDriverMajorVersion() {
        return 0;
    }

    public int getDriverMinorVersion() {
        return 1;
    }

    public String getDriverName() {
        return "ChronoCacheDriver";
    }

    public String getDriverVersion() {
        return "1.0";
    }

    public ResultSet getExportedKeys(String catalog, String schema, String table) {
        return makeEmptyResultSet();
    }

    public String getExtraNameCharacters() {
        // No other chars pls
        return "";
    }

    public ResultSet getFunctionColumns(String catalog, String schemaPattern, String functionNamePattern, String columnNamePattern) {
        // we could probably support functions, but I haven't tested this.
        return makeEmptyResultSet();
    }

    public ResultSet getFunctions(String catalog, String schemaPattern, String functionNamePattern) {
        return makeEmptyResultSet();
    }

    public String getIdentifierQuoteString() {
        return "\"";
    }

    public ResultSet getImportedKeys(String catalog, String schema, String table) {
        return makeEmptyResultSet();
    }


    public ResultSet getIndexInfo(String catalog, String schema, String table, boolean unique, boolean approximate) {      
        return makeEmptyResultSet();
    }

    public int getJDBCMajorVersion() {
        return 4;
    }

    public int getJDBCMinorVersion() {
        return 2;
    }

    // These are all DB specific, so I've set these arbitrarily for now. Should be adjusted later.
    public int getMaxBinaryLiteralLength() {
        return 0;
    }

    public int getMaxNameLength() {
        // FIXME: Should ask the DB
        // I just picked this number out of a hat.
        return 25;
    }

    public int getMaxCatalogNameLength() {
        return getMaxNameLength();
    }

    public int getMaxCharLiteralLength() {
        return 0;
    }

    public int getMaxColumnNameLength() {
        return getMaxNameLength();
    }

    public int getMaxColumnsInGroupBy() {
        return 0;
    }

    public int getMaxColumnsInIndex() {
        return 0;
    }

    public int getMaxColumnsInOrderBy() {
        return 0;
    }

    public int getMaxColumnsInSelect() {
        return 0;
    }

    public int getMaxColumnsInTable() {
        // From PGJDBC
        return 1600;
    }

    public int getMaxConnections() {
        //FIXME: should ask the DB
        return 100;
    }

    public int getMaxCursorNameLength() {
        return getMaxNameLength();
    }

    public int getMaxIndexLength() {
        return 0;
    }

    public int getMaxProcedureNameLength() {
        return getMaxNameLength();
    }

    public int getMaxRowSize() {
        return 0;
    }

    public int getMaxSchemaNameLength() {
        return getMaxNameLength();
    }

    public int getMaxStatementLength() {
        return 0;
    }

    public int getMaxStatements() {
        return 0;
    }

    public int getMaxTableNameLength() {
        return getMaxNameLength();
    }

    public int getMaxTablesInSelect() {
        return 0;
    }

    public int getMaxUserNameLength() {
        return getMaxNameLength();
    }

    public String getNumericFunctions() {
        //PG has a lot of math functions, but I don't think I should specialize it to PG.
        //FIXME: whatever is common should go here. Assume nothing for now?
        return "";
    }

    public ResultSet getPrimaryKeys(String catalog, String schema, String table) {
        return makeEmptyResultSet();
    }

    public ResultSet getProcedureColumns(String catalog, String schemaPattern, String procedureNamePattern, String columnNamePattern) {
        return makeEmptyResultSet();
    }

    public ResultSet getProcedures(String catalog, String schemaPattern, String procedureNamePattern) {
        return makeEmptyResultSet();
    }

    public String getProcedureTerm() {
        // FIXME: Should be generic, but we will stick with PG
        return "function";
    }

    public ResultSet getPseudoColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern) {
        return makeEmptyResultSet();
    }

    public int getResultSetHoldability() {
        return ResultSet.HOLD_CURSORS_OVER_COMMIT;
    }

    public RowIdLifetime getRowIdLifetime() {
        return RowIdLifetime.ROWID_UNSUPPORTED;
    }

    public ResultSet getSchemas() {
        return makeEmptyResultSet();
    }

    public ResultSet getSchemas(String catalog, String schemaPattern) {
        return makeEmptyResultSet();
    }

    public String getSchemaTerm() {
        return "schema";
    }

    public String getSearchStringEscape() {
        return "\\";
    }

    public String getSQLKeywords() {
        return "";
    }

    public int getSQLStateType() {
        return sqlStateSQL;
    }

    public String getStringFunctions() {
        return "";
    }

    public ResultSet getSuperTables(String catalog, String schemaPattern, String tableNamePattern) {
        return makeEmptyResultSet();
    }

    public ResultSet getSuperTypes(String catalog, String schemaPattern, String typeNamePattern) {
        return makeEmptyResultSet();
    }

    public String getSystemFunctions() {
        return "";
    }

    public ResultSet getTablePrivileges(String catalog, String schemaPattern, String tableNamePattern) {
        return makeEmptyResultSet();
    }

    public ResultSet getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types) {
        return makeEmptyResultSet();
    }

    public ResultSet getTableTypes() {
        return makeEmptyResultSet();
    }

    public String getTimeDateFunctions() {
        return "";
    }

    public ResultSet getTypeInfo() {
        return makeEmptyResultSet();
    }

    public ResultSet getUDTs(String catalog, String schemaPattern, String typeNamePattern, int[] types) {
        return makeEmptyResultSet();
    }

    public String getURL() {
        ChronoCacheConnection ccConn = (ChronoCacheConnection) conn;
        return ccConn.getURL();
    }

    public String getUserName() {
        ChronoCacheConnection ccConn = (ChronoCacheConnection) conn;
        return ccConn.getUserName();
    }

    public ResultSet getVersionColumns(String catalog, String schema, String table) {
        return makeEmptyResultSet();
    }

    public boolean insertsAreDetected(int type) {
        return false;
    }

    public boolean isCatalogAtStart() {
        return false;
    }

    public boolean isReadOnly() {
        return false;
    }

    public boolean locatorsUpdateCopy() {
        return true;
    }

    public boolean nullPlusNonNullIsNull() {
        return true;
    }

    public boolean nullsAreSortedAtEnd() {
        return false;
    }

    public boolean nullsAreSortedAtStart() {
        return false;
    }

    public boolean nullsAreSortedHigh() {
        return true;
    }

    public boolean nullsAreSortedLow() {
        return false;
    }

    public boolean othersDeletesAreVisible(int type) {
        return false;
    }

    public boolean othersInsertsAreVisible(int type) {
        return false;
    }

    public boolean othersUpdatesAreVisible(int type) {
        return false;
    }

    public boolean ownDeletesAreVisible(int type) {
        // We don't let you affect the DB from a resultSet
        return false;
    }

    public boolean ownInsertsAreVisible(int type) {
        // We don't let you affect the DB from a resultSet
        return false;
    }

    public boolean ownUpdatesAreVisible(int type) {
        // We don't let you affect the DB from a resultSet
        return false;
    }

    public boolean storesLowerCaseIdentifiers() {
        return true;
    }

    public boolean storesLowerCaseQuotedIdentifiers() {
        return false;
    }

    public boolean storesMixedCaseIdentifiers() {
        return false;
    }

    public boolean storesMixedCaseQuotedIdentifiers() {
        return true;
    }

    public boolean storesUpperCaseIdentifiers() {
        return false;
    }

    public boolean storesUpperCaseQuotedIdentifiers() {
        return false;
    }

    public boolean supportsAlterTableWithAddColumn() {
        return true;
    }

    public boolean supportsAlterTableWithDropColumn() {
        return true;
    }

    public boolean supportsANSI92EntryLevelSQL() {
        return true;
    }

    public boolean supportsANSI92FullSQL() {
        return true;
    }

    public boolean supportsANSI92IntermediateSQL() {
        return true;
    }

    public boolean supportsBatchUpdates() {
        // FIXME: PG Supports this, but we don't implement it yet.
        return false;
    }

    public boolean supportsCatalogsInDataManipulation() {
        return false;
    }

    public boolean supportsCatalogsInIndexDefinitions() {
        return false;
    }

    public boolean supportsCatalogsInPrivilegeDefinitions() {
        return false;
    }

    public boolean supportsCatalogsInProcedureCalls() {
        return false;
    }


    public boolean supportsCatalogsInTableDefinitions() {
        return false;
    }

    public boolean supportsColumnAliasing() {
        return true;
    }

    public boolean supportsConvert() {
        return false;
    }

    public boolean supportsConvert( int from, int to ) {
        return false;
    }

    public boolean supportsCoreSQLGrammar() {
        return false;
    }

    public boolean supportsCorrelatedSubqueries() {
        return true;
    }

    public boolean supportsDataDefinitionAndDataManipulationTransactions() {
        return true;
    }

    public boolean supportsDataManipulationTransactionsOnly() {
        return false;
    }

    public boolean supportsDifferentTableCorrelationNames() {
        return false;
    }

    public boolean supportsExpressionsInOrderBy() {
        return true;
    }

    public boolean supportsExtendedSQLGrammar() {
        return false;
    }

    public boolean supportsFullOuterJoins() {
        return true;
    }

    public boolean supportsGetGeneratedKeys() {
        return false;
    }

    public boolean supportsGroupBy() {
        return true;
    }

    public boolean supportsGroupByBeyondSelect() {
        return true;
    }

    public boolean supportsGroupByUnrelated() {
        return true;
    }

    public boolean supportsIntegrityEnhancementFacility() {
        return false;
    }

    public boolean supportsLikeEscapeClause() {
        return true;
    }

    public boolean supportsLimitedOuterJoins() {
        return true;
    }

    public boolean supportsMinimumSQLGrammar() {
        return true;
    }

    public boolean supportsMixedCaseIdentifiers() {
        return false;
    }

    public boolean supportsMixedCaseQuotedIdentifiers() {
        return true;
    }

    public boolean supportsMultipleOpenResults() {
        // PG probably does this, but we don't.
        return false;
    }

    public boolean supportsMultipleResultSets() {
        return false;
    }

    public boolean supportsMultipleTransactions() {
        return true;
    }

    public boolean supportsNamedParameters() {
        // No callable statements
        return false;
    }

    public boolean supportsNonNullableColumns() {
        return true;
    }

    public boolean supportsOpenCursorsAcrossCommit() {
        return true;
    }

    public boolean supportsOpenCursorsAcrossRollback() {
        return true;
    }

    public boolean supportsOpenStatementsAcrossCommit() {
        return true;
    }

    public boolean supportsOpenStatementsAcrossRollback() {
        return true;
    }

    public boolean supportsOrderByUnrelated() {
        return true;
    }

    public boolean supportsOuterJoins() {
        return true;
    }

    public boolean supportsPositionedDelete() {
        return false;
    }

    public boolean supportsPositionedUpdate() {
        return false;
    }

    public boolean supportsResultSetConcurrency(int type, int concurrency) { 
        return concurrency == ResultSet.CONCUR_READ_ONLY;
    }

    public boolean supportsResultSetHoldability(int holdability) { 
        return true;
    }

    public boolean supportsResultSetType(int type) {
        return type == ResultSet.TYPE_FORWARD_ONLY;
    }

    public boolean supportsSavepoints() {
        return false;
    }

    public boolean supportsSchemasInDataManipulation() {
        return true;
    }

    public boolean supportsSchemasInIndexDefinitions() {
        return true;
    }

    public boolean supportsSchemasInPrivilegeDefinitions() {
        return true;
    }

    public boolean supportsSchemasInProcedureCalls() {
        return true;
    }

    public boolean supportsSchemasInTableDefinitions() {
        return true;
    }

    public boolean supportsSelectForUpdate() {
        //FIXME: probably supported, but haven't tried
        return false;
    }

    public boolean supportsStatementPooling() {
        // Not supported in CC
        return false;
    }

    public boolean supportsStoredFunctionsUsingCallSyntax() {
        return true;
    }

    public boolean supportsStoredProcedures() {
        return false;
    }

    public boolean supportsSubqueriesInComparisons() {
        return true;
    }

    public boolean supportsSubqueriesInExists() { 
        return true;
    }

    public boolean supportsSubqueriesInIns() {
        return true;
    }

    public boolean supportsSubqueriesInQuantifieds() {
        return true;
    }

    public boolean supportsTableCorrelationNames() {
        return true;
    }

    public boolean supportsTransactionIsolationLevel(int level) {
        // Not sure if this is the correct way to do it.
        return level <= Connection.TRANSACTION_READ_COMMITTED;
    }

    public boolean supportsTransactions() {
        // Supports autocommit transactions and could be extended to support RC transactions.
        return false;
    }

    public boolean supportsUnion() {
        return true;
    }

    public boolean supportsUnionAll() {
        return true;
    }

    public boolean updatesAreDetected(int type) {
        return false;
    }

    public boolean usesLocalFilePerTable() {
        return false;
    }

    public boolean usesLocalFiles() {
        return false;
    }

    public boolean isWrapperFor( Class<?> cls ) {
        return false;
    }

    public <T> T unwrap( Class<T> cls ) {
        return null;
    }



}
