DROP FUNCTION IF EXISTS "AllineaSequences"();
DROP FUNCTION IF EXISTS "AllineaSequence"
                            ( "pTableName"    varchar
                            , "pColumnName"   varchar
                            , "pSequenceName" varchar
                            );


CREATE OR REPLACE FUNCTION  "AllineaSequence"
                            ( "pTableName"    varchar
                            , "pColumnName"   varchar
                            , "pSequenceName" varchar
                            )
  RETURNS bigint AS $$
DECLARE
  nId   bigint;
BEGIN
  EXECUTE 'SELECT COALESCE(MAX(' || "pColumnName" || '),0) + 1 FROM ' || "pTableName" INTO nId;
  EXECUTE 'ALTER SEQUENCE ' || "pSequenceName" || ' RESTART WITH ' || nId;
  ------------
  RETURN nId;
END;
$$  LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION "AllineaSequences"()
  RETURNS smallint AS $$
DECLARE
  nId   bigint;
BEGIN

  PERFORM "AllineaSequence"('sigas_aliquote','id_aliquota','seq_aliquote');
  PERFORM "AllineaSequence"('sigas_allarmi','id_allarme','seq_allarmi');
  PERFORM "AllineaSequence"('sigas_anagrafica_soggetti','id_anag','seq_anagrafica_soggetti');
  PERFORM "AllineaSequence"('sigas_dich_consumi','id_consumi','seq_dich_consumi');
  PERFORM "AllineaSequence"('sigas_dich_versamenti','id_versamento','seq_dich_versamenti');
  PERFORM "AllineaSequence"('sigas_dich_scarti','id_scarti','seq_dich_scarti');
  PERFORM "AllineaSequence"('sigas_frontespizio_utf','id_frontespizio','seq_frontespizio_utf');
  PERFORM "AllineaSequence"('sigas_import_utf','id_import','seq_import_utf');
  PERFORM "AllineaSequence"('sigas_quadro_f_utf','id_quadro_f','seq_quadro_f_utf');
  PERFORM "AllineaSequence"('sigas_quadro_g_utf','id_quadro_g','seq_quadro_g_utf');
  PERFORM "AllineaSequence"('sigas_quadro_h_utf','id_quadro_h','seq_quadro_h_utf');
  PERFORM "AllineaSequence"('sigas_quadro_i_utf','id_quadro_i','seq_quadro_i_utf');
  PERFORM "AllineaSequence"('sigas_quadro_m_utf','id_quadro_m','seq_quadro_m_utf');
  PERFORM "AllineaSequence"('sigas_quadro_n_utf','id_quadro_n','seq_quadro_n_utf');
  PERFORM "AllineaSequence"('sigas_validazione','id_valid','seq_validazione');
  PERFORM "AllineaSequence"('sigas_ana_comunicazioni','id_comunicazione','seq_ana_comunicazioni');
  PERFORM "AllineaSequence"('sigas_tassi','id_tasso','seq_tassi');
  PERFORM "AllineaSequence"('sigas_rimborso','id_rimborso','seq_rimborso');
  ------------
  RETURN 0;
END;
$$  LANGUAGE plpgsql;


SELECT * FROM "AllineaSequences"();
