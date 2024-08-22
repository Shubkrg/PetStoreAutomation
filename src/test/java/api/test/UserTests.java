package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;
import junit.framework.Assert;

//UserEndPints.java
//Created for perform Create, Read, Update, Delete requests the user API.
//java-faker is designed to generate fake data for fields
//in an object based on annotations and configurations.

public class UserTests {

	Faker faker;
	User userpayload;

	public Logger logger;

	@BeforeClass
	public void setup()

	{

		faker = new Faker();
		userpayload = new User();

		userpayload.setId(faker.idNumber().hashCode());
		userpayload.setUsername(faker.name().username());
		userpayload.setFirstName(faker.name().firstName());
		userpayload.setLastName(faker.name().lastName());
		userpayload.setEmail(faker.internet().safeEmailAddress());
		userpayload.setPassword(faker.internet().password());
		userpayload.setPhone(faker.phoneNumber().cellPhone());

		// logs
		logger = LogManager.getLogger(this.getClass());
		logger.debug("debugging....");

	}

	@Test(priority = 1)
	public void testPostUser() {

		logger.info("*******Creating User*******");
		Response response = UserEndPoints.createUser(userpayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("*******User is created*******");

	}

	@Test(priority = 2)
	public void testGetUserByName() {

		logger.info("*******Reading Userinfo*******");
		Response response = UserEndPoints.readUser(this.userpayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("*******User info displayed*******");
	}

	@Test(priority = 3)
	public void testUpdateUserByName() {

		logger.info("*******Updated User*******");
		// update data using payload
		userpayload.setFirstName(faker.name().firstName());
		userpayload.setLastName(faker.name().lastName());
		userpayload.setEmail(faker.internet().safeEmailAddress());
		Response response = UserEndPoints.updateUser(this.userpayload.getUsername(), userpayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("*******User Updated  *******");

		// Checking data after update

		Response responseAfterupdate = UserEndPoints.readUser(this.userpayload.getUsername());
//		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);

	}

	// Delete
	@Test(priority = 4)

	public void testDeleteUserByName() {
		logger.info("*******Deleting User*******");
		Response response = UserEndPoints.deleteUser(this.userpayload.getUsername());

		logger.info("*******User Deleted*******");
	}

}
