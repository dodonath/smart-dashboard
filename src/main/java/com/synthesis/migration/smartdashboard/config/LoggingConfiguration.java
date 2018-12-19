package com.synthesis.migration.smartdashboard.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingConfiguration.class);

	@Pointcut("within(com.synthesis.migration.smartdashboard.controller..*)" )
	public void controllerLogging(){}

	@Pointcut("within(com.synthesis.migration.smartdashboard.service..*)" )
	public void serviceLogging(){}

	@Pointcut("within(com.synthesis.migration.smartdashboard.dao..*)" )
	public void repositoryLogging(){}


	@Before("controllerLogging()")
	public void logBeforeControllerEntry(JoinPoint point)
	{
		logging(point,"entering");
	}

	@After("controllerLogging()")
	public void logAfterControllerEntry(JoinPoint point)
	{
		logging(point,"exiting");
	}

	@Before("serviceLogging()")
	public void logBeforeServiceEntry(JoinPoint point)
	{
		logging(point,"entering");
	}

	@After("serviceLogging()")
	public void logAfterServiceEntry(JoinPoint point)
	{
		logging(point,"exiting");
	}
	
	@Before("repositoryLogging()")
	public void logBeforeDaoEntry(JoinPoint point)
	{
		logging(point,"entering");
	}

	@After("repositoryLogging()")
	public void logAfterDaoEntry(JoinPoint point)
	{
		logging(point,"exiting");
	}

	private void logging(JoinPoint point,String msg)
	{
		String className = point.getSignature().getDeclaringTypeName();
		LOGGER.debug(className+" "+MethodSignature.class.cast(point.getSignature()).getMethod().getName() +"..."+msg);
	}



}
