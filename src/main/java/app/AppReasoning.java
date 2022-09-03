package app;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import util.KnowledgeSessionHelper;

public class AppReasoning {
		
	static KieContainer kieContainer;
	
	public static KieSession prepareSession() {
		if (kieContainer == null) kieContainer = KnowledgeSessionHelper.createRuleBase();
		return KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "test-session");
	}
	
	public static int classifyCustomers(KieSession kSession) {
		kSession.getAgenda().getAgendaGroup("classify-customers").setFocus();
        return kSession.fireAllRules();
	}
	
	public static int classifyAccommodations(KieSession kSession) {
		kSession.getAgenda().getAgendaGroup("classify-accommodations").setFocus();
        return kSession.fireAllRules();
	}

	public static int runRecommendations(KieSession kSession) {
		kSession.getAgenda().getAgendaGroup("recommend-actions").setFocus();
        return kSession.fireAllRules();
	}
	
	public static int runRecommendAccommodations(KieSession kSession) {
		kSession.getAgenda().getAgendaGroup("test-com").setFocus();
        return kSession.fireAllRules();
	}

	public static int runDiscounts(KieSession kSession) {
		kSession.getAgenda().getAgendaGroup("add-discounts").setFocus();
        return kSession.fireAllRules();
	}

	public static int runAwards(KieSession kSession) {
		 kSession.getAgenda().getAgendaGroup("awards").setFocus();
        return kSession.fireAllRules();
	}
	
	
}
