ALTER TABLE sigas_pagamenti_versamenti 
	ADD CONSTRAINT fk_sigas_pagamenti_versamenti_sigas_anagrafica_soggetti FOREIGN KEY (fk_anag) 
	REFERENCES sigas_anagrafica_soggetti(id_anag) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION;


ALTER TABLE sigas_pagamenti_versamenti 
	ADD CONSTRAINT fk_sigas_pagamenti_versamenti_sigas_pagamenti FOREIGN KEY (fk_pagamento) 
	REFERENCES sigas_pagamenti(id_pagamento) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION;

	
ALTER TABLE sigas_pagamenti_versamenti 
	ADD CONSTRAINT fk_sigas_pagamenti_versamenti_sigas_provincia FOREIGN KEY (fk_provincia) 
	REFERENCES sigas_provincia(id_provincia) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION;

	
ALTER TABLE sigas_pagamenti_versamenti 
	ADD CONSTRAINT fk_sigas_pagamenti_versamenti_tipo_versamento FOREIGN KEY (id_tipo_versamento) 
	REFERENCES sigas_tipo_versamento(id_tipo_versamento) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION;
	
	
ALTER TABLE sigas_pagamenti_versamenti 
	ADD CONSTRAINT fk_sigas_pagamenti_versamenti_versamento FOREIGN KEY (fk_versamento) 
	REFERENCES sigas_dich_versamenti(id_versamento) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION;