CREATE SEQUENCE IF NOT EXISTS seq_carrello_pagamenti OWNED BY sigas_carrello_pagamenti.id_carrello;
CREATE SEQUENCE IF NOT EXISTS seq_carrello_rt OWNED BY sigas_carrello_rt.id_carrello_rt;
CREATE SEQUENCE IF NOT EXISTS seq_carrello_notifica OWNED BY sigas_carrello_notifica.id_carrello_notifica;


CREATE SEQUENCE IF NOT EXISTS seq_t_dichiarante OWNED BY sigas_dichiarante.id_dichiarante;
CREATE SEQUENCE IF NOT EXISTS seq_t_legale_rappresent OWNED BY sigas_legale_rappresent.id_legale_rappresent;
CREATE SEQUENCE IF NOT EXISTS seq_t_operatore OWNED BY sigas_operatore.id_operatore;
CREATE SEQUENCE IF NOT EXISTS seq_utenti OWNED BY sigas_utenti.id_utente;
CREATE SEQUENCE IF NOT EXISTS seq_anagrafica_utente OWNED BY sigas_anagrafica_utente.id_anag_utente ;
CREATE SEQUENCE IF NOT EXISTS seq_utente_provvisorio OWNED BY sigas_utente_provvisorio.id_utente_provv;
