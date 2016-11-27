package com.intendu.mes.features.support.rest

class RestCall {

    String method
    def headers = [:]
    String path
    String body = ""
    def query = [:]

    RestCall(method, path, query = [:]) {
        this.method = method
        this.path = path
        this.query = query
    }

}
