package com.intendu.mes.features.support.services

import com.mongodb.*
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.bson.conversions.Bson
import org.bson.Document

class MongoService {
    private static MongoClient mongoClient
    private static host = "localhost"    //your host name
    private static port = 27017      //your port no.
    private static databaseName = "PHI-qa"
    private static MongoDatabase db
    private static MongoService instance

    private MongoService() {
        mongoClient = new MongoClient(host,port)
        db = mongoClient.getDatabase(databaseName)
    }
    public static MongoService client() {
        if (instance == null) {
            instance = new MongoService()
        }
        return instance
    }

    public long removeByInternalId(String internalId) {
        MongoCollection table = db.getCollection("blocks")
        Bson filter = new Document("internalId", internalId).append("isTest", true)

        table.deleteMany(filter).getDeletedCount()
    }
}