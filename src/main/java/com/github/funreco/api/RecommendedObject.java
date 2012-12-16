package com.github.funreco.api;

import java.util.List;

public class RecommendedObject {
	private Object object;
    private List<Friend> by;
	
	public boolean equals(RecommendedObject recommendedObject) {
		return (this.object.equals(recommendedObject.object) && this.by.equals(recommendedObject.by));
	}
}
