import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class JDBC {
	static HashSet<String> table = new HashSet<String>();

	public static void countriesTable() {

		String url = "jdbc:sqlserver://localhost:1433;" + "databaseName=" + Main.databaseName + ";" + "encrypt=true;"
				+ "trustServerCertificate=true";

		String user = /* "sa" */ Main.databaseUsername;
		String pass = /* "root" */ Main.databasePass;

		Connection con = null;
		try {

			Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			DriverManager.registerDriver(driver);

			con = DriverManager.getConnection(url, user, pass);

			String sql = "IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Countries')\r\n" + "BEGIN\r\n"
					+ "CREATE TABLE Countries(\r\n" + "Country_Name VARCHAR(50)\r\n" + ");\r\n" + "END\r\n"
					+ "truncate table Countries \r\n" + "INSERT INTO Countries (Country_Name)\r\n" + "VALUES";
			
			table.add("Countries");

			StringBuilder values = new StringBuilder();
			for (int i = 0; i < APIConsumer.countrySet.size(); i++) {
				if (i > 0) {
					values.append(",");
				}
				values.append("(?)");
			}
			sql += values.toString();

			PreparedStatement statement = con.prepareStatement(sql);
			for (int i = 0; i < APIConsumer.countrySet.size(); i++) {
				statement.setString(i + 1, APIConsumer.countrySet.get(i));
			}
			statement.executeUpdate();

			statement.close();
			con.close();
		} catch (Exception ex) {
			System.err.println(ex);
		}
	}

	/*
	 * -----------------------------------------------------------------------------
	 * ----------------
	 */

	public static void deleteTable() {

		String url = "jdbc:sqlserver://localhost:1433;" + "databaseName=" + Main.databaseName + ";" + "encrypt=true;"
				+ "trustServerCertificate=true";

		String user = /* "sa" */ Main.databaseUsername;
		String pass = /* "root" */ Main.databasePass;

		Connection con = null;
		try {

			Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			DriverManager.registerDriver(driver);

			con = DriverManager.getConnection(url, user, pass);

			String sql = "IF EXISTS (SELECT * FROM sys.tables WHERE name = '" + Main.tableName1 
			+ "')\r\n" 
			+ "BEGIN\r\n"
			+ "Drop table " + Main.tableName1 + "\r\n" 
			+ "END\r\n";
			
			if(Main.tableName1.equals("Countries")) {
				table.remove(0);
			}
			else {
				table.remove(1);
			}

			PreparedStatement statement = con.prepareStatement(sql);

			statement.executeUpdate();
			statement.close();
			con.close();
			
			System.out.println("Deleted Successfully :)");

		} catch (Exception ex) {
			System.err.println(ex);
		}
	}

	/*
	 * -----------------------------------------------------------------------------
	 * ----------------
	 */

	public static void printCountriesTable() {

		String url = "jdbc:sqlserver://localhost:1433;" + "databaseName=" + Main.databaseName + ";" + "encrypt=true;"
				+ "trustServerCertificate=true";

		String user = /* "sa" */ Main.databaseUsername;
		String pass = /* "root" */ Main.databasePass;

		Connection con = null;
		try {

			Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			DriverManager.registerDriver(driver);

			con = DriverManager.getConnection(url, user, pass);
			
			DatabaseMetaData dbm = con.getMetaData();
	        ResultSet tables = dbm.getTables(null, null, "Countries", null);
	        
	         if (tables.next()) {

	 			String sql = "select *\r\n" + "From Countries";

	 			PreparedStatement statement = con.prepareStatement(sql);
	 			ResultSet resultSet = statement.executeQuery();

	 			while (resultSet.next()) {
	 				String countryName = resultSet.getString("Country_Name");
	 				System.out.println("\t" + countryName);
	 			}

	 			statement.close();
	 			con.close();} 
	         else {
	          }
		} catch (Exception ex) {
			System.err.println(ex);
		}
	}

	/*
	 * -----------------------------------------------------------------------------
	 * ----------------
	 */

	public static void universitiesTable() {

		String url = "jdbc:sqlserver://localhost:1433;" + "databaseName=" + Main.databaseName + ";" + "encrypt=true;"
				+ "trustServerCertificate=true";

		String user = /* "sa" */ Main.databaseUsername;
		String pass = /* "root" */ Main.databasePass;

		Connection con = null;
		try {

			Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			DriverManager.registerDriver(driver);

			con = DriverManager.getConnection(url, user, pass);

			String sql = "IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'University')\r\n" 
			        + "BEGIN\r\n"
			        + "CREATE TABLE University(\r\n" 
			        + "alpha_two_code VARCHAR(50),\r\n" 
			        + "name VARCHAR(MAX),\r\n"
			        + "country VARCHAR(50),\r\n" 
			        + "domains VARCHAR(50),\r\n"
			        + "web_pages VARCHAR(50),\r\n" 
			        + "state_province VARCHAR(50)\r\n" 
			        + ");\r\n" 
			        + "END\r\n"
			        + "MERGE INTO University AS target\r\n"
			        + "USING (SELECT ?, ?, ?, ?, ?, ?) AS source (name, country, domains, web_pages, alpha_two_code, state_province)\r\n"
			        + "ON (target.name = source.name)\r\n"
			        + "WHEN MATCHED THEN\r\n"
			        + "UPDATE SET name = source.name, domains = source.domains, web_pages = source.web_pages, alpha_two_code = source.alpha_two_code, state_province = source.state_province\r\n"
			        + "WHEN NOT MATCHED THEN\r\n"
			        + "INSERT (name, country, domains, web_pages, alpha_two_code, state_province)\r\n"
			        + "VALUES (source.name, source.country, source.domains, source.web_pages, source.alpha_two_code, source.state_province);";

			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, APIConsumer.unversitiesList.get(APIConsumer.unversitiesList.size() - 1).name);
			statement.setString(2, APIConsumer.unversitiesList.get(APIConsumer.unversitiesList.size() - 1).country);
			statement.setString(3, APIConsumer.unversitiesList.get(APIConsumer.unversitiesList.size() - 1).domains[0]);
			statement.setString(4, APIConsumer.unversitiesList.get(APIConsumer.unversitiesList.size() - 1).web_pages[0]);
			statement.setString(5, APIConsumer.unversitiesList.get(APIConsumer.unversitiesList.size() - 1).alpha_two_code);
			statement.setString(6, APIConsumer.unversitiesList.get(APIConsumer.unversitiesList.size() - 1).state_province);
			statement.executeUpdate();


			// Close the PreparedStatement object
			statement.close();

			con.close();
		} catch (Exception ex) {
			System.err.println(ex);
		}
	}

	/*
	 * -----------------------------------------------------------------------------
	 * ----------------
	 */

	public static void printUniversityTable() {

		String url = "jdbc:sqlserver://localhost:1433;" + "databaseName=" + Main.databaseName + ";" + "encrypt=true;"
				+ "trustServerCertificate=true";

		String user = /* "sa" */ Main.databaseUsername;
		String pass = /* "root" */ Main.databasePass;

		Connection con = null;
		try {

			Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			DriverManager.registerDriver(driver);

			con = DriverManager.getConnection(url, user, pass);

			String sql = "select *\r\n" + "From University";

			PreparedStatement statement = con.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				String alpha_two_code = resultSet.getString("alpha_two_code");
				//System.out.println(alpha_two_code);
				
				String name = resultSet.getString("name");
				//System.out.println(name);
				
				String country = resultSet.getString("country");
				//System.out.println(country);
				
				String domains = resultSet.getString("domains");
				//System.out.println(domains);
				
				String web_pages = resultSet.getString("web_pages");
				//System.out.println(web_pages);
				
				String state_province = resultSet.getString("state_province");
				//System.out.println(state_province);
				
				System.out.print("\tUniversity Name: " + name);
				System.out.print("\n\tUniversity Country: " + country);
                System.out.print("\n\tUniversity Two Code: " + alpha_two_code);
                System.out.print("\n\tUniversity State-Province: " + state_province);
                System.out.print("\n\tUniversity Domains: " + domains);                
                System.out.print("\n\tUniversity Web Page: " + web_pages);
                System.out.println("\n -------------------------------------------------------------------------------------------");
			}

			statement.close();
			con.close();
		} catch (Exception ex) {
			System.err.println(ex);
		}
	}
	
	
	
	public static void printTables() {
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
					System.out.println("Table Name:  " + tableName);
					table.add(tableName);
				}
			}

			// Close the connection
			conn.close();
		} catch (Exception ex) {
		}
	}
	
	public static void checkTables() {
		try {
			// Connect to the database
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
				if (!tableName.equalsIgnoreCase("trace_xe_action_map") && !tableName.equalsIgnoreCase("trace_xe_event_map")) {
					table.add(tableName);
				}
			}

			// Close the connection
			conn.close();
		} catch (Exception ex) {
		}
	}
	
	public static void backup() {

		String url = "jdbc:sqlserver://localhost:1433;" + "databaseName=" + Main.databaseName + ";" + "encrypt=true;"
				+ "trustServerCertificate=true";

		String user = /* "sa" */ Main.databaseUsername;
		String pass = /* "root" */ Main.databasePass;

		Connection con = null;
		try {

			Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			DriverManager.registerDriver(driver);

			con = DriverManager.getConnection(url, user, pass);

			String sql = "BACKUP DATABASE " + Main.databaseName + "\r\n"
					+ "TO DISK = 'C:\\Users\\Lenovo\\eclipse-workspace\\UniversitiesProject\\" + Main.databaseName + ".bak'\r\n"
					+ "WITH DESCRIPTION = 'Full Backup for" + Main.databaseName + " Database'";
			
	
			PreparedStatement statement = con.prepareStatement(sql);

			statement.executeUpdate();

			statement.close();
			con.close();
			
			System.out.println("Backup Successfully :)");
		} catch (Exception ex) {
			System.err.println(ex);
		}
	}
	
}