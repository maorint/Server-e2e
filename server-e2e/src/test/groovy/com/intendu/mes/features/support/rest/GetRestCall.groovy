package com.intendu.mes.features.support.rest

class GetRestCall extends RestCall {

    GetRestCall(path, query = [:]) {
        super("GET", path, query)
    }

}
