package com.zodoto.util.ducks;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class DuckControlServiceTest {
	
	DuckConfiguration duckConfiguration;
	DuckSourceMock duckSource;
	DuckPersistMock duckPersist;
	DuckControlService duckControl;

	@Before
	public void setUp() throws Exception {
		duckConfiguration = new DuckConfiguration();
		duckSource = new DuckSourceMock();
		duckPersist = new DuckPersistMock();
		duckControl = new DuckControlService();

		duckControl.setDuckPersist(duckPersist);
		duckControl.setDuckSource(duckSource);
		duckControl.setDuckConfiguration(duckConfiguration);
		duckControl.setDuckLog(new DuckLogSysout());
	}
	
	private void initializeTestInitial()	{
		duckConfiguration.setRequestSize(100);
		duckConfiguration.setResponseSize(10);
		duckConfiguration.setRetries(3);
		duckConfiguration.setWaitMillis(100);
		duckConfiguration.setWaitCount(30);
		duckConfiguration.setWatermark(30);
		
		duckSource.duckRangeStatus = DuckStatus.SUCCESS;
		duckSource.duckRangeStartKey = 201;
		duckSource.duckRangeEndKey = 300;
		duckSource.duckStatus = DuckStatus.SUCCESS;
		duckSource.delay = 0;
		duckSource.maximumSize = Integer.MIN_VALUE;
		duckSource.name = "";
	}

	
	public DuckRange testInitialOne() throws Exception {
		initializeTestInitial();
		return duckControl.get("Harper");
	}

	@Test
	public void testInitialOneStartKey() throws Exception {
		assertEquals(201, testInitialOne().getStartKey());
	}

	@Test
	public void testInitialOneEndKey() throws Exception {
		assertEquals(210, testInitialOne().getEndKey());
	}

	@Test
	public void testInitialOneName() throws Exception {
		testInitialOne();
		assertEquals("harper", duckSource.name);
	}

	
	public DuckRange testInitialTwo() throws Exception {
		initializeTestInitial();
		duckControl.get("Raven");
		duckSource.name = "";
		return duckControl.get("Raven");
	}
	
	@Test
	public void testInitialTwoStartKey() throws Exception {
		assertEquals(211, testInitialTwo().getStartKey());
	}

	@Test
	public void testInitialTwoEndKey() throws Exception {
		assertEquals(220, testInitialTwo().getEndKey());
	}

	@Test
	public void testInitialTwoName() throws Exception {
		testInitialTwo();
		assertEquals("", duckSource.name);
	}

	
	public DuckRange testInitialThree() throws Exception {
		initializeTestInitial();
		duckControl.get("Octavia");
		duckControl.get("Octavia", 30);
		duckSource.name = "";
		return duckControl.get("Octavia");
	}

	@Test
	public void testInitialThreeStartKey() throws Exception {
		assertEquals(221, testInitialThree().getStartKey());
	}

	@Test
	public void testInitialThreeEndKey() throws Exception {
		assertEquals(230, testInitialThree().getEndKey());
	}

	@Test
	public void testInitialThreeName() throws Exception {
		testInitialThree();
		assertEquals("", duckSource.name);
	}

	

	public DuckRange testInitialFour() throws Exception {
		initializeTestInitial();
		duckControl.get("Octavia");
		duckControl.get("Octavia");
		duckControl.get("Octavia");
		duckControl.get("Octavia");
		duckSource.name = "";
		return duckControl.get("Octavia");
	}

	@Test
	public void testInitialFourStartKey() throws Exception {
		assertEquals(241, testInitialFour().getStartKey());
	}

	@Test
	public void testInitialFourEndKey() throws Exception {
		assertEquals(250, testInitialFour().getEndKey());
	}

	@Test
	public void testInitialFourName() throws Exception {
		testInitialFour();
		assertEquals("", duckSource.name);
	}

	

	public void testPersist() throws Exception{
		duckConfiguration.setRequestSize(100);
		duckConfiguration.setResponseSize(10);
		duckConfiguration.setRetries(3);
		duckConfiguration.setWaitMillis(100);
		duckConfiguration.setWaitCount(30);
		duckConfiguration.setWatermark(30);

		List<DuckData> persist = new ArrayList<DuckData>();
		persist.add(new DuckData().setName("Austin").setNextKey(1001).setEndKey(2000));
		persist.add(new DuckData().setName("Boston").setNextKey(10001).setEndKey(20000));
		persist.add(new DuckData().setName("Chicago").setNextKey(100001).setEndKey(200000));
		duckPersist.duckList = persist;
		duckControl.initialize();
		
	}
	
	@Test
	public void testPersist1Start() throws Exception{
		testPersist();
		DuckRange dr = duckControl.get("Austin");
		assertEquals(1001, dr.getStartKey());
	}
	
	@Test
	public void testPersist1End() throws Exception{
		testPersist();
		DuckRange dr = duckControl.get("Austin");
		assertEquals(1010, dr.getEndKey());
	}
	
	@Test
	public void testPersist2Start() throws Exception{
		testPersist();
		DuckRange dr = duckControl.get("Boston");
		assertEquals(10001, dr.getStartKey());
	}
	
	@Test
	public void testPersist2End() throws Exception{
		testPersist();
		DuckRange dr = duckControl.get("Boston");
		assertEquals(10010, dr.getEndKey());
	}
	
	@Test
	public void testPersist3Start() throws Exception{
		testPersist();
		DuckRange dr = duckControl.get("Chicago");
		assertEquals(100001, dr.getStartKey());
	}
	
	@Test
	public void testPersist3End() throws Exception{
		testPersist();
		DuckRange dr = duckControl.get("Chicago");
		assertEquals(100010, dr.getEndKey());
	}
	
	@Test
	public void testPersist4Start() throws Exception{
		testPersist();
		DuckRange dr = duckControl.get("Austin");
		dr = duckControl.get("Austin");
		assertEquals(1011, dr.getStartKey());
	}
	
	@Test
	public void testPersist4End() throws Exception{
		testPersist();
		DuckRange dr = duckControl.get("Austin");
		dr = duckControl.get("Austin");
		assertEquals(1020, dr.getEndKey());
	}

}
