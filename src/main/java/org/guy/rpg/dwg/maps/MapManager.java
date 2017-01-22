package org.guy.rpg.dwg.maps;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.guy.rpg.dwg.models.Landmark;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;

public class MapManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(MapManager.class);
	private static final String CSV_FILE_NAME = "/properties/map/landmarks.csv";

	public static List<Landmark> getLandmarks() {
		List<Landmark> landmarks = new ArrayList<Landmark>();

		CSVReader csvReader = null;

		try {
			InputStream is = MapManager.class.getResourceAsStream(CSV_FILE_NAME); 
			csvReader = new CSVReader(new InputStreamReader(is), '|', '"', 0);

			ColumnPositionMappingStrategy<Landmark> mappingStrategy = new ColumnPositionMappingStrategy<Landmark>();
			mappingStrategy.setType(Landmark.class);

			// Fields in Landmark POJO:
			String[] columns = new String[] { "name", "description", "longitude", "latitude" };
			mappingStrategy.setColumnMapping(columns);

			CsvToBean<Landmark> ctb = new CsvToBean<Landmark>();
			landmarks = ctb.parse(mappingStrategy, csvReader);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
		} finally {
			try {
				csvReader.close();
			} catch (Exception ex) {
				LOGGER.error(ex.getMessage());
			}
		}

		return landmarks;
	}

}
