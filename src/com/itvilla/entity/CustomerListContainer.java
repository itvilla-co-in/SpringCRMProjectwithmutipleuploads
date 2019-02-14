package com.itvilla.entity;

import java.util.LinkedList;
import java.util.List;

public class CustomerListContainer {
	
	//Important. Set this to a default List in order to avoid null pointer exceptions when the list is empty
    private List<Customer> customerList = new LinkedList<Customer>();

	public CustomerListContainer(List<Customer> customerList) {
		super();
		this.customerList = customerList;
	}
    
    
	public CustomerListContainer() {
		
	}


	public List<Customer> getCustomerList() {
		return customerList;
	}


	public void setCustomerList(List<Customer> customerList) {
		this.customerList = customerList;
	}
	
	

}
