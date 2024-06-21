package com.epam.ta.page;

import com.epam.ta.model.User;
import com.epam.ta.service.EmailVerifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends AbstractPage
{
	private final Logger logger = LogManager.getRootLogger();
	private final String PAGE_URL = "https://github.com/login";

	@FindBy(id = "login_field")
	private WebElement inputLogin;

	@FindBy(id = "password")
	private WebElement inputPassword;

	@FindBy(id = "otp")
	private WebElement emailVerificationCode;

	@FindBy(xpath = "//input[@value='Sign in']")
	private WebElement buttonSubmit;

	@FindBy(xpath = "(//input[@value='Ask me later'])[1]")
	private WebElement askMeLater;



	public LoginPage(WebDriver driver)
	{
		super(driver);
		PageFactory.initElements(this.driver, this);
	}

	@Override
	public LoginPage openPage()
	{
		driver.navigate().to(PAGE_URL);
		logger.info("Login page opened");
		return this;
	}

	public MainPage login(User user) throws Exception {
		inputLogin.sendKeys(user.getUsername());
		inputPassword.sendKeys(user.getPassword());
		buttonSubmit.click();
		Thread.sleep(3000);
//		String verificationCode = EmailVerifier.getContent();
//		emailVerificationCode.sendKeys(verificationCode);
//		if (askMeLater.isDisplayed()) {
//			askMeLater.click();
//		}
		logger.info("Login performed");
		return new MainPage(driver);
	}
}
