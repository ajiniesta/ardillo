package com.iniesta.ardillo.util.dddbb;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.util.Callback;

import com.iniesta.ardillo.domain.ArdilloConnection;
import com.iniesta.ardillo.util.DatabaseDataNode;
import com.iniesta.ardillo.util.table.CommonRow;
import com.iniesta.ardillo.util.table.TableColumnInfo;
import com.iniesta.ardillo.util.table.TableColumnInfo.ColumnType;
import com.iniesta.ardillo.util.table.TableCreator;

public class MetaDataCalculations {

	public static List<DatabaseDataNode> calculateTables(ArdilloConnection ardilloConnection) {
		List<DatabaseDataNode> tables = null;
		if (ardilloConnection != null) {
			Connection connection = null;
			try {
				connection = ardilloConnection.createConnection();
				DatabaseMetaData metaData = connection.getMetaData();
				ResultSet resultSet = metaData.getTables(null, ardilloConnection.getSchema(), "%", null);
				if(resultSet!=null){
					while (resultSet.next()) {
						String tableName = resultSet.getString(3);
						if(tableName!=null){
							if(tables==null){
								tables = new ArrayList<DatabaseDataNode>();
							}
							tables.add(new DatabaseDataNode(tableName, ardilloConnection));
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (connection != null) {
						connection.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return tables;
	}
	
	public static List<TableColumnInfo> calculateColumnsDetails(ArdilloConnection ardilloConnection, String tableName) {
		List<TableColumnInfo> cols = null;
		if (ardilloConnection != null) {
			Connection connection = null;
			try {
				connection = ardilloConnection.createConnection();
				DatabaseMetaData metaData = connection.getMetaData();
				ResultSet resultSet = metaData.getColumns(null, ardilloConnection.getSchema(), tableName,"%");
				if(resultSet!=null){
					while (resultSet.next()) {						
						String c = resultSet.getString("COLUMN_NAME");
						String tn = resultSet.getString("TYPE_NAME");
						TableColumnInfo col = new TableColumnInfo(c, ColumnType.create(tn));
						if(cols==null){
							cols = new ArrayList<TableColumnInfo>();
						}						
						cols.add(col);
						
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (connection != null) {
						connection.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return cols;
	}
	
	public static List<CommonRow> calculateData(ArdilloConnection ardilloConnection, Callback<Connection, ResultSet> callback, TableCreator tc) {
		List<CommonRow> rows = null;
		if (ardilloConnection != null) {
			Connection connection = null;
			try {
				connection = ardilloConnection.createConnection();
				ResultSet resultSet = callback.call(connection);				
				if(resultSet!=null){
					rows = new ArrayList<CommonRow>();
					while (resultSet.next()) {
						CommonRow row = new CommonRow();
						row.add(tc.getFields(resultSet));						
						rows.add(row);						
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (connection != null) {
						connection.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return rows;
	}
}
