import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.zaproxy.clientapi.core.ApiResponse;
import org.zaproxy.clientapi.core.ApiResponseElement;
import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiException;

public class ActiveScan {

	public static void main(String[] args) throws InterruptedException, ClientApiException {
		ClientApi api = new ClientApi("localhost", 8080, "null");
		
		ApiResponse resp = api.ascan.scan("http://dvwa.co.uk/", "True", "False", null, null, null);

        String scanid = ((ApiResponseElement) resp).getValue();
        int progress;

        //Track Progress
        while (true) {
            Thread.sleep(5000);
            progress =
                    Integer.parseInt(
                            ((ApiResponseElement) api.ascan.status(scanid)).getValue());
            System.out.println("Active Scan progress : " + progress + "%");
            if (progress >= 100) {
                break;
            }
        }
        
        //Report
        String Report = new String(api.core.htmlreport(), StandardCharsets.UTF_8);
        try {
 		Files.write(Paths.get(System.getProperty("user.dir")+"//Report//activescan.html"), Report.getBytes());
        } catch (IOException e) { 		
 		e.printStackTrace();
        }
        System.out.println("Active Scan complete");
	}

}
