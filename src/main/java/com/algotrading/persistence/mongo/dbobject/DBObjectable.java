package com.algotrading.persistence.mongo.dbobject;

import com.mongodb.DBObject;

/**
 * Please implements this interface for the object who will become DBObject in
 * mongodb format.
 */
public interface DBObjectable {

	public DBObject toDBObject();

}
