-- Runs once on first startup: one database per service, so no services share data.
CREATE DATABASE product_db;
CREATE DATABASE order_db;
-- notification-service has no database, it only reacts to events.