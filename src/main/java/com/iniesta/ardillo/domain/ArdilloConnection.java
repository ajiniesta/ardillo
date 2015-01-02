package com.iniesta.ardillo.domain;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ArdilloConnection implements Serializable {
	private static final long serialVersionUID = -2758391252901914990L;

	private Integer id;
	private String name;
	private String dbms;
	private String host;
	private Integer port;
	private String user;
	private String password;
	private String schema;
	private String driver;

	@Id
	@GeneratedValue
	@Column
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(unique=true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	@Column
	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	@Column
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Column
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column
	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	@Column
	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	@Column
	public String getDbms() {
		return dbms;
	}

	public void setDbms(String dbms) {
		this.dbms = dbms;
	}

	@Override
	public String toString() {
		return "ArdilloConnection [id=" + id + ", name=" + name + ", dbms=" + dbms + ", host=" + host + ", port=" + port + ", user=" + user + ", password=" + password + ", prefixdb=" + schema + ", driver=" + driver + "]";
	}

	public void update(ArdilloConnection conn) {
		if (conn != null) {
			name = conn.name;
			host = conn.host;
			port = conn.port;
			user = conn.user;
			password = conn.password;
			schema = conn.schema;
			driver = conn.driver;
			dbms = conn.dbms;
		}
	}

	public Connection createConnection() throws SQLException, ClassNotFoundException {
		Connection conn = null;
		Properties connectionProps = new Properties();
		connectionProps.put("user", this.user);
		connectionProps.put("password", this.password);

		Class.forName(driver);
		String portStr = port!=null?":" + this.port:"";
		String url = null;
		if(dbms.contains(":file") || dbms.contains(":mem")){
			url = "jdbc:" + this.dbms + ":" + this.host ;			
		}else {
			url = "jdbc:" + this.dbms + "://" + this.host + portStr + "/";
		}
		conn = DriverManager.getConnection(url, connectionProps);

		return conn;
	}

}