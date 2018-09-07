import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;
import org.zaproxy.clientapi.core.*;


public class ActiveScan {


    @Test
    public void runActiveScan() throws ClientApiException, InterruptedException, IOException {

		ClientApi api = new ClientApi("localhost", 8080,   "null");
//		api.core.newSession("zap testing","");
		String BaseUrl = "http://localhost/mutillidae/index.php?page=login.php";
		api.accessUrl(BaseUrl);
		api.ascan.removeScanPolicy("high ones");
		api.ascan.addScanPolicy("high ones","HIGH","HIGH");
		api.ascan.setEnabledPolicies("4","high ones");

        //run with custom scan policy
        ApiResponse resp = api.ascan.scan(BaseUrl, "True", "False", "high ones", null, null);

        //run default scan policy
//        ApiResponse resp = api.ascan.scan(BaseUrl, "True", "False", "", null, null);


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


        ApiResponse resultSummary =api.core.alertsSummary(BaseUrl);

//        System.out.println(resultSummary.toString());
        System.out.println(((ApiResponseSet) resultSummary).getKeys());


        String actual = ((ApiResponseSet) resultSummary).getValue("High").toString();
        System.out.println("Actual" + actual);
        Assert.assertEquals(actual, "0" );

        System.out.println("Active Scan complete");
	}



}
