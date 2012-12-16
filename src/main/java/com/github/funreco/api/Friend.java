package com.github.funreco.api;

public class Friend {
	private String facebookId;
    private String name;


	public String getFacebookId() {
		return facebookId;
	}


	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public boolean equals(Friend friend) {
		return (this.facebookId == friend.facebookId && this.name == friend.name);
	}

}
