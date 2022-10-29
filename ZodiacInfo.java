package Horoscope;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;




public class ZodiacInfo {

	public Map<String, String> getInfo(String signe) {
		try {
			
			signe = signe.toLowerCase();
			URL url = new URL("https://aztro.sameerkumar.website/?sign=" + signe + "&day=today");
			
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			
			InputStream data = connection.getInputStream();
			
			String jsonData = "";
			
			int d;
			
			while((d = data.read()) != -1) {
				jsonData += (char) d;
			}
			
			@SuppressWarnings("unchecked")
			Map<String, String> info = new ObjectMapper().readValue(jsonData, Map.class);
			
			return info;
		    
		} catch(Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
}
