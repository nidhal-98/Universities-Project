import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;

import com.google.gson.Gson;

public class APIConsumer {
    static HashSet<String> countrySet = new HashSet<String>();
    
    public static void APICountries() {
        String apiUrl = "http://universities.hipolabs.com/search?country=";
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
                String countryName = newUnversity.country;
                if (!countrySet.contains(countryName) && !countryName.equals("Israel")) {
                    System.out.println(countryName);
                    countrySet.add(countryName);
                }
            }
            
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
                System.out.println(newUnversity.name);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}