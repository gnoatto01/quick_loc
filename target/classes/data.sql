CREATE TABLE IF NOT EXISTS tb_roles(
    role_id INT PRIMARY KEY, 
    role_name VARCHAR(25) NOT NULL 
);

INSERT INTO  tb_roles (role_id, role_name) VALUES (1, 'ADMIN');
INSERT INTO  tb_roles (role_id, role_name) VALUES (2, 'BASIC');