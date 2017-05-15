package com.zodoto.util.ducks;

import static org.junit.Assert.*;

import org.junit.Test;

public class DuckEggTest {
	
	@Test
	public void testNewName() {
		DuckEgg egg = new DuckEgg("Fredo");
		assertEquals("Fredo", egg.getName());
	}
	
	@Test
	public void testNewNoRange() {
		DuckEgg egg = new DuckEgg("Fredo");
		DuckRange range = egg.getNextRange(10);
		assertTrue(range.isEmpty());
	}
	
	@Test
	public void testNewPlusRange() throws Exception {
		DuckEgg egg = new DuckEgg("Fredo");
		egg.addRange(new DuckRange(1, 100));
		DuckRange range = egg.getNextRange(10);
		assertTrue(range.isSuccess());
	}
	
	@Test
	public void testRangeStartKey() throws Exception {
		DuckEgg egg = new DuckEgg("Fredo");
		egg.addRange(new DuckRange(1, 100));
		DuckRange range = egg.getNextRange(10);
		assertEquals(1, range.getStartKey());
	}
	
	@Test
	public void testRangeEndKey() throws Exception {
		DuckEgg egg = new DuckEgg("Fredo");
		egg.addRange(new DuckRange(1, 100));
		DuckRange range = egg.getNextRange(10);
		assertEquals(10, range.getEndKey());
	}
	
	@Test
	public void testRangeEndKeyShort() throws Exception {
		DuckEgg egg = new DuckEgg("Fredo");
		egg.addRange(new DuckRange(1, 8));
		DuckRange range = egg.getNextRange(10);
		assertEquals(8, range.getEndKey());
	}
	
	@Test
	public void testRangeStartKeyMore() throws Exception {
		DuckEgg egg = new DuckEgg("Fredo");
		egg.addRange(new DuckRange(1, 100));
		egg.getNextRange(85);
		DuckRange range = egg.getNextRange(10);
		assertEquals(86, range.getStartKey());
	}
	
	@Test
	public void testRangeEndKeyMore() throws Exception {
		DuckEgg egg = new DuckEgg("Fredo");
		egg.addRange(new DuckRange(1, 100));
		egg.getNextRange(85);
		DuckRange range = egg.getNextRange(10);
		assertEquals(95, range.getEndKey());
	}
	
	@Test
	public void testRangeEndKeyShortMore() throws Exception {
		DuckEgg egg = new DuckEgg("Fredo");
		egg.addRange(new DuckRange(1, 90));
		egg.getNextRange(85);
		DuckRange range = egg.getNextRange(10);
		assertEquals(90, range.getEndKey());
	}
	
	@Test
	public void testRangeNeedMore() throws Exception {
		DuckEgg egg = new DuckEgg("Fredo");
		egg.addRange(new DuckRange(1, 20));
		assertTrue(egg.needMore(25));
	}
	
	@Test
	public void testRangeNeedNoMore() throws Exception {
		DuckEgg egg = new DuckEgg("Fredo");
		egg.addRange(new DuckRange(1, 20));
		assertFalse(egg.needMore(15));
	}
	
	@Test
	public void testOnDeckName() throws Exception {
		DuckEgg egg = new DuckEgg("Fredo");
		egg.addRange(new DuckRange(1, 20));
		egg.addRange(new DuckRange(61, 80));
		DuckData duckData = egg.getData();
		assertEquals("fredo", duckData.getName());
	}
	
	@Test
	public void testOnDeckNextKey() throws Exception {
		DuckEgg egg = new DuckEgg("Fredo");
		egg.addRange(new DuckRange(1, 20));
		egg.addRange(new DuckRange(61, 80));
		DuckData duckData = egg.getData();
		assertEquals(1, duckData.getNextKey());
	}
	
	@Test
	public void testOnDeckEndKey() throws Exception {
		DuckEgg egg = new DuckEgg("Fredo");
		egg.addRange(new DuckRange(1, 20));
		egg.addRange(new DuckRange(61, 80));
		DuckData duckData = egg.getData();
		assertEquals(20, duckData.getEndKey());
	}
	
	@Test
	public void testOnDeckNextStartKey() throws Exception {
		DuckEgg egg = new DuckEgg("Fredo");
		egg.addRange(new DuckRange(1, 20));
		egg.addRange(new DuckRange(61, 80));
		DuckData duckData = egg.getData();
		assertEquals(61, duckData.getOnDeckStartKey());
	}
	
	@Test
	public void testOnDeckNextEndKey() throws Exception {
		DuckEgg egg = new DuckEgg("Fredo");
		egg.addRange(new DuckRange(1, 20));
		egg.addRange(new DuckRange(61, 80));
		DuckData duckData = egg.getData();
		assertEquals(80, duckData.getOnDeckEndKey());
	}

	
	@Test
	public void testNextOnDeckNextKey() throws Exception {
		DuckEgg egg = new DuckEgg("Fredo");
		egg.addRange(new DuckRange(1, 20));
		egg.addRange(new DuckRange(61, 80));
		egg.getNextRange(25);
		DuckData duckData = egg.getData();
		assertEquals(61, duckData.getNextKey());
	}
	
	@Test
	public void testNextOnDeckEndKey() throws Exception {
		DuckEgg egg = new DuckEgg("Fredo");
		egg.addRange(new DuckRange(1, 20));
		egg.addRange(new DuckRange(61, 80));
		egg.getNextRange(25);
		DuckData duckData = egg.getData();
		assertEquals(80, duckData.getEndKey());
	}
	
	@Test
	public void testNextOnDeckNextStartKey() throws Exception {
		DuckEgg egg = new DuckEgg("Fredo");
		egg.addRange(new DuckRange(1, 20));
		egg.addRange(new DuckRange(61, 80));
		egg.getNextRange(25);
		DuckData duckData = egg.getData();
		assertEquals(-1, duckData.getOnDeckStartKey());
	}
	
	@Test
	public void testNextOnDeckNextEndKey() throws Exception {
		DuckEgg egg = new DuckEgg("Fredo");
		egg.addRange(new DuckRange(1, 20));
		egg.addRange(new DuckRange(61, 80));
		egg.getNextRange(25);
		DuckData duckData = egg.getData();
		assertEquals(-1, duckData.getOnDeckEndKey());
	}
}
