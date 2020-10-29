package com.websocketchat.model;

import java.util.Set;

public class State implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String type;
	private String user;
	private Set<ChatUser> users;

	public State(String type, String user, Set<ChatUser> users) {
		super();
		this.type = type;
		this.user = user;
		this.users = users;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Set<ChatUser> getUsers() {
		return users;
	}

	public void setUsers(Set<ChatUser> users) {
		this.users = users;
	}
}
