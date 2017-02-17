package org.guy.rpg.dwg.ai;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.guy.rpg.dwg.models.KeyValuePair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.reflect.Whitebox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.stormpath.sdk.lang.Collections;

@RunWith( SpringJUnit4ClassRunner.class )
@SpringBootTest
public class AIManagerTest {

	@Autowired
	AIManager aiManager;
	
	@Test
	public void testResolvePlaceholders() throws Exception {
		// Given
		KeyValuePair mockKVPair1 = new KeyValuePair("one", "This value equals {{A}}.");
		KeyValuePair mockKVPair2 = new KeyValuePair("two", "This value equals {{B_C}}.");
		List<KeyValuePair> kvPairs = Collections.toList(mockKVPair1, mockKVPair2);
		
		KeyValuePair expectedMockKVPair1 = new KeyValuePair("one", "This value equals the real A.");
		KeyValuePair expectedMockKVPair2 = new KeyValuePair("two", "This value equals a complex {value}.");
		
		// When
		List<KeyValuePair> resolvedPairs = Whitebox.invokeMethod(aiManager, "resolvePlaceholders", kvPairs);
		
		// Then
		assertTrue(resolvedPairs.size() == 2);
		assertTrue(resolvedPairs.contains(expectedMockKVPair1));
		assertTrue(resolvedPairs.contains(expectedMockKVPair2));
	}
	
	@Test
	public void testResolvePlaceholders_mix() throws Exception {
		// Given
		KeyValuePair mockKVPair1 = new KeyValuePair("one", "This value equals {{A}}.");
		KeyValuePair mockKVPair2 = new KeyValuePair("two", "This value equals abc.");
		List<KeyValuePair> kvPairs = Collections.toList(mockKVPair1, mockKVPair2);
		
		KeyValuePair expectedMockKVPair1 = new KeyValuePair("one", "This value equals the real A.");
		KeyValuePair expectedMockKVPair2 = new KeyValuePair("two", "This value equals abc.");
		
		// When
		List<KeyValuePair> resolvedPairs = Whitebox.invokeMethod(aiManager, "resolvePlaceholders", kvPairs);
		
		// Then
		assertTrue(resolvedPairs.size() == 2);
		assertTrue(resolvedPairs.contains(expectedMockKVPair1));
		assertTrue(resolvedPairs.contains(expectedMockKVPair2));
	}
}
