package com.hoffmann.punchclock;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Scanner;

public class TimeClock {

	static Boolean quitAsking = false;
	static Boolean createAnother = true;
	
	public static void main(String[] args) {
		createDir();
		createPCToday();
		startMethod(false, false);
	}
	
	static void startMethod(Boolean clIn, Boolean clOut) {
		Scanner inAgain = new Scanner(System.in);
		
		Boolean cIn = clIn;
		Boolean cOut = clOut;
		
		if (cIn == false && cOut == false) {
			System.out.println("\nWelcome to the console Time Clock\n");
			
			punchIn();
		}
		
		if (cIn == true && cOut == true) {
			System.out.println("\nWould you like to exit the app?\n('y' or 'Y' for yes, 'n' or 'N' for no)");
			String answer = inAgain.nextLine();
			
			if (answer.equals("y")||answer.equals("Y")) {
				inAgain.close();
				System.exit(0);
			}else if (answer.equals("n")||answer.equals("N")) {
				main(null);
			}
		}
	}
	
	static void punchIn() {
		Scanner pIn = new Scanner(System.in);
		Integer retry = 0;
		Boolean exit = false;
		String reason = "";
		String[] clockIn = new String[3];
		String fClockIn = "";
		
		while(!exit) {
			System.out.println("Press 'I' to punch in: ");
			String in = pIn.nextLine();
			
			// System.out.println(in);
			if (in.equals("I") || in.equals("i")) {
				System.out.println("\nEnter the reason for clocking in: ");
				reason = pIn.nextLine();
				
				LocalDateTime dateTimeIn = LocalDateTime.now();
				
				clockIn[0] = reason;
				clockIn[1] = "Time in: ";
				clockIn[2] = dateTimeIn.toString().substring(11, 16);
				
				System.out.println("\nClocked In.\n");
				
				fClockIn = clockIn[0].toString() + "\n" + clockIn[1].toString() + clockIn[2].toString();
				
				try {
					writeToFile(fClockIn);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				punchOut(fClockIn);
				
				exit = true;
			} else {
				if (retry <= 3) {
					System.out.println("Try again.");
					retry += 1;
				} else if (retry >= 3 && retry <= 5) {
					System.out.println("Try again. I know you can do it.. just hit the 'i' key...");
					retry += 1;
				} else if (retry >= 5 && retry < 8) {
					System.out.println("Okay, quit messing around. Either hit the 'i' key or exit the application..");
					retry += 1;
				} else if (retry >= 8) {
					System.out.println("Goodbye!");
					pIn.close();
					System.exit(0);
				}
			}
		}
	}
	
	static void punchOut(String input) {
		Scanner pOut = new Scanner(System.in);
		Integer retry = 0;
		Boolean exit = false;
		String[] clockOut = new String[2];
		String fClockOut = input;
		
		while(!exit) {
			System.out.println("Press 'O' to punch out: ");
			String in = pOut.nextLine();
			
			//System.out.println(in);
			if (in.equals("O") || in.equals("o")) {
				LocalDateTime dateTimeIn = LocalDateTime.now();
				clockOut[0] = "Time Out: ";
				clockOut[1] = dateTimeIn.toString().substring(11, 16);
				
				System.out.println("\nClocked Out.\n");
				
				fClockOut = clockOut[0].toString() + clockOut[1].toString();
				
				try {
					writeToFile(fClockOut);
				} catch (IOException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
				startMethod(true, true);
				exit = true;
			} else {
				if (retry <= 3) {
					System.out.println("Try again.");
					retry += 1;
				} else if (retry >= 3 && retry <= 5) {
					System.out.println("Try again. I know you can do it.. just hit the 'o' key...");
					retry += 1;
				} else if (retry >= 5 && retry < 8) {
					System.out.println("Okay, quit messing around. Either hit the 'o' key or exit the application..");
					retry += 1;
				} else if (retry >= 8) {
					System.out.println("Goodbye!");
					pOut.close();
					System.exit(0);
				}
			}
		}
	}
	
	static String createDir() {
		String filePath = "C:/PunchCards_";
		LocalDateTime  today = LocalDateTime.now();
		String todayDate = today.toString().substring(0, 4);
		Path path = Paths.get(filePath + todayDate);
		
		if (!Files.exists(path)) {
			try {
				Files.createDirectory(path);
				System.out.println("Directory created!");
			} catch (IOException e) {
				// TODO: handle exception
				System.out.println("Directory alread exists.");
				e.printStackTrace();
			}
		}
		
		return filePath + todayDate;
	}
	
	static File createPCToday () {
		LocalDateTime today = LocalDateTime.now();
		String todayDate = today.toString().substring(0, 10);
		Scanner scan = new Scanner(System.in);
		
		//todayDate = todayDate.replace('-', '_');
		
		String punchCardToday = "Punch_Card_" + todayDate + ".dat";
		
		File file = new File(createDir(), punchCardToday.replace('-', '_'));
		
		if(!file.exists()) {
			try {
				file.createNewFile();
				System.out.println("\nFile created: " + file);
			} catch (IOException e) {
				// TODO: handle exception
				System.out.println("File already exists: " + file);
				e.printStackTrace();
			}
		} else {
			if (quitAsking.equals(false)) {
				System.out.println(file + " exists.");
				System.out.println(
						"\nWould you like for me to quit asking to create a new punch card?\n('y' or 'Y' for Yes or 'n' or 'N' for no)");
				String tempAnswer = scan.nextLine();
				
				//System.out.println(tempAnswer);
				
				if(tempAnswer.equals("Y") || tempAnswer.equals("y")) {
					quitAsking = true;
				}else {
					quitAsking = false;
				}
				
				if(quitAsking.equals(false)) {
					System.out.println(
							"\n" + createPCToday() + " exists would you like to create another?\\n('y' or 'Y' for Yes or 'n' or 'N' for no)");
					tempAnswer = scan.nextLine();
					
					if(tempAnswer.equals("y") || tempAnswer.equals("Y")) {
						Integer increment = 0;
						increment += increment;
						punchCardToday = "Punch_Card_" + todayDate + increment + ".dat";
						
						file = new File(createDir(), punchCardToday.replace("-", "_"));
						try {
							file.createNewFile();
							System.out.println("\nFile created: " + file);
						} catch (IOException e) {
							// TODO: handle exception
							System.out.println("File already exists: " + file);
							scan.close();
							e.printStackTrace();
						}
					}
				}
			}
		}
		
		return file;
	}
	
	static void writeToFile(String input) throws IOException {
		String filePath = createPCToday().toString();
		FileWriter write = new FileWriter(filePath, true);
		
		BufferedWriter fWrite = new BufferedWriter(write);
		fWrite.newLine();
		fWrite.newLine();
		fWrite.append(input);
		fWrite.close();
	}
}
