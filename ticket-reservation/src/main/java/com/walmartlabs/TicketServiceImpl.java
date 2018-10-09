package com.walmartlabs;

import com.walmartlabs.TicketService;
import com.walmartlabs.SeatHold;
import java.util.HashMap;

/**
 * 
 * @author aswinram
 * Description: The program below contains services for ticket reservation such as:
 * 1) The user will be able to find the number of tickets available
 * 2) The user will be able to hold the number of seats he wants.
 * 3) The user will be able reserve the seats he wants, which is subject to tickets availability 
 *    and the time for which the user has held the tickets.
 **/

public class TicketServiceImpl implements TicketService {
	// Eager instantiation of the Singleton object
	private static TicketServiceImpl ticketServiceImpl = new TicketServiceImpl();
	// All the seat holders information stored in a hash map
	// The information held in this map used by the reserve seats service
	private HashMap<Long, SeatHold> seatHolderMapping = new HashMap<Long, SeatHold>();
	// A timer value of 10 seconds has been provided for every user to hold the
	// seats
	final private int TICKET_HOLDING_TIME = 10000;
	// The total number of tickets available for all the users is 10
	final private int MAX_NUM_TICKETS = 10;
	// Error message to be displayed to the user, if the user tries to book after
	// the maximum ticket holding time
	final private String TIMER_EXPIRY_MESSAGE = "Tickets no longer held";
	// Error message to be displayed to the user, if the tickets are unavailable
	final private String TICKETS_UNAVAILABLE_MESSAGE = "Tickets no longer available";

	private int numberOfTickets;

	// Constructor made private, to avoid duplicate creation of Objects
	private TicketServiceImpl() {
		numberOfTickets = MAX_NUM_TICKETS;
	}

	// Returns the eagerly instantiated TicketServiceImpl object
	public static TicketServiceImpl getInstance() {
		return ticketServiceImpl;
	}

	/**
	 * The number of seats in the venue that are not reserved
	 *
	 * @return the number of tickets available in the venue
	 */
	@Override
	public int numSeatsAvailable() {
		return numberOfTickets;
	}

	/**
	 * Find and hold the best available seats for a customer
	 * 
	 * @param numSeats      the number of seats to find and hold
	 * @param customerEmail unique identifier for the customer
	 * @return a SeatHold object identifying the specific seats and related
	 **/
	@Override
	public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
		synchronized (this) {
			// Since the block is synchronized, only one user can hold seats at a given
			// point of time
			// So,the timestamp at which the user held the seat,is used as seat holder id
			Long seatHolderId = System.currentTimeMillis();
			SeatHold seatHold = new SeatHold(seatHolderId, numSeats, customerEmail);
			seatHolderMapping.put(seatHolderId, seatHold);
			return seatHold;
		}
	}

	/**
	 * Commit seats held for a specific customer
	 *
	 * @param seatHoldId    the seat hold identifier
	 * @param customerEmail the email address of the customer to which the seat hold
	 *                      is assigned
	 * @return a reservation confirmation code
	 */
	@Override
	public String reserveSeats(Long seatHoldId, String customerEmail) {
		synchronized (this) {
			Long currentTime = System.currentTimeMillis();
			SeatHold seatHolder = seatHolderMapping.get(seatHoldId);
			int reservedSeats = seatHolder.getNumSeats();
			// The below condition checks if the user has held the seat more than the
			// maximum
			// ticket holding time and if the number of tickets requested by user is
			// available
			if ((currentTime - seatHolder.getSeatHoldId()) <= TICKET_HOLDING_TIME
					&& (numberOfTickets - reservedSeats) >= 0) {
				numberOfTickets -= reservedSeats;
				return String.valueOf(seatHoldId);
			} else if ((currentTime - seatHolder.getSeatHoldId()) > TICKET_HOLDING_TIME) {
				return TIMER_EXPIRY_MESSAGE;
			}
			return TICKETS_UNAVAILABLE_MESSAGE;
		}
	}
}
