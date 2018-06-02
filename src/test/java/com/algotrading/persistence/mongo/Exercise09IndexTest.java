package com.algotrading.persistence.mongo;

import static org.hamcrest.CoreMatchers.is;

import java.net.UnknownHostException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Exercise can be found in
 * https://github.com/trishagee/mongodb-getting-started/tree/master/src/test/
 * java/com/mechanitis/mongodb/gettingstarted
 * 
 * Java driver tutorial:
 * https://www.mongodb.com/blog/post/getting-started-with-mongodb-and-java-part-
 * i
 * 
 * https://www.mongodb.com/blog/post/getting-started-with-mongodb-and-java-part-
 * ii
 */
public class Exercise09IndexTest {
	private DB database;
	private DBCollection collection;

	@Test
	public void shouldCreateAnAscendingIndex() {
		// given
		collection.insert(new BasicDBObject("fieldToIndex", "Bob"));

		// when
		// TODO: added the index to the collection
		collection.createIndex(new BasicDBObject("fieldToIndex", 1));

		// then
		DBObject indexKey = (DBObject) collection.getIndexInfo()
				.get(1)
				.get("key");
		assertTrue(indexKey.keySet()
				.contains("fieldToIndex"));
		assertThat((Integer) indexKey.get("fieldToIndex"), is(1));
		assertThat((String) collection.getIndexInfo()
				.get(1)
				.get("name"), is("fieldToIndex_1"));
	}

	@Before
	public void setUp() throws UnknownHostException {
		MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
		database = mongoClient.getDB("Examples");
		collection = database.getCollection("people");
	}

	@After
	public void tearDown() {
		database.dropDatabase();
	}
}