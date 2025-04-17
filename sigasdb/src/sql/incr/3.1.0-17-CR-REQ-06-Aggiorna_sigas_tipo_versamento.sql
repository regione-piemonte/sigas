insert into sigas_tipo_versamento (id_tipo_versamento, denominazione, descrizione) 
values ((select max(id_tipo_versamento) + 1 from sigas_tipo_versamento),'Deposito Cauzionale - Primo deposito', 'Deposito Cauzionale - Primo deposito');

insert into sigas_tipo_versamento (id_tipo_versamento, denominazione, descrizione) 
values ((select max(id_tipo_versamento) + 1 from sigas_tipo_versamento),'Deposito Cauzionale - Integrazione', 'Deposito Cauzionale - Integrazione');