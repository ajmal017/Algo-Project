package com.algotrading.persistence.mongo;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;

import java.net.UnknownHostException;

import org.junit.Test;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

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
public class Exercise03InsertTest {
	@Test
	public void shouldTurnAPersonIntoADBObject() {
		// Given
		Person bob = new Person("bob", "Bob The Amazing", new Address("123 Fake St", "LondonTown", 1234567890),
				asList(27464, 747854));

		// When
		DBObject bobAsDBObject = PersonAdaptor.toDBObject(bob);

		// Then
		String expectedDBObject = "{" + " \"_id\" : \"bob\" ," + " \"name\" : \"Bob The Amazing\" ,"
				+ " \"address\" : {" + " \"street\" : \"123 Fake St\" ," + " \"city\" : \"LondonTown\" ,"
				+ " \"phone\" : 1234567890" + "} ," + " \"books\" : [ 27464 , 747854]" + "}";
		assertThat(bobAsDBObject.toString(), is(expectedDBObject));
	}

	@Test
	public void shouldBeAbleToSaveAPerson() throws UnknownHostException {
		// Given
		MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
		DB database = mongoClient.getDB("Examples");
		DBCollection collection = database.getCollection("people");

		Person charlie = new Person("charlie", "Charles", new Address("74 That Place", "LondonTown", 1234567890),
				asList(1, 74));

		// When
		// TODO: insert Charlie into the collection
		collection.insert(PersonAdaptor.toDBObject(charlie));

		// Then
		assertThat(collection.find()
				.count(), is(1));

		// Clean up
		database.dropDatabase();
	}
}