package org.guy.rpg.dwg.csv;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.guy.rpg.dwg.models.KeyValuePair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith( SpringJUnit4ClassRunner.class )
@SpringBootTest
public class CSVManagerTest {

	@Autowired
	CSVManager csvManager;
	
	@Test
	public void testGetCSVAsListOfPOJOs() throws Exception {
		// Given
		String csvFileName = "/properties/ai/test.intent/responses.csv";
		KeyValuePair expectedKVPair = new KeyValuePair("bard", "A response for Bards!");
		
		// When
		List<KeyValuePair> kvPairs = csvManager.getCSVAsListOfPOJOs(KeyValuePair.class, csvFileName, "key", "value");
		
		// Then
		assertTrue(kvPairs.size() == 1);
		assertTrue(kvPairs.contains(expectedKVPair));
	}
	
}
