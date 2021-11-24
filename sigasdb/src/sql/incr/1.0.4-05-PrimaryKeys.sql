ALTER TABLE sigas_pagamenti 
	ADD CONSTRAINT pk_sigas_pagamenti PRIMARY KEY (id_pagamento);
	
ALTER TABLE sigas_pagamenti_versamenti 
	ADD CONSTRAINT  pk_sigas_pagamenti_versamenti PRIMARY KEY (id_pagamento_versamento);