package org.mrbag.ProxyController.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

import org.mrbag.ProxyController.Objects.Dute.TransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UtilTransaction {

	@Autowired
	UtilUser users;
	
	@Autowired
	UtilProxyServer servers;
	
	@Autowired
	UtilCreditals creditals;
	
	static Map<Long, Double> srvs = null;
	
	public static int days_save = 7;
	
	@Value("${app.days}")
	public static void setDays_save(int days_save) {
		UtilTransaction.days_save = days_save;
	}
	
	void preLoad() {
		srvs = servers.getIDandCost();
	}
	
	
	@Scheduled(cron = "${app.cron.transaction}")
	public void updateTransaction() {
		log.info("Start update transaction");
		LocalDateTime ldt = LocalDateTime.now();
		if (srvs == null)
			preLoad();
		Collection<TransactionDTO> trans = creditals.convertTransction(ldt, ldt.plusMinutes(30));
		trn:
		for(TransactionDTO t : trans) {
			if (t.getBalance_user() >= 0 && t.getBalance_user() >= srvs.getOrDefault(t.getId_server(), 9999.9)) {
				if (users.operationWriting(t.getId_user(), srvs.get(t.getId_server()))) {
					creditals.setDateById(t.getId_creditals(), t.getLdt().plusDays(1));
					continue trn;
				}
			}
			//TODO local logic notification user
			log.debug("Not process transction before: " + t);
			creditals.setContinueWithId(t.getId_creditals(), false);
		}
		log.info("Count nodes where was transction: " + trans.size() + ", pool servers: " + srvs.size());
		srvs = null;
		log.info("End update transction");
	}
	
	@Scheduled(cron = "${app.cron.trash}")
	public void clearTrash() {
		log.info("Cleaning trash note in proxycreditals table");
		LocalDateTime ldt = LocalDateTime.now().minusDays(days_save);
		creditals.deleteALLBefore(ldt);
	}
	
}
