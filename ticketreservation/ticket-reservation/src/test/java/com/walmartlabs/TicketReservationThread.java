package com.walmartlabs;

/**
 * @author aswinram
 * Description: The Ticket Reservation Thread class has been created to mock an user object,
 * trying to reserve tickets
 */
import com.walmartlabs.TicketServiceImpl;

public class TicketReservationThread extends Thread {
	private TicketServiceImpl ticketServiceImpl;
	private Long seatHolderId;
	private String customerEmail;
	private String confirmationCode;

	public TicketReservationThread(TicketServiceImpl ticketServiceImpl, Long seatHolderId, String customerEmail) {
		this.ticketServiceImpl = ticketServiceImpl;
		this.seatHolderId = seatHolderId;
		this.customerEmail = customerEmail;
	}

	public void run() {
		confirmationCode = ticketServiceImpl.reserveSeats(seatHolderId, customerEmail);
	}

	public String getConfirmationCode() {
		return confirmationCode;
	}

	public int getNumSeatsAvailable() {
		return ticketServiceImpl.numSeatsAvailable();
	}
}