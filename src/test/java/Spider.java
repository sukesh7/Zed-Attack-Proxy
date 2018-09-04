import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.zaproxy.clientapi.core.ApiResponse;
import org.zaproxy.clientapi.core.ApiResponseElement;
import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiException;

public class Spider {

	public static void main(String[] args) throws InterruptedException, ClientApiException, IOException {
		ClientApi api = new ClientApi("localhost", 8080, "null");
		ApiResponse resp = api.spider.scan("http://dvwa.co.uk/", null, null, null, null);
//		api.spider.scan("http://dvwa.co.uk/", null, null, null, null);
		String scanid;
		int progress;

		// Generate Scan Id
		scanid = ((ApiResponseElement) resp).getValue();

		//Progress Tracker
		while (true) {
			Thread.sleep(1000);
			progress = Integer.parseInt(((ApiResponseElement) api.spider.status(scanid)).getValue());
			System.out.println("Spider progress : " + progress + "%");
			if (progress >= 100) {
				break;
			}
		}

		String Report = new String(api.core.htmlreport(), StandardCharsets.UTF_8);
		Files.write(Paths.get(System.getProperty("user.dir") + "//Report//spider.html"), Report.getBytes());
		System.out.println("Spider complete");
	}

}
