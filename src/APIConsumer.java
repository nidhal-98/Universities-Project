import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.google.gson.Gson;

public class APIConsumer {
    static ArrayList<String> countrySet = new ArrayList<String>();
    static ArrayList<Unversity> unversitiesList = new ArrayList<Unversity>();
    
    public static void APICountries() {
        String apiUrl = "http://universities.hipolabs.com/search?country=";
        try {
        	countrySet.clear();
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("HTTP error code : " + conn.getResponseCode());
            }
            
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output;
            StringBuilder json = new StringBuilder();
            
            while ((output = br.readLine()) != null) {
                json.append(output);
            }
            
            conn.disconnect();
            
            Gson gson = new Gson();
            Unversity unversityArr[] = gson.fromJson(json.toString(), Unversity[].class);
            
            // Use myObj for further processing

            for(int i=0; i< unversityArr.length; i++) {
                Unversity newUnversity = unversityArr[i];
                String countryName = newUnversity.country;
                if (!countrySet.contains(countryName) && !countryName.equals("Israel")) {
                    countrySet.add(countryName);
                }
            }
			JDBC.countriesTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /* --------------------------------------------------------------------------------------------------------------------------- */
    
    public static void APIUniversity() {
        String apiUrl = "http://universities.hipolabs.com/search?country=" + Main.countryEntrerd;
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("HTTP error code : " + conn.getResponseCode());
            }
            
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output;
            StringBuilder json = new StringBuilder();
            
            while ((output = br.readLine()) != null) {
                json.append(output);
            }
            
            conn.disconnect();
            
            Gson gson = new Gson();
            Unversity unversityArr[] = gson.fromJson(json.toString(), Unversity[].class);
            
            // Use myObj for further processing

            for(int i=0; i< unversityArr.length; i++) {
                Unversity newUnversity = unversityArr[i];
                System.out.print((i+1) + ".   University Name: " + newUnversity.name);
                System.out.print("\n     University Two Code: " + newUnversity.alpha_two_code);
                System.out.print("\n     University State-Province: " + newUnversity.state_province);
                System.out.print("\n     University Domains: ");
                for(int j=0; j<newUnversity.domains.length; j++) {                	
                	 System.out.print(newUnversity.domains[j] + ", ");
                }
                System.out.print("\n     UniversityUniversity Web Page: ");
                for(int j=0; j<newUnversity.web_pages.length; j++) {                	
                	 System.out.print(newUnversity.web_pages[j] + ", ");
                }
                System.out.println("\n -------------------------------------------------------------------------------------------");
                unversitiesList.add(newUnversity);
                JDBC.universitiesTable();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}