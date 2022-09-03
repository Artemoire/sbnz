package app;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.kie.api.runtime.KieSession;

import model.Accommodation;
import model.Customer;
import model.Reservation;
import model.Tier;
import util.PredefinedLocations;

public class DiscountsTest {
	KieSession kSession = null;

	@Test
	public void newMemberDiscountTest() {
		kSession = AppReasoning.prepareSession();

		Accommodation accommodation = new Accommodation("Hotel Aleksandra", Tier.STONE, 2, 30);		

		Customer customer = new Customer("Deki//D", "deki.d@uns.ac.rs", Tier.STONE);
		customer.setRegisteredAt(new Date());
        Reservation r = accommodation.createReservation(customer, new Date(), new Date());

		kSession.insert(r);
		kSession.insert(customer);

		int fired = AppReasoning.runDiscounts(kSession);

		assertThat(fired, is(1));
		System.out.println("Finalizing bill... discount: "+r.getDiscount().getPercentage()*100+"%\n");
	}
	
	@Test
	public void accommodationDiscoveryDiscountTest() {
		kSession = AppReasoning.prepareSession();

		Accommodation accommodation = new Accommodation("Stari Sad", Tier.STONE, 0, 30);
		Accommodation another = new Accommodation("Hotel Aleksandra", Tier.STONE, 2, 30);
		Date now = new Date();

		Customer customer = new Customer("Deki//D", "deki.d@uns.ac.rs", Tier.IRON);
		customer.setRegisteredAt(DateUtils.addDays(now, -35));
		another.createReservation(customer, now, now).complete(4);
		another.createReservation(customer, now, now).complete(4);
		another.createReservation(customer, now, now).complete(4);
        Reservation r= accommodation.createReservation(customer, now, now);

		kSession.insert(r);
		kSession.insert(customer);

		int fired = AppReasoning.runDiscounts(kSession);

		assertThat(fired, is(1));		
		System.out.println("Finalizing bill... discount: "+r.getDiscount().getPercentage()*100+"%\n");
	}
	
	@Test
	public void couponApplyTest() {
		kSession = AppReasoning.prepareSession();

		Accommodation accommodation = new Accommodation("Stari Sad", Tier.STONE, 2, 30);
		Date now = new Date();

		Customer customer = new Customer("Deki//D", "deki.d@uns.ac.rs", Tier.IRON);
		customer.setRegisteredAt(DateUtils.addDays(now, -35));
		customer.awardCoupon(0.15);
		customer.awardCoupon(0.05);
		
		accommodation.createReservation(customer, now, now).complete(4);
        Reservation r= accommodation.createReservation(customer, now, now);

		kSession.insert(r);
		kSession.insert(customer);

		int fired = AppReasoning.runDiscounts(kSession);

		assertThat(fired, is(2));		
		System.out.println("Finalizing bill... discount: "+r.getDiscount().getPercentage()*100+"%\n");
	}
	
	@Test
	public void applyLoyaltyDiscount() {
		kSession = AppReasoning.prepareSession();

		Accommodation accommodation = new Accommodation("Stari Sad", Tier.STONE, 2, 30);
		accommodation.setLoyaltyDiscount(0.1);
		Date now = new Date();

		Customer customer = new Customer("Deki//D", "deki.d@uns.ac.rs", Tier.IRON);
		customer.setRegisteredAt(DateUtils.addDays(now, -35));
		
		accommodation.createReservation(customer, now, now).complete(4);
		accommodation.createReservation(customer, now, now).complete(4);
		accommodation.createReservation(customer, now, now).complete(4);
        Reservation r= accommodation.createReservation(customer, now, now);

		kSession.insert(r);
		kSession.insert(customer);

		int fired = AppReasoning.runDiscounts(kSession);

		assertThat(fired, is(1));
		System.out.println("Finalizing bill... discount: "+r.getDiscount().getPercentage()*100+"%\n");
	}
	
	@Test
	public void limitDiscount() {
		kSession = AppReasoning.prepareSession();

		Accommodation accommodation = new Accommodation("Stari Sad", Tier.STONE, 2, 30);
		accommodation.setLoyaltyDiscount(0.1);
		accommodation.setMaxDiscount(0.5);
		Date now = new Date();

		Customer customer = new Customer("Deki//D", "deki.d@uns.ac.rs", Tier.IRON);
		customer.setRegisteredAt(DateUtils.addDays(now, -35));
		customer.awardCoupon(0.45);
		
		accommodation.createReservation(customer, now, now).complete(4);
		accommodation.createReservation(customer, now, now).complete(4);
		accommodation.createReservation(customer, now, now).complete(4);
        Reservation r= accommodation.createReservation(customer, now, now);

		kSession.insert(r);
		kSession.insert(customer);

		int fired = AppReasoning.runDiscounts(kSession);

		assertThat(fired, is(3));
		System.out.println("Finalizing bill... discount: "+r.getDiscount().getPercentage()*100+"%\n");
	}

	
	@Test
	public void limitBadUserDiscountTest() {
		kSession = AppReasoning.prepareSession();

		Accommodation accommodation = new Accommodation("Stari Sad", Tier.STONE, 2, 30);
		accommodation.setLoyaltyDiscount(0.1);
		accommodation.setMaxDiscount(0.5);
		Date now = new Date();

		Customer customer = new Customer("Deki//D", "deki.d@uns.ac.rs", Tier.IRON);
		customer.setRegisteredAt(DateUtils.addDays(now, -35));
		customer.awardCoupon(0.425);
		
		accommodation.createReservation(customer, now, now).complete(2);
		accommodation.createReservation(customer, now, now).complete(2);
		accommodation.createReservation(customer, now, now).complete(2);
		accommodation.createReservation(customer, now, now).setCancelled(true);
		accommodation.createReservation(customer, now, now).setCancelled(true);
		accommodation.createReservation(customer, now, now).setCancelled(true);
        Reservation r= accommodation.createReservation(customer, now, now);

		kSession.insert(r);
		kSession.insert(customer);

		int fired = AppReasoning.runDiscounts(kSession);

		assertThat(fired, is(3));
		System.out.println("Finalizing bill... discount: "+r.getDiscount().getPercentage()*100+"%\n");
	}

}
