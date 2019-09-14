DROP TABLE IF EXISTS Transfer;
DROP TABLE IF EXISTS Client;
DROP TABLE IF EXISTS Account;

CREATE TABLE Client (
  id INT AUTO_INCREMENT PRIMARY KEY,
  first_name varchar(50) ,
  last_name varchar(50) 
);

CREATE TABLE Account
(
	id INT AUTO_INCREMENT PRIMARY KEY,
	iban VARCHAR(22) NOT NULL,
	amount DOUBLE NOT NULL, 
	creation_date TIMESTAMP,
	id_client INT,
	FOREIGN KEY(id_client) REFERENCES Client(id)
);


CREATE TABLE Transfer(
	id INT AUTO_INCREMENT PRIMARY KEY,
	id_recepient int ,
	id_sender int,
	amount DOUBLE NOT NULL,
	FOREIGN KEY(id_recepient) REFERENCES Account(id),
	FOREIGN	KEY(id_sender) REFERENCES Account(id)
);


INSERT INTO CLIENT(first_name,last_name) VALUES ('abdessamie','sohail');
INSERT INTO CLIENT(first_name,last_name) VALUES ('michael','sohail');
INSERT INTO CLIENT(first_name,last_name) VALUES ('vlad','sohail');
INSERT INTO CLIENT(first_name,last_name) VALUES ('petrovski','sohail');
INSERT INTO CLIENT(first_name,last_name) VALUES ('jokovic','sohail');


INSERT INTO ACCOUNT(iban,id_client,amount) VALUES ('GB46BUKB20041538290008',1,188100.25);
INSERT INTO ACCOUNT(iban,id_client,amount) VALUES ('GB46BUKB20041538560009',2,255889.25);

INSERT INTO Transfer(id_sender,id_recepient,amount) VALUES (1,2,200);
