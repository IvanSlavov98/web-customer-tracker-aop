package com.luv2code.springdemo.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CRMLoggingAspect {

	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Pointcut("execution(* com.luv2code.springdemo.controller.*.*(..))")
	private void forControllerPackage() {}
	
	@Pointcut("execution(* com.luv2code.springdemo.service.*.*(..))")
	private void forServicePackage() {}
	
	@Pointcut("execution(* com.luv2code.springdemo.dao.*.*(..))")
	private void forDAOPackage() {}
	
	@Pointcut("forControllerPackage() || forServicePackage() || forDAOPackage()")
	private void forAppFlow() {}
	
	@Before("forAppFlow()")
	public void before(JoinPoint joinPoint) {
		
		String methodName = joinPoint.getSignature().toShortString();
		
		logger.info("\n ====> @Before Calling Method: " + methodName);
		
		Object[] args = joinPoint.getArgs();
		
		for(Object arg : args) {
			logger.info("====> Argument: " + arg.toString());
		}
	}
	
	@AfterReturning(
			pointcut = "forAppFlow()",
			returning = "res"
			)
	public void afterReturning(JoinPoint joinPoint, Object res) {
		
		String methodName = joinPoint.getSignature().toShortString();
		
		logger.info("\n ====> @AfterReturning Calling Method: " + methodName);
		logger.info(" ====> Result: " + res);
	}
}
