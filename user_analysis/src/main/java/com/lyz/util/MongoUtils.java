package com.lyz.util;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * @Author: liyuzhan
 * @classDesp： Mongo工具类
 * @Date: 2019/12/31 16:00
 * @Email: 1031759184@qq.com
 */
public class MongoUtils {
    private static MongoClient mongoClient = new MongoClient("192.168.153.178", 27017);


    public static Document findOneBy(String tableName, String dataBase, String yearBaseType) {
        MongoDatabase mongoDatabase = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(tableName);
        Document doc = new Document();
        doc.put("info", yearBaseType);
        FindIterable<Document> itRer = mongoCollection.find(doc);
        MongoCursor<Document> mongoCursor = itRer.iterator();
        if (mongoCursor.hasNext()) {
            return mongoCursor.next();
        } else {
            return null;
        }
    }


    public static void saveOrUpdateMongo(String tableName, String dataBase, Document doc) {
        MongoDatabase mongoDatabase = mongoClient.getDatabase(dataBase);
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(tableName);
        String key = "_id";
        if (!doc.containsKey(key)) {
            ObjectId objectid = new ObjectId();
            doc.put(key, objectid);
            mongoCollection.insertOne(doc);
            return;
        }
        Document matchDocument = new Document();
        String objectId = doc.get(key).toString();
        matchDocument.put(key, new ObjectId(objectId));
        FindIterable<Document> findIterable = mongoCollection.find(matchDocument);
        if (findIterable.iterator().hasNext()) {
            mongoCollection.updateOne(matchDocument, new Document("$set", doc));
            try {
                System.out.println("come into saveOrUpdateMongo ---- update---" + JSONObject.toJSONString(doc));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            mongoCollection.insertOne(doc);
            try {
                System.out.println("come into saveOrUpdateMongo ---- insert---" + JSONObject.toJSONString(doc));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
