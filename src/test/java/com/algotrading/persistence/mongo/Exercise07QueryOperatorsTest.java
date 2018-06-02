package com.algotrading.persistence.mongo;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;

import java.net.UnknownHostException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
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
public class Exercise07QueryOperatorsTest {
	private DB database;
	private DBCollection collection;

	@Test
	public void shouldReturnADBObjectWithAPhoneNumberLessThan1000000000() {
		// Given
		Person charlie = new Person("charlie", "Charles", new Address("74 That Place", "LondonTown", 1234567890),
				asList(1, 74));
		collection.insert(PersonAdaptor.toDBObject(charlie));

		Person bob = new Person("bob", "Bob The Amazing", new Address("123 Fake St", "LondonTown", 987654321),
				asList(27464, 747854));
		collection.insert(PersonAdaptor.toDBObject(bob));

		// When
		// TODO build up a query which checks the numeric value
		DBObject query = new BasicDBObject("address.phone", new BasicDBObject("$lt", 1000000000));
		// TODO use this query to get a List of matching Documents from the
		// database
		DBCursor results = collection.find(query);

		// Then
		assertThat(results.size(), is(1));
		assertThat((String) results.next()
				.get("_id"), is(bob.getId()));
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