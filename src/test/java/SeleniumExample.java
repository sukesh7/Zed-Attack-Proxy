import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiException;


public class SeleniumExample {

	//@Test
	public void seleniumTests() throws InterruptedException, ClientApiException, IOException {
		System.setProperty("webdriver.gecko.driver", "./geckodriver");

		WebDriver driver;
		// Selenium Profile Setup
		FirefoxOptions firefoxOptions = new FirefoxOptions();
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("network.proxy.type", 1);
		profile.setPreference("network.proxy.http", "localhost");
		profile.setPreference("network.proxy.http_port", 8080);
		firefoxOptions.setProfile(profile);
		driver = new FirefoxDriver(firefoxOptions);

		// Selenium running the script
		driver.get("http://localhost/mutillidae/");
		driver.get("http://localhost/mutillidae/index.php?page=add-to-your-blog.php");
		Thread.sleep(1000);
		driver.quit();

		// Report Generation
		ClientApi api = new ClientApi("localhost", 8080, "null");
        String Report = new String(api.core.htmlreport(), StandardCharsets.UTF_8);
        System.out.println(System.getProperty("user.dir"));
        Path filePath = Paths.get(System.getProperty("user.dir") + "/scan-results/seleniumTests.html");
        if (!Files.exists(filePath, LinkOption.NOFOLLOW_LINKS)) {
            Files.createFile(filePath);
        }
        Files.write(filePath, Report.getBytes());

		System.out.println("Stop");
	}

}
