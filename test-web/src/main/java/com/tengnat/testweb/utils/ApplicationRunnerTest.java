package com.tengnat.testweb.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class ApplicationRunnerTest implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("***************"+"启动ApplicationRunnerTest");
        log.debug("debug日志");
        log.error("error日志");
        log.info("info日志");
    }
}
