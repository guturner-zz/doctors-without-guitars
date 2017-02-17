package org.guy.rpg.dwg.maps;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.guy.rpg.dwg.models.Landmark;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith( SpringJUnit4ClassRunner.class )
@SpringBootTest
public class MapManagerTest {

	@Autowired
	MapManager mapManager;
	
	@Test
	public void testGetLandmarks() throws Exception {
		// Given:
		List<Landmark> landmarks = null;
		Landmark expectedLandmark1 = new Landmark("Sample Landmark", "This is a landmark.", "25", "150");
		Landmark expectedLandmark2 = new Landmark("Sample Landmark 2", "Another \"landmark\"!", "30", "175");
		
		// When:
		landmarks = mapManager.getLandmarks();
		
		// Then:
		assertTrue("Landmark list should have been created.", landmarks != null);
		assertTrue("Sample CSV file has 2 rows.", landmarks.size() == 2);
		assertTrue(landmarks.contains(expectedLandmark1));
		assertTrue(landmarks.contains(expectedLandmark2));
	}
	
}
