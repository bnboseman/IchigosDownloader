package com.nicholeboseman;
import java.util.ArrayList;

public class Show {
	private String title;
	private ArrayList<String> songs;
	Show(String title) { 
		this.title = title;
		songs = new ArrayList<String>();
	} 
	
	public void getSongs() {
		
	}
	public void addSong( String song ) {
		songs.add( song );
	}
	
	public String toString() {
		String returnString;
		
		returnString = this.title + "\n";
		returnString += songs.toString();
		return returnString;
	}
}
