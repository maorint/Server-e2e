package com.intendu.mes.features.support.rest

import groovy.json.JsonBuilder

class PostRestCall extends RestCall {

    PostRestCall(path, entity) {
        super("POST", path)
        body = new JsonBuilder(entity).toPrettyString()
    }
    PostRestCall(path) {
        super("POST", path)
    }

}
