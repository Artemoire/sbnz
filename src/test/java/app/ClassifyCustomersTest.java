package app;

import org.apache.commons.lang.time.DateUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import model.Accommodation;
import model.Customer;
import model.Tier;
import util.KnowledgeSessionHelper;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

public class ClassifyCustomersTest {

	KieSession kSession = null;
	
	@Test
    public void upgradeToIronTest() {
        kSession = AppReasoning.prepareSession();
        
        Accommodation accommodation = new Accommodation();
        Customer customer = new Customer("Deki//D", "deki.d@uns.ac.rs", Tier.STONE);
        accommodation.createReservation(customer, new Date(), new Date()).setCompleted(true);
        accommodation.createReservation(customer, new Date(), new Date()).setCompleted(true);
        accommodation.createReservation(customer, new Date(), new Date()).setCompleted(true);
        
        kSession.insert(customer);
        
        int fired = AppReasoning.classifyCustomers(kSession);
        
        assertThat(fired, is(1));
        assertThat(customer.getTier(), is(Tier.IRON));
    }
    
    @Test
    public void upgradeToDiamondTest1() {
    	kSession = AppReasoning.prepareSession();
        
        Accommodation accommodation = new Accommodation();
        accommodation.setPrice(150);

        Customer customer = new Customer("Deki//D", "deki.d@uns.ac.rs", Tier.IRON);
        Date now = new Date();
        for(int i = 0; i < 10; i++)
        	accommodation.createReservation(customer, now, now).setCompleted(true);        
        
        kSession.insert(customer);
        
        int fired = AppReasoning.classifyCustomers(kSession);
        
        assertThat(fired, is(1));
        assertThat(customer.getTier(), is(Tier.DIAMOND));
    }
    
    @Test
    public void upgradeToDiamondLuxuryTest() {
    	kSession = AppReasoning.prepareSession();
        
        Accommodation accommodation = new Accommodation();
        accommodation.setPrice(150);

        Customer customer = new Customer("Deki//D", "deki.d@uns.ac.rs", Tier.IRON);
        Date now = new Date();

        accommodation.createReservation(customer, DateUtils.addDays(now, -15), DateUtils.addDays(now, -10)).setCompleted(true);
        accommodation.createReservation(customer, DateUtils.addDays(now, -25), DateUtils.addDays(now, -20)).setCompleted(true);                
        
        kSession.insert(customer);
        
        int fired = AppReasoning.classifyCustomers(kSession);
        
        assertThat(fired, is(1));
        assertThat(customer.getTier(), is(Tier.DIAMOND));
    }
    
    @Test
    public void upgradeToNetheriteTest() {
    	kSession = AppReasoning.prepareSession();
        
        Accommodation accommodation = new Accommodation();
        accommodation.setPrice(150);

        Customer customer = new Customer("Deki//D", "deki.d@uns.ac.rs", Tier.DIAMOND);
        Date now = new Date();

        accommodation.createReservation(customer, DateUtils.addDays(now, -15), DateUtils.addDays(now, -10)).setCompleted(true);
        accommodation.createReservation(customer, DateUtils.addDays(now, -25), DateUtils.addDays(now, -20)).setCompleted(true);
        accommodation.createReservation(customer, DateUtils.addDays(now, -15), DateUtils.addDays(now, -10)).setCompleted(true);
        accommodation.createReservation(customer, DateUtils.addDays(now, -25), DateUtils.addDays(now, -20)).setCompleted(true);
        
        kSession.insert(customer);
        
        int fired = AppReasoning.classifyCustomers(kSession);
        
        assertThat(fired, is(1));
        assertThat(customer.getTier(), is(Tier.NETHERITE));
    }
    
    @Test
    public void downgradeToDiamondTest() {
    	kSession = AppReasoning.prepareSession();

    	Accommodation accommodation = new Accommodation();
        accommodation.setPrice(150);

        Customer customer = new Customer("Deki//D", "deki.d@uns.ac.rs", Tier.NETHERITE);
        Date twoYearsAgo = DateUtils.addYears(new Date(),-2);

        accommodation.createReservation(customer, twoYearsAgo, DateUtils.addDays(twoYearsAgo, 15)).setCompleted(true);
        
        kSession.insert(customer);
        
        int fired = AppReasoning.classifyCustomers(kSession);
        
        assertThat(fired, is(1));
        assertThat(customer.getTier(), is(Tier.DIAMOND));    	
    }

    @Test
    public void downgradeToIronTest() {
    	kSession = AppReasoning.prepareSession();

    	Accommodation accommodation = new Accommodation();
        accommodation.setPrice(150);

        Customer customer = new Customer("Deki//D", "deki.d@uns.ac.rs", Tier.DIAMOND);
        Date now = new Date();

        accommodation.createReservation(customer, DateUtils.addDays(now, -95), DateUtils.addDays(now, -93)).setCompleted(true);
        
        kSession.insert(customer);
        
        int fired = AppReasoning.classifyCustomers(kSession);
        
        assertThat(fired, is(1));
        assertThat(customer.getTier(), is(Tier.IRON));    	
    }

    
}
