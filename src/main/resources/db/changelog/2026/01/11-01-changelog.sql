-- liquibase formatted sql

-- changeset xumoyuddin:1768152645883-1
ALTER TABLE _account DROP CONSTRAINT fk__account_on_user;

-- changeset xumoyuddin:1768152645883-2
ALTER TABLE _transaction DROP CONSTRAINT fk_transaction_from_account;

-- changeset xumoyuddin:1768152645883-3
ALTER TABLE _transaction DROP CONSTRAINT fk_transaction_to_account;

-- changeset xumoyuddin:1768152645883-5
DROP TABLE _account CASCADE;

-- changeset xumoyuddin:1768152645883-6
DROP TABLE account CASCADE;

