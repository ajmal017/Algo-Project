package com.algotrading.persistence.mongo;

import static org.hamcrest.CoreMatchers.is;

import java.net.UnknownHostException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
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
public class Exercise08SkipAndLimitTest {
	private DB database;
	private DBCollection collection;

	@Test
	public void shouldReturnDBObjects3to9Of20DBObjectsUsingSkipAndLimit() {
		// Given
		for (int i = 0; i < 20; i++) {
			collection.insert(new BasicDBObject("name", "person" + i).append("someIntValue", i));
		}

		// When
		// TODO no need for a query, just combine the find with the other
		// operators available
		DBCursor results = collection.find(new BasicDBObject())
				.skip(3)
				.limit(7);

		// Then
		assertThat(results.size(), is(7));
		assertThat((int) results.next()
				.get("someIntValue"), is(3));
		assertThat((int) results.next()
				.get("someIntValue"), is(4));
		assertThat((int) results.next()
				.get("someIntValue"), is(5));
		assertThat((int) results.next()
				.get("someIntValue"), is(6));
		assertThat((int) results.next()
				.get("someIntValue"), is(7));
		assertThat((int) results.next()
				.get("someIntValue"), is(8));
		assertThat((int) results.next()
				.get("someIntValue"), is(9));
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