package com.intendu.mes.features

import com.intendu.mes.features.support.entities.UserInfo

this.metaClass.mixin(cucumber.api.groovy.Hooks)
this.metaClass.mixin(cucumber.api.groovy.EN)


def organization
def username
def password

Given(~/^I'm an organization manager$/) { ->
    organization = ORGANIZATION_NAME
    username = ORGANIZATION_USER
    password = ORGANIZATION_PASSWORD
}

When(~/^I log into intendu server$/) { ->
    restClient = mesClientFactory.organization(organization, username, password)
}
Then(~/^I should get my user info$/) { ->
    UserInfo user = restClient.getLoggedinUser()
    assert user.user.nickname == username
    assert user.organization.name == organization
}

Given(~/^I'm logged in player in the system$/) { ->
    restClient = mesClientFactory.organization(ORGANIZATION_NAME, ORGANIZATION_USER, ORGANIZATION_PASSWORD)

}
Given(~/^I started a new game session$/) { ->
    restClient.createGameSession(PLAYER_ID)
}


Given(~/^I'm a player$/) { ->
    organization = PLAYER_ORG
    username = PLAYER_NICKNAME
    password = PLAYER_PASSWORD
    email = PLAYER_EMAIL
}

When(~/^I log into intendu server on login page/) { ->
    intenduWeb.login(organization, username, password)
}

When(~/^I log into intendu server on home login page/) { ->
    intenduWeb.homelogin(email, password)
}

Then(~/^I get dashboard screen/) { ->
    assert intenduWeb.isDahsboardPage()
}


