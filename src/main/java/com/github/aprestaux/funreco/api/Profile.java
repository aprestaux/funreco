package com.github.aprestaux.funreco.api;

public class Profile {
	private String facebookId;
    private String email;
    private String name;


    public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
        return "Profile{" +
                "email='" + email + '\'' +
                ", facebookId='" + facebookId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

	public boolean equals(Profile profile) {
		return (this.facebookId == profile.facebookId && this.email == profile.email && this.name == profile.name);
	}
}
