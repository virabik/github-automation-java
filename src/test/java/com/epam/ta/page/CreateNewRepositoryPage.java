package com.epam.ta.page;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CreateNewRepositoryPage extends AbstractPage
{
	private final String BASE_URL = "http://www.github.com/new";
	private final Logger logger = LogManager.getRootLogger();

	@FindBy(id = ":r4:")
	private WebElement inputRepositoryName;

	@FindBy(id = ":r5:")
	private WebElement inputRepositoryDescription;

	@FindBy(xpath = "//span[contains(text(), 'Create repository')]")
	private WebElement buttonCreate;

	private final By labelEmptyRepoSetupOptionLocator = By.xpath("//h3/strong[text()='Quick setup']");

	@FindBy(xpath = "//a[@data-pjax='#repo-content-pjax-container' and contains(text(), 'testRepo')]")
	private WebElement linkCurrentRepository;

	public CreateNewRepositoryPage(WebDriver driver)
	{
		super(driver);
		PageFactory.initElements(this.driver, this);
	}

	public boolean isCurrentRepositoryEmpty()
	{
		WebElement labelEmptyRepoSetupOption = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
				.until(ExpectedConditions.presenceOfElementLocated(labelEmptyRepoSetupOptionLocator));
		return labelEmptyRepoSetupOption.isDisplayed();
	}

	public CreateNewRepositoryPage createNewRepository(String repositoryName, String repositoryDescription) throws InterruptedException {
		inputRepositoryName.sendKeys(repositoryName);
		inputRepositoryDescription.sendKeys(repositoryDescription);
		Thread.sleep(1000);
		buttonCreate.click();
		Thread.sleep(1000);
		logger.info("Created repository with name: [" + repositoryName +
				"[ and description: [" + repositoryDescription + "]");
		return this;
	}

	public String getCurrentRepositoryName() throws InterruptedException {
		Thread.sleep(1000);
		return linkCurrentRepository.getText();
	}

	@Override
	public CreateNewRepositoryPage openPage()
	{
		driver.navigate().to(BASE_URL);
		return this;
	}

}
