package ro.ase.cts.classes;

import java.util.ArrayList;

import ro.ase.cts.exceptions.InvalidNameException;
import ro.ase.cts.exceptions.MathsException;
import ro.ase.cts.exceptions.NotSupportedSeatException;
import ro.ase.cts.exceptions.WrongTicketPriceException;

public class Cinema {
	
	private String cinemaName;
	private int noSeats;
	//private double[] ticketPrice;
	private ArrayList<Integer> ticketPrice;
	
	public static final int MAX_NAME_LETTERS = 40;
	public static final int MIN_NAME_LETTERS = 2;
	
	public static final int MINIMUM_SEAT_NUMBERS = 5;
	public static final int MAXIMUM_SEAT_NUMBERS = 150;

	
	//default constructor
	public Cinema() {
	//this.cinemaName=cinemaName;
	ticketPrice=new ArrayList<>();
	}
	
	//constructor with arguments
	public Cinema(String name, int seats, ArrayList<Integer> price) {
		this.cinemaName = name;
		this.noSeats = seats;
		this.ticketPrice = new ArrayList<>();
		for (Integer priceTicket : ticketPrice) {
			this.ticketPrice.add(priceTicket);
		}
	}
	
	//getters and setters
	public String getCinemaName() {
		return this.cinemaName;
	}

	public void setCinemaName(String name) throws InvalidNameException {
		if (name.length() < MIN_NAME_LETTERS) {
			throw new InvalidNameException("Name can't be less than " + MIN_NAME_LETTERS + " letters!");
		}
		if (name.length() > MAX_NAME_LETTERS) {
			throw new InvalidNameException("Name can't be more than  " + MAX_NAME_LETTERS + "letters");
		
		}
		this.cinemaName = name;
	}

	public int getNoSeats() {
		return this.noSeats;
	}

	public void setNoSeats(Integer noSeats) throws NotSupportedSeatException {
		if(noSeats == null)
			throw new NotSupportedSeatException(cinemaName);
		if(noSeats > MAXIMUM_SEAT_NUMBERS || noSeats < MINIMUM_SEAT_NUMBERS)
			throw new NotSupportedSeatException(cinemaName);
		this.noSeats = noSeats;
	}

	public ArrayList<Integer> getTicketPrices() {
		return this.ticketPrice;
	}

	public int getTicketPrice(int i) {
		return ticketPrice.get(i);
	}
	
	public void setTicketPrice(ArrayList<Integer> ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	//function that adds a new ticket price
	public void addTicket(int price) throws WrongTicketPriceException {
		if (price > 100 || price < 1) {
			throw new WrongTicketPriceException();
		}
		ticketPrice.add(price);
		
	}
	
	//function that computes the average price of the tickets
	public double averagePrice() {
		double sum = 0;
		for(Integer price : ticketPrice) {
			sum+=price;
		}
		return sum/ticketPrice.size();
	}

	//function that return the minimum price of a ticket
	public static int min(int[] values) throws MathsException {
		if(values.length < Integer.MIN_VALUE || values.length > Integer.MAX_VALUE)
			throw new MathsException("Not ok");
		int minimum = values[0];
		for(int i=1;i<values.length;i++)
			if(minimum > values[i])
				minimum = values[i];
		return minimum;
	}
	
}