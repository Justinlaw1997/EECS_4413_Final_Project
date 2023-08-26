package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.jupiter.api.Test;

class AdminServletTest {
	
	private int exit;
	
	@Test
	void testHomePage() throws IOException, InterruptedException {
		StringBuilder output = new StringBuilder();
		String curl = "curl -X GET http://jil2.us-east-2.elasticbeanstalk.com/";

		setUpCurl(curl, output);
		
		String expected = "<!DOCTYPE html>\n"
				+ "<html>\n"
				+ "<head>\n"
				+ "<meta charset=\"UTF-8\">\n"
				+ "<title>Item Catalog</title>\n"
				+ "</head>\n"
				+ "<body>\n"
				+ "	<h2> Welcome to our store!</h2>\n"
				+ "	\n"
				+ "	<form method='get' action='/welcome.jsp'>\n"
				+ "		<input type=\"submit\" value=\"Enter\" />\n"
				+ "	</form>\n"
				+ "\n"
				+ "</body>\n"
				+ "</html>";
		
		assertEquals(0, exit);
		assertEquals(expected, output.toString().trim());		
	}
	
	private void setUpCurl(String curl, StringBuilder output) throws IOException, InterruptedException {
		ProcessBuilder processBuilder; 
		String os = System.getProperty("os.name").toLowerCase();
		if (os.contains("win")) {
			processBuilder = new ProcessBuilder("cmd.exe", "/c", curl);
		} else {
			processBuilder = new ProcessBuilder("bash", "-c", curl);
		}

		Process process = processBuilder.start();
		InputStream inputStream = process.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		
		String line;
		while((line = reader.readLine()) != null) {
			output.append(line).append('\n');
		}
		
		exit = process.waitFor();
	}

}
