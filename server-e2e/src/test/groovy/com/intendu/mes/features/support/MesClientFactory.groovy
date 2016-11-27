package com.intendu.mes.features.support

import com.intendu.mes.features.support.entities.LoginRequest
import com.intendu.mes.features.support.rest.GetRestCall
import com.intendu.mes.features.support.rest.PostRestCall
import com.intendu.mes.features.support.rest.RestCallExecutor
import com.intendu.mes.features.support.rest.RestResponse

import groovy.json.JsonSlurper

class MesClientFactory {

    static final String SIGNIN_PATH = "v1/auth/signin/credentials";

    def domain
    def RestCallExecutor restExecutor
    def RestCallExecutor noDomainRestExecutor

    MesClientFactory(domain) {
        this.restExecutor = new RestCallExecutor(domain)
        this.noDomainRestExecutor = new RestCallExecutor()
        this.domain = domain;

    }
    RestMesClient organization(organization, username, password) {
        createMesClientFrom(SIGNIN_PATH, new LoginRequest(nickname: username, orgname: organization, password: password))
    }


    private def createMesClientFrom(path, loginRequest) {
        PostRestCall call = new PostRestCall(
                path,
                loginRequest)
        call.setHeaders(["Content-Type": "application/json"])
        RestResponse res = restExecutor.execute(call)
        assert res.statusCode/100 == 2, res.body
        def json = new JsonSlurper().parseText(res.body)
        restExecutor.setToken(json.token.id)

        return new RestMesClient(restExecutor)
    }



}