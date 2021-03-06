package Model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import javax.swing.table.DefaultTableModel;

import View.OptionsMain;

/**
 * Auction class; 
 * Precondition: myDate must be of "mm/dd/yyyy" format, and myStartTime and myEndTime must be of "hh:mm" format.
 * 
 * @author Abigail Smith
 * @version 5/20/15
 * 
 */
public class Auction {

	/**
	 * A list of the Auction's Donors.
	 */
	private ArrayList<Donor> myDonors;
	
	/**
	 * A list of the Auction's Items.
	 */
	private ArrayList<Item> myItems;
	
	/**
	 * A list of the Auction's Bidders.
	 */
	private ArrayList<Bidder> myBidders;
	
	/**
	 * The date of the Auction.
	 */
	private String myDate;
	
	/**
	 * The time the Auction opens.
	 */
	private String myStartTime;
	
	/**
	 * The time the Auction closes.
	 */
	private String myEndTime;
	
	/**
	 * Name of the facilitator of the Auction.
	 */
	private String myFacilitator;
	
	/**
	 * The file path to save all the Auction stats to.
	 */
	//private String myFileName;
	
	private int BidderID;
	
	//public static final String FILE_PATH = "output/AuctionFile.csv";
	private String myFilePath;
	
	/**
	 * And empty constructor, used when opening up a previously saved file.
	 */
	public Auction(String aFilePath) {
		
		this(null, null, null, null, aFilePath);
	}
	
	/**
	 * Auction constructor with parameters.
	 * 
	 * @param aDate The date of the Auction; must be of "mm/dd/yyyy" format.
	 * @param aStartTime The Auction's start time; must be of "hh:mm" format.
	 * @param anEndTime The Auction's closing time; must be of "hh:mm" format.
	 * @param aFacilitator The Auction's facilitator.
	 */
	public Auction(String aDate, String aStartTime, String anEndTime, String aFacilitator, String aFilePath) {
		myDate = aDate;
		myStartTime = aStartTime;
		myEndTime = anEndTime;
		myFacilitator = aFacilitator;
		BidderID = 0;
		myFilePath = aFilePath;
		
//		myFile = new File(aFilePath);
//		if (myFile.exists())  {
//			myFile.delete();
//			myFile = new File(aFilePath);
//		}
		myDonors = new ArrayList<Donor>();
		myItems = new ArrayList<Item>();
		myBidders = new ArrayList<Bidder>();
		
		///String fileName = "output/" + myFacilitator + ":" + myDate + ".csv";
	}
	
	/**
	 * Returns a list of Donors in this Auction.
	 * 
	 * @return myDonors
	 */
	public ArrayList<Donor> getDonors() {
		return myDonors;
	}
	
	/**
	 * Returns a list of Items in this Auction.
	 * 
	 * @return myItems
	 */
	public ArrayList<Item> getItems() {
		return myItems;
	}
	
	/**
	 * Returns a list of Bidders in this Auction.
	 * 
	 * @return myBidders
	 */
	public ArrayList<Bidder> getBidders() {
		return myBidders;
	}
	
	/**
	 * Adds a Donor to the myDonors list.
	 * 
	 * @param aDonor a Donor to be added.
	 */
	public void addDonor(Donor aDonor) {
		myDonors.add(aDonor);
	}
	
	/**
	 * Adds an Item to the myItems list.
	 * 
	 * @param an Item an Item to be added.
	 */
	public void addItem(Item anItem) {
		myItems.add(anItem);
	}
	
	/**
	 * Adds a Bidder to the myBidder's list.
	 * 
	 * @param aBidder a Bidder to be added.
	 */
	public void addBidder(Bidder aBidder) {
		myBidders.add(aBidder);
	}
	
