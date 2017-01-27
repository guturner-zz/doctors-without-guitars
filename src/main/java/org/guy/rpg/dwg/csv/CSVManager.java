package org.guy.rpg.dwg.csv;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.guy.rpg.dwg.models.KeyValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;

@Component
public class CSVManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(CSVManager.class);
	
	public <T> List<T> getCSVAsListOfPOJOs(Class<T> resultClass, String fileName, String ... colNames) {
		List<T> resultList = new ArrayList<T>();
		CSVReader csvReader = null;

		try {
			InputStream is = CSVManager.class.getResourceAsStream(fileName); 
			csvReader = new CSVReader(new InputStreamReader(is), '|', '"', 0);

			ColumnPositionMappingStrategy<T> mappingStrategy = new ColumnPositionMappingStrategy<T>();
			mappingStrategy.setType(resultClass);

			mappingStrategy.setColumnMapping(colNames);

			CsvToBean<T> ctb = new CsvToBean<T>();
			resultList = ctb.parse(mappingStrategy, csvReader);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			ex.printStackTrace();
		} finally {
			try {
				csvReader.close();
			} catch (Exception ex) {
				LOGGER.error(ex.getMessage());
			}
		}

		return resultList;
	}
	
	public Map<String, String> getKeyValuePairsFromCSVAsMap(String fileName) {
		List<KeyValuePair> list = getCSVAsListOfPOJOs(KeyValuePair.class, fileName, "key", "value");
		return list.stream().collect(Collectors.toMap( x -> x.getKey(), x -> x.getValue()));

	}
}
