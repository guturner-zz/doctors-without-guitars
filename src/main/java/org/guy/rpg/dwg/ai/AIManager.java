package org.guy.rpg.dwg.ai;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.guy.rpg.dwg.models.KeyValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;

@Component
@PropertySource("classpath:properties/apiai.properties")
public class AIManager {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AIManager.class);

	// Value from src/main/resources/properties/apiai.properties
	@Value("${api.ai.apikey}")
	private String apiKey;
	
	AIConfiguration config;
	AIDataService dataService;
	
	@PostConstruct
    public void init() {
		config = new AIConfiguration(apiKey);
		dataService  = new AIDataService(config);
    }
	
	public String sendRequest(String statement) {
		String responseText = "Hmm, I need to think on that. Ask again later.";
		
		try {
			AIRequest request = new AIRequest(statement);
			AIResponse response = dataService.request(request);

			if (response.getStatus().getCode() == 200) {
				responseText = response.getResult().getFulfillment().getSpeech();
			} 
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		
		return responseText;
	}
	
	public Map<String, String> getResponses(String intentName) {
		Map<String, String> responseMap = new HashMap<String, String>();
		CSVReader csvReader = null;

		try {
			String csvFileName = "/properties/ai/" + intentName + "/responses.csv";
			InputStream is = AIManager.class.getResourceAsStream(csvFileName); 
			csvReader = new CSVReader(new InputStreamReader(is), '|', '"', 0);

			ColumnPositionMappingStrategy<KeyValuePair> mappingStrategy = new ColumnPositionMappingStrategy<KeyValuePair>();
			mappingStrategy.setType(KeyValuePair.class);

			// Fields in KeyValuePair POJO:
			String[] columns = new String[] { "key", "value" };
			mappingStrategy.setColumnMapping(columns);

			CsvToBean<KeyValuePair> ctb = new CsvToBean<KeyValuePair>();
			List<KeyValuePair> keyValuePairs = ctb.parse(mappingStrategy, csvReader);
			
			for (KeyValuePair kv : keyValuePairs) {
				responseMap.put(kv.getKey(), kv.getValue());
			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
		} finally {
			try {
				csvReader.close();
			} catch (Exception ex) {
				LOGGER.error(ex.getMessage());
			}
		}

		return responseMap;
	}
}
