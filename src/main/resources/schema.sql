CREATE TABLE account (
    account_id INTEGER NOT NULL AUTO_INCREMENT,
    document_number VARCHAR(11) NOT NULL,
    PRIMARY KEY (account_id)
);

CREATE TABLE transactions (
    transaction_id INTEGER NOT NULL AUTO_INCREMENT,
    account_id INTEGER NOT NULL,
    operation_type INTEGER NOT NULL,
    amount NUMERIC NOT NULL,
    event_date TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (transaction_id),
    FOREIGN KEY (account_id) REFERENCES account(account_id)
);