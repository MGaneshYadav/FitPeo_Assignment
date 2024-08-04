package fitPeoScripts;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

public class FitPeoTest {
	@Test
	public void testScripts() {
		
		// Initialize a new Chrome browser instance
		WebDriver driver = new ChromeDriver();
		//Maximize the browser
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		JavascriptExecutor js = (JavascriptExecutor) driver;

		try {
			// Navigate to the FitPeo homepage
			driver.get("https://www.fitpeo.com");

			// Navigate to the Revenue Calculator page
			WebElement revenueCalculatorLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Revenue Calculator")));
			revenueCalculatorLink.click();

			// Scroll down to the slider section
			WebElement slider = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class,'MuiSlider-colorPrimary')]")));
			js.executeScript("arguments[0].scrollIntoView(false);", slider);

			// Locate the slider element
			// WebElement slider = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class,'MuiSlider-colorPrimary')]")));
			// Create an instance of Actions class
			// Actions actions = new Actions(driver);
			// //Calculate the amount to move (adjust based on your needs)
			// int offsetX = -26; // Example offset, adjust as needed
			// // Perform the drag action
			// actions.clickAndHold(slider).moveByOffset(offsetX, 0).release().perform();

			WebElement textField = driver.findElement(By.xpath("//input[@type='number']"));
			textField.click();
			textField.sendKeys(Keys.CONTROL + "a");
			textField.sendKeys(Keys.BACK_SPACE); 
			textField.sendKeys("820");

			// Verify the text field value is updated to 820
			wait.until(ExpectedConditions.attributeToBe(textField, "value", "820"));
			String textFieldValue = textField.getAttribute("value");
			if ("820".equals(textFieldValue)) {
				System.out.println("Slider adjusted to 820 successfully.");
			} else {
				System.out.println("Failed to adjust slider to 820. TextField value: " + textFieldValue);
			}

			// Update the text field to 560
			textField.click();
			textField.sendKeys(Keys.CONTROL + "a"); 
			textField.sendKeys(Keys.BACK_SPACE);
			textField.sendKeys("560");

			// Validate the slider value is updated to 560
			wait.until(ExpectedConditions.attributeToBe(textField, "value", "560"));
			String sliderValue = textField.getAttribute("value");
			if ("560".equals(sliderValue)) {
				System.out.println("Slider updated to 560 successfully.");
			} else {
				System.out.println("Failed to update slider to 560. Slider value: " + sliderValue);
			}
			
			textField.click();
			textField.sendKeys(Keys.CONTROL + "a");
			textField.sendKeys(Keys.BACK_SPACE);
			textField.sendKeys("820");

			// Scroll down further to select CPT codes
			js.executeScript("window.scrollBy(0, 800);"); // Adjust scrolling as needed

			// Select the checkboxes for CPT codes
			String[] cptCodes = {"CPT-99091", "CPT-99453", "CPT-99454", "CPT-99474"};
			for (String cptCode : cptCodes) {
				WebElement checkbox = driver.findElement(By.xpath("//p[text()='"+cptCode+"']/..//input[@type='checkbox']"));

				if (!checkbox.isSelected()) {
					checkbox.click();
				}
			}
			// Validate the Total Recurring Reimbursement value
			WebElement reimbursementHeader = driver.findElement(By.xpath("//p[text()='Total Recurring Reimbursement for all Patients Per Month:']//p"));
			wait.until(ExpectedConditions.visibilityOf(reimbursementHeader));
			if (reimbursementHeader.getText().contains("$110700")) {
				System.out.println("Total Recurring Reimbursement validated successfully.");
			} else {
				System.out.println("Failed to validate Total Recurring Reimbursement.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the browser
			driver.quit();
		}
	}
}
