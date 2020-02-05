package kr.ac.hongik.apl.locke.thread.sample;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service("asyncTaskSample")
public class AsyncTaskSample {

    @Async("sampleExecutor")
    public void executorSample(String str) {

        System.out.println("====>>>> THREAD START");
        System.out.println("<<<<==== THREAD END");
    }

    @Async("sampleExecutor")
    public void executorSample2(String str) {

        System.out.println("=====>>>>> THREAD START");
        System.out.println("<<<<<===== THREAD END");
    }
}
