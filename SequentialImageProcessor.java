/*
 * Mauricio Renon
 * ICS432
 * 
 * SequentialImageProcessor.java
 * 
 * a sequential program to read images from a directory
 * process them with either a invert, smear, oil1, or oil6 filter
 * and write them back to disk
 * 
 *
 */

import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import com.jhlabs.image.*;

public class SequentialImageProcessor {

	static long readTimeStart = 0;
	static long processTimeStart = 0;
	static long writeTimeStart = 0;
	
	static long readTimeEnd = 0;
	static long processTimeEnd = 0;
	static long writeTimeEnd = 0;
	
	static int size; 
	
/**
 * to save the image to the disk 
 * 
 * @param image the image to save
 * @param filename the new filename of the image
 * @param i a counter for the number of images processed
 */
	private static void saveImage(BufferedImage image, String filename, int i){
		try {
			if(i == 1){ //if first image
				writeTimeStart = System.currentTimeMillis();
			}
			ImageIO.write(image, "jpg", new File(filename));
			if(i == size - 1){ //if last image
				writeTimeEnd = System.currentTimeMillis();
			}
			System.out.print("w");
		} catch (IOException e) {
			System.out.println("Cannot write file "+filename);
			System.exit(1);
		}
	}

	/**
	 * takes in two command line args
	 * 1) filter
	 * 2) working directory
	 * reads in the files and processes them with either a 
	 * invert, smear, oil1, or oil6 filter
	 * 
	 * and prints out the total time each process took
	 * 
	 * @param args array of command line arguments
	 */
	public static void main(String args[]) {

		BufferedImage input=null;
		BufferedImage output = null;
		BufferedImageOp filter;
		

	
		String filterType;
		String directory;
		
		File folder;
		File[] listOfFiles;
		
		if(args.length == 0){
			System.out.println("Usage: SequentialImageProcessor [oil1|oil6|invert|smear] < directory >");
		}
		else{
			filterType = args[0];
			directory = args[1];
			folder = new File(directory);
			listOfFiles = folder.listFiles();
			
			for(int i = 1; i < listOfFiles.length; i++){
				try {		
					size = listOfFiles.length;
					input = ImageIO.read(new File(directory + listOfFiles[i].getName()));
					if(i == 1){ //if first image
						readTimeStart = System.currentTimeMillis();	
					}
					if(i == listOfFiles.length - 1){ //if last image
						readTimeEnd = System.currentTimeMillis();
					}
					System.out.print("r");
					//System.out.println(readTime);
				
				} catch (IOException e) {
					System.out.println("Cannot read file ./image.jpg");
					System.exit(1);
				}
			
				output = new BufferedImage(input.getWidth(), input.getHeight(), input.getType()); 
			
				if(filterType.equalsIgnoreCase("oil1")){
					if(i==1){//if first image
						processTimeStart = System.currentTimeMillis();
					}
					// Apply the Oil1 filter
					filter = new OilFilter();
					((OilFilter)filter).setRange(1);
					filter.filter(input,output);
					String oil1 = directory + "/oil1_" + listOfFiles[i].getName();
					if(i == listOfFiles.length - 1){ //if last image
						processTimeEnd = System.currentTimeMillis();
					}
					System.out.print("p");
					saveImage(output, oil1, i);
				}
				else if(filterType.equalsIgnoreCase("oil6")){;  
					if(i==1){ //if first image
						processTimeStart = System.currentTimeMillis();
					}
					// Apply the Oil6 filter
					filter = new OilFilter();
					((OilFilter)filter).setRange(6);
					filter.filter(input,output);
					String oil6 = directory + "/oil6_" + listOfFiles[i].getName();
					if(i == listOfFiles.length - 1){ //if last image
						processTimeEnd = System.currentTimeMillis();
					}
					System.out.print("p");
					saveImage(output, oil6, i);;
				}
				else if(filterType.equalsIgnoreCase("smear")){  
					if(i==1){ //if first image
						processTimeStart = System.currentTimeMillis();
					}
					// Apply the Smear filter
					filter = new SmearFilter();
					((SmearFilter)filter).setShape(0);
					filter.filter(input,output);
					String smear = directory + "/smear_" + listOfFiles[i].getName();
					if(i == listOfFiles.length - 1){ //if last image
						processTimeEnd = System.currentTimeMillis();
					}
					System.out.print("p");
					saveImage(output, smear, i);
				}
				else if(filterType.equalsIgnoreCase("invert")){
					if(i==1){ //if first image
						processTimeStart = System.currentTimeMillis();
					}
					// Apply the Invert filter
					filter = new InvertFilter();
					filter.filter(input,output);
					String invert = directory + "/invert_" + listOfFiles[i].getName();
					if(i == listOfFiles.length - 1){ //if last image
						processTimeEnd = System.currentTimeMillis();
					}
					System.out.print("p");
					saveImage(output, invert, i);
				}
			}
			long readTimeUpdate = (readTimeEnd - readTimeStart);
			long processTimeUpdate = (processTimeEnd - processTimeStart);
			long writeTimeUpdate = (writeTimeEnd - writeTimeStart);
			long overallTime = readTimeUpdate + processTimeUpdate + writeTimeUpdate;
			
			System.out.println();
			System.out.printf("Time Spent Reading: %.3f", readTimeUpdate/1e3); //makes longs values read 3 decimal places
			System.out.println("sec.");
			System.out.printf("Time Spent Processing: %.3f", processTimeUpdate/1e3);
			System.out.println("sec.");
			System.out.printf("Time Spent Writing: %.3f", writeTimeUpdate/1e3);
			System.out.println("sec.");
			System.out.printf("Overall Execution Time: %.3f", overallTime/1e3);
			System.out.println("sec.");
			
		}
	}
}

