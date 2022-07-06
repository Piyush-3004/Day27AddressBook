package com.blz.asg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AddressBook {

	String bookName;
	ArrayList<Contact> contactDetail = new ArrayList<Contact>();
	static ArrayList<Contact> contactDetails = new ArrayList<Contact>();
	static HashMap<String, ArrayList<Contact>> system = new HashMap();
	static HashMap<String, ArrayList<Contact>> cityDict = new HashMap();
	static HashMap<String, ArrayList<Contact>> stateDict = new HashMap();

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		addContact();

		int op = 1;
		while (op != 0) {
			System.out.println("enter 0 to break , 1 to proceed");
			op = scanner.nextInt();
			if (op == 0)
				break;
			System.out.println(
					"Enter 0 to Exit,1 to add new book & contact ,2 to view ,3 to edit ,4 to delete,5 to search by city,6 to get count, "
							+ "\n 7 to sort on name, 8 to sort on city/statr/zipCode,9 to add Book to file");
			int ch = scanner.nextInt();
			switch (ch) {
			case 1:
				addNewBook();
				break;
			case 2:
				viewContact();
				break;
			case 3:
				editContact(contactDetails);
				break;
			case 4:
				AddressBook.deleteContact(contactDetails);
				break;
			case 5:
				searchByCity();
				break;
			case 6:
				showCountByState();
				break;
			case 7:
				sortByName();
			case 8:
				sortByCityStateZip();
			case 9:
				try {
					addBookToFile();
				} catch (IOException e) {
					System.out.println("not fount" + e);
				}

			}
		}
	}

	////////////////// Uc 13 add Contact Boot to File ///////////////////

	public static void addBookToFile() throws IOException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("enter book name to add in text file");
		String bn = scanner.next();

		Path fn = Paths.get("C:\\Users\\cheti\\eclipse-workspace\\Day27CollectionLibAddressBook\\addbook.txt");

		Files.writeString(fn, system.get("book1").toString());

	}

	///////////////////// Uc12///////////////////////////

	public static void sortByCityStateZip() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("enter book name");
		String bn = scanner.next();
		System.out.println("enter 1 to sort on City ,2 to Sort on State, 3 to sort on Zip");
		int c = scanner.nextInt();
		switch (c) {
		case 1:
			List srtCity = system.get(bn).stream().sorted((o1, o2) -> o1.city.compareTo(o2.city.toString()))
					.collect(Collectors.toList());
			System.out.println(srtCity);
			break;
		case 2:
			List srtState = system.get(bn).stream().sorted((o1, o2) -> o1.state.compareTo(o2.state.toString()))
					.collect(Collectors.toList());
			System.out.println(srtState);
			break;
		case 3:
			List srtZip = system.get(bn).stream()
					.sorted((o1, o2) -> (Integer.valueOf(o1.zip_Code)).compareTo(Integer.valueOf(o2.zip_Code)))
					.collect(Collectors.toList());
			System.out.println(srtZip);
			break;
		}

		List srtd = system.get(bn).stream().sorted((o1, o2) -> o1.first_Name.compareTo(o2.first_Name.toString()))
				.collect(Collectors.toList());

		System.out.println(srtd);
	}

	///////////////////// Uc11///////////////////////////

	public static void sortByName() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("enter book name");
		String bn = scanner.next();

		List srtd = system.get(bn).stream().sorted((o1, o2) -> o1.first_Name.compareTo(o2.first_Name.toString()))
				.collect(Collectors.toList());

		System.out.println(srtd);
	}

	////////////// Uc 10 ///////
	public static void countByCityorstate() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter city name");
		String stateOrCity = scanner.next();
		List personsInCItyOrState = contactDetails.stream()
				.filter(x -> x.city.equals(stateOrCity) || x.state.equals(stateOrCity)).collect(Collectors.toList());

		System.out.println(personsInCItyOrState.size());
	}

	////////////// Uc 9 Maintain dictionary of city to person and state to person
	////////////// //////////////
	static void showCountByState() {
		int count = 0;

		List cities = contactDetails.stream().map(x -> x.city).collect(Collectors.toList());
		List citiesv = contactDetails.stream().collect(Collectors.toList());

		cityDict.put(cities.toString(), contactDetails);

		Set<String> citydict = cityDict.keySet();
		for (String city : citydict) {
			System.out.println("Name: " + city);
			System.out.println(cityDict.get(city));
		}
		List states = contactDetails.stream().map(x -> x.state).collect(Collectors.toList());
		List statesv = contactDetails.stream().collect(Collectors.toList());

		stateDict.put(states.toString(), contactDetails);

		Set<String> statedict = stateDict.keySet();
		for (String state : statedict) {
			System.out.println("Name: " + state);
			System.out.println(cityDict.get(state));
		}
		System.out.println("Number of persons are : " + count);
	}
