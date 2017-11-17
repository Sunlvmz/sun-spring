package com.sunmvc.aop.advisor;

import com.sunmvc.aop.pointcut.Pointcut;

/**
 * 切点通知器
 *
 */
public interface PointcutAdvisor extends Advisor {

	/**
	 * 获得切点
	 * @return
	 */
	Pointcut getPointcut();
	
}
