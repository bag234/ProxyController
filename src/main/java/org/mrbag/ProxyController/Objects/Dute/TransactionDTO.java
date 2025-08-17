package org.mrbag.ProxyController.Objects.Dute;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class TransactionDTO {

	long id_creditals;
	
	long id_server;
	
	long id_user;
	
	double balance_user;
	
	LocalDateTime ldt; 
	
	// SELECT pc.id, pc.ps, pc.user.id, pc.user.balance, pc.toDate FROM ProxyCreditals pc WHERE pc.isContinue AND pc.toDate BETWEEN :start AND :end AND pc.user.balance >= 0;
	
}
