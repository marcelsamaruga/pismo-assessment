package com.pismo.assessment;

import com.pismo.assessment.entity.Account;
import com.pismo.assessment.entity.OperationTypeEnum;
import com.pismo.assessment.repository.AccountRepository;
import com.pismo.assessment.repository.TransactionRepository;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.anyLong;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class TransactionControllerTests {

	@LocalServerPort
	private int port;

	@SpyBean
	private AccountRepository accountRepository;

	@SpyBean
	private TransactionRepository transactionRepository;

	@BeforeEach
	void setup() {
		RestAssured.port = port;
	}

	private final String DOCUMENT_NUMBER = "01928374756";

	@Test
	@DisplayName("GIVEN a credit bank operation WHEN there is a request to create a new transaction THEN it should create successfully and reduce the balance")
	public void it_should_create_a_credit_transaction_successfully() {
		final var account = accountRepository.save(
				Account.builder().documentNumber(DOCUMENT_NUMBER).build()
		);

		final BigDecimal amount = new BigDecimal(100);
		final OperationTypeEnum operationType = OperationTypeEnum.CREDIT_VOUCHER;

		var request = Map.of("account_id", account.getAccountId()
				, "operation_type_id", operationType.getId()
				, "amount", amount);

		var response =
			given()
				.header("Content-Type", "application/json")
				.and()
				.body(request)
				.when()
				.post("/transactions")
				.then()
				.statusCode(HttpStatus.CREATED.value())
				.extract()
				.response()
				.asString();

		int accountToEvaluate = JsonPath.from(response).get("account_id");
		int operationTypeToEvaluate = JsonPath.from(response).get("operation_type_id");
		BigDecimal amountToEvaluate = new BigDecimal(JsonPath.from(response).get("amount").toString());

		assertThat(accountToEvaluate).isEqualTo(account.getAccountId().intValue());
		assertThat(operationTypeToEvaluate).isEqualTo(operationType.getId().intValue());
		assertThat(amountToEvaluate).isEqualTo(amount);
	}

	@Test
	@DisplayName("GIVEN a debit bank operation WHEN there is a request to create a new transaction THEN it should create successfully and increase de balance")
	public void it_should_create_a_debit_transaction_successfully() {
		final var account = accountRepository.save(
				Account.builder().documentNumber(DOCUMENT_NUMBER).build()
		);

		final BigDecimal amount = new BigDecimal(100);
		final OperationTypeEnum operationType = OperationTypeEnum.WITHDRAWAL;

		var request = Map.of("account_id", account.getAccountId()
				, "operation_type_id", operationType.getId()
				, "amount", amount);

		var response =
				given()
						.header("Content-Type", "application/json")
						.and()
						.body(request)
						.when()
						.post("/transactions")
						.then()
						.statusCode(HttpStatus.CREATED.value())
						.extract()
						.response()
						.asString();

		int accountToEvaluate = JsonPath.from(response).get("account_id");
		int operationTypeToEvaluate = JsonPath.from(response).get("operation_type_id");
		BigDecimal amountToEvaluate = new BigDecimal(JsonPath.from(response).get("amount").toString());

		assertThat(accountToEvaluate).isEqualTo(account.getAccountId().intValue());
		assertThat(operationTypeToEvaluate).isEqualTo(operationType.getId().intValue());
		assertThat(amountToEvaluate).isEqualTo(amount.negate());
	}

	@Test
	@DisplayName("GIVEN an account id that does not exist WHEN there is a request to create a new transaction THEN not account should be found")
	public void it_should_throw_an_exception_when_account_is_not_found() {
		final Long invalidAccount = 999999L;

		var request = Map.of("account_id", invalidAccount
						   , "operation_type_id", OperationTypeEnum.CREDIT_VOUCHER.getId()
						   , "amount", new BigDecimal(100));

		given()
				.header("Content-Type", "application/json")
				.and()
				.body(request)
				.when()
				.post("/transactions")
				.then()
				.statusCode(HttpStatus.NOT_FOUND.value());
	}


	@Test
	@DisplayName("GIVEN an account id that does not exist WHEN there is a request to create a new transaction THEN not account should be found")
	public void it_should_throw_an_exception_when_operation_type_is_not_valid() {
		final Long invalidOperationType = 999999L;

		var request = Map.of("account_id", 1L
				, "operation_type_id", invalidOperationType
				, "amount", new BigDecimal(100));

		given()
				.header("Content-Type", "application/json")
				.and()
				.body(request)
				.when()
				.post("/transactions")
				.then()
				.statusCode(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	@DisplayName("GIVEN a server side error WHEN there is a request to create a new transaction THEN an exception should be thrown")
	public void it_should_throw_an_exception_when_an_server_error_happens() {
		BDDMockito.given(accountRepository.findById(anyLong())).willThrow(RuntimeException.class);

		var request = Map.of("account_id", 1L
				, "operation_type_id", OperationTypeEnum.CREDIT_VOUCHER.getId()
				, "amount", new BigDecimal(100));

		given()
				.header("Content-Type", "application/json")
				.and()
				.body(request)
				.when()
				.post("/transactions")
				.then()
				.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}

}
