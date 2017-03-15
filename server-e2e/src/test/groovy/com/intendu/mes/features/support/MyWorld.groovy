package com.intendu.mes.features.support

import com.intendu.mes.features.support.entities.Block


class MyWorld {


    def mesDomain = System.getProperty('serverUrl', 'http://localhost:9001')

    def ORGANIZATION_NAME = System.getProperty('adminOrganization', "TestOrg")
    def ORGANIZATION_USER = System.getProperty('adminUsername', "nickname")
    def ORGANIZATION_PASSWORD = System.getProperty('adminPassword', "!123Abc")
    def THERAPIST_ID = "581ef90a8c2984890081773b"
    def PLAYER_ID = "58107eb08c29846d00577b2d"

    def PLAYER_ORG = System.getProperty('playerOrganization', "intendu")
    def PLAYER_NICKNAME = System.getProperty('playerUsername', "playerfortest")
    def PLAYER_PASSWORD = System.getProperty('playerPassword', "\$portVU10")
    def PLAYER_EMAIL = System.getProperty('playerEmail', "maor+987@intendu.com")
//    def PLAYER_PASSWORD = System.getProperty('playerPassword', "bXnDdV9H")
//    def PLAYER_EMAIL = System.getProperty('playerEmail', "maor+22@intendu.com")

    ArrayList<Block> blocks = new ArrayList<>()

    MesClientFactory mesClientFactory = new MesClientFactory(mesDomain)

    RestMesClient restClient

    def intenduDomain = System.getProperty("intenduDomain", "http://qa-web.intendu.com");

    def IntenduWeb intenduWeb = new IntenduWeb(new SeleniumHelper(intenduDomain))


}
