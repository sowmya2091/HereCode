package Here;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Test {

	public WebDriver driver;

	public Test(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath = "//div[@class='card__details-content']")
	public WebElement cardDetail;

	@FindBy(xpath = "//div[@class='card__details-footer']")
	public List<WebElement> cardFooterSections;

	public List<WebElement> cardFooterLinks(int i) {
		List<WebElement> cardFooterLinks = driver.findElements(
				By.xpath("(//div[@class='card__details-footer'])[" + i + "]//ul[@class='dp-cta-links-wrapper']/li"));
		return cardFooterLinks;

	}

	public boolean isdropDownVariant(int i, int j) {
		boolean isDropDownVariant = driver.findElements(By.xpath("((//div[@class='card__details-footer'])[" + i
				+ "]//ul[@class='dp-cta-links-wrapper']/li)[" + j + "]/div")).size() > 0;
		return isDropDownVariant;
	}

	public WebElement dropDownVariant(int i, int j) {
		WebElement dropDownVar = driver.findElement(By.xpath("((//div[@class='card__details-footer'])[" + i
				+ "]//ul[@class='dp-cta-links-wrapper']/li)[" + j + "]/div"));
		return dropDownVar;
	}

	public List<WebElement> dropDownVariantSubLinks(int i, int j) {
		List<WebElement> dropDownSubLinks = driver.findElements(By.xpath("((//div[@class='card__details-footer'])[" + i
				+ "]//ul[@class='dp-cta-links-wrapper']/li)[" + j + "]/div//ul/li"));
		return dropDownSubLinks;
	}

	public WebElement dropDownSubLink(int i, int j, int k) {
		WebElement subLink = driver.findElement(By.xpath("(((//div[@class='card__details-footer'])[" + i
				+ "]//ul[@class='dp-cta-links-wrapper']/li)[" + j + "]/div//ul/li)[" + k + "]/a"));
		return subLink;
	}

	public WebElement cardFooterLink(int i, int j) {
		WebElement link = driver.findElement(By.xpath("((//div[@class='card__details-footer'])[" + i
				+ "]//ul[@class='dp-cta-links-wrapper']/li)[" + j + "]/a"));
		return link;

	}

}
