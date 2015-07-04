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
	 public final static void main(String[] args) {
		 
		 String[] links;
		 
		 if (args.length > 0 ) {
			 links = args;
		 } else {
			 links = new String[]{"new" };
		 }
		 
		 for (String link: links) {
			 downloadMusic( "http://ichigos.com/sheets/" + link  );
		 }
		 
	 }
	 
	 public static void downloadMusic( String url ) {
		 boolean show = false;
		 String html;
		 String href;
		 String path = "/Users/Sirius/Documents/Anime";
		 boolean piano = false;
		 try{
			 Document doc = Jsoup.connect(url).get();
			 
			 Elements elements = doc.getAllElements();
			 
			 for ( Element element : elements ) {
				 String htmlclass = element.attr("class");
				 if ( htmlclass.compareTo("title2") == 0 ) {
					 show = true;
					 path = "/Users/Sirius/Documents/Anime/" + element.text().replace(" ", "_").replaceAll("[^a-zA-Z_]", "");
					 if (path.endsWith("_") ) path = path.substring(0, path.length() -1);
					 new File(path).mkdir();
					 System.out.println( element.text() );
				 } else if (show == true ) {
					 href = element.attr("href");
					 html = element.html();
					 if ( element.toString().toLowerCase().contains("piano") && !html.toLowerCase().contains("and") && !html.contains(",") ) {
						 piano = true;
					 } else if( piano == true && href.length() > 1 && href.contains("pdf")) {
						 //System.out.println( href);
						 try{
							 HttpDownloadUtility.downloadFile("http://ichigos.com/" + href,  path);
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