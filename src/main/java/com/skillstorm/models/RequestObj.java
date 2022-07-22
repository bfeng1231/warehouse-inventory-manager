package com.skillstorm.models;

import java.util.List;

/**
 * Class used for taking in an array of ids from the htttp request for the deleteMany methods
 *
 */
public class RequestObj {
	private List<Integer> ids;

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	@Override
	public String toString() {
		return "RequestObj [ids=" + ids + "]";
	}
	
	
}
