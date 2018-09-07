import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;
import org.zaproxy.clientapi.core.ApiResponse;
import org.zaproxy.clientapi.core.ApiResponseElement;
import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiException;

public class ActiveScan {

//	public static void main(String[] args) throws InterruptedException, ClientApiException {

    @Test
    public void runActiveScan() throws ClientApiException, InterruptedException, IOException {
		ClientApi api = new ClientApi("localhost", 8080,   "null");
		api.accessUrl("http://dvwa.co.uk/");

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

        String Report = new String(api.core.htmlreport(), StandardCharsets.UTF_8);
        System.out.println(System.getProperty("user.dir"));
        Path filePath = Paths.get(System.getProperty("user.dir") + "/scan-results/active-scan.html");
        if (!Files.exists(filePath, LinkOption.NOFOLLOW_LINKS)) {
            Files.createFile(filePath);
        }
        Files.write(filePath, Report.getBytes());
        System.out.println("Active Scan complete");
	}

}
