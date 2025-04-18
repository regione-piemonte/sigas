/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.aspect;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import it.csi.sigas.sigasbl.common.Constants;
import it.csi.sigas.sigasbl.common.exception.BusinessException;
import it.csi.util.performance.StopWatch;

@Aspect
public class LoggingAspect {

	private static Logger logger = Logger.getLogger(Constants.COMPONENT_NAME + ".tracer");
	//private static Random random;
	private static SecureRandom random;
	private static ObjectMapper mapper;
	private StopWatch stopWatch = null;
	
	{
		mapper = new ObjectMapper();
		//random = new Random();
		random = new SecureRandom();
	}

	@Value("${app.devmode:false}")
	private Boolean devMode;

	@Around(value = "execution(* it.csi.sigas.sigasbl.service.impl..*(..))", argNames = "joinPoint")
	public Object aroundManagerCall(ProceedingJoinPoint joinPoint) throws Throwable {
		boolean logInput = isDevMode() || logger.isDebugEnabled();
		boolean logOutput = isDevMode() || logger.isDebugEnabled();
		boolean measureTiming = isDevMode() || logger.isInfoEnabled();
		return logInputOutput("service", joinPoint, logInput, logOutput, measureTiming);
	}

	@Around(value = "execution(* it.csi.sigas.sigasbl.model.repositories..*(..))", argNames = "joinPoint")
	public Object aroundDAOCall(ProceedingJoinPoint joinPoint) throws Throwable {
		boolean logInput = isDevMode() || logger.isDebugEnabled();
		boolean logOutput = isDevMode() || logger.isDebugEnabled();
		boolean measureTiming = isDevMode() || logger.isInfoEnabled();
		return logInputOutput("repository", joinPoint, logInput, logOutput, measureTiming);
	}

	@Around(value = "execution(* it.csi.sigas.sigasbl.facade.impl..*(..))", argNames = "joinPoint")
	public Object aroundFacadeCall(ProceedingJoinPoint joinPoint) throws Throwable {
		boolean logInput = isDevMode() || logger.isDebugEnabled();
		boolean logOutput = isDevMode() || logger.isDebugEnabled();
		boolean measureTiming = isDevMode() || logger.isInfoEnabled();
		return logInputOutput("service", joinPoint, logInput, logOutput, measureTiming);
	}

	@Around(value = "execution(* it.csi.sigas.sigasbl.model.mapper.impl..*(..))", argNames = "joinPoint")
	public Object aroundMapperCall(ProceedingJoinPoint joinPoint) throws Throwable {
		boolean logInput = isDevMode() || logger.isDebugEnabled();
		boolean logOutput = isDevMode() || logger.isDebugEnabled();
		boolean measureTiming = isDevMode() || logger.isInfoEnabled();
		return logInputOutput("mapper", joinPoint, logInput, logOutput, measureTiming);
	}

	@Around(value = "execution(* it.csi.sigas.sigasbl.security..*(..))", argNames = "joinPoint")
	public Object aroundSecurityCall(ProceedingJoinPoint joinPoint) throws Throwable {
		boolean logInput = isDevMode() || logger.isDebugEnabled();
		boolean logOutput = isDevMode() || logger.isDebugEnabled();
		boolean measureTiming = isDevMode() || logger.isInfoEnabled();
		return logInputOutput("security", joinPoint, logInput, logOutput, measureTiming);
	}
	
	@Around(value = "execution(* it.csi.sigas.sigasbl.rest..*(..))", argNames = "joinPoint")
	public Object aroundRestCall(ProceedingJoinPoint joinPoint) throws Throwable {
		boolean logInput = isDevMode() || logger.isDebugEnabled();
		boolean logOutput = isDevMode() || logger.isDebugEnabled();
		boolean measureTiming = isDevMode() || logger.isInfoEnabled();
		return logInputOutput("request", joinPoint, logInput, logOutput, measureTiming);
	}
	
