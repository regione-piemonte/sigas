ALTER TABLE sigas_dich_consumi ADD COLUMN selected_import boolean null;

UPDATE sigas_dich_consumi SET selected_import = true;