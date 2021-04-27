package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class HomePage {

    @FindBy(css = "#submit-button")
    private WebElement submitButton;

    //@FindBy(className = "???")
    //WebElement class????Title;

    //@FindBy(id = "edit-credential-url")
    //WebElement credentialEditURLfield;

    public HomePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void logout() {
        this.submitButton.click();
    }

    public String getEncryptedCredPW(String username, CredentialService credentialService) {
        int usernameInt = Integer.parseInt(username); //Username name is hardcoded to 1 in tests, so just convert to int and pass in
        List<Credential> cred = credentialService.getCredentials(usernameInt);
        System.out.println("Now in getEncryptedCredPW method, the ENcrypted Password = " + cred.get(0).getPassword());
        return cred.get(0).getPassword();
    }
}





