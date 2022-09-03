package app;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.kie.api.runtime.KieSession;

import model.Accommodation;
import model.Customer;
import model.Tier;
import util.PredefinedLocations;

public class RecommendAccommodationsTest {
	KieSession kSession = null;

	@Test
	public void recommendAccommodationsTest() {
		kSession = AppReasoning.prepareSession();
		kSession.setGlobal("tStart", DateUtils.addMinutes(new Date(), -60));
		kSession.setGlobal("tEnd", DateUtils.addMinutes(new Date(), 60));

		Accommodation accommodation1 = new Accommodation("Hotel Aleksandra", Tier.IRON, 2, 150);
		Accommodation accommodation2 = new Accommodation("Hotel Dunavac", Tier.IRON, 2, 150);
		Accommodation accommodation3 = new Accommodation("Hotel Maroko", Tier.IRON, 2, 100);
		Accommodation accommodation4 = new Accommodation("Hotel Parking", Tier.DIAMOND, 2, 150);
		Accommodation accommodation5 = new Accommodation("Hotel Krntija", Tier.STONE, 2, 160);
		
		accommodation1.setHasAirCon(true); // MATCHES
		accommodation1.setHasWifi(true);
		accommodation1.setHasPool(true);
		
		accommodation2.setHasWifi(true); // DOESNT MATCH PREFERENCE
		accommodation2.setHasPool(true);
		
		accommodation3.setHasAirCon(true); // MATCHES BUT BELOW AVG PRICE
		accommodation3.setHasWifi(true);
		accommodation3.setHasPool(true);
		
		accommodation4.setHasAirCon(true); // MATCHES
		accommodation4.setHasWifi(true);
		accommodation4.setHasPool(true);
		
		accommodation5.setHasAirCon(true); // MATCHES BUT NOT TIER
		accommodation5.setHasWifi(true);
		accommodation5.setHasPool(true);

		accommodation1.setLocation(PredefinedLocations.NOVI_SAD);
		accommodation2.setLocation(PredefinedLocations.NOVI_SAD);
		accommodation3.setLocation(PredefinedLocations.NOVI_SAD);
		accommodation4.setLocation(PredefinedLocations.NOVI_SAD);
		accommodation5.setLocation(PredefinedLocations.NOVI_SAD);
		
		accommodation1.setDistanceFromLocation(0);
		accommodation2.setDistanceFromLocation(0);
		accommodation3.setDistanceFromLocation(0);
		accommodation4.setDistanceFromLocation(0);
		accommodation5.setDistanceFromLocation(0);

		Customer customer = new Customer("Deki//D", "deki.d@uns.ac.rs", Tier.IRON);
		customer.setRegisteredAt(DateUtils.addYears(new Date(), -2));
		
		for(int i = 0; i < 7; i++)
			accommodation1.createReservation(customer, new Date(), new Date()).setCompleted(true);
		for(int i = 0; i < 3; i++)
			accommodation2.createReservation(customer, new Date(), new Date()).setCompleted(true);

		kSession.insert(accommodation1);
		kSession.insert(accommodation2);
		kSession.insert(accommodation3);
		kSession.insert(accommodation4);
		kSession.insert(accommodation5);
		kSession.insert(customer);

		int fired = AppReasoning.runRecommendAccommodations(kSession);
		assertThat(fired, is(2));
	}
}
