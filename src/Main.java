import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
				JDBC.backup();
				break;
			case "2":
				try {
					String url = "jdbc:sqlserver://localhost:1433;" + "databaseName=" + Main.databaseName + ";"
							+ "encrypt=true;" + "trustServerCertificate=true";
					String username = Main.databaseUsername;
					String password = Main.databasePass;
					Connection conn = DriverManager.getConnection(url, username, password);

					DatabaseMetaData metadata = conn.getMetaData();
					String[] types = { "TABLE" };
					ResultSet resultSet = metadata.getTables(null, null, "%", types);

					while (resultSet.next()) {
						String tableName = resultSet.getString("TABLE_NAME");
						if (!tableName.equalsIgnoreCase("trace_xe_action_map")
								&& !tableName.equalsIgnoreCase("trace_xe_event_map")) {
							JDBC.table.add(tableName);
						}
					}

					// Close the connection
					conn.close();
				} catch (Exception ex) {
				}
				if(JDBC.table.isEmpty()) {
					System.out.println("There is no Tables");
				}
				else {
					JDBC.printTables();
					System.out.print("\nEnter Table Name:  ");
					tableName1 = sc.next();
					JDBC.deleteTable();
					JDBC.table.remove(tableName1);
				}
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
						System.out.println("\n\t\tThe Universities from API");
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
						if(JDBC.table.contains("University")) {							
							System.out.println("************************************************************");
							System.out.println("\n\t\tThe University from Database");
							System.out.println("\n************************************************************");
							JDBC.printUniversityTable();
						}
						System.out.println("");
					}
				}
				else {
					System.err.println("Entre Valid Input");
				}
				break;
				
			case "5":
				APIConsumer.unversitiesHashList.clear();
				APIConsumer.APIallCountries();
				System.out.print("If the country has two word, WRITE it like ");			
				System.err.println("'United+States'");
				System.out.print("Enter name of country: ");
				String countryName = sc.next();
				for(int i=0; i<APIConsumer.unversitiesHashList.size(); i++) {
					if(APIConsumer.unversitiesHashList.get(i).country.equalsIgnoreCase(countryName)) {
		                System.out.print((i+1) + ".   University Name: " + APIConsumer.unversitiesHashList.get(i).name);
		                System.out.print("\n     University Two Code: " + APIConsumer.unversitiesHashList.get(i).alpha_two_code);
		                System.out.print("\n     University State-Province: " + APIConsumer.unversitiesHashList.get(i).state_province);
		                System.out.print("\n     University Domains: ");
		                for(int j=0; j<APIConsumer.unversitiesHashList.get(i).domains.length; j++) {                	
		                	 System.out.print(APIConsumer.unversitiesHashList.get(i).domains[j] + ", ");
		                }
		                System.out.print("\n     University Web Page: ");
		                for(int j=0; j<APIConsumer.unversitiesHashList.get(i).web_pages.length; j++) {                	
		                	 System.out.print(APIConsumer.unversitiesHashList.get(i).web_pages[j] + ", ");
		                }
		                System.out.println("\n -------------------------------------------------------------------------------------------");
					}
				}
				break;
				
				default:
					System.err.println("Invalid Input");
					break;
			}

		}
		
	}

}
