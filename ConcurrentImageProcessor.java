/*
 * Mauricio Renon
 * ICS432
 * 
 * ConcurrentImageProcessor.java
 * 
 * a concurrent program to read images from a directory
 * process them with either a invert, smear, oil1, or oil6 filter
 * and write them back to disk
 * 
 * tried to implement a GUI progress bar but all that code is commented out
 * because i couldnt get it to work and update properly
 *
 */


import java.awt.image.*;
import java.io.*;
import java.util.concurrent.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.jhlabs.image.*;

public class ConcurrentImageProcessor {
	static String filterType;
	static String directory;
	static File folder;
	static File[] listOfFiles;
	
	static long overallTime;
	static double loading = 0;
	
	

/*
 * creates LinkedBlockQueue to store the images of size 8
 * takes in two command line arguments
 * 1) the filter
 * 2) the working directory
 * 
 * creates threads to read, process, write
 * 
 * there is a thread for a progress bar display but it is commented out
 * 
 * @param args array of command line arguments
 * 	
 */
	public static void main(String[] args) throws InterruptedException{
		
		LinkedBlockingQueue<BufferedImage> readToProcess = new LinkedBlockingQueue<BufferedImage>(7); //because 0 counts as 1
		LinkedBlockingQueue<BufferedImage> processToWrite = new LinkedBlockingQueue<BufferedImage>(7); //because 0 counts as 1
		LinkedBlockingQueue<BufferedImage> progressBar = new LinkedBlockingQueue<BufferedImage>();
		
		if(args.length == 0){
			System.out.println("Usage: ConcurrentImageProcessor [oil1|oil6|smear|invert] < directory >");
		}
		else{
			filterType = args[0];
			directory = args[1];
			folder = new File(directory);
			listOfFiles = folder.listFiles();

			overallTime = 0;
			
			FileReadImage FRI = new FileReadImage(readToProcess, directory, listOfFiles);
			Thread readThread = new Thread(FRI);
			
			ProcessImage PI = new ProcessImage(readToProcess, processToWrite, filterType, readThread);
			Thread processThread = new Thread(PI);
			
			FileWriteImage FWI = new FileWriteImage(processToWrite, listOfFiles, directory, filterType, processThread, progressBar);
			Thread writeThread = new Thread(FWI);
			
			//ProgressBar PB = new ProgressBar(listOfFiles, progressBar, writeThread);
			//Thread GUIThread = new Thread(PB);
			
			overallTime = System.currentTimeMillis();
			
			readThread.start();
			processThread.start();
			writeThread.start();
			//GUIThread.start();
			
			readThread.join();
			processThread.join();
			writeThread.join();	
			//GUIThread.join();
			
			long readTime = FRI.getReadTime();
			long processTime = PI.getProcessTime();
			long writeTime = FWI.getWriteTime();
			
			overallTime = System.currentTimeMillis() - overallTime;

			System.out.println();
			System.out.println("Time Spent Reading: " + (readTime / 1000.00) + " sec.");
			System.out.println("Time Spent Processing: " + (processTime / 1000.00) + " sec.");
			System.out.println("Time Spent Writing: " + (writeTime / 1000.00) + " sec.");
			System.out.println("Overall Execution Time: " + (overallTime/1000.00) + " sec.");
			
		}
	}
}

/**
 * Thread class FileReadImage Class
 * Reads images within a directory and 
 * stores them in a queue of size 8, blocks when 
 * the queue is full and waits for space to open
 * @author mauriciofloydianrenon
 *
 */

class FileReadImage implements Runnable{
	
	private final LinkedBlockingQueue<BufferedImage> readToProcess;
	private String directory;
	private File[] listOfFiles;
	private static long ReadTimeStart;
	private static long readTime;

	/*
	 * FileReadImage Constructor
	 * creates a FileReadImage object
	 * 
	 * @param readToProcess to Blocking Queue that holds images that were read to be processed
	 * @param directory name of the given directory to find the files in
	 * @param listOfFiles the files stored in an array
	 * 
	 */
	public FileReadImage(LinkedBlockingQueue<BufferedImage> readToProcess, String directory, File[] listOfFiles){
		this.readToProcess = readToProcess;
		this.directory = directory;
		this.listOfFiles = listOfFiles;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			for(int i = 1; i < listOfFiles.length; i++){
					if(listOfFiles[i].getName().endsWith(".jpg")){
						BufferedImage input;
						//start time
						ReadTimeStart = System.currentTimeMillis();
						input = ImageIO.read(new File(directory +"/"+ listOfFiles[i].getName()));
						ReadTimeStart = System.currentTimeMillis() - ReadTimeStart; 
						readTime = readTime + ReadTimeStart;
						//update time
						readToProcess.put(input);
						System.out.print("r");
					}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(InterruptedException ie){
			ie.printStackTrace();
		}
	}
	
	/*
	 * getReadTime gets the total time to read images
	 * 
	 * @return readTime the total time it took to read the images
	 */
	public long getReadTime(){
		return readTime;
	}
}

/**
 * Thread class ProcesImage Class
 * Processes images using invert,
 * smear, oil1, and oil6 filters
 * 
 * @author mauriciofloydianrenon
 *
 */
class ProcessImage implements Runnable{
	
	private final LinkedBlockingQueue<BufferedImage> readToProcess;
	private final LinkedBlockingQueue<BufferedImage> processToWrite;
	private String filterType;
	private BufferedImageOp filter;
	private Thread readThread;
	private static long ProcessTimeStart;
	private static long processTime;


	/*
	 * ProcessImage Constructor
	 * creates a ProcessImage object
	 * 
	 * @param readToProcess to Blocking Queue that holds images that were read to be processed
	 * @param processToWrite Blocking Queue that holds images that were process to be written to disk
	 * @param filterType name of the given filter to apply to images
	 * @param readThread the Thread used for the reader class
	 * 
	 */
	public ProcessImage(LinkedBlockingQueue<BufferedImage> readToProcess, LinkedBlockingQueue<BufferedImage> processToWrite, String filterType, Thread readThread){
		this.readToProcess = readToProcess;
		this.processToWrite = processToWrite;
		this.filterType = filterType;
		this.readThread = readThread;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try{
			while(readThread.isAlive() || readToProcess.size() != 0){ //while the queue is not empty
				ProcessTimeStart = System.currentTimeMillis();
				BufferedImage originalImage = (BufferedImage) readToProcess.take();
				BufferedImage newImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), originalImage.getType());
				
				if(filterType.equalsIgnoreCase("oil1")){ //oil1
					filter = new OilFilter();
					((OilFilter)filter).setRange(1);
					filter.filter(originalImage, newImage);
					processToWrite.put(newImage);
					ProcessTimeStart = System.currentTimeMillis() - ProcessTimeStart;
					processTime = processTime + ProcessTimeStart;
					System.out.print("p");
				}
				else if(filterType.equalsIgnoreCase("oil6")){ //oil6
					filter = new OilFilter();
					((OilFilter)filter).setRange(6);
					filter.filter(originalImage, newImage);
					processToWrite.put(newImage);
					ProcessTimeStart = System.currentTimeMillis() - ProcessTimeStart;
					processTime = processTime + ProcessTimeStart;
					System.out.print("p");
				}
				else if(filterType.equalsIgnoreCase("smear")){ //smear
					filter = new SmearFilter();
					((SmearFilter)filter).setShape(0);
					filter.filter(originalImage, newImage);
					processToWrite.put(newImage);
					ProcessTimeStart = System.currentTimeMillis() - ProcessTimeStart;
					processTime = processTime + ProcessTimeStart;
					System.out.print("p");
				}
				else if(filterType.equalsIgnoreCase("invert")){ //invert
					filter = new InvertFilter();
					filter.filter(originalImage, newImage);
					processToWrite.put(newImage);
					ProcessTimeStart = System.currentTimeMillis() - ProcessTimeStart;
					processTime = processTime + ProcessTimeStart;
					System.out.print("p");
				}
			}
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	
	/*
	 * getProcessTime gets the total time to process images
	 * 
	 * @return processTime the total time it took to process the images
	 */
	public long getProcessTime(){
		return processTime;
	}
}

class FileWriteImage implements Runnable{

	private final LinkedBlockingQueue<BufferedImage> processToWrite;
	private final LinkedBlockingQueue<BufferedImage> progressBar;
	private File[] listOfFiles;
	private String directory;
	private String filterType;
	private Thread processThread;
	private static long WriteTimeStart;
	private static long writeTime;
	

	/*
	 * FileWriteImage Constructor
	 * creates a ProcessImage object
	 * 
	 * @param processToWrite Blocking Queue that holds images that were process to be written to disk
	 * @param listOfFiles the files stored in an array
	 * @param directory the working directory
	 * @param filterType name of the given filter to apply to images
	 * @param processThread the Thread used for the processer class
	 * @param progressBar Blocking Queue that holds images that were to update a progress bar display (currently unimplemented)
	 * 
	 */	
	public FileWriteImage(LinkedBlockingQueue<BufferedImage> processToWrite, File[] listOfFiles, String directory, String filterType, Thread processThread, LinkedBlockingQueue<BufferedImage> progressBar){
		this.processToWrite = processToWrite;
		this.listOfFiles = listOfFiles;
		this.directory = directory;
		this.filterType = filterType;
		this.processThread = processThread;
		this.progressBar = progressBar;
		
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		
		while(processThread.isAlive() || processToWrite.size() != 0){
			for(int i = 1; i < this.listOfFiles.length; i++){
				BufferedImage finishImage = null;
				try {
					finishImage = (BufferedImage) processToWrite.take();
					progressBar.put(finishImage);
					WriteTimeStart = System.currentTimeMillis(); //start time
					ImageIO.write(finishImage, "jpg", new File(directory + filterType + "_" + listOfFiles[i].getName()));
					WriteTimeStart = System.currentTimeMillis() - WriteTimeStart;
					writeTime = writeTime + WriteTimeStart;
					System.out.print("w");
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}catch(IOException e){
					System.out.println("Cannot write file");
					System.exit(1);
				}
			}
		}
	}
	
	/*
	 * getProcessTime gets the total time to process images
	 * 
	 * @return processTime the total time it took to process the images
	 */
	public long getWriteTime(){
		return writeTime;
	}
}
/**
 * tried to implement a GUI display for a progress bar
 * doesnt update the actual bar, tried to make it work
 * like another producer consumer
 * 
 * to try to see how it works remove comment block on here
 * and commented out GUI thread initialization, start(); and join();
 * in the main method
 * 
class ProgressBar implements Runnable{
	private JFrame frame;
	private JProgressBar progressBarDisplay;
	int totalImages = 0;
	static double loading = 0;
	
	private File[] listOfFiles;
	private final LinkedBlockingQueue<BufferedImage> progressBar;
	private Thread writeThread;
	
	public ProgressBar(File[] listOfFiles, LinkedBlockingQueue<BufferedImage> progressBar, Thread writeThread){
		frame = new JFrame("Image Processing");
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		progressBarDisplay = new JProgressBar();
		progressBarDisplay.setValue((int)loading);
		progressBarDisplay.setStringPainted(true);
		frame.add(progressBarDisplay);
		frame.setSize(300, 100);
		frame.setVisible(true);
		
		this.listOfFiles = listOfFiles;
		this.progressBar = progressBar;
		this.writeThread = writeThread;
	}
	
	@Override
	public void run() {
		double percent = 0;
		for(int i = 1;  i< listOfFiles.length; i++){
			totalImages++;
		}
		while(writeThread.isAlive() || progressBar.size() != 0){
			loading++; 
			progressBar.remove(0);
			while(loading < totalImages){
				percent = (loading/totalImages) * 100;
				progressBarDisplay.setValue((int)percent);
				progressBarDisplay.setStringPainted(true);
			}
		}
		percent = (loading/totalImages) * 100;
		progressBarDisplay.setValue((int) percent);
		progressBarDisplay.setStringPainted(true);
	}
}**/


