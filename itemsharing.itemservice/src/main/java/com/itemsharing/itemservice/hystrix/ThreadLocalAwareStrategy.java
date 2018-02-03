package com.itemsharing.itemservice.hystrix;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.itemsharing.itemservice.util.UserContextHolder;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariable;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableLifecycle;
import com.netflix.hystrix.strategy.properties.HystrixProperty;

public class ThreadLocalAwareStrategy extends HystrixConcurrencyStrategy {

	private HystrixConcurrencyStrategy  existingConcurrencyStrategy;
	
	public ThreadLocalAwareStrategy(HystrixConcurrencyStrategy  existingConcurrencyStrategy){
		this.existingConcurrencyStrategy = existingConcurrencyStrategy;
	}
	
	public BlockingQueue<Runnable> getBlockingQueue(int maxQueueSize) {
		return existingConcurrencyStrategy != null ? existingConcurrencyStrategy.getBlockingQueue(maxQueueSize) : super.getBlockingQueue(maxQueueSize);
	}

	public <T> HystrixRequestVariable<T> getRequestVariable(HystrixRequestVariableLifecycle<T> rv){
		return existingConcurrencyStrategy != null ? existingConcurrencyStrategy.getRequestVariable(rv): super.getRequestVariable(rv);
	}
	
	public ThreadPoolExecutor getThreadPool(
			HystrixThreadPoolKey threadPoolKey,
			HystrixProperty<Integer> corePoolSize,
			HystrixProperty<Integer> maximumPoolSize,
			HystrixProperty<Integer> keepAliveTime,
			TimeUnit unit,
			BlockingQueue<Runnable> workQueue
			){
		return existingConcurrencyStrategy != null ? existingConcurrencyStrategy.getThreadPool(threadPoolKey, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue):
			super.getThreadPool(threadPoolKey, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}
	
	public <T> Callable<T> wrapCallable(Callable<T> callable) {
		return existingConcurrencyStrategy != null ? existingConcurrencyStrategy.wrapCallable( 
				new DelegatingUserContextCallable<T>(callable, UserContextHolder.getContext())):
			super.wrapCallable(new DelegatingUserContextCallable<T>(callable, UserContextHolder.getContext()));
	}

}
