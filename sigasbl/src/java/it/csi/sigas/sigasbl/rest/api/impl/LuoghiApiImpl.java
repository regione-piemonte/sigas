/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.rest.api.impl;

import java.util.List;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.sigas.sigasbl.common.Esito;
import it.csi.sigas.sigasbl.dispatcher.LuoghiDispatcher;
import it.csi.sigas.sigasbl.model.vo.ResponseVO;
import it.csi.sigas.sigasbl.model.vo.luoghi.ComuneVO;
import it.csi.sigas.sigasbl.model.vo.luoghi.NazioneVO;
import it.csi.sigas.sigasbl.model.vo.luoghi.ProvinciaVO;
import it.csi.sigas.sigasbl.model.vo.luoghi.RegioneVO;
import it.csi.sigas.sigasbl.rest.api.ILuoghiApi;
import it.csi.sigas.sigasbl.util.SpringSupportedResource;


@Service
public class LuoghiApiImpl extends SpringSupportedResource implements ILuoghiApi {
	
	@Autowired
	private LuoghiDispatcher luoghiDispatcher;

	 private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public Response getAllProvince() {
		logger.info("START: getAllProvince");
		List<ProvinciaVO> lista = luoghiDispatcher.getAllProvince();
		logger.info("END: getAllProvince");
		return Response.ok().entity(new ResponseVO<List<ProvinciaVO>>(Esito.SUCCESS, lista)).build();
	}
	
	@Override
	public Response getComuniByIdProvincia(@QueryParam("idProvincia") Long idProvincia) {
		logger.info("START: getComuniByIdProvincia");
		List<ComuneVO> lista = luoghiDispatcher.getComuniByIdProvincia(idProvincia);
		logger.info("END: getComuniByIdProvincia");
		return Response.ok().entity(new ResponseVO<List<ComuneVO>>(Esito.SUCCESS, lista)).build();
	}
	
	@Override
	public Response provinciaBySigla(@QueryParam("siglaProvncia") String siglaProvncia) {
		logger.info("START: provinciaBySigla");
		ProvinciaVO lista = luoghiDispatcher.getProvinciaBySigla(siglaProvncia);
		logger.info("END: provinciaBySigla");
		return Response.ok().entity(new ResponseVO<ProvinciaVO>(Esito.SUCCESS, lista)).build();
	}
	
	



	

	@Override
	public Response getAllComuni() {
		List<ComuneVO> lista = luoghiDispatcher.getAllComuni();
		return Response.ok().entity(new ResponseVO<List<ComuneVO>>(Esito.SUCCESS, lista)).build();
	}


	

	@Override
	public Response ricercaIndirizzo(@QueryParam("indirizzo") String indirizzo, @QueryParam("id") Long id) {
		List<String> listaVie = luoghiDispatcher.ricercaIndirizzo(indirizzo, id);
		return Response.ok(new ResponseVO<List<String>>(Esito.SUCCESS, listaVie)).build();
	}


}
