package com.intendu.mes.features.support

import com.intendu.mes.features.support.entities.Block


class MyWorld {


    def mesDomain = System.getProperty('serverUrl', 'http://localhost:9000')

    def ORGANIZATION_NAME = System.getProperty('serverUrl', "TestOrg")
    def ORGANIZATION_USER = System.getProperty('serverUrl', "nickname")
    def ORGANIZATION_PASSWORD = System.getProperty('serverUrl', "!123Abc")
    def PLAYER_ID = "581ef90a8c2984890081773b"

    def PLAYER_ORG = System.getProperty('playerOrganization', "TestOrg")
    def PLAYER_NICKNAME = System.getProperty('playerNickname', "playerfortest")
    def PLAYER_PASSWORD = System.getProperty('playerPassword', "Te\$t123")
    def PLAYER_EMAIL = System.getProperty('playerEmail', "maor@intendu.com")

    ArrayList<Block> blocks = new ArrayList<>()

    MesClientFactory mesClientFactory = new MesClientFactory(mesDomain)

    RestMesClient restClient

    def intenduDomain = System.getProperty("intenduDomain", "http://localhost:9000/");

    def IntenduWeb intenduWeb = new IntenduWeb(new SeleniumHelper(intenduDomain))


}