	/**
	 * Deletes a Donor from the myDonors list.
	 * 
	 * @param aDonor a Donor to be deleted.
	 */
	public void deleteDonor(Donor aDonor) {
		for (Item item : myItems) {
			if (aDonor == item.getDonor()) {
				item.setDonor(null);
				try {
					this.editFile("Items", item.getName(), item.getQr(), null, 0);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		myDonors.remove(aDonor);
		
	}
	
	/**
	 * Deletes an Item from the myItems list.
	 * 
	 * @param anItem an Item to be deleted.
	 */
	public void deleteItem(Item anItem) {
		boolean breakLoop = false;
		for (Donor donor : myDonors) {
			for (Item item : donor.getItemList()) {
				if (item == anItem) {
					donor.delete(item);
					try {
						this.editFile("Donors", donor.getFirstName() + "," + donor.getLastName(),
								0, null, 0);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					breakLoop = true;
					break;
				}
			}
			if (breakLoop) {
				break;
			}
		}
		myItems.remove(anItem);
	}
	
	/**
	 * Deletes a Bidder from the myBidders list.
	 * 
	 * @param aBidder a Bidder to be deleted.
	 */
	public void deleteBidder(Bidder aBidder) {
		myBidders.remove(aBidder);
	}
	
	/**
	 * Handles the event of a bid being placed.
	 * 
	 * @param aBidderID The ID of the bidder's device.
	 * @param anItemQR The QR code of the item being bid on.
	 * @throws IllegalArgumentException if the bidder's ID passe does not belong to any Bidder, or if the item QR code
	 * passed does not belong to any Item.
	 */
	public void placeBid(int aBidderID, long anItemQR) {
		Bidder bidder = null;
		Item itemBidOn = null;
		for (Bidder aBidder : myBidders) {
			if (aBidder.getid() == aBidderID) {
				bidder = aBidder;
				break;
			}
		}
		
		for (Item anItem : myItems) {
			if (anItem.getQr() == anItemQR) {
				itemBidOn = anItem;
				break;
			}
		}
		
		if (bidder != null && itemBidOn != null) {
			bidder.placeBid(itemBidOn);
		} else {
			throw new IllegalArgumentException("One of the parameters is invalid.");
		}
		
	}
	
	/**
	 * Increments the current bidder ID number, and returns it.
	 * 
	 * @return the new Bidder's ID
	 */
	public int assignID() {
		BidderID++;
		return BidderID;
	}
	
/**
 * Takes in a new line and writes it to the jar file.
 * 
 * @param sentinal The String that designates what section in the file to write to, could be "Donors", "Items", or "Bidders"
 * @param input The new line to write to the file.
 * @throws IOException
 */
public void writeToFile(String sentinal, String input) throws IOException {
		String writeBack = "";

		Scanner scanner = new Scanner(Paths.get(myFilePath));
		String line = scanner.nextLine();
		writeBack += line;
		while (!line.contains(sentinal) && scanner.hasNextLine()) {
			line = scanner.nextLine();
			writeBack += "\n" + line;
		}
		writeBack += "\n" + input;
		while (scanner.hasNextLine()) {
			writeBack += "\n" + scanner.nextLine();
		}
		scanner.close();
		
		File writeTo = new File(myFilePath);
		if (writeTo.exists() && writeTo.delete()) {
			writeTo = new File(myFilePath);
		}
		
		PrintWriter writeFile = new PrintWriter(new FileWriter(writeTo, true));
		writeFile.println(writeBack);
		
		writeFile.close();
	}

	/**
	 * Edits a line in the file.
	 * 
	 * @param sentinal Indicates which Object the line being edited is, could either be "Items" or "Donors"
	 * @param nameToFind The name of the object to be edited.
	 * @param codeToFind If it exists, the code number for the object to be edited.
	 * @param nameToAdd The name of the object to be added to this line, if it consists of two parts (first name & last Name), 
	 * it must be comma separated (firstName,lastName), or if it is null, then an item is being removed from the line.
	 * @param codeToAdd If it exists, the code to the new object being added.
	 * was bid on, "w" indicates the item being added was won.
	 * @throws IOException
	 */
	public void editFile(String sentinal, String nameToFind, long codeToFind, String nameToAdd, long codeToAdd) throws IOException {
		String writeBack = "";
		Scanner scanner = new Scanner(Paths.get(myFilePath));
		String line = scanner.nextLine();
		writeBack += line;
		
		while (!line.contains(sentinal) && scanner.hasNextLine()) {
			line = scanner.nextLine();
			writeBack += "\n" + line;
		}
		
		line = scanner.nextLine();
		String[] tokens = line.split(",");
		String newline = line;
		if (sentinal.equals("Items")) {		//modifying an Item line
			while (!tokens[1].equals(nameToFind) && Integer.parseInt(tokens[6]) != codeToFind) {
				writeBack += "\n" + newline;
				newline = scanner.nextLine();
				tokens = newline.split(",");
			}
			
			if (nameToAdd == null) {
				newline = tokens[0];
				for (int i = 1; i < tokens.length; i++) {
					if (i == 5) {
						newline += ",no donor added";
					} else {
						newline += "," + tokens[i];
					}
				}
			} else {
				String[] theName = nameToAdd.split(",");
			
				if (theName.length > 1 || theName.length == 0) {		//adding or removing a donor
					newline = tokens[0];
					for (int i = 1; i < tokens.length; i++) {
						if (i == 5) {
							newline += "," + nameToAdd;
						} else {
							newline += "," + tokens[i];
						}
					}
				} else if (tokens.length == 1) {		//adding a bidder
					newline += "," + nameToAdd + " : " + codeToAdd;
				}
			}
		} else if (sentinal.equals("Donors")) {		//modifying a Donor line, adding a donated item
			String[] toks = nameToFind.split(",");
			while (!tokens[1].equals(toks[0]) && !tokens[2].equals(toks[1])) {
				writeBack += "\n" + newline;
				newline += scanner.nextLine();
				tokens = newline.split(",");
			}
			
			if (nameToAdd == null) {		//removing an item
				for (Donor donor : myDonors) {
					if (tokens[1].equals(donor.getFirstName()) && tokens[2].equals(donor.getLastName())) {
						newline = tokens[0];
						for (int i = 1; i < 6; i++) {
							newline += "," + tokens[i];
						}
						ArrayList<Item> items = (ArrayList<Item>) donor.getItemList();
						for (Item item : items) {
							newline += "," + item.getName() + " : " + item.getQr();
						}
						
						break;
					}
				}
				
			} else {
				newline += "," + nameToAdd + " : " + codeToAdd;
			}
		}
			
		writeBack += "\n" + newline;
		
		while (scanner.hasNextLine()) {
			writeBack += "\n" + scanner.nextLine();
		}
			
		scanner.close();
			
		File writeTo = new File(myFilePath);
		if (writeTo.exists() && writeTo.delete()) {
			writeTo = new File(myFilePath);
		}
			
		PrintWriter writeFile = new PrintWriter(new FileWriter(writeTo, true));
		writeFile.println(writeBack);
			 
		writeFile.close();
	}
	
	
	/**
	 * @param nameToFind
	 * @param codeToFind
	 * @param nameToAdd
	 * @param nickName
	 * @param phone
	 * @param address
	 * @param email
	 * @param codeToAdd
	 * @param itemBW Applies to an item being added to a Bidder line, must be "", "b" or "w"; "b" indicates the item being added has been bid on, 
	 * "w" indicates the item being added has been won, and "" indicates there isn't an item being added at all, 
	 * but instead the bidder info itself is being changed.
	 * @throws IOException
	 */
	public void editFileBidder(String nameToFind, long codeToFind, String nameToAdd, String nickName, 
			String phone, String address, String email, long codeToAdd, String itemBW) throws IOException {
		
		String writeBack = "";
		Scanner scanner = new Scanner(Paths.get(myFilePath));
		String line = scanner.nextLine();
		writeBack += line;
		
		while (!line.contains("Bidders") && scanner.hasNextLine()) {
			line = scanner.nextLine();
			writeBack += "\n" + line;
		}
		
		line = scanner.nextLine();
		String[] tokens = line.split(",");
		String newline = line;
		
		String[] toks = nameToFind.split(",");
		while (!tokens[1].equals(toks[0]) && !tokens[2].equals(toks[1]) && Integer.parseInt(tokens[4]) != codeToFind) {
			writeBack += "\n" + newline;
			newline += scanner.nextLine();
			tokens = newline.split(",");
		}
		if (itemBW.equals("")) {		//modifying the Bidder info itself
			//System.out.println(scanner.nextLine());
			String[] newName = nameToAdd.split(",");
			newline = "+," + newName[0] + "," + newName[1] + "," + nickName + "," + codeToFind + "," 
					+ phone + "," + email + "," + address;
			int count = 0;
			while (scanner.hasNextLine() && count < 2) {
				newline += "\n" + scanner.nextLine();
				count++;
			}
		} else if (itemBW.equals("b")) { 										//adding an item that has been bid on
			String nextline = scanner.nextLine();
			if (nextline.contains("-")) {								//there is already a line of items bid on
				newline += "\n" + nextline + "," + nameToAdd + " : " + codeToAdd;
			} else {													//need to create a line of items bid on
				newline += "\n-," + nameToAdd + " : " + codeToAdd + "\n" + nextline;
			}
		} else if (itemBW.equals("w")) {														//adding an item won
			newline = "\n" + scanner.nextLine();
			String nextline = scanner.nextLine();
			if (newline.contains("-")) {								//there is already a line of items won
				newline += "\n" + nextline + "," + nameToAdd + " : " + codeToAdd;
			} else {													//need to create a line of items won
				newline += "\n-," + nameToAdd + " : " + codeToAdd + "\n" + nextline;
			}
		}
		
		writeBack += "\n" + newline;
		
		while (scanner.hasNextLine()) {
			writeBack += "\n" + scanner.nextLine();
		}
			
		scanner.close();
			
		File writeTo = new File(myFilePath);
		if (writeTo.exists() && writeTo.delete()) {
			writeTo = new File(myFilePath);
		}
			
		PrintWriter writeFile = new PrintWriter(new FileWriter(writeTo, true));
		writeFile.println(writeBack);
			 
		writeFile.close();
	}
	
	/**
	 * Deletes an object from the file.
	 * 
	 * @param sentinal Indicates which Object the line being edited is, could either be "Items", "Donors", or "Bidders"
	 * @param name The name of the object being deleted.
	 * @param code If it exists, the code of the object being deleted.
	 * @throws IOException
	 */
	public void deleteFromFile (String sentinal, String name, long code) throws IOException {
		String writeBack = "";
		Scanner scanner = new Scanner(Paths.get(myFilePath));
		
		String line = scanner.nextLine();
		writeBack += line;
		while (!line.contains(sentinal) && scanner.hasNextLine()) {
			line = scanner.nextLine();
			writeBack += "\n" + line;
		}
		
		String newline = "";
		
		if (line.contains(sentinal) && scanner.hasNextLine()) {
			newline = scanner.nextLine();
			
			System.out.println("newline: " + newline);
			
			String[] tokens = newline.split(",");
			
			if (sentinal.equals("Items")) {
				while (!tokens[1].equals(name) && Long.parseLong(tokens[6]) != code && scanner.hasNextLine()) {
					writeBack += "\n" + newline;
					newline = scanner.nextLine();
					tokens = newline.split(",");
				}
				newline = scanner.nextLine();
				
			} else if (sentinal.equals("Donors")) {
				String[] toks = name.split(",");
				while (!tokens[1].equals(toks[0]) && !tokens[2].equals(toks[1]) && scanner.hasNextLine()) {
					writeBack += "\n" + newline;
					newline += scanner.nextLine();
					tokens = newline.split(",");
				}
				newline = scanner.nextLine();
				
			} else if (sentinal.equals("Bidders")) {
				String[] toks = name.split(",");
				while (scanner.hasNextLine() && !tokens[1].equals(toks[0]) && !tokens[2].equals(toks[1]) && Integer.parseInt(tokens[4]) != code) {
					System.out.println("tokens[1] + toks[0]: " + tokens[1] + " " + toks[0] + " tokens[2] + toks[1]: " + tokens[2] + " " + toks[1]
							+ " tokens[4] + code: " + tokens[4] + " " + code);
					
					writeBack += "\n" + newline;
					writeBack += "\n" + scanner.nextLine();
					writeBack += "\n" + scanner.nextLine();
					line = scanner.nextLine();
					
					System.out.println("inside while: " + line);
					
					newline = line;
					tokens = line.split(",");
				}
				int count = 0;
				while (scanner.hasNextLine() && count < 2) {
					newline = "";
					System.out.println("2nd while: " + scanner.nextLine());
					count++;
				}
				if (scanner.hasNextLine()) {
					
					System.out.println("if");
					
					newline = scanner.nextLine();					
				} else {
					
					System.out.println("else");
					
					newline = "";
				}
				
			}
		}
		if (!newline.equals("")) {
			
			System.out.println("newline == \"\": " + newline);
			
			writeBack += "\n" + newline;
		}
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			
			System.out.println("last while: " + line);
			
			writeBack += "\n" + line;
		}
		scanner.close();
		
		File writeTo = new File(myFilePath);
		if (writeTo.exists() && writeTo.delete()) {
			writeTo = new File(myFilePath);
		}
			
		PrintWriter writeFile = new PrintWriter(new FileWriter(writeTo, true));
		writeFile.println(writeBack);
			 
		writeFile.close();
	}

	
	/**
	 * Creates a new file for this Auction, placing all it's info into a csv file titled with  
	 * the facilitator's name and the date of the Auction; 
	 * currently the file is saved in a folder under this project called "tempOutput".
	 * @throws IOException 
	 */
	public void exportFile(String fileName) throws IOException {
		PrintWriter writeFile = new PrintWriter(new FileWriter(fileName, true));
		writeFile.println("#,Auction Info");
		writeFile.println("+," + myFacilitator + "," + myDate + "," + myStartTime + "," + myEndTime);
		
		//write out all items.
		writeFile.println("#,Items");
		for (Item item : myItems) {
			
			writeFile.println("+," + item.toString());
			writeFile.println();
		}
		
		//write out Donors
		writeFile.println("#,Donors");
		for (Donor donor : myDonors) {
			
			writeFile.println("+," + donor.toString());
			writeFile.println();
		}
		
		//write out Bidders
		writeFile.println("#,Bidders");
		for (Bidder bidder : myBidders) {
			writeFile.println("+," + bidder.toString());
			writeFile.println();
		}
		
		writeFile.close();
	}
	
	/**
	 * This would read in an Auction file and populate the software with the data in the file.
	 * @throws IOException 
	 */
	public void importFile(String filePath) throws IOException {
		Scanner reader = new Scanner (Paths.get(filePath));
		String line;
		String[] input;
		if (reader.hasNextLine()) {
			line = reader.nextLine(); //eat the "#Auction Info" line
			if (reader.hasNext()) {
				line = reader.nextLine(); //grab the line with Auction info
				input = line.split(",");
				myFacilitator = input[1];
				myDate = input[2];
				myStartTime = input[3];
				myEndTime = input[4];
				
				if (reader.hasNextLine()) {
					line = reader.nextLine(); //eat the "#Items" line
					
					parseItems(reader);
					parseDonors(reader);
					parseBidders(reader);
					
				}
			}
		}
		reader.close();
	}
	
	/**
	 * A helper method that reads through a list of items in a csv file and creates objects out of them.
	 * 
	 * @param reader a Scanner to read through the file.
	 */
	private void parseItems(Scanner reader) {
		if (reader.hasNextLine()) {
			String line = reader.nextLine(); //grab the next line, will either be a "+" or a "#" line
			String[] input = line.split(",");
			while (input[0].equals("+")) { //the Items info
				String name = input[1];
				String description = input[2];
				int minInc = Integer.parseInt(input[3]);
				int startingPrice = Integer.parseInt(input[4]);
				long qr = Long.parseLong(input[6]);
				Item item = new Item(name, description, minInc, startingPrice, qr);
				myItems.add(item);
				
				DefaultTableModel model = (DefaultTableModel) OptionsMain._itemTable.getModel();
				model.addRow(new Object[]{name, qr, "$" + startingPrice, "$" + minInc});
				
				if (reader.hasNextLine()) {
					line = reader.nextLine(); //grab the next line with Items info, or the "#Donors" line
					input = line.split(",");
				} else {
					break;
				}
			}
		}
	}
	
	/**
	 * A helper method that reads through a list of Donors in a csv file and creates objects out of them.
	 * 
	 * @param reader a Scanner to read through the file.
	 */
	private void parseDonors(Scanner reader) {
		if (reader.hasNextLine()) {
			String line = reader.nextLine();
			String[] input = line.split(",");
			while (input[0].equals("+")) {
				String firstName = input[1];
				String lastName = input[2];
				String phone = input[3];
				String email = input[4];
				String address = input[5];
				Donor donor = new Donor(firstName, lastName, email, address, phone);
				for (int i = 6; i < input.length; i++) {
					String[] input2 = input[i].split(" : ");
					for (Item item : myItems) {
						if (item.getName().equals(input2[0]) && item.getQr() == Long.parseLong(input2[1])) {
							donor.add(item);
							item.setDonor(donor);
							break;
						}
					}
				}
	            OptionsMain._comboModel.addElement(firstName + " " 
				+ lastName + " - " + email);
	            DefaultTableModel model = (DefaultTableModel) OptionsMain._donorTable.getModel();
				model.addRow(new Object[]{firstName, lastName, email, address, phone});
				myDonors.add(donor);
				
				if (reader.hasNextLine()) {
					line = reader.nextLine(); //grab the next line with Donor info, or the "#Bidders" line
					input = line.split(",");
				} else {
					break;
				}
			}
		}
	}
	
	/**
	 * A helper method that reads through a list of Bidders in a csv file and creates objects out of them.
	 * 
	 * @param reader a Scanner to read through the file.
	 */
	private void parseBidders(Scanner reader) {
		boolean firstBidder = true;
		if (reader.hasNextLine()) {
			String line = reader.nextLine();
			String[] input = line.split(",");
			while (input[0].equals("+")) {
				String firstName = input[1];
				String lastName = input[2];
				String nickName = input[3];
				String phone = input[5];
				String email = input[6];
				String address = input[7];
				int id = Integer.parseInt(input[4]);
				
				Bidder bidder = new Bidder(firstName, lastName, email, address, nickName, phone);
				bidder.setid(id);
				
				if (firstBidder) {
					BidderID = bidder.getid();
					firstBidder = false;
				}
				
				if (reader.hasNextLine()) {
					line = reader.nextLine(); //eat the "items bid on" line
					if (reader.hasNextLine()) {
						line = reader.nextLine(); //read in the "items won" line
						input = line.split(",");
						if (input.length > 1) { //here read in items won only
							for (int i = 1; i < input.length; i++) {
								String[] input2 = input[i].split(":");
								for (Item item : myItems) {
									if (item.getQr() == Long.parseLong(input2[1])) {
										bidder.addItemWon(item);
										break;
									}
								}
							}
						}
					}
				} 
				
				myBidders.add(bidder);
				
				if (reader.hasNextLine()) {
					line = reader.nextLine(); //grab the next line with Donor info, or the "#Bidders" line
					input = line.split(",");
				} else {
					break;
				}
			}
		}
	}
	
	/**
	 * A main method to simulate  bids being placed inside this Auction.
	 * 
	 * @param args
	 * @throws IOException
	 */
//	public static void main (String[] args) throws IOException {
//		Auction a = new Auction();
//		a.importFile("");
//		if (args.length == 2) {
//			a.placeBid(Integer.parseInt(args[0]), Long.parseLong(args[1]));
//		}
//	}
}

