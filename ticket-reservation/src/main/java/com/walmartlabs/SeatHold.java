package com.walmartlabs;
/**
 * 
 * @author aswinram
 * Description: The class below holds information about the person holding the seat.
 * It stores the following information:
 *  1) seatHoldId- The timestamp at which the user held the seat.
 *  2) numSeats- The number of seats, the user wanted to hold
 *  3) customerEmail- The email id of the user, who wanted to hold the tickets   
 */

public class SeatHold {
	private Long seatHoldId;
	private int numSeats;
	private String customerEmail;

	public SeatHold(Long seatHoldId, int numSeats, String customerEmail) {
		this.seatHoldId = seatHoldId;
		this.numSeats = numSeats;
		this.customerEmail = customerEmail;
	}

	public Long getSeatHoldId() {
		return seatHoldId;
	}

	public void setSeatHoldId(Long seatHoldId) {
		this.seatHoldId = seatHoldId;
	}

	public int getNumSeats() {
		return numSeats;
	}

	public void setNumSeats(int numSeats) {
		this.numSeats = numSeats;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
}
