package com.skillstorm.services;

public class URLParserService {
	
	public String[] extractParamFromURL(String url) {
		//System.out.println(url);
		String[] splitString = url.split("/");
//		for(String i : splitString) {
//			System.out.println(i);
//		}
		return splitString;
	}
}
