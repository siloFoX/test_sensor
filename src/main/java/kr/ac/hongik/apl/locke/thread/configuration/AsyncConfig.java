package kr.ac.hongik.apl.locke.thread.configuration;

import kr.ac.hongik.apl.locke.thread.exception.AsyncExceptionHandler;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;


import javax.annotation.Resource;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration // Register bean object
@EnableAsync // Declare async processor
public class AsyncConfig implements AsyncConfigurer {

    // Parameter of Executor
    private static int SAMPLE_CORE_POOL_SIZE = 2; // Num of default thread
    private static int SAMPLE_MAX_POOL_SIZE = 5; // Maximum Num of thread
//    private static int SAMPLE_QUEUE_CAPACITY = 0;
//    private static String SAMPLE_EXECUTOR_BEAN_NAME = "sampleExecutor";

    @Resource(name = "sampleExecutor")
    private ThreadPoolExecutor threadPoolExecutor;

    @Bean(name = "sampleExecutor")
    @Override
    public Executor getAsyncExecutor() {

        ThreadPoolExecutor beanExecutor = new ThreadPoolExecutor();

        beanExecutor.setCorePoolSize(SAMPLE_CORE_POOL_SIZE);
        beanExecutor.setMaximumPoolSize(SAMPLE_MAX_POOL_SIZE);
//        beanExecutor.setQueueCapacity(SAMPLE_QUEUE_CAPACITY);
//        beanExecutor.setBeanName(SAMPLE_EXECUTOR_BEAN_NAME);
//        beanExecutor.initialize();

        return beanExecutor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {

        return new AsyncExceptionHandler();
    }
}
