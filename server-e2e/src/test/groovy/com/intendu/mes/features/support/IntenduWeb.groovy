package com.intendu.mes.features.support

class IntenduWeb {
    public static SeleniumHelper seleniumHelper
    public static final String LOGIN_PATH = "/#/login/"
    public static final String HOMELOGIN_PATH = "/#/homelogin/"
    public static final String NEW_THERAPIST_PATH = "/#/admin/therapist/new"

    public static final String ORG_NAME_ID = "orgname";
    public static final String USER_NICKNAME_ID = "nickname"
    public static final String USER_PASSWORD_ID = "password"
    public static final String USER_EMAIL_ID = "email"

    public static final String LOGIN_BUTTON_XPATH = "//button[@type='submit']"
    public static final String TRAINING_LIST_XPATH = "//div[@class='pull-left title']"



    IntenduWeb(seleniumHelper) {
        this.seleniumHelper = seleniumHelper
    }
    def login(String organization, String nickname, String password) {
        seleniumHelper.gotoUrl(LOGIN_PATH)
        seleniumHelper.waitForElementId(ORG_NAME_ID)
        seleniumHelper.setTextFieldById(ORG_NAME_ID, organization)
        seleniumHelper.setTextFieldById(USER_NICKNAME_ID, nickname)
        seleniumHelper.setTextFieldById(USER_PASSWORD_ID, password)
        seleniumHelper.clickByXpath(LOGIN_BUTTON_XPATH)
        seleniumHelper.waitForXpath("//div/h1[text()='Choose player']")
    }
    def homelogin(String email, String password) {
        seleniumHelper.gotoUrl(HOMELOGIN_PATH)
        seleniumHelper.waitForElementId(USER_EMAIL_ID)
        seleniumHelper.setTextFieldById(USER_EMAIL_ID, email)
        seleniumHelper.setTextFieldById(USER_PASSWORD_ID, password)
        seleniumHelper.clickByXpath(LOGIN_BUTTON_XPATH)
    }


    def isDahsboardPage() {
        try {
            seleniumHelper.waitForXpath(TRAINING_LIST_XPATH)

            return true
        } catch (Exception e) {
            print e.toString()
            return false
        }
    }

    def registerTherapist() {
        seleniumHelper.gotoUrl(NEW_THERAPIST_PATH)
        seleniumHelper.waitForXpath("//div[@ng-controller='CreateTherapistCtrl']")

        SetTherapistEmail(getRandomMail())
    }
    def SetTherapistEmail(mailAddress) {
        seleniumHelper.setTextFieldByXpath("//input[@ng-model='\$select.search']", mailAddress)
        seleniumHelper.clickByXpath("//input[@ng-model='\$select.search']")
        sleep(1000)
        seleniumHelper.clickByXpath("//button[@ng-click='createNew()']")
    }

    def getRandomMail() {
        String basic_mail_address = "maor+%s@intendu.com"
        int randomDate = (int)(new Date().getTime() /1000F)%100000;
        return sprintf(basic_mail_address, randomDate)

    }

}