	@Around(value = "execution(* it.csi.sigas.sigasbl.rest..*(..))", argNames = "joinPoint")
	public Object aroundRestStopWatchCall(ProceedingJoinPoint joinPoint) throws Throwable {
		String type = "request";
		return stopWatch(joinPoint, type);
	}
	
	@Around(value = "execution(* it.csi.sigas.sigasbl.service.impl..*(..))", argNames = "joinPoint")
	public Object aroundManagerStopWatchCall(ProceedingJoinPoint joinPoint) throws Throwable {
		String type = "service";
		return stopWatch(joinPoint, type);
	}
	
	@Around(value = "execution(* it.csi.sigas.sigasbl.model.repositories..*(..))", argNames = "joinPoint")
	public Object aroundDAOStopWatchCall(ProceedingJoinPoint joinPoint) throws Throwable {
		String type = "repository";
		return stopWatch(joinPoint, type);
	}


	private Object logInputOutput(String sourceType, ProceedingJoinPoint joinPoint, boolean logInput, boolean logOutput, boolean measureTiming) throws Throwable {

		final String IDENTATION_PARAM = "aspectLogIdentationLevel";

		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = null;
		if (requestAttributes != null)
			request = ((ServletRequestAttributes) requestAttributes).getRequest();
		
		Integer identationLevel = 0;
		if (request != null && request.getAttribute(IDENTATION_PARAM) != null)
			identationLevel = (Integer) request.getAttribute(IDENTATION_PARAM);
		String ray = getRequestRay(request);

		if (request != null)
			request.setAttribute(IDENTATION_PARAM, identationLevel + 1);

		String rayPrefix = "[RAY" + ray + "] ";

		String identationFirst = "";
		String identation = "";

		for (int i = 0; i < identationLevel; i++) {
			identation += "|    ";
		}

		String openingTag = "/----------------------------------------------------";
		String closingTag = "\\----------------------------------------------------";
		identationFirst = identation;

		String identedSourceType = sourceType;
		if (identedSourceType.length() < 10) {
			int toAdd = (10 - identedSourceType.length());
			for (int i = 0; i < toAdd; i++) {
				identedSourceType += " ";
			}
		}

		String logStr;
		Object result = null;

		String logPrefixFirst = rayPrefix + "[ " + identedSourceType + "] - " + identationFirst + "| ";
		String logPrefix = rayPrefix + "[ " + identedSourceType + "] - " + identation + "| ";
		String logPrefixHeader = rayPrefix + "[ " + identedSourceType + "] - " + identation + "";

		String signatureName = getTargetObject(joinPoint.getTarget()).getSimpleName() + "." + joinPoint.getSignature().getName();

		logger.info(logPrefixHeader + openingTag);
		
		if (logInput) {
			logStr = String.format(logPrefixFirst + "%s INPUT : ( ", signatureName);
			Object[] args = joinPoint.getArgs();
			for (int i = 0; i < args.length; i++) {
				if (args[i] != null) {
					logStr += serializeParameter(args[i]) + ", ";
				}
			}
			if (args.length > 0) {
				logStr = logStr.substring(0, logStr.length() - 2);
			}
			logStr += " )";

			logger.info(logStr);
		} else {
			logStr = String.format(logPrefixFirst + "%s START", signatureName);
			logger.info(logStr);
		}

		Throwable threw = null;
		Long startTime = measureTiming ? new Date().getTime() : null;

		try {
			result = joinPoint.proceed();
		} catch (Throwable e) {
			threw = e;
		}

		if (measureTiming) {
			logStr = String.format(logPrefix + "%s DURATION : ", signatureName);
			logStr += ((new Date().getTime()) - startTime) + " ms";
			//logger.info(logStr);
		}

		if (threw != null) {
			if (threw instanceof BusinessException) {
				logStr = String.format(logPrefix + "%s END with ERRORCODE: %s and MESSAGE: %s", signatureName, ((BusinessException) threw).getCodice(), ((BusinessException) threw).getMessage());
				logger.info(logStr);
			} else {
				logStr = String.format(logPrefix + "%s THREW EXCEPTION : ", signatureName);
				logStr += threw.getMessage();
				logger.error(logStr, threw);
			}
		} else {
			if (logOutput) {
				logStr = String.format(logPrefix + "%s OUTPUT : ", signatureName);
				logStr += serializeParameter(result);
				logger.info(logStr);
			} else {
				logStr = String.format(logPrefix + "%s FINISHED", signatureName);
				logger.info(logStr);
			}
		}

		if (requestAttributes != null)
			request.setAttribute(IDENTATION_PARAM, identationLevel);
		
		logger.info(logPrefixHeader + closingTag);
		

		if (threw != null) {
			throw threw;
		}

		return result;
	}

