package com.cmu.edu.safedrop.dto;

import java.util.List;

public class Rating {

	@Override
	public String toString() {
		return "Rating [ratings=" + ratings + "]";
	}
	private List<Ratings> ratings;

 	public List<Ratings> getRatings(){
		return this.ratings;
	}
	public void setRatings( List<Ratings> ratings){
		this.ratings = ratings;
	}

}
