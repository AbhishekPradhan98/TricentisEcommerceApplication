package stepDefinitions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;

public class steps extends BaseClass
{
	// Browser Launch
		@Before
		public void setup() throws IOException {

			// Load properties file
			configProp = new Properties();
			FileInputStream configPropfile = new FileInputStream("config.properties");
			configProp.load(configPropfile);

			String br = configProp.getProperty("browser");

			// Launching browser
			if (br.equals("firefox")) {
				System.setProperty("webdriver.gecko.driver", configProp.getProperty("firefoxpath"));
				driver = new FirefoxDriver();
			}

			else if (br.equals("chrome")) {
				System.setProperty("webdriver.chrome.driver", configProp.getProperty("chromepath"));
				driver = new ChromeDriver();
			}

			else if (br.equals("edge")) {
				System.setProperty("webdriver.edge.driver", configProp.getProperty("edgepath"));
				driver = new EdgeDriver();
			}

			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		}

		// Take screenshot of failed testcases

		@After
		public void tearDown(Scenario scenario) {
			if (scenario.isFailed()) {
				String screenshotName = scenario.getName().replaceAll(" ", "_");
				byte[] sourcePath = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
				scenario.attach(sourcePath, "image/png", screenshotName);
			}
		}

	

		@Given("open browser with url {string}")
		public void open_browser_with_url(String url) 
		{
		  driver.get(url);
		}



}
