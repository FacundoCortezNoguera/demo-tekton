CREATE TABLE call_logs (
   id SERIAL PRIMARY KEY,
   timestamp TIMESTAMP NOT NULL,
   endpoint VARCHAR(255),
   parameters TEXT,
   response TEXT,
   error BOOLEAN
);