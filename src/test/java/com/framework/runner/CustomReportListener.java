package com.framework.runner;

import com.framework.stepdefinitions.TestUtil;
import com.framework.utils.ExcelUtil;
import com.framework.utils.ExtentReportManager;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.Plugin;
import io.cucumber.plugin.event.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomReportListener implements ConcurrentEventListener, Plugin {

	public CustomReportListener() {

	}

	@Override
	public void setEventPublisher(EventPublisher publisher) {
		publisher.registerHandlerFor(TestCaseStarted.class, this::ScenarioStarted);
		publisher.registerHandlerFor(TestStepStarted.class, this::StepStarted);
		publisher.registerHandlerFor(TestStepFinished.class, this::stepFinished);
		publisher.registerHandlerFor(TestCaseFinished.class, this::ScenarioFinished);

	};

	// This event is triggered when Test Case is started
	// here we create the scenario node
	public void ScenarioStarted(TestCaseStarted event) {
		String featurePath = event.getTestCase().getUri().toString();
		String[] featureArr = featurePath.split("/");
		String featureName = featureArr[featureArr.length - 1].split("\\.")[0];	
		System.out.println(CucumberRunnerClass.folderName);
		ExtentReportManager.initReport(event.getTestCase().getName(), featureName, CucumberRunnerClass.folderName);
	};

	// This event is triggered when Test Case is started
	// here we create the scenario node
	public void ScenarioFinished(TestCaseFinished event) {
		String featurePath = event.getTestCase().getUri().toString();
		String[] featureArr = featurePath.split("/");
		String featureName = featureArr[featureArr.length - 1].split("\\.")[0];	
		Map<String, String> map = new HashMap<String,String>();
		map.put("Feature Name", featureName);
		map.put("Scenario Name", event.getTestCase().getName());
		map.put("Status", event.getResult().getStatus().name());
		map.put("Error", event.getResult().getError() != null ? event.getResult().getError().getMessage(): "");
		ExcelUtil.updateConsolidatedReport(map);

	};

	public void StepStarted(TestStepStarted event) {
		System.out.println("#####################step location "+event.getTestStep().getId().toString());
	}

	// This is triggered when TestStep is finished
	public void stepFinished(TestStepFinished event) {
		String stepName = "";
		if (event.getTestStep() instanceof PickleStepTestStep) {
			PickleStepTestStep steps = (PickleStepTestStep) event.getTestStep();
			stepName = steps.getStep().getKeyword() + " " + steps.getStep().getText();
		} else {
			HookTestStep hoo = (HookTestStep) event.getTestStep();
			stepName = hoo.getHookType().name() + " Hook";
		}

		if (event.getResult().getStatus().toString() == "PASSED") {
			ExtentReportManager.getExtentTest().pass(stepName);
		} else if (event.getResult().getStatus().toString() == "SKIPPED")

		{
			ExtentReportManager.getExtentTest().skip(stepName);
		} else {
			ExtentReportManager.getExtentTest().fail(stepName + "\n"+ event.getResult().getError().getLocalizedMessage());
		}
	}
}
