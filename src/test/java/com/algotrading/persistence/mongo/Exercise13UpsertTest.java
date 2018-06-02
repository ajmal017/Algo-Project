package com.algotrading.persistence.mongo;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;

import java.net.UnknownHostException;
import java.util.Collections;

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
public class Exercise13UpsertTest {
	private DB database;
	private DBCollection collection;

	// Upsert
	@Test
	public void shouldOnlyInsertDBObjectIfItDidNotExistWhenUpsertIsTrue() {
		// Given
		Person bob = new Person("bob", "Bob The Amazing", new Address("123 Fake St", "LondonTown", 1234567890),
				asList(27464, 747854));
		collection.insert(PersonAdaptor.toDBObject(bob));

		Person charlie = new Person("charlie", "Charles", new Address("74 That Place", "LondonTown", 1234567890),
				asList(1, 74));
		collection.insert(PersonAdaptor.toDBObject(charlie));

		// new person not in the database yet
		Person claire = new Person("claire", "Claire", new Address("1", "Town", 836558493),
				Collections.<Integer> emptyList());

		// When
		// TODO create query to find Claire by ID
		DBObject findClaire = new BasicDBObject("_id", "claire");
		// TODO Perform an update with this new person to show it does NOT get
		// added to the database
		WriteResult resultOfUpdate = collection.update(findClaire, PersonAdaptor.toDBObject(claire), false, false);

		// Then
		assertThat(resultOfUpdate.getN(), is(0));
		// without upsert this should not have been inserted
		assertThat(collection.find(findClaire)
				.count(), is(0));

		// When
		// TODO Perform an update with this new person to show it DOES get added
		// to the database
		WriteResult resultOfUpsert = collection.update(findClaire, PersonAdaptor.toDBObject(claire), true, false);

		// Then
		assertThat(resultOfUpsert.getN(), is(1));

		DBObject newClaireDBObject = collection.find(findClaire)
				.toArray()
				.get(0);
		// all values should have been updated to the new object values
		assertThat((String) newClaireDBObject.get("_id"), is(claire.getId()));
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
