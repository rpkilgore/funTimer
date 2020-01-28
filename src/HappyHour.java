import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.*;
import java.time.temporal.ChronoUnit;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class HappyHour {
	
	// message to display... Going to have 2
	// one for prior to happy hour and one during :)
	private String prior_happyhr = "until happy hour...", during_happyhr = "until happy hour is over...";
	
	// this will be the date/time for happy hr..
	// for now we going with everyday but todo make it customizable
	private String happyhr_time = "17:30:00";
	private int happyHr_duration_hrs = 2;
	private int happyHr_duration_min = 00;
	private JLabel lblCountdown;
	private boolean isHappyHr = false;
	private boolean stopCountDown = false;
	private JProgressBar pb;
	private PixelCanvas daImage;
	private long pixelsPerSecond = 0;
	
	public HappyHour(JLabel lblTimer, JProgressBar jpb, PixelCanvas image){
		super();
		lblCountdown = lblTimer;
		pb = jpb;
		daImage = image;
	}
		
	public void doWork(){
		
		// Creating my own little HappyHour Timer
		LocalTime LT = LocalTime.parse(happyhr_time);
		// Next we need the current time
		LocalTime CT = LocalTime.now();

		//TODO check for this first to see if we are already after happy hr
		if(CT.isAfter(LT)){
			// just like when we are in the loop.. switch it up!
			LT = LT.plus(happyHr_duration_hrs, ChronoUnit.HOURS);
			LT = LT.plus(happyHr_duration_min, ChronoUnit.MINUTES);
			isHappyHr=true;
			//daImage.resetImage();
		}
		
		// Now lets figure out the difference between the times..
		Duration D = Duration.between(CT, LT);
		
		System.out.println("Total Seconds: " + Math.abs(D.getSeconds()));

		pb.setMaximum((int) Math.abs(D.getSeconds()));
		
		// Do some calculations to see how many pixels to print each second
		pixelsPerSecond = daImage.getPixels() / Math.abs(D.getSeconds());
		
		System.out.println("pixels per second: " + pixelsPerSecond);
		
		System.out.println("starting..");
		
		
		while (CT.isBefore(LT)){
			
			// Grab the new time while we are in our loop
			CT = LocalTime.now();

			// Get the new difference between them
			D = Duration.between(CT, LT);
			
			// Get seconds specifically
			int seconds = (int) D.getSeconds() % 60;
			// Get minutes specifically
			int minutes = (int) D.toMinutes() % 60;
			// Get hours specifically
			int hours = (int) D.toHours();

			// now if all variables are 0... then guess what!! :)
			if(seconds==0 && minutes==0 && hours==0){
				// Time to change things up!
				// Change LT
				LT = LT.plus(happyHr_duration_hrs, ChronoUnit.HOURS);
				LT = LT.plus(happyHr_duration_min, ChronoUnit.MINUTES);
				
				// Set our boolean so we know what message to print out..
				if(isHappyHr){
					isHappyHr=false;
					//daImage.resetImage();
				}else{
					isHappyHr = true;
					// change the image?
					BufferedImage img = null;
					try {
					    img = ImageIO.read(new File("Fun_Time2.jpg"));
					} catch (IOException e) {
					    e.printStackTrace();
					}
					// now resize it?
					Image dimg = img.getScaledInstance(daImage.getWidth(), daImage.getHeight(),
					        Image.SCALE_SMOOTH);
					// set it in our canvas... but don't refresh? cause we will do that later.. :)
					daImage.setSecondImage(dimg);
				}
			}
			if(isHappyHr){
				if(lblCountdown!=null)
					lblCountdown.setText(hours + " hours " + minutes + " min " + seconds + " seconds " + during_happyhr);
				
				//System.out.println(hours + " hours " + minutes + " min " + seconds + " seconds " + during_happyhr);
			}else{
				if(lblCountdown!=null)
					lblCountdown.setText(hours + " hours " + minutes + " min " + seconds + " seconds " + prior_happyhr);
				
				//System.out.println(hours + " hours " + minutes + " min " + seconds + " seconds " + prior_happyhr);
			}
			
			if(stopCountDown){
				return;
			}
			// increase our progress bar
			if(pb.getValue() == pb.getMaximum()){
				// time to reset the max?
				pb.setValue(0); // reset to 0
				pb.setMaximum((int) D.getSeconds());
			}
			pb.setValue(pb.getValue() + 1);
			
			daImage.updateImage(pixelsPerSecond);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean isStopCountDown() {
		return stopCountDown;
	}

	public void setStopCountDown(boolean stopCountDown) {
		this.stopCountDown = stopCountDown;
	}

	public String getPrior_happyhr() {
		return prior_happyhr;
	}

	public void setPrior_happyhr(String prior_happyhr) {
		this.prior_happyhr = prior_happyhr;
	}

	public String getDuring_happyhr() {
		return during_happyhr;
	}

	public void setDuring_happyhr(String during_happyhr) {
		this.during_happyhr = during_happyhr;
	}

	public String getHappyhr_time() {
		return happyhr_time;
	}

	public void setHappyhr_time(String happyhr_time) {
		this.happyhr_time = happyhr_time;
	}

	public int getHappyHr_duration_hrs() {
		return happyHr_duration_hrs;
	}

	public void setHappyHr_duration_hrs(int happyHr_duration_hrs) {
		this.happyHr_duration_hrs = happyHr_duration_hrs;
	}

	public int getHappyHr_duration_min() {
		return happyHr_duration_min;
	}

	public void setHappyHr_duration_min(int happyHr_duration_min) {
		this.happyHr_duration_min = happyHr_duration_min;
	}

	public static void main(String[] args) {
		
		HappyHour myHappyHr = new HappyHour(null, null, null);
		myHappyHr.doWork();
	}

}
