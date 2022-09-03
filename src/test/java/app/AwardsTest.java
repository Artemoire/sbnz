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

public class AwardsTest {
	KieSession kSession = null;

	@Test
	public void returningMemberCouponAwardTest() {
		kSession = AppReasoning.prepareSession();

		Accommodation accommodation = new Accommodation("Hotel Aleksandra", Tier.STONE, 2, 30);		

		Customer customer = new Customer("Deki//D", "deki.d@uns.ac.rs", Tier.STONE);
		customer.setRegisteredAt(DateUtils.addYears(new Date(), -2));
		
		kSession.insert(customer);

		int fired = AppReasoning.runAwards(kSession);

		assertThat(fired, is(1));
	}
	
	@Test
	public void firstLuxuryCouponAwardTest() {
		kSession = AppReasoning.prepareSession();

		Accommodation accommodation = new Accommodation("Hotel Dunavac", Tier.STONE, 4, 151);		

		Customer customer = new Customer("Deki//D", "deki.d@uns.ac.rs", Tier.IRON);
		customer.setRegisteredAt(DateUtils.addYears(new Date(), -2));
		accommodation.createReservation(customer, new Date(), new Date()).complete(2);
		
		kSession.insert(customer);

		int fired = AppReasoning.runAwards(kSession);

		assertThat(fired, is(1));
	}

}
