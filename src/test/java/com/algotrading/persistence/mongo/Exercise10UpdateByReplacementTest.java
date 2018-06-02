package com.algotrading.persistence.mongo;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;

import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.WriteResult;

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
public class Exercise10UpdateByReplacementTest {
	private DB database;
	private DBCollection collection;

	@Test
	public void shouldReplaceWholeDBObjectWithNewOne() {
		// Given
		Person bob = new Person("bob", "Bob The Amazing", new Address("123 Fake St", "LondonTown", 1234567890),
				asList(27464, 747854));
		collection.insert(PersonAdaptor.toDBObject(bob));

		Person charlie = new Person("charlie", "Charles", new Address("74 That Place", "LondonTown", 1234567890),
				asList(1, 74));
		collection.insert(PersonAdaptor.toDBObject(charlie));

		// When
		Person updatedCharlieObject = new Person("charlie", "Charles the Suave",
				new Address("A new street", "GreatCity", 7654321), Collections.<Integer> emptyList());
		// TODO create query to find Charlie by ID
		DBObject findCharlie = new BasicDBObject("_id", "charlie");
		// TODO do an update replacing the whole previous Document with the new
		// one
		WriteResult resultOfUpdate = collection.update(findCharlie, PersonAdaptor.toDBObject(updatedCharlieObject));

		// Then
		assertThat(resultOfUpdate.getN(), is(1));

		DBObject newCharlieDBObject = collection.find(findCharlie)
				.toArray()
				.get(0);
		// all values should have been updated to the new object values
		assertThat((String) newCharlieDBObject.get("_id"), is(updatedCharlieObject.getId()));
		assertThat((String) newCharlieDBObject.get("name"), is(updatedCharlieObject.getName()));
		assertThat((List<Integer>) newCharlieDBObject.get("books"), is(updatedCharlieObject.getBookIds()));
		DBObject address = (DBObject) newCharlieDBObject.get("address");
		assertThat((String) address.get("street"), is(updatedCharlieObject.getAddress()
				.getStreet()));
		assertThat((String) address.get("city"), is(updatedCharlieObject.getAddress()
				.getTown()));
		assertThat((int) address.get("phone"), is(updatedCharlieObject.getAddress()
				.getPhone()));
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