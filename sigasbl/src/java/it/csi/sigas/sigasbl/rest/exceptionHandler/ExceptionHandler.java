/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.rest.exceptionHandler;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.springframework.security.access.AccessDeniedException;

import it.csi.sigas.sigasbl.common.Constants;
import it.csi.sigas.sigasbl.common.ErrorCodes;
import it.csi.sigas.sigasbl.common.ErrorMessages;
import it.csi.sigas.sigasbl.common.Esito;
import it.csi.sigas.sigasbl.common.exception.BadRequestRestException;
import it.csi.sigas.sigasbl.common.exception.BusinessException;
import it.csi.sigas.sigasbl.model.vo.ExceptionVO;
import it.csi.sigas.sigasbl.model.vo.ResponseVO;

@Provider
public class ExceptionHandler implements ExceptionMapper<RuntimeException> {

	private Logger logger = LoggerFactory.getLogger(Constants.HANDLER_EXCEPTION);

	@Override
	public Response toResponse(RuntimeException exception) {
		// BUSSINESREQUEST EXCEPTION SERVICE SIGAS
		if (exception instanceof BusinessException) {
			return handleBusinessException((BusinessException) exception);
		}
//		// BADREQUEST EXCEPTION SERVICE
//		if (exception instanceof it.csi.irba.irbabl.common.exception.BadRequestException) {
//			return handleBadRequestExceptionIrba((it.csi.irba.irbabl.common.exception.BadRequestException) exception);
//		}
		// EXCEPTION QUERY PARAM DI RESTEASY
		if (exception instanceof BadRequestRestException) {
			return handleBadRequestExceptionRestEasy((BadRequestRestException) exception);
		}
		// HIBERNATE CONCURRENCY 
		if (exception instanceof HibernateOptimisticLockingFailureException) {
			return handleHibernateOptimisticLockingFailureException((HibernateOptimisticLockingFailureException) exception);
		}
		
//		if (exception instanceof AccessDeniedExceptionOnLogin) {
//			return handleAccessDeniedExceptionOnLogin((AccessDeniedExceptionOnLogin) exception);
//		}
		if (exception instanceof AccessDeniedException) {
			return handleAccessDeniedException((AccessDeniedException) exception);
		}
//		if (exception instanceof AuthenticationException) {
//			return handleNotAuthenticated((AuthenticationException) exception);
//		}
//		// VALIDAZIONE OGGETTI
//		if (exception instanceof MethodConstraintViolationException)
//			return handleValidationException((MethodConstraintViolationException) exception);
//		else {
			return handleGenericException(exception);
//		}
	}
	private Response handleBadRequestExceptionRestEasy(BadRequestRestException exception) {
		logger.error("handleBadRequestExceptionRestEasy", exception);
		return Response.status(HttpStatus.BAD_REQUEST.value()).entity(
				new ResponseVO<ExceptionVO>(Esito.ERROR, new ExceptionVO(HttpStatus.BAD_REQUEST.value(), "Paramentri di input al servzio non validi", ErrorCodes.BAD_REQUEST_EXCEPTION_RESTEASY)))
				.build();

	}
	
	private Response handleHibernateOptimisticLockingFailureException(HibernateOptimisticLockingFailureException exception) {
		logger.error("handleHibernateOptimisticLockingFailureException", exception);
		return Response.status(HttpStatus.CONFLICT.value()).entity(
				new ResponseVO<ExceptionVO>(Esito.ERROR, new ExceptionVO(HttpStatus.CONFLICT.value(), "I dati sono stati aggiornati o cancellati da un'altra transazione", ErrorCodes.BUSSINESS_EXCEPTION)))
				.build();

	}

	// ECCEZIONE DI BUSINESS RITORNA UN 200
	protected Response handleBusinessException(BusinessException exception) {
		logger.error("handleBusinessException: " + exception.getMessage());
		return Response.status(HttpStatus.OK.value()).entity(new ResponseVO<ExceptionVO>(Esito.ERROR, new ExceptionVO(HttpStatus.OK.value(), exception.getMessage(), exception.getCodice()))).build();
	}

	// ECCEZIONE DI SPRING SECURITY NOT AUTHORIZED 403
	protected Response handleAccessDeniedException(AccessDeniedException exception) {
		return Response.status(HttpStatus.FORBIDDEN.value())
				.entity(new ResponseVO<ExceptionVO>(Esito.ERROR, new ExceptionVO(HttpStatus.FORBIDDEN.value(), exception.getMessage(), ErrorCodes.ACCESS_DENIED))).build();
	}

//	// ECCEZIONE IRBA ON LOGIN NOT AUTHORIZED 403
//	protected Response handleAccessDeniedExceptionOnLogin(AccessDeniedExceptionOnLogin exception) {
//		return Response.status(HttpStatus.FORBIDDEN.value())
//				.entity(new ResponseVO<ExceptionVO>(Esito.ERROR, new ExceptionVO(HttpStatus.FORBIDDEN.value(), exception.getMessage(), ErrorCodes.ACCESS_DENIED_ON_LOGIN))).build();
//	}
//
//	// ECCEZIONE DI SPRING SECURITY NOT AUTHENTICATED 401
//	protected Response handleNotAuthenticated(AuthenticationException exception) {
//		return Response.status(HttpStatus.UNAUTHORIZED.value())
//				.entity(new ResponseVO<ExceptionVO>(Esito.ERROR, new ExceptionVO(HttpStatus.UNAUTHORIZED.value(), exception.getMessage(), ErrorCodes.NOT_AUTHENTICATED))).build();
//	}

	// ECCEZIONE INTERNA AL SERVER
	protected Response handleGenericException(RuntimeException exception) {
		logger.error("handleGenericException: " + exception.getMessage());
		return Response.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.entity(new ResponseVO<ExceptionVO>(Esito.ERROR, new ExceptionVO(HttpStatus.INTERNAL_SERVER_ERROR.value(), ErrorMessages.MSG_GENERIC_EXCEPTION))).build();
	}

}
