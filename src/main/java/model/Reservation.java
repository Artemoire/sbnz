package model;

import java.io.Serializable;
import java.util.Date;

public class Reservation implements Serializable {

	private static final long serialVersionUID = 1L;

	private Customer customer;
	private Accommodation accommodation;
	private int rated;
	private Date reservedFrom;
	private Date reservedTo;
	private boolean cancelled;
	private boolean completed;
	private Discount discount;

	public Reservation() {
	}

	public void applyDiscount(double percentage) {
		if (this.discount == null)
			this.discount = new Discount(0.0);
		this.discount.setPercentage(this.discount.getPercentage() + percentage);
	}

	public void complete(int rating) {
		this.rated = rating;
		this.completed = true;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Accommodation getAccommodation() {
		return accommodation;
	}

	public void setAccommodation(Accommodation accommodation) {
		this.accommodation = accommodation;
	}

	public int getRated() {
		return rated;
	}

	public void setRated(int rated) {
		this.rated = rated;
	}

	public Date getReservedFrom() {
		return reservedFrom;
	}

	public void setReservedFrom(Date reservedFrom) {
		this.reservedFrom = reservedFrom;
	}

	public Date getReservedTo() {
		return reservedTo;
	}

	public void setReservedTo(Date reservedTo) {
		this.reservedTo = reservedTo;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public Discount getDiscount() {
		return discount;
	}

	public void setDiscount(Discount discount) {
		this.discount = discount;
	}

}
