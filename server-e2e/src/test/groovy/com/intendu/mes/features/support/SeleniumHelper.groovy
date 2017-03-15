package com.intendu.mes.features.support

import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.interactions.Actions

class SeleniumHelper {
    private String domain
    def WebDriver driver;

    SeleniumHelper(String domain) {
        this.domain = domain;
    }

    def start() {
        //driver = new FirefoxDriver()
        driver = new ChromeDriver()
        return this
    }

    def terminate() {
        if (driver != null)
            driver.quit()
        else
            driver.close()
   }

    def waitForElementId(String elemId) {
        int waitTimeout = 10
        for (int i=0;i < waitTimeout;i++) {
            if (driver.findElements(By.id(elemId)).size() > 0)
                return
            sleep(500)
        }
        throw new Exception("Failed to find element " + elemId)
    }

    def waitForXpath(String xpath) {
        int waitTimeout = 10
        for (int i=0;i < waitTimeout;i++) {
            if (driver.findElements(By.xpath(xpath)).size() > 0)
                return
            sleep(500)
        }
        throw new Exception("Failed to find xpath " + xpath)
    }

    def setTextFieldById(String fieldId, String content) {
        driver.findElement(By.id(fieldId)).sendKeys(content)
    }
    def setTextFieldByXpath(String xpath, String content) {
        driver.findElement(By.xpath(xpath)).sendKeys(content)
    }

    def clickByXpath(String xpath) {
        driver.findElement(By.xpath(xpath)).click()
    }

    def setBodyField(String bodyText) {
        // From: http://stackoverflow.com/questions/20069737/how-to-identify-and-switch-to-the-frame-in-selenium-webdriver-when-frame-does-no
        def iframeElement = driver.findElement(By.cssSelector("iframe[title='Rich Text Editor, Body field']"))
        driver.switchTo().frame(iframeElement);
        // From: http://stackoverflow.com/questions/28814916/how-do-i-select-elements-inside-an-iframe-with-xpath
        def bodyElement = driver.findElement(By.xpath(".//p"))
        def hh = bodyElement.getSize().height.toString()
        // From: http://stackoverflow.com/questions/19312958/java-selenium-webdriver-enter-the-text-into-the-text-area
        bodyElement.sendKeys(Keys.TAB, bodyText)

        driver.switchTo().defaultContent();
    }


    def selectRadioButton(String fieldId) {
        //driver.findElement(By.xpath("//div[@id='" + fieldId + "' ]/input"))
        driver.findElement(By.id(fieldId)).click()
    }

    def selectItemById(String fieldId) {
        //driver.findElement(By.xpath("//div[@id='" + fieldId + "' ]/input"))
//        driver.findElement(By.id(fieldId)).selec
    }
    def scrollToItem(String fieldId) {
        def element = driver.findElement(By.id(fieldId))
        Actions actions = new Actions(driver);
        actions.moveToElement(element)
        actions.perform()
    }


    def getCurrentUrl() {
        return driver.currentUrl.substring(domain.length()-1)
    }

    def getItemIdFromUrl(){
        // Assume
        def currentUrl = getCurrentUrl()
        assert currentUrl.startsWith(SUCCESSFUL_CREATION_URL_PREFIX)
        return currentUrl.substring(SUCCESSFUL_CREATION_URL_PREFIX.length())
    }

    def gotoUrl(String url) {
        driver.get(domain + url)
        //TODO: add explicit wait
        sleep(500)
        //preventConfirmToLeaveDialog()
    }
}
