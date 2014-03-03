package com.iniesta.ardillo.util.dddbb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.iniesta.ardillo.domain.ArdilloConnection;
import com.iniesta.ardillo.util.table.CommonRow;
import com.iniesta.ardillo.util.table.TableCreator;

public class QueryExecutor {

	public static List<CommonRow> calculateData(ArdilloConnection ardilloConnection, String query, TableCreator tc) {
		List<CommonRow> rows = null;
		if (ardilloConnection != null) {
			Connection connection = null;
			try {
				connection = ardilloConnection.createConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query);
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
