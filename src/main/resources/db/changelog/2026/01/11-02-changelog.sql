-- liquibase formatted sql

-- changeset xumoyuddin:1768152998629-6
ALTER TABLE _account DROP CONSTRAINT fk__account_on_user;

-- changeset xumoyuddin:1768152998629-7
ALTER TABLE _transaction DROP CONSTRAINT fk_transaction_from_account;

-- changeset xumoyuddin:1768152998629-8
ALTER TABLE _transaction DROP CONSTRAINT fk_transaction_to_account;

-- changeset xumoyuddin:1768152998629-2
ALTER TABLE account
    ADD CONSTRAINT uc_account_account_number UNIQUE (account_number);

-- changeset xumoyuddin:1768152998629-3
ALTER TABLE account
    ADD CONSTRAINT FK_ACCOUNT_ON_USER FOREIGN KEY (user_id) REFERENCES _user (id);

-- changeset xumoyuddin:1768152998629-4
ALTER TABLE _transaction
    ADD CONSTRAINT FK_TRANSACTION_FROM_ACCOUNT FOREIGN KEY (from_account_id) REFERENCES account (id);

-- changeset xumoyuddin:1768152998629-5
ALTER TABLE _transaction
    ADD CONSTRAINT FK_TRANSACTION_TO_ACCOUNT FOREIGN KEY (to_account_id) REFERENCES account (id);

-- changeset xumoyuddin:1768152998629-10
DROP TABLE _account CASCADE;

-- changeset xumoyuddin:1768152998629-1
ALTER TABLE account ALTER COLUMN balance TYPE DECIMAL USING (balance::DECIMAL);

