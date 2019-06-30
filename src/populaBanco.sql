INSERT INTO public.banco (id, nome) VALUES (1, 'Mito-Bank');
INSERT INTO public.agencia (id, nome, banco_id) VALUES (1, 'Mito-City', 1);

INSERT INTO public.conta (id, digito, saldo, senha) VALUES (1, 0, 1000.00, '1234');
INSERT INTO public.conta (id, digito, saldo, senha) VALUES (2, 0, 666.00, '1234');
INSERT INTO public.conta (id, digito, saldo, senha) VALUES (3, 0, 1222.00, '1234');
INSERT INTO public.conta (id, digito, saldo, senha) VALUES (4, 0, 999.00, '1234');
INSERT INTO public.conta (id, digito, saldo, senha) VALUES (5, 0, 34567.63, '1234');

INSERT INTO public.cliente (id, cpf, email, first_name, last_name, login, middle_name, senha, conta_id, agencia_id) VALUES (1, '686.927.790-75', 'Rhuanw7@gmail.com', 'Rhuan', 'Webster', 'webster', 'Alfredo', '123456', 1, 1);
INSERT INTO public.cliente (id, cpf, email, first_name, last_name, login, middle_name, senha, conta_id, agencia_id) VALUES (2, '672.838.050-79', 'paulormartins12@gmail.com', 'Paulo', 'Oliveira', 'paulo', 'Alfredo', '123456', 2, 1);
INSERT INTO public.cliente (id, cpf, email, first_name, last_name, login, middle_name, senha, conta_id, agencia_id) VALUES (3, '083.062.520-80', 'emidio.tonetti@gmail.com', 'Emidio', 'Tasaver', 'tasaver', 'Alfredo', '123456', 3, 1);
INSERT INTO public.cliente (id, cpf, email, first_name, last_name, login, middle_name, senha, conta_id, agencia_id) VALUES (4, '956.198.001-05', 'relixauco@gmail.com', 'Rachimi', 'Solarevisk', 'rachimito', 'Alfredo', '123456', 4, 1);
INSERT INTO public.cliente (id, cpf, email, first_name, last_name, login, middle_name, senha, conta_id, agencia_id) VALUES (5, '956.198.001-05', 'professordiegoguedes@gmail.com', 'Diego', 'Guedes', 'guedes', 'Alfredo', '123456', 5, 1);




