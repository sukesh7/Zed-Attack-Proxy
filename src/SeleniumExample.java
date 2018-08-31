import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiException;

public class SeleniumExample {

	public static void main(String[] args) throws InterruptedException, ClientApiException, IOException {
		WebDriver driver;
		System.setProperty("webdriver.gecko.driver", "/Users/antoadai/Downloads/geckodriver");

		// Selenium Profile Setup
		FirefoxOptions firefoxOptions = new FirefoxOptions();
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("network.proxy.type", 1);
		profile.setPreference("network.proxy.http", "localhost");
		profile.setPreference("network.proxy.http_port", 8080);
		firefoxOptions.setProfile(profile);
		driver = new FirefoxDriver(firefoxOptions);

		// Selenium running the script
		driver.get("http://dvwa.co.uk/");
		driver.findElement(By.xpath("//a[text()='Dewhurst Security']")).click();
		Thread.sleep(10000);
		driver.quit();

		// Report Generation
		ClientApi api = new ClientApi("localhost", 8080, "null");
		String Report = new String(api.core.htmlreport(), StandardCharsets.UTF_8);
		Files.write(Paths.get(System.getProperty("user.dir") + "//Report//report.html"), Report.getBytes());
		System.out.println("Stop");
	}

}