	private boolean isDevMode() {
		if (devMode != null) {
			return devMode;
		} else {
			return false;
		}
	}

	protected Class<?> getTargetObject(Object proxy) throws Exception {
		// AopUtils.getTargetClass(joinPoint.getTarget())

		if (AopUtils.isJdkDynamicProxy(proxy)) {
			//while ((AopUtils.isJdkDynamicProxy(proxy))) {
			if((AopUtils.isJdkDynamicProxy(proxy)) && 
			   ((Advised) proxy).getTargetSource().getTarget() != null) 
			{
				return getTargetObject(((Advised) proxy).getTargetSource().getTarget());
			}
			return proxy.getClass();
		} else if (AopUtils.isCglibProxy(proxy)) {
			Class<?> proxyClass = proxy.getClass().getSuperclass();
			while (AopUtils.isCglibProxy(proxyClass)) {
				proxyClass = proxy.getClass().getSuperclass();
			}
			return proxyClass;
		} else if (proxy.getClass().getCanonicalName().contains("com.sun.proxy.$Proxy")) {
			Class<?>[] interfaces = proxy.getClass().getInterfaces();
			if (interfaces.length > 0) {
				return interfaces[0];
			} else {
				return proxy.getClass();
			}
		} else {
			return proxy.getClass();
		}
	}

	private String getRequestRay(HttpServletRequest request) {
		String ray = null;
		if(request!=null) {
			ray = (String) request.getAttribute("aspectLogRequestRay");
		}		
		
		if (ray == null) {
			//ray = String.valueOf(Double.valueOf(Math.floor(100000 + random.nextDouble() * 800000)).intValue());
			ray = String.valueOf(Math.floor(100000 + random.nextDouble() * 800000));
			if(request!=null) {
				request.setAttribute("aspectLogRequestRay", ray);
			}
		}

		return ray;
	}


	private String serializeParameter(Object raw) {
		if (raw == null)
			return "null";
		try {
			if (raw instanceof String) {
				return "\"" + (String) raw + "\"";
			} else if (raw instanceof Object) {
				if (raw.getClass().getName().startsWith("it.sigas")) {
					return raw.getClass().getSimpleName() + ":" + mapper.writeValueAsString(raw);
				} else {
					return raw.toString();
				}
			} else {
				return String.valueOf(raw);
			}
		} catch (Exception e) {
			return String.valueOf(raw);
		}
	}
	
	private Object stopWatch(ProceedingJoinPoint joinPoint, String type) throws Throwable {
		String method = joinPoint.getSignature().getName();
		String className = joinPoint.getSignature().getDeclaringTypeName();
		try {
			stopWatch = new StopWatch("sigasbl");
			stopWatch.start();
			Object rval = joinPoint.proceed();
			return rval;
		} finally {
			stopWatch.stop();
			stopWatch.dumpElapsed(className, method, "invocazione " + type + " [sigas]::[" + method + "]", "(Interceptor)");
		}
	}

}
