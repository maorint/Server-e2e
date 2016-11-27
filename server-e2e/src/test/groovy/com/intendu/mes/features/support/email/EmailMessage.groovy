package com.intendu.mes.features.support.email

import groovy.transform.ToString

@ToString
class EmailMessage {

    String subject
    String from
    Object body
}
