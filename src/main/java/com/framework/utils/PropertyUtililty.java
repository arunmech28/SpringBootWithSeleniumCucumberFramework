package com.framework.utils;

import com.framework.constants.FrameworkConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class PropertyUtililty {
	
	public static String getProperty(String propertyName) {
		Properties globalProps = new Properties();
		try {
			globalProps.load(new FileInputStream(new File(FrameworkConstants.GLOBALCONFIGURATION)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return globalProps.getProperty(propertyName);
	}

}
