package com.framework.utils;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author arunpand
 * 
 *
 */
public class FeatureConverter {
	/**
	 * @author arunpand
	 * @param args
	 * @throws Throwable
	 */
	public static void main(String[] args) throws Throwable {
		System.out.println("arun test");
		File ru = new File(System.getProperty("user.dir") + "/src/test/resources/runnablefeatures/");
		FileUtils.deleteDirectory(ru);
		ru.mkdir();
		FeatureConverter.getRunnableFeaturesList();
	}

	/**
	 * @author - arunpand
	 * @returnType - void
	 * @throws Throwable
	 */
	public static void getRunnableFeaturesList() throws Throwable {
		File file = new File(System.getProperty("user.dir") + "/src/test/resources/RunManager.xlsx");
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		Map<String, List<String>> scenarioIDs = FeatureConverter.getScenarioData(wb);
		XSSFSheet sh1 = wb.getSheet("TestData");
		int row1 = sh1.getLastRowNum();
		Map<String, Boolean> feature = new LinkedHashMap<String, Boolean>();
		for (Map.Entry<String, List<String>> entry : scenarioIDs.entrySet()) {
			String currentFeature = entry.getValue().get(0);
			String description = entry.getValue().get(1);
			String featureName = currentFeature.split("/")[currentFeature.split("/").length - 1];
			String scenarioId = entry.getKey();
			for (int i = 1; i <= row1; i++) {
				String scenario = sh1.getRow(i).getCell(0).getStringCellValue();
				System.out.println(scenario);
				if (sh1.getRow(i).getCell(0).getStringCellValue().equalsIgnoreCase(scenarioId)) {
					if (!feature.containsKey(currentFeature)) {
						feature.put(currentFeature, true);
						new File(System.getProperty("user.dir") + "/src/test/resources/runnablefeatures/"
								+ currentFeature + ".feature");
						FileWriter writer = new FileWriter(System.getProperty("user.dir")
								+ "/src/test/resources/runnablefeatures/" + currentFeature + ".feature", true);
						writer.write("Feature: " + featureName);
						writer.write("\r\n");
						FileReader reader = new FileReader(System.getProperty("user.dir")
								+ "/src/test/resources/features/" + currentFeature + ".feature");
						BufferedReader bufferedReader = new BufferedReader(reader);
						String line;
						while ((line = bufferedReader.readLine()) != null) {
//							if (line.contains("Scenario:") && line.contains(scenarioId) || line.contains(description)) {
							if (line.contains("Scenario:") && line.contains(scenarioId)) {
								int iteration = Integer.parseInt(sh1.getRow(i).getCell(1).getStringCellValue());
								if (iteration > 1) {
									writer.write("\r\n");
									writer.write("  Scenario: " + scenarioId + "-iteration-" + iteration);
//									writer.write(
//											"  Scenario: " + scenarioId + "-iteration" + iteration + ":" + description);
								} else {
									writer.write("\r\n");
									writer.write(line);
								}
								while ((line = bufferedReader.readLine()) != null) {
									if (line.contains("Scenario:")) {
										break;
									} else {
										writer.write("\r\n");
										writer.write(line);
									}
								}
							}
//							writer.write("\r\n");
						}
						writer.close();
						bufferedReader.close();
					} else {
						FileWriter writer = new FileWriter(System.getProperty("user.dir")
								+ "/src/test/resources/runnablefeatures/" + currentFeature + ".feature", true);
						FileReader reader = new FileReader(System.getProperty("user.dir")
								+ "/src/test/resources/features/" + currentFeature + ".feature");
						BufferedReader bufferedReader = new BufferedReader(reader);
						String line;
						while ((line = bufferedReader.readLine()) != null) {
							if (line.contains("Scenario:") && line.contains(scenarioId)) {
//								if (line.contains("Scenario:") && line.contains(scenarioId) && line.contains(description)) {
								int iteration = Integer.parseInt(sh1.getRow(i).getCell(1).getStringCellValue());
								if (iteration > 1) {
									writer.write("\r\n");
									writer.write("  Scenario: " + scenarioId + "-iteration-" + iteration);
//									writer.write(
//											"  Scenario: " + scenarioId + "-iteration" + iteration + ":" + description);
								} else {
									writer.write("\r\n");
									writer.write(line);
								}
								while ((line = bufferedReader.readLine()) != null) {
									if (line.contains("Scenario:")) {
										break;
									} else {
										writer.write("\r\n");
										writer.write(line);
									}
								}
							}
//							writer.write("\r\n");
						}
						writer.close();
						bufferedReader.close();
					}
				}
			}

		}

	}

	/**
	 * Gets the scenario data.
	 *
	 * @author arunpand
	 * @param wb the wb
	 * @return the scenario data
	 */
	public static Map<String, List<String>> getScenarioData(XSSFWorkbook wb) {

		
		XSSFSheet sh = wb.getSheet("Runner");
		int row = sh.getLastRowNum();
//		System.out.println(row);
//		System.out.println(sh.getRow(4).getCell(0).getStringCellValue());
		Map<String, List<String>> scenarioIDs = new LinkedHashMap<String, List<String>>();
		for (int i = 1; i <= row; i++) {
			if (sh.getRow(i).getCell(3).getStringCellValue().equalsIgnoreCase("Y")) {
				List<String> datas = new ArrayList<String>();
				datas.add(sh.getRow(i).getCell(1).getStringCellValue());
				datas.add(sh.getRow(i).getCell(2).getStringCellValue());
				scenarioIDs.put(sh.getRow(i).getCell(0).getStringCellValue(), datas);
			}
		}

		return scenarioIDs;
	}

}
