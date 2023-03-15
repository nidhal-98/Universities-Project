import java.sql.*;
import java.util.Scanner;

public class JDBC {
    public static void countriesTable() {

        String url = "jdbc:sqlserver://localhost:1433;" +
                "databaseName=" + Main.databaseName + ";" + 
                "encrypt=true;" +
                "trustServerCertificate=true";

        String user = /*"sa"*/    Main.databaseUsername;
        String pass = /*"root"*/  Main.databasePass;

        Connection con = null;
        try {

            Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            DriverManager.registerDriver(driver);

            con = DriverManager.getConnection(url, user, pass);

            String sql = "IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Countries')\r\n"
                    + "BEGIN\r\n"
                    + "CREATE TABLE Countries(\r\n"
                    + "Country_Name VARCHAR(50)\r\n"
                    + ");\r\n"
                    + "END\r\n"
                    + "truncate table Countries \r\n"
                    + "INSERT INTO Countries (Country_Name)\r\n"
                    + "VALUES ";

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
}