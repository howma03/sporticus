package com.sporticus.interfaces;

import java.util.Properties;

public interface IServiceMetaData {

	enum TYPE {
		XML,
		JSON
	}

	Properties convertFrom(String data, TYPE type);

	String convertTo(Properties properties, TYPE type);
}
