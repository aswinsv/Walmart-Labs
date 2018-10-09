package com.walmartlabs;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 * 
 * @author aswinram
 * Description: The program below has a set of JUnit tests, to test ticket service application for
 * various scenarios. The scenarios used have been described, above each unit test. 
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AppTest {
	/**
	 * Test hold and reservation of seats 
	 * 1) User books 6 tickets using the application
	 * 2) After the reservation, the total number of tickets shown to the user should be 4
	 **/
	@Test
	public void testTicketsService1() {
		try {
			final TicketServiceImpl firstUserTicket = TicketServiceImpl.getInstance();
			TicketHoldingThread ticketHoldingThread = new TicketHoldingThread(firstUserTicket, 6,
					"aswinram.92@gmail.com");
			ticketHoldingThread.start();
			ticketHoldingThread.join(1000);
			SeatHold firstSeatHolder = ticketHoldingThread.getSeatHolder();
			TicketReservationThread ticketReservationThread = new TicketReservationThread(firstUserTicket,
					firstSeatHolder.getSeatHoldId(), firstSeatHolder.getCustomerEmail());
			ticketReservationThread.start();
			ticketReservationThread.join(1000);
			assertEquals(4, ticketReservationThread.getNumSeatsAvailable());
		} catch (InterruptedException ex) {
			System.out.println("Thread has been interrupted");
		}
	}

	/**
	 * Test hold and reservation of seats 
	 * 1) User finds and holds 2 tickets 
	 * 2) The user tries to reserve the seat after the maximum ticket holding time, which
	 *    is 10 seconds
	 * 3) The user is not able to reserve tickets and the application
	 *    throws an error message, which says "Tickets no longer held"
	 */
	@Test
	public void testTicketsService2() {
		try {
			final TicketServiceImpl firstUserTicket = TicketServiceImpl.getInstance();
			TicketHoldingThread ticketHoldingThread = new TicketHoldingThread(firstUserTicket, 2,
					"aswinram.92@gmail.com");
			ticketHoldingThread.start();
			ticketHoldingThread.join(1000);
			SeatHold firstSeatHolder = ticketHoldingThread.getSeatHolder();
			TicketReservationThread ticketReservationThread = new TicketReservationThread(firstUserTicket,
					firstSeatHolder.getSeatHoldId(), firstSeatHolder.getCustomerEmail());
			ticketReservationThread.sleep(11000);
			ticketReservationThread.start();
			ticketReservationThread.join(1000);
			assertEquals("Tickets no longer held", ticketReservationThread.getConfirmationCode());
		} catch (InterruptedException ex) {
			System.out.println("Thread has been interrupted");
		}
	}

	/**
	 * Test hold and reservation of seats 
	 * 1) The first user logs in and books 8 tickets
	 * 2) The second user logs in and tries to book 8 tickets. 
	 * 3) The second user is not able to reserve tickets and the application throws an error
	 * 4) message, which says "Tickets no longer available"
	 */
	@Test
	public void testTicketsService3() {
		try {
			final TicketServiceImpl firstUserTicket = TicketServiceImpl.getInstance();
			TicketHoldingThread firstUserHoldingThread = new TicketHoldingThread(firstUserTicket, 8,
					"aswinram.92@gmail.com");
			firstUserHoldingThread.start();
			firstUserHoldingThread.join(1000);
			SeatHold firstSeatHolder = firstUserHoldingThread.getSeatHolder();
			TicketReservationThread firstUserReservationThread = new TicketReservationThread(firstUserTicket,
					firstSeatHolder.getSeatHoldId(), firstSeatHolder.getCustomerEmail());
			firstUserReservationThread.start();
			firstUserReservationThread.sleep(1000);
			final TicketServiceImpl secondUserTicket = TicketServiceImpl.getInstance();
			TicketHoldingThread secondUserHoldingThread = new TicketHoldingThread(secondUserTicket, 8,
					"provogue_23@yahoo.com");
			secondUserHoldingThread.start();
			secondUserHoldingThread.join(1000);
			SeatHold secondSeatHolder = secondUserHoldingThread.getSeatHolder();
			TicketReservationThread secondUserReservationThread = new TicketReservationThread(secondUserTicket,
					secondSeatHolder.getSeatHoldId(), secondSeatHolder.getCustomerEmail());
			secondUserReservationThread.start();
			secondUserReservationThread.join(1000);
			assertEquals("Tickets no longer available", secondUserReservationThread.getConfirmationCode());
		} catch (InterruptedException ex) {
			System.out.println("Thread has been interrupted");
		}
	}

}
