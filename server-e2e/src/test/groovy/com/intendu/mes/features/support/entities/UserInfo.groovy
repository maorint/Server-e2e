package com.intendu.mes.features.support.entities

class UserInfo {

    Token token
    User user
    Organization organization

    class Token {
        String id
        String expiresOn
    }
    class User {
        String _id
        String creationDate
        String organizationID
        String email
        String isEmailConfirmed
        String nickname
        String name
        String surname
        Object roles
        String archived
    }
    class Organization {
        String _id
        String name
        String timeZoneID
        String address
        String quota
        String explorationOrder
    }

}
