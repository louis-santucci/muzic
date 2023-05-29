package com.muzic.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@TestConfiguration
@EnableAsync
@EnableTransactionManagement(proxyTargetClass = true)
public class MuzicTestConfiguration {
}
