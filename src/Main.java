import java.util.Scanner;

public class Main {
	
	static Scanner sc = new Scanner(System.in);
	static String countryEntrerd = " ";;
	static String databaseName;
	static String databaseUsername;
	static String databasePass;
	
	static String tableName1;

	public static void main(String[] args) {
		boolean menue = true;
		System.out.print("Enter Database Name:      ");
		databaseName = sc.next();
		System.out.print("Enter Database Username:  ");
		databaseUsername= sc.next();
		System.out.print("Enter Database Password:  ");
		databasePass = sc.next();
		while(menue) {
			System.out.println("\n1) backup");
			System.out.println("2) Removing Table");
			System.out.println("3) Print Universities");
			System.out.println("4) API / Database");
			System.out.println("5) Search");
			System.out.println("*********************");
			System.out.print("Enter Number:  ");
			String option = sc.next();
			
			switch (option) {
			case "1":
				break;
			case "2":
				JDBC.printTables();
				System.out.print("\nEnter Table Name:  ");
				tableName1 = sc.next();
				JDBC.deleteTable();
				System.out.println("Done!\n");
				break;
			case "3":
		    	System.out.println("Wait a moment :)");
				APIConsumer.APICountries();
				for(int i=0; i<APIConsumer.countrySet.size(); i++) {
					System.out.println(APIConsumer.countrySet.get(i));
				}
				System.out.print("\nEnter name of the country:  ");
				Main.sc.nextLine();
				Main.countryEntrerd = Main.sc.nextLine();
				
				if(APIConsumer.countrySet.stream().anyMatch(c -> c.equalsIgnoreCase(Main.countryEntrerd))) {
					APIConsumer.APIUniversity();
				}
				else {
					System.out.println(Main.countryEntrerd + " is not in the list!\n");
				}
				break;
			case "4":
				System.out.println("1. API");
				System.out.println("2. Databse");
				String select = sc.next();
				if(select.equalsIgnoreCase("1")) {
	                System.out.println("************************************************************");
					System.out.println("\n\t\tThe Countries from API");
	                System.out.println("\n************************************************************");
					APIConsumer.APICountries();
					for(int i=0; i<APIConsumer.countrySet.size(); i++) {
						System.out.println(APIConsumer.countrySet.get(i));
					}
					System.out.println("");
					if(countryEntrerd.equalsIgnoreCase(" ")) {
						System.out.println("To print university:");
						System.out.println("  First, go to Optin #3 to enter country");
					}
					else {
						
						System.out.println("************************************************************");
						System.out.println("\n\t\tThe University from API");
						System.out.println("\n************************************************************");
						APIConsumer.APIUniversity();
						System.out.println("");
					}
				}
				else if(select.equalsIgnoreCase("2")) {
					if(JDBC.table.isEmpty()) {
						System.out.println("There is no tables");
					}
					else {
						
						System.out.println("************************************************************");
						System.out.println("\n\t\tThe Countries from Database");
						System.out.println("\n************************************************************");
						JDBC.printCountriesTable();
						System.out.println("************************************************************");
						System.out.println("\n\t\tThe University from Database");
						System.out.println("\n************************************************************");
						JDBC.printUniversityTable();
						System.out.println("");
					}
				}
				else {
					System.err.println("Entre Valid Input");
				}
				break;
			}

		}
		
	}

}
