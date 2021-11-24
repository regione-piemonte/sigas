CREATE UNIQUE INDEX ak_fase_pagamento_01 ON sigas_tipo_pagamento USING btree (cod_tipo_pagamento);
CREATE UNIQUE INDEX ak_fase_pagamento_02 ON sigas_tipo_pagamento USING btree (desc_tipo_pagamento);
CREATE UNIQUE INDEX ak_modalita_pagamento_01 ON sigas_modalita_pagamento USING btree (desc_modalita_pagamento);
CREATE UNIQUE INDEX ak_stato_carrello_01 ON sigas_carrello_stato USING btree (desc_stato_carrello);
CREATE UNIQUE INDEX ak_c_messaggi_01 ON sigas_c_messaggi USING btree (desc_chiave_messaggio);