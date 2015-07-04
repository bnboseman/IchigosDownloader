package com.nicholeboseman;
import java.util.ArrayList;

public class Show {
	private String title;
	private ArrayList<String> songs;
	Show(String title) { 
		this.title = title;
		songs = new ArrayList<String>();
	} 
	
	public void addSong( String song ) {
		songs.add( song );
	}
	
	public String toString() {
		String returnString;
		
		returnString = this.title + "\n";
		String[] songs = new String[this.songs.size() ];
		songs = this.songs.toArray(songs);
		
		for (String song: songs ) {
			try {
			HttpDownloadUtility.downloadFile(song, ".");
			} catch( Exception e ) {
				System.err.println( e.getMessage());
			}
			returnString += "\t" + song + "\n";
		}
		return returnString;
	}
}
