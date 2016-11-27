package com.intendu.mes.features.support.rest

import groovy.json.JsonBuilder

class PutRestCall extends RestCall {

    PutRestCall(path, entity) {
        super("PUT", path)
        body = new JsonBuilder(entity).toPrettyString()
    }
}
