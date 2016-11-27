package com.intendu.mes.features.support.rest;


class DeleteRestCall extends RestCall {

    DeleteRestCall(path, query = [:]) {
        super("DELETE", path, query)
    }
}