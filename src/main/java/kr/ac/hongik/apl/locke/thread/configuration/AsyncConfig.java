package kr.ac.hongik.apl.locke.thread.configuration;

import kr.ac.hongik.apl.locke.thread.exception.AsyncExceptionHandler;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


import javax.annotation.Resource;

@Configuration // Register bean object
@EnableAsync // Declare async processor
public class AsyncConfig implements AsyncConfigurer {

    // Parameter of Executor
    private static int SAMPLE_CORE_POOL_SIZE = 2; // Num of default thread
    private static int SAMPLE_MAX_POOL_SIZE = 5; // Maximum Num of thread
    private static int SAMPLE_QUEUE_CAPACITY = 0;
    private static String SAMPLE_EXECUTOR_BEAN_NAME = "sampleExecutor";

    @Resource(name = "sampleExecutor")
    private ThreadPoolTaskExecutor sampleExecutor;

    @Bean(name = "sampleExecutor")
    @Override
    public ThreadPoolTaskExecutor getAsyncExecutor() {

        ThreadPoolTaskExecutor beanExecutor = new ThreadPoolTaskExecutor();

        beanExecutor.setCorePoolSize(SAMPLE_CORE_POOL_SIZE);
        beanExecutor.setMaxPoolSize(SAMPLE_MAX_POOL_SIZE);
        beanExecutor.setQueueCapacity(SAMPLE_QUEUE_CAPACITY);
        beanExecutor.setBeanName(SAMPLE_EXECUTOR_BEAN_NAME);
        beanExecutor.initialize();

        return beanExecutor;
    }

    public boolean isSampleTaskExecute() {

        boolean rtn = true;

        System.out.println("EXECUTOR_SAMPLE.getActiveCount() : " + sampleExecutor.getActiveCount());
        if (sampleExecutor.getActiveCount() >= SAMPLE_MAX_POOL_SIZE + SAMPLE_QUEUE_CAPACITY) {
            rtn = false;
        }

        return rtn;
    }

    public boolean isSampleExecute(int createCnt) {

        boolean rtn = true;

        if((sampleExecutor.getActiveCount() + createCnt) > SAMPLE_MAX_POOL_SIZE + SAMPLE_QUEUE_CAPACITY) {
            rtn = false;
        }

        return rtn;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {

        return new AsyncExceptionHandler();
    }
}
