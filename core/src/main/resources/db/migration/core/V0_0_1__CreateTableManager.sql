CREATE TABLE "MANAGER" (
  "ID" BIGSERIAL NOT NULL,
  "USERNAME" VARCHAR(60) DEFAULT NULL,
  "PASSWORD" VARCHAR(60) DEFAULT NULL,
  "VERSION" BIGINT DEFAULT NULL,
  "CREATED_DATE" TIMESTAMP DEFAULT NULL,
  "CREATED_BY" BIGINT DEFAULT NULL,
  "LAST_MODIFIED_DATE" TIMESTAMP DEFAULT NULL,
  "LAST_MODIFIED_BY" BIGINT DEFAULT NULL,
  "ACTIVE" BOOLEAN NOT NULL DEFAULT TRUE,
  PRIMARY KEY ("ID"),
  CONSTRAINT manager_unique UNIQUE ("USERNAME")
);
