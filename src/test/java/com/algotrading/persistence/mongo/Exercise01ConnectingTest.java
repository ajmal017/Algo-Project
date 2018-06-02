package com.algotrading.persistence.mongo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import org.junit.Test;

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
public class Exercise01ConnectingTest {
	@Test
	public void shouldCreateANewMongoClientConnectedToLocalhost() throws Exception {
		// When
		// TODO: get/create the MongoClient
		MongoClient mongoClient = new MongoClient();

		// Then
		assertThat(mongoClient, is(notNullValue()));
	}
}