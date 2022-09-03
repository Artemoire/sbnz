package model;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Accommodation implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private Location location;
	private double distanceFromLocation;
	private Tier tier;
	private int rating;
	private double price;
	private List<Reservation> reservations;

	private boolean hasKitchen;
	private boolean hasWaterCooker;
	private boolean hasWifi;
	private boolean hasAirCon;
	private boolean hasSauna;
	private boolean hasPool;
	private boolean hasGym;

	private double maxDiscount;
	private double loyaltyDiscount;

	public Accommodation() {
		this.reservations = new LinkedList<>();
		this.maxDiscount = 0.9;
	}

	public Accommodation(String name, Tier tier, int rating, double price) {
		this.name = name;
		this.tier = tier;
		this.rating = rating;
		this.price = price;
		this.reservations = new LinkedList<>();
		this.maxDiscount = 0.9;
	}

	public Reservation createReservation(Customer customer, Date from, Date to) {
		Reservation r = new Reservation();

		r.setCustomer(customer);
		r.setAccommodation(this);
		r.setReservedFrom(from);
		r.setReservedTo(to);

		customer.getReservations().add(r);
		reservations.add(r);

		return r;
	}

	public boolean isPrefferedBy(Customer c) {
		long treshold = Math.round(0.65 * (double) c.getReservations().size());
		int i = 0;
		if (c.getReservations().stream().filter(r -> r.getAccommodation().hasKitchen).count() >= treshold && hasKitchen)
			i++;
		if (c.getReservations().stream().filter(r -> r.getAccommodation().hasWaterCooker).count() >= treshold
				&& hasWaterCooker)
			i++;
		if (c.getReservations().stream().filter(r -> r.getAccommodation().hasWifi).count() >= treshold
				&& hasWifi)
			i++;
		if (c.getReservations().stream().filter(r -> r.getAccommodation().hasAirCon).count() >= treshold
				&& hasAirCon)
			i++;
		if (c.getReservations().stream().filter(r -> r.getAccommodation().hasSauna).count() >= treshold
				&& hasSauna)
			i++;
		if (c.getReservations().stream().filter(r -> r.getAccommodation().hasPool).count() >= treshold
				&& hasPool)
			i++;
		if (c.getReservations().stream().filter(r -> r.getAccommodation().hasGym).count() >= treshold
				&& hasGym)
			i++;
		return i >= 3;
	}

	public boolean isFree(Date from, Date to) {
		return reservations.stream().filter(r -> r.getReservedFrom().before(to) && r.getReservedTo().after(from))
				.count() == 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public double getDistanceFromLocation() {
		return distanceFromLocation;
	}

	public void setDistanceFromLocation(double distanceFromLocation) {
		this.distanceFromLocation = distanceFromLocation;
	}

	public Tier getTier() {
		return tier;
	}

	public void setTier(Tier tier) {
		this.tier = tier;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	public boolean isHasKitchen() {
		return hasKitchen;
	}

	public void setHasKitchen(boolean hasKitchen) {
		this.hasKitchen = hasKitchen;
	}

	public boolean isHasWaterCooker() {
		return hasWaterCooker;
	}

	public void setHasWaterCooker(boolean hasWaterCooker) {
		this.hasWaterCooker = hasWaterCooker;
	}

	public boolean isHasWifi() {
		return hasWifi;
	}

	public void setHasWifi(boolean hasWifi) {
		this.hasWifi = hasWifi;
	}

	public boolean isHasAirCon() {
		return hasAirCon;
	}

	public void setHasAirCon(boolean hasAirCon) {
		this.hasAirCon = hasAirCon;
	}

	public boolean isHasSauna() {
		return hasSauna;
	}

	public void setHasSauna(boolean hasSauna) {
		this.hasSauna = hasSauna;
	}

	public boolean isHasPool() {
		return hasPool;
	}

	public void setHasPool(boolean hasPool) {
		this.hasPool = hasPool;
	}

	public boolean isHasGym() {
		return hasGym;
	}

	public void setHasGym(boolean hasGym) {
		this.hasGym = hasGym;
	}

	public double getMaxDiscount() {
		return maxDiscount;
	}

	public void setMaxDiscount(double maxDiscount) {
		this.maxDiscount = maxDiscount;
	}

	public double getLoyaltyDiscount() {
		return loyaltyDiscount;
	}

	public void setLoyaltyDiscount(double loyaltyDiscount) {
		this.loyaltyDiscount = loyaltyDiscount;
	}

}