///////////////// UC_8 ability to search across city and State  /////////////////

	public static void searchByCity() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter city name");
		String stateOrCity = scanner.next();
		List all = contactDetails.stream().filter(x -> x.city.equals(stateOrCity) || x.state.equals(stateOrCity))
				.collect(Collectors.toList());

		System.out.println(all.size());
	}

	public static void addNewBook() {
		Scanner scanner = new Scanner(System.in);
		int op = 1;
		while (op != 0) {
			System.out.println("Enter 1 to add new book , 0 to exit");
			op = scanner.nextInt();
			if (op != 0) {
				addNewContact();
			}
		}
	}

	static void deleteContact(ArrayList<Contact> contactDetails) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter name to delete the info");
		String name = scanner.next();
		for (int i = 0; i < contactDetails.size(); i++) {
			System.out.println(contactDetails.get(i).first_Name);

			if (contactDetails.get(i).first_Name.equals(name)) {
				contactDetails.remove(contactDetails.get(i));
			}
		}
	}

	static void editContact(ArrayList<Contact> contactDetails) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter name to update the info");
		String name = scanner.next();
		for (int i = 0; i < contactDetails.size(); i++) {
			System.out.println(contactDetails.get(i).first_Name);

			if (contactDetails.get(i).first_Name.equals(name)) {

				System.out.println(
						"Enter 1 to change firstname , 2 to chnage lastname , 3 to change address , 4 to change City \n 5 tochange state, 6 to change zipcode , 7 to change obilenumber , 8 to change Email.");
				int op = scanner.nextInt();
				switch (op) {
				case 1:
					System.out.println("Enter new name");
					String newName = scanner.next();
					contactDetails.get(i).first_Name = newName;
					break;
				case 2:
					String newLastName = scanner.next();
					contactDetails.get(i).last_Name = newLastName;
					break;
				case 3:
					String newAddress = scanner.next();
					contactDetails.get(i).address = newAddress;
					break;
				case 4:
					String newCity = scanner.next();
					contactDetails.get(i).city = newCity;
					break;
				case 5:
					String newState = scanner.next();
					contactDetails.get(i).state = newState;
					break;
				case 6:
					int newZipCode = scanner.nextInt();
					contactDetails.get(i).zip_Code = newZipCode;
					break;
				case 7:
					int newPhoneNumber = scanner.nextInt();
					contactDetails.get(i).phone_Number = newPhoneNumber;
					break;
				case 8:
					String newEmail = scanner.next();
					contactDetails.get(i).email = newEmail;
					break;
				}
			}
		}
	}

	static void addNewContact() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("enter book name");
		String bn = scanner.next();
		System.out.println("Enter First Name : ");
/////// Uc 7 Check for duplicate contact  //////////////////
		String firstName = scanner.next();
		boolean duplicateContact = contactDetails.stream().anyMatch(x -> x.first_Name.equals(firstName));
		if (duplicateContact == true) {
			System.out.println("It is a duplicate contact.");
			return;
		}
		System.out.println("Enter Last Name : ");
		String lastName = scanner.next();
		System.out.println("Enter Address : ");
		String address = scanner.next();
		System.out.println("Enter City : ");
		String city = scanner.next();
		System.out.println("Enter State : ");
		String state = scanner.next();
		System.out.println("Enter ZipCode : ");
		int zipCode = scanner.nextInt();
		System.out.println("Enter Mobile Number : ");
		long phonenumber = scanner.nextLong();
		System.out.println("Enter EmailId : ");
		String emailId = scanner.next();
		AddressBook addBook1 = new AddressBook();
//		ArrayList<Contact> contactDetails = new ArrayList<Contact>();

		addBook1.contactDetail
				.add(new Contact(firstName, lastName, address, city, state, zipCode, phonenumber, emailId));
		contactDetails.addAll(addBook1.contactDetail);
		if (system.containsKey(bn)) {
			system.get(bn).addAll(addBook1.contactDetail);
			addBook1.contactDetail
					.add(new Contact(firstName, lastName, address, city, state, zipCode, phonenumber, emailId));
		} else {
			system.put(addBook1.bookName = bn, addBook1.contactDetail);
			contactDetails.addAll(addBook1.contactDetail);
		}
	}

	static void addContact() {
		AddressBook addBook = new AddressBook();
		addBook.bookName = "book0";
		addBook.contactDetail.add(new Contact("Piyush", "Patil", "Nashik", "Nashik", "Maharashtra", 422001, 901155747,
				"piyushp@gmail.com"));
		contactDetails.addAll(addBook.contactDetail);
		addBook.contactDetail.add(new Contact("Dinesh", "Patil", "Nashik", "Nashik", "Maharashtra", 422001, 901155747,
				"piyushp@gmail.com"));
		contactDetails.addAll(addBook.contactDetail);
		addBook.contactDetail.add(new Contact("Pyush", "Patil", "Nashik", "Nashik", "Maharashtra", 422001, 901155747,
				"piyushp@gmail.com"));

		system.put(addBook.bookName, addBook.contactDetail);
		contactDetails.addAll(addBook.contactDetail);
		AddressBook addBook2 = new AddressBook();

		addBook2.bookName = "book1";
		addBook2.contactDetail.add(new Contact("Piyush", "Patil", "Nasik", "Nasik", "Maharashtra", 422001, 901155747,
				"piyushp@gmail.com"));
		system.put(addBook2.bookName, addBook2.contactDetail);
		contactDetails.addAll(addBook2.contactDetail);

	}

	static void viewContact() {
		Set<String> bookNames = system.keySet();
		for (String book : bookNames) {
			System.out.println("Name: " + book);
			System.out.println(system.get(book));
		}
//		for (int i = 0; i < contactDetails.size(); i++) {
//			System.out.println(contactDetails.get(i));
//		}
	}

}
