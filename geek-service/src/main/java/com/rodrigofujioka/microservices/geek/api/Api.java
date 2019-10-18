package com.rodrigofujioka.microservices.geek.api;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rodrigofujioka.microservices.geek.intercomm.Grude4jClient;
import com.rodrigofujioka.microservices.geek.model.Account;
import com.rodrigofujioka.microservices.geek.model.Customer;
import com.rodrigofujioka.microservices.geek.model.CustomerType;

@RestController
public class Api {
	
	@Autowired
	private Grude4jClient grude4jClient;
	
	protected Logger logger = Logger.getLogger(Api.class.getName());
	
	private List<Customer> customers;
	
	public Api() {
		customers = new ArrayList<>();
		customers.add(new Customer(1, "12345", "Rodrigo Fujioka", CustomerType.INDIVIDUAL));
		customers.add(new Customer(2, "12346", "Flauber Grude4J", CustomerType.INDIVIDUAL));
		customers.add(new Customer(3, "12347", "André Grude4j", CustomerType.INDIVIDUAL));
		customers.add(new Customer(4, "12348", "Ramon Conductor", CustomerType.INDIVIDUAL));
	}
	
	@RequestMapping("/customers/grude/{msg}")
	public Customer findByPesel(@PathVariable("pesel") String msg) {
		logger.info(String.format("Customer.findByPesel(%s)", msg));
		return customers.stream().filter(it -> it.getPesel().equals(msg)).findFirst().get();	
	}
	
	@RequestMapping("/customers")
	public List<Customer> findAll() {
		logger.info("Customer.findAll()");
		return customers;
	}
	
	@RequestMapping("/customers/{id}")
	public Customer findById(@PathVariable("id") Integer id) {
		logger.info(String.format("Customer.findById(%s)", id));
		Customer customer = customers.stream().filter(it -> it.getId().intValue()==id.intValue()).findFirst().get();
		List<Account> accounts =  grude4jClient.getAccounts(id);
		customer.setAccounts(accounts);
		return customer;
	}
	
}
