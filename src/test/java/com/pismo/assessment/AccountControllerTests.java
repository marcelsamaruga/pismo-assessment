package com.pismo.assessment;

import com.pismo.assessment.entity.Account;
import com.pismo.assessment.repository.AccountRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class AccountControllerTests {

	@LocalServerPort
	private int port;

	@SpyBean
	private AccountRepository repository;

	@BeforeEach
	void setup() {
		RestAssured.port = port;
	}

	private final String DOCUMENT_NUMBER = "12345678901";

	@Test
	@DisplayName("GIVEN a document number WHEN a request to create an account is invoked THEN an account is created")
	public void it_should_create_account_successfully() {
		var account = Map.of("document_number", DOCUMENT_NUMBER);

		given()
			.header("Content-Type", "application/json")
			.and()
			.body(account)
			.when()
			.post("/accounts")
			.then()
			.statusCode(HttpStatus.CREATED.value())
			.body("account_id", notNullValue());
	}

	@Test
	@DisplayName("GIVEN an account id WHEN there is a request to get account details THEN account details is retrieved")
	public void it_should_get_an_account() {
		final Integer accountId = 1;

		repository.save(Account.builder().documentNumber(DOCUMENT_NUMBER).build());

		given()
				.header("Content-Type", "application/json")
				.and()
				.pathParams("accountId", accountId)
				.when()
				.get("/accounts/{accountId}")
				.then()
				.statusCode(HttpStatus.OK.value())
				.body("account_id", equalTo(accountId))
				.body("document_number", equalTo(DOCUMENT_NUMBER));
	}

	@Test
	@DisplayName("GIVEN an account id that does not exist WHEN there is a request to get account details THEN not account should be found")
	public void it_should_get_an_account_not_found() {
		given()
				.header("Content-Type", "application/json")
				.and()
				.pathParams("accountId", -1)
				.when()
				.get("/accounts/{accountId}")
				.then()
				.statusCode(HttpStatus.NOT_FOUND.value());
	}

}
