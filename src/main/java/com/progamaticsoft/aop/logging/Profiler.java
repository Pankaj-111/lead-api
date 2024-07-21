package com.progamaticsoft.aop.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class Profiler {

	@Around("@annotation(Profile)")
	public Object logExecutionTime(final ProceedingJoinPoint joinPoint) throws Throwable {
		log.info("[*** EXECUTING {} ]", joinPoint.getSignature());
		final Object[] args = joinPoint.getArgs();
		for (Object object : args) {
			log.info("[*** Arguments {} ]", object);
			if (object instanceof org.apache.catalina.connector.RequestFacade) {
				HttpServletRequest request = (HttpServletRequest) object;
				log.info("Request parameters :{} ", request.getParameterMap().keySet());
			}
		}
		final StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		final Object proceed = joinPoint.proceed();
		stopWatch.stop();
		log.info("[*** {} HAS TAKEN {} MS ]", joinPoint.getSignature(), stopWatch.getTotalTimeMillis());
		return proceed;
	}
}