import java.util.Scanner;

public class Main {
	
	static Scanner sc = new Scanner(System.in);
	static String countryEntrerd;
	static String databaseName;
	static String databaseUsername;
	static String databasePass;

	public static void main(String[] args) {
		boolean menue = true;
		System.out.print("Enter Database Name:      ");
		databaseName = sc.next();
		System.out.print("Enter Database Username:  ");
		databaseUsername= sc.next();
		System.out.print("Enter Database Password:  ");
		databasePass = sc.next();
		while(menue) {
			System.out.println("1) backup");
			System.out.println("2) Removing Table");
			System.out.println("3) Print Universities");
			System.out.println("4) API / Database");
			System.out.println("5) Search");
			System.out.print("\n Enter Number:  ");
			String option = sc.next();
			
			switch (option) {
			case "1":
				break;
			case "2":
				break;
			case "3":
		    	System.out.println("Wait a moment :)");
				APIConsumer.APICountries();
				for(int i=0; i<APIConsumer.countrySet.size(); i++) {
					System.out.println(APIConsumer.countrySet.get(i));
				}
				System.out.print("\nEnter name of the country:  ");
				Main.countryEntrerd = Main.sc.next();
				
				if(APIConsumer.countrySet.stream().anyMatch(c -> c.equalsIgnoreCase(Main.countryEntrerd))) {
					APIConsumer.APIUniversity();
				}
				else {
					System.out.println(Main.countryEntrerd + " is not in the list!\n");
				}
				break;
			}

		}
		
	}

}
