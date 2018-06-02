package com.algotrading.persistence.mongo;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;

import java.net.UnknownHostException;
import java.util.List;

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
public class Exercise06SelectFieldsTest {
	private DB database;
	private DBCollection collection;

	@Test
	public void shouldFindAllDBObjectsWithTheNameCharlesAndOnlyReturnNameAndId() {
		// Given
		Person charlie = new Person("charlie", "Charles", new Address("74 That Place", "LondonTown", 1234567890),
				asList(1, 74));
		collection.insert(PersonAdaptor.toDBObject(charlie));

		Person bob = new Person("bob", "Bob The Amazing", new Address("123 Fake St", "LondonTown", 1234567890),
				asList(27464, 747854));
		collection.insert(PersonAdaptor.toDBObject(bob));

		// When
		// TODO create the correct query to find Charlie by name (see above)
		DBObject query = new BasicDBObject("name", "Charles");
		// TODO use this query, combined with the "fields" selector, to get a
		// list of result documents with only the name and ID fields
		BasicDBObject projection = new BasicDBObject("name", 1); // To only
																	// display
																	// name
		DBCursor results = collection.find(query, projection);

		// Then
		assertThat(results.size(), is(1));
		DBObject theOnlyResult = results.next();
		assertThat((String) theOnlyResult.get("_id"), is(charlie.getId()));
		assertThat((String) theOnlyResult.get("name"), is(charlie.getName()));
		assertThat(theOnlyResult.get("address"), is(nullValue()));
		assertThat(theOnlyResult.get("books"), is(nullValue()));
	}

	// BONUS
	@Test
	public void shouldFindAllDBObjectsWithTheNameCharlesAndExcludeAddressInReturn() {
		// Given
		Person charlie = new Person("charlie", "Charles", new Address("74 That Place", "LondonTown", 1234567890),
				asList(1, 74));
		collection.insert(PersonAdaptor.toDBObject(charlie));

		Person bob = new Person("bob", "Bob The Amazing", new Address("123 Fake St", "LondonTown", 1234567890),
				asList(27464, 747854));
		collection.insert(PersonAdaptor.toDBObject(bob));

		// When
		// TODO create the correct query to find Charlie by name (see above)
		DBObject query = new BasicDBObject("name", "Charles");
		// TODO use this query, combined with the "fields" selector, to get a
		// list of result documents without address subdocument
		BasicDBObject projection = new BasicDBObject("address", 0); // To
																	// exclude
																	// address
																	// field
		DBCursor results = collection.find(query, projection);

		// Then
		assertThat(results.size(), is(1));
		DBObject theOnlyResult = results.next();
		assertThat((String) theOnlyResult.get("_id"), is(charlie.getId()));
		assertThat((String) theOnlyResult.get("name"), is(charlie.getName()));
		assertThat(theOnlyResult.get("address"), is(nullValue()));
		assertThat((List<Integer>) theOnlyResult.get("books"), is(charlie.getBookIds()));
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