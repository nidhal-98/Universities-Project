import java.util.Scanner;

public class Main {
	
	static Scanner sc = new Scanner(System.in);
	static String countryEntrerd;
	static String databaseName;
	public static void main(String[] args) {
		System.out.print("Enter Database Name:  ");
		APIConsumer.APICountries();
		System.out.print("\nEnter name of the country:  ");
		countryEntrerd = sc.next();
		
		if(APIConsumer.countrySet.stream().anyMatch(c -> c.equalsIgnoreCase(countryEntrerd))) {
			APIConsumer.APIUniversity();
		}
		else {
			System.out.println(countryEntrerd + " is not in the list!");
		}
		
	}

}
