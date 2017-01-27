package org.guy.rpg.dwg.csv;

import static org.junit.Assert.*;

import java.util.List;

import org.guy.rpg.dwg.Application;
import org.guy.rpg.dwg.models.KeyValuePair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes={Application.class})
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
