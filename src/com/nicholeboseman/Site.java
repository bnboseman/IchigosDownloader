package com.nicholeboseman;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Site {
	private File path;
	private String[] links;
	
	
	 public final static void main(String[] args) {
		 
		 
		 
		 
		 
		 Site ichigos = new Site( "/Users/bboseman/Documents/Anime/");
		 
		 String[] filesToDownload = {"new"};
		 ichigos.startDownload( filesToDownload);
		 
	 }
	 
	 public Site( String path) {
		 this.path = new File(path);
		 if (!this.path.exists()) {
			 this.path.mkdir();
		 }
	 }
	 
	 /** Set which files to download. Only download new by default */
	 public void startDownload( String[] type ) {
		 String[] links;
		 if (type[0] == "all") {
			 links = new String[] {"others", "a", "b", "c","d","e","f","fi","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
		 }
		 if (type.length > 0 ) {
			 links = type;
		 } else {
			 links = new String[]{"new" };
		 }
		 for (String link: links) {
			 downloadMusic( "http://ichigos.com/sheets/" + link  );
		 }
	 }
	 
	 public void downloadMusic( String url ) {
		 boolean show = false;
		 String html;
		 String href;
		 boolean piano = false;
		 String fileDownloadLocation = "";
		 File file;
		 try{
			 Document doc = Jsoup.connect(url).get();
			 
			 Elements elements = doc.getAllElements();
			 
			 // loop though all elements
			 for ( Element element : elements ) {
				// Check to see if we are looking at the show name ( shows have a title2 class
				 String htmlclass = element.attr("class");
				 if ( htmlclass.compareTo("title2") == 0 ) {
					 show = true;
					 // Create new directory for the show
					 fileDownloadLocation = this.path.getPath() + File.separator + element.text().replace(" ", "_").replaceAll("[^a-zA-Z_]", "");
					 
					 // If the last character is a _ remove ( get rid of trailing _ for sequels )
					 if (fileDownloadLocation.endsWith("_") ) fileDownloadLocation = fileDownloadLocation.substring(0, fileDownloadLocation.length() -1);
					 // Set the path to the new file location and create new directory
					 new File(fileDownloadLocation).mkdir();
					 // Print show name
					 System.out.println( element.text() );
				 } else if (show == true ) {
					 // get the link
					 href = element.attr("href");
					 html = element.html();
					 // See if the link is piano sheet music
					 if ( element.toString().toLowerCase().contains("piano") && !html.toLowerCase().contains("and") && !html.contains(",") ) {
						 piano = true;
					 } else if( piano == true && href.length() > 1 && href.contains("pdf")) {
						 //System.out.println( href);
						 try{
							 HttpDownloadUtility.downloadFile("http://ichigos.com/" + href,  path.getAbsolutePath() + File.separator + fileDownloadLocation);
						 } catch (Exception e ) {
							 System.err.println( e.getMessage());
						 }
							 piano = false;
					 }  else {
						 //System.out.println( html.toLowerCase());
					 }
				 }
			 }
		 } catch (Exception e ) {
			 //System.err.println( e.getMessage() );
		 }
	 }
}