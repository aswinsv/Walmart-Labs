package com.walmartlabs;

/**
 * @author aswinram
 * Description: The Ticket Holding Thread class has been created to mock an user object,
 * trying to hold tickets.
 */
import com.walmartlabs.SeatHold;
import com.walmartlabs.TicketServiceImpl;

public class TicketHoldingThread extends Thread {
	private TicketServiceImpl ticketServiceImpl;
	private int numSeats;
	private String customerEmail;
	private SeatHold seatHolder;

	public TicketHoldingThread(TicketServiceImpl ticketServiceImpl, int numSeats, String customerEmail) {
		this.ticketServiceImpl = ticketServiceImpl;
		this.numSeats = numSeats;
		this.customerEmail = customerEmail;
	}

	public void run() {
		seatHolder = ticketServiceImpl.findAndHoldSeats(numSeats, customerEmail);
	}

	public SeatHold getSeatHolder() {
		return seatHolder;
	}
}