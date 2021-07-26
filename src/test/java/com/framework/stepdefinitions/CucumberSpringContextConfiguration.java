package com.framework.stepdefinitions;

import com.framework.application.SpringBootWithSeleniumCucumberFrameworkApplication;
import io.cucumber.java.Before;
import io.cucumber.spring.CucumberContextConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.ContextConfiguration;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Scope(SCOPE_CUCUMBER_GLUE)
@CucumberContextConfiguration
@ContextConfiguration(classes = SpringBootWithSeleniumCucumberFrameworkApplication.class, loader = SpringBootContextLoader.class)
@PropertySource("classpath:/application.yml")
public class CucumberSpringContextConfiguration {

  private static final Logger LOG = LoggerFactory.getLogger(CucumberSpringContextConfiguration.class);

  /**
   * Need this method so the cucumber will recognize this class as glue and load spring context configuration
   */
  @Before
  public void setUp() {
    LOG.info("-------------- Spring Context Initialized For Executing Cucumber Tests --------------");
  }

}