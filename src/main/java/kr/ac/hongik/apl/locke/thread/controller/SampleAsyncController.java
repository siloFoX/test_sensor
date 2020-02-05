package kr.ac.hongik.apl.locke.thread.controller;

import kr.ac.hongik.apl.locke.thread.configuration.AsyncConfig;
import kr.ac.hongik.apl.locke.thread.sample.AsyncTaskSample;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller("SampleAsyncController")
public class SampleAsyncController {

    @Resource(name = "asyncTaskSample")
    private AsyncTaskSample asyncTaskSample;

    @Resource(name = "asyncConfig")
    private AsyncConfig asyncConfig;

    @RequestMapping("/sample/sampleTask.do")
        public ModelAndView doTask(HttpServletRequest request, HttpServletResponse response) throws Exception {

            try {

                asyncTaskSample.executorSample("a");
            } catch (TaskRejectedException e) {
                e.printStackTrace();
            }

            ModelAndView mav = new ModelAndView();
//            mav.setViewName("thread/sample/sampleTask");
        return mav;
    }
}
