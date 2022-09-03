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

public class RecommendActionsTest {
	KieSession kSession = null;

	@Test
	public void recommendDisableDiscountsTest() {
		kSession = AppReasoning.prepareSession();
		kSession.setGlobal("tStart", DateUtils.addMinutes(new Date(), -60));
		kSession.setGlobal("tEnd", DateUtils.addMinutes(new Date(), 60));

		Accommodation accommodation1 = new Accommodation("Hotel Aleksandra", Tier.STONE, 2, 150);
		Accommodation accommodation2 = new Accommodation("Hotel Parking", Tier.STONE, 2, 150);

		accommodation1.setLocation(PredefinedLocations.NOVI_SAD);
		accommodation1.setDistanceFromLocation(5.0);
		accommodation2.setLocation(PredefinedLocations.NOVI_SAD);
		accommodation2.setDistanceFromLocation(16.0);

		Customer customer = new Customer("Deki//D", "deki.d@uns.ac.rs", Tier.STONE);

//        accommodation.createReservation(customer, new Date(), new Date()).setCompleted(true);

		kSession.insert(accommodation1);
		kSession.insert(accommodation2);

		int fired = AppReasoning.runRecommendations(kSession);

		assertThat(fired, is(1));
	}
	
	@Test
	public void recommendPriceChange() {
		kSession = AppReasoning.prepareSession();
		kSession.setGlobal("tStart", DateUtils.addMinutes(new Date(), -60));
		kSession.setGlobal("tEnd", DateUtils.addMinutes(new Date(), 60));

		Accommodation accommodation1 = new Accommodation("Hotel Aleksandra", Tier.STONE, 3, 100);
		Accommodation accommodation2 = new Accommodation("Hotel Parking", Tier.STONE, 3, 120);

		accommodation1.setLocation(PredefinedLocations.NOVI_SAD);
		accommodation2.setLocation(PredefinedLocations.NOVI_SAD);

		kSession.insert(accommodation1);
		kSession.insert(accommodation2);

		int fired = AppReasoning.runRecommendations(kSession);

		assertThat(fired, is(2));
	}

}
