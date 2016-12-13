package com.cubrid.common.core.util;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Constants {
	public static final String JVM_LOCALE;
	public static final List<String> SUPPORTED_LOCALES;

	static {
		SUPPORTED_LOCALES = Arrays.asList("en_US", "en_UK", "km_KH", "zh_CN", "jp_JP", "ko_KR", "tr_TR");
		String locale = Locale.getDefault().toString();

		if (!SUPPORTED_LOCALES.contains(locale)) {
			JVM_LOCALE = "en_US";
		} else {
			JVM_LOCALE = locale;
		}
	}
}
