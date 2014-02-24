package com.iniesta.ardillo.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ArdilloConnection implements Serializable{
	private static final long serialVersionUID = -2758391252901914990L;
	
	private Integer id;
	private String name;
	private String host;
	private Integer port;
	private String user;
	private String password;
	private String prefixdb;
	private String driver;

	@Id
	@GeneratedValue
	@Column
	public Integer getId(){ return id;}

	public void setId(Integer id){ this.id = id; }

	@Column
	public String getName(){return name;}

	public void setName(String name){this.name = name;}

	@Column
	public String getHost(){return host;}

	public void setHost(String host){this.host = host;}	

	@Column
	public Integer getPort(){ return port;}

	public void setPort(Integer port){ this.port = port; }	

	@Column
	public String getUser(){return user;}

	public void setUser(String user){this.user = user;}

	@Column
	public String getPassword(){return password;}

	public void setPassword(String password){this.password = password;}

	@Column
	public String getPrefixDB(){return prefixdb;}

	public void setPrefixDB(String prefixdb){this.prefixdb = prefixdb;}

	@Column
	public String getDriver(){return driver;}

	public void setDriver(String driver){this.driver = driver;}		

	public void update(ArdilloConnection conn){
		if(conn!=null){
			name = conn.name;
			host = conn.host;
			port = conn.port;
			user = conn.user;
			password = conn.password;
			prefixdb = conn.prefixdb;
			driver = conn.driver;
		}
	}

}