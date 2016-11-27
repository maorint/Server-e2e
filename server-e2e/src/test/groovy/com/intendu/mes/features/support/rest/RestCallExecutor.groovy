package com.intendu.mes.features.support.rest

import com.google.common.base.Strings
import groovyx.net.http.RESTClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.methods.HttpPut
import org.apache.http.client.methods.HttpDelete
import org.apache.http.client.methods.HttpRequestBase
import org.apache.http.conn.ssl.NoopHostnameVerifier
import org.apache.http.conn.ssl.SSLConnectionSocketFactory
import org.apache.http.entity.ByteArrayEntity
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.impl.client.HttpClients
import org.apache.http.ssl.SSLContexts

class RestCallExecutor {

    def domain = ""

//    def sslFactory = new SSLConnectionSocketFactory(
//            SSLContexts.createDefault(), SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
    //def client = HttpClientBuilder.create().setSSLSocketFactory(sslFactory).build()

    def client = HttpClients.createDefault() // .custom().setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();
    def token

    final Map methodToRequest = [
            "POST": { rc -> createPost(rc) },
            "GET": { rc -> createGet(rc) },
            "PUT": { rc -> createPut(rc) },
            "DELETE": { rc -> createDelete(rc) }
    ]

    def createGet(restCall) {
        return new HttpGet()
    }

    def createDelete(restCall) {
        return new HttpDelete()
    }

    def createPut(restCall) {
        return new HttpPut()
    }

    def createPost(restCall) {
        HttpPost request = new HttpPost()
        request.setEntity(new ByteArrayEntity(restCall.getBody().getBytes("UTF-8")))
        return request
    }

    RestCallExecutor(domain) {
        this.domain = domain
        //this.client = new RESTClient(domain)
    }

    RestCallExecutor() {
    }

    RestResponse execute(restCall) {
        HttpRequestBase httpRequest = createRequestFrom(restCall)
        httpRequest.setURI(URI.create(createUrl(restCall)))
        restCall.headers.each { it -> httpRequest.setHeader(it.key, it.value) }

        if (!Strings.isNullOrEmpty(restCall.body)) {
            httpRequest.addHeader("Content-Type", "application/json")
        }

        if (token) {
            httpRequest.addHeader("X-Auth-Token", token)
        }

        return new RestResponse(client.execute(httpRequest))
    }

    def createUrl(restCall) {
        if(!domain.isEmpty()){
            return "${domain}/${createPathFrom(restCall)}"
        }else{
            return "${createPathFrom(restCall)}"
        }
    }

    def createPathFrom(restCall) {
        StringBuilder requestUrl = new StringBuilder(restCall.path as String)
        if(!restCall.query.isEmpty()) {
            requestUrl.append("?")
            requestUrl.append(RestUtil.createParamsStringFrom(restCall.query))
        }
        requestUrl.toString()
    }

    /*def createUrl(restCall) {
        StringBuilder requestUrl = new StringBuilder("${domain}/${restCall.path}")
        requestUrl.append("?")
        requestUrl.append(RestUtil.createParamsStringFrom(restCall.query))
        requestUrl.toString()
    }*/

    HttpRequestBase createRequestFrom(restCall) {
        Closure creator = methodToRequest[restCall.getMethod()]
        creator.call(restCall)
    }

}
