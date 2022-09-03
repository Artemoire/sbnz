package app;

import org.apache.commons.lang.time.DateUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import model.Accommodation;
import model.Customer;
import model.Reservation;
import model.Tier;
import util.KnowledgeSessionHelper;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

public class ClassifyAccommodationsTest {

	KieSession kSession = null;

	@Test
	public void upgradeToIronTest() {
		kSession = AppReasoning.prepareSession();
		kSession.setGlobal("tStart", DateUtils.addMinutes(new Date(), -60));
		kSession.setGlobal("tEnd", DateUtils.addMinutes(new Date(), 60));

		Accommodation accommodation = new Accommodation("Hotel Aleksandra", Tier.STONE, 3, 30);
		Customer customer = new Customer("Deki//D", "deki.d@uns.ac.rs", Tier.STONE);

		Date now = new Date();

		accommodation.createReservation(customer, now, now).setCompleted(true);
		accommodation.createReservation(customer, now, now).setCompleted(true);

		kSession.insert(accommodation);

		int fired = AppReasoning.classifyAccommodations(kSession);

		assertThat(fired, is(1));
		assertThat(accommodation.getTier(), is(Tier.IRON));
	}

	@Test
	public void upgradeToDiamondTest() {
		kSession = AppReasoning.prepareSession();
		kSession.setGlobal("tStart", DateUtils.addMinutes(new Date(), -60));
		kSession.setGlobal("tEnd", DateUtils.addMinutes(new Date(), 60));

		Accommodation accommodationGood = new Accommodation("Hotel Aleksandra", Tier.IRON, 4, 60);
		Accommodation accommodationBad = new Accommodation("Hotel Parking", Tier.IRON, 4, 60);
		Customer customer = new Customer("Deki//D", "deki.d@uns.ac.rs", Tier.DIAMOND);

		Date now = new Date();

		accommodationGood.createReservation(customer, now, now).complete(4);
		accommodationGood.createReservation(customer, now, now).complete(4);
		accommodationBad.createReservation(customer, now, now).complete(2);
		accommodationBad.createReservation(customer, now, now).complete(2);

		kSession.insert(accommodationGood);
		kSession.insert(accommodationBad);

		int fired = AppReasoning.classifyAccommodations(kSession);

		assertThat(fired, is(1));
		assertThat(accommodationGood.getTier(), is(Tier.DIAMOND));
		assertThat(accommodationBad.getTier(), is(Tier.IRON));
	}

	@Test
	public void upgradeToNetheriteTest() {
		kSession = AppReasoning.prepareSession();
		kSession.setGlobal("tStart", DateUtils.addMinutes(new Date(), -60));
		kSession.setGlobal("tEnd", DateUtils.addMinutes(new Date(), 60));

		Accommodation accommodation = new Accommodation("Hotel Aleksandra", Tier.DIAMOND, 4, 150);
		Customer customer = new Customer("Deki//D", "deki.d@uns.ac.rs", Tier.DIAMOND);

		Date now = new Date();

		for (int i = 0; i < 10; i++)
			accommodation.createReservation(customer, now, now).complete(4);

		kSession.insert(accommodation);
		int fired = AppReasoning.classifyAccommodations(kSession);

		assertThat(fired, is(1));
		assertThat(accommodation.getTier(), is(Tier.NETHERITE));
	}
	
	@Test
	public void downgradeToDiamondTest() {
		kSession = AppReasoning.prepareSession();
		kSession.setGlobal("tStart", DateUtils.addMinutes(new Date(), -60));
		kSession.setGlobal("tEnd", DateUtils.addMinutes(new Date(), 60));

		Accommodation accommodation = new Accommodation("Hotel Aleksandra", Tier.NETHERITE, 4, 150);
		Customer customer = new Customer("Deki//D", "deki.d@uns.ac.rs", Tier.DIAMOND);

		Date now = new Date();

		accommodation.createReservation(customer, now, now).complete(3);
		
		kSession.insert(accommodation);		
		int fired = AppReasoning.classifyAccommodations(kSession);

		assertThat(fired, is(1));
		assertThat(accommodation.getTier(), is(Tier.DIAMOND));
	}
	
	@Test
	public void downgradeToIronTest() {
		kSession = AppReasoning.prepareSession();
		kSession.setGlobal("tStart", DateUtils.addMinutes(new Date(), -60));
		kSession.setGlobal("tEnd", DateUtils.addMinutes(new Date(), 60));

		Accommodation accommodation = new Accommodation("Hotel Aleksandra", Tier.DIAMOND, 4, 150);
		Customer customer = new Customer("Deki//D", "deki.d@uns.ac.rs", Tier.DIAMOND);

		Date now = new Date();

		accommodation.createReservation(customer, now, now).complete(2);
		
		kSession.insert(accommodation);		
		int fired = AppReasoning.classifyAccommodations(kSession);

		assertThat(fired, is(1));
		assertThat(accommodation.getTier(), is(Tier.IRON));
	}

}