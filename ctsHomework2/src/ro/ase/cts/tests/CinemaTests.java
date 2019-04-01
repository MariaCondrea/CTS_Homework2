package ro.ase.cts.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.TestCase;
import ro.ase.cts.classes.Cinema;
import ro.ase.cts.exceptions.InvalidNameException;
import ro.ase.cts.exceptions.MathsException;
import ro.ase.cts.exceptions.NotSupportedSeatException;
import ro.ase.cts.exceptions.WrongTicketPriceException;

public class CinemaTests {
	Cinema cinema;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Tests start !");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("Tests end !");
	}
	
	
	@Before
	public void setUp() {
		this.cinema = new Cinema();
	}
	
	@After
	public void tearDown() {
		this.cinema = null;
	}
	
	
	//CORRECT Boundary Conditions Testing + Right BICEP
	@Test
	public void testNameConformance() throws InvalidNameException {
		cinema.setCinemaName("Maria");
		assertEquals("Maria",cinema.getCinemaName());
	}
	
	@Test(expected = InvalidNameException.class)
	public void testSetNameRangeAboveLimit() throws InvalidNameException {
		String name = " ";
		for (int i = 0; i < 51; i++) {
			name += "";
		}
		cinema.setCinemaName(name);
	}

	@Test(expected = InvalidNameException.class)
	public void testSetNameRangeBelowLimit() throws InvalidNameException {
		cinema.setCinemaName("M");
	}
	
	
	@Test
	public void testMinDescendingOrdering() {
		int[] values = {10,9,8,7,6,5};
		int expectedMin = 5;
		try {	
			int actualMin = 
					Cinema.min(values);
			assertEquals(expectedMin, actualMin);
		} catch (MathsException e) {
			fail("Exception");
		}
	}
	
	@Test
	public void testMinAscendingOrdering() {
		int[] values = {5,6,7,8,9,10};
		int expectedMin = 5;
		
		try {
			
			int actualMin = 
					Cinema.min(values);
			assertEquals(expectedMin, actualMin);
			
		} catch (MathsException e) {
			fail("Exception");
		}
	}
	
	
	@Test
	public void testAddSeatsNumberConformanceNormalValues() throws NotSupportedSeatException  {
		cinema.setNoSeats(40);
		assertEquals(40, cinema.getNoSeats());
	}

	@Test
	public void setSeatNumbersLowBoundaryValue() {
		int newSeatsNumber = Cinema.MINIMUM_SEAT_NUMBERS;
		try {
			cinema.setNoSeats(newSeatsNumber);
			int actualNumberOfSeats = cinema.getNoSeats();
			assertEquals(newSeatsNumber, actualNumberOfSeats);
		} catch (NotSupportedSeatException e) {
			fail("Exception not expected");
		}
	}
	
	@Test
	public void setSeatNumbersUpperBoundaryValue() {
		int newSeatsNumber = Cinema.MAXIMUM_SEAT_NUMBERS;
		try {
			cinema.setNoSeats(newSeatsNumber);
			int actualNumberOfSeats = cinema.getNoSeats();
			assertEquals(newSeatsNumber, actualNumberOfSeats);
		} catch (NotSupportedSeatException e) {
			fail("Exception not expected");
		}
	}
	
	@Test(expected = NotSupportedSeatException.class)
	public void testSetNumberSeatsLeftRange() throws NotSupportedSeatException {
		int newSeatsNumber = -10;
		cinema.setNoSeats(newSeatsNumber);
	}
	
	@Test(expected = NotSupportedSeatException.class)
	public void testSetNumberSeatsNullReference() throws NotSupportedSeatException {
		Integer newSeatsNumber = null;
		cinema.setNoSeats(newSeatsNumber);
	}
	
	//cardinality tests
	
	@Test(expected = Exception.class)
	public void testCardinalityZero() {
		int[] values=null;
		try{
		Cinema.min(values);
		fail("It doesn't throw an exception");
		}
		catch(MathsException e){
			assertTrue(true);
		}
	}
	
	@Test
	public void testCardinalityOne() {
		int[] values = {1};
		int expectedMin = Arrays.stream(values).min().getAsInt();
		try {
			int actualMin = Cinema.min(values);
			assertEquals(expectedMin, actualMin);
		} catch (MathsException e) {
			fail("Uh oh");
		}
	}
	
	@Test
	public void testCardinalityTWO() {
		int[] values = {9,4,5,1,-2};
		int expectedMin = Arrays.stream(values).min().getAsInt();
		try {
			int actualMin = Cinema.min(values);
			assertEquals(expectedMin, actualMin);
		} catch (MathsException e) {
			fail("Uh oh");
		}
	}
	
	
	@Test
	public void testAverageTime() throws WrongTicketPriceException  {
		Random rand = new Random();
		for (int i = 0; i < 100000; i++) {
			cinema.addTicket((rand.nextInt(9) + 1));
		}

		long start = System.currentTimeMillis();
		cinema.averagePrice();
		long finish = System.currentTimeMillis();

		if ((finish - start) < 1000) {
			assertTrue(true);
		} else {
			fail("Average computation takes too long");
		}
	}

	
	@Test
	public void testAddTicketPriceInverse() throws WrongTicketPriceException{
		cinema.addTicket(6);
		assertEquals(6, cinema.getTicketPrice(0));
	}
	
	@Test
	public void testCrossCheckAveragePriceTickets() throws WrongTicketPriceException {
		cinema.addTicket(12);
		cinema.addTicket(21);
		cinema.addTicket(49);
		cinema.addTicket(10);

		float expected = (float) cinema.averagePrice();
		float calculated = (cinema.getTicketPrice(0) + cinema.getTicketPrice(1)+ cinema.getTicketPrice(2) + cinema.getTicketPrice(3)) / 4;
		assertEquals(expected, calculated, 0.0f);
	}
	
	@Test
	public void testExternalReferenceAddTicketPrice() throws WrongTicketPriceException{
		cinema.addTicket(12);
		cinema.addTicket(21);
		cinema.addTicket(49);
		cinema.addTicket(10);

		int initialTicketPrice = cinema.getTicketPrice(0);
		cinema.getTicketPrices().add(0, 5);

		int actualTicketPrice = cinema.getTicketPrice(0);
		assertNotEquals(initialTicketPrice, actualTicketPrice);
	}
	
	@Test
	public void testAverageOrdering() throws WrongTicketPriceException {
		cinema.addTicket(12);
		cinema.addTicket(21);
		cinema.addTicket(49);
		cinema.addTicket(10);

		Collections.sort(cinema.getTicketPrices(), (g2, g3) -> g2 - g3);
		assertEquals(12, cinema.getTicketPrice(1));
	}
	
	@Test
	public void constructorTest() {
		try {
		new Cinema();
		ArrayList<Integer> ticketPrice = new ArrayList<>();
		ticketPrice.add(12);
		ticketPrice.add(21);
		ticketPrice.add(49);
	    new Cinema("Cinema City",200,ticketPrice);
		//assertThat(cinema,is(cinema.toString()));
		}
		catch(Exception e) {
			fail("Uh oh");
		}
	}
}
