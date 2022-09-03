package model;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private String email;
	private Tier tier = Tier.STONE;
	private List<Reservation> reservations;
	private List<Discount> coupons;
	private Date registeredAt;
	private Awards awards;

	public Customer() {
	}

	public Customer(String name, String email, Tier tier) {
		this.name = name;
		this.email = email;
		this.tier = tier;
		this.reservations = new LinkedList<>(); 
		this.coupons = new LinkedList<>();
		this.awards = new Awards();
		this.registeredAt = new Date();
	}

	public void awardCoupon(double percentage) {
		this.coupons.add(new Discount(percentage));
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Tier getTier() {
		return tier;
	}

	public void setTier(Tier tier) {
		this.tier = tier;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	public Date getRegisteredAt() {
		return registeredAt;
	}

	public void setRegisteredAt(Date registeredAt) {
		this.registeredAt = registeredAt;
	}

	public Awards getAwards() {
		return awards;
	}

	public void setAwards(Awards awards) {
		this.awards = awards;
	}

	public List<Discount> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<Discount> coupons) {
		this.coupons = coupons;
	}

	
	
}
