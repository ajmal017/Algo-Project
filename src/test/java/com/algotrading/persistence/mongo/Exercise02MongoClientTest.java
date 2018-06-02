package com.algotrading.persistence.mongo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import java.net.UnknownHostException;

import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import static org.junit.Assert.assertThat;

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
public class Exercise02MongoClientTest {
	@Test
	public void shouldGetADatabaseFromTheMongoClient() throws Exception {
		// Given
		MongoClient mongoClient = new MongoClient();

		// When
		// TODO get the database from the client
		DB database = mongoClient.getDB("TheDatabaseName");

		// Then
		assertThat(database, is(notNullValue()));
	}

	@Test
	public void shouldGetACollectionFromTheDatabase() throws Exception {
		// Given
		// TODO any setup
		MongoClient mongoClient = new MongoClient();
		DB database = mongoClient.getDB("TheDatabaseName");

		// When
		// TODO get collection
		DBCollection collection = database.getCollection("TheCollectionName");

		// Then
		assertThat(collection, is(notNullValue()));
	}

	@Test(expected = Exception.class)
	public void shouldNotBeAbleToUseMongoClientAfterItHasBeenClosed() throws UnknownHostException {
		// Given
		MongoClient mongoClient = new MongoClient();

		// When
		// TODO close the mongoClient
		mongoClient.close();

		// Then
		mongoClient.getDB("SomeDatabase")
				.getCollection("coll")
				.insert(new BasicDBObject("field", "value"));
	}

}