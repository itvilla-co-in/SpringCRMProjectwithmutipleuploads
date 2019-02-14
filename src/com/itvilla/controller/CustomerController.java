package com.itvilla.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.itvilla.entity.Customer;
import com.itvilla.entity.CustomerListContainer;
import com.itvilla.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	// need to inject our customer service
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/list")
	public String listCustomers(Model theModel) {
		
		// get customers from the service
		List<Customer> theCustomers = customerService.getCustomers();
				
		// add the customers to the model
		theModel.addAttribute("customers", theCustomers);
		
		return "list-customers";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		// create model attribute to bind form data
		Customer theCustomer = new Customer();
		
		theModel.addAttribute("customer", theCustomer);
		
		return "customer-form";
	}
	
	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer theCustomer) {
		
		// save the customer using our service
		customerService.saveCustomer(theCustomer);	
		
		return "redirect:/customer/list";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int theId,
									Model theModel) {
		
		// get the customer from our service
		Customer theCustomer = customerService.getCustomer(theId);	
		
		// set customer as a model attribute to pre-populate the form
		theModel.addAttribute("customer", theCustomer);
		
		// send over to our form		
		return "customer-form";
	}
	
	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int theId) {
		
		// delete the customer
		customerService.deleteCustomer(theId);
		
		return "redirect:/customer/list";
	}
	
	
	@GetMapping("/newListPage")
	public String newListPage(Model theModel,
			HttpSession session, 
            HttpServletRequest request, 
            @RequestParam(value="f", required=false) String flush,
            @RequestParam(value="message", required=false) String message) {
		
		// create model attribute to bind form data
		// customer theCustomer = new Customer();
		
		//if someone is going to press reset button
		//then add some dummy rows.
		// get customers from the service
		List<Customer> theCustomers = customerService.getCustomers();
		CustomerListContainer customerListContainer = new CustomerListContainer();
		
		if (theCustomers.size() == 0 && flush != null)
		{
			theModel.addAttribute("customerListContainer", getDummyPersonListContainer());
			//session.setAttribute("customerListContainer", getDummyPersonListContainer());
		}
		else
		{
			customerListContainer.setCustomerList(theCustomers);
			theModel.addAttribute("customerListContainer", customerListContainer);

		}
		
		/*
		if( flush != null )
            session.setAttribute("customerListContainer", getDummyPersonListContainer());
		//if in an existing session there are no customers to display then add some dummy rows.	
        if( session.getAttribute("customerListContainer") == null )
            session.setAttribute("customerListContainer", getDummyPersonListContainer());
		// what if there are already some customers for the first time... 	
        theModel.addAttribute("customerListContainer", (CustomerListContainer)session.getAttribute("customerListContainer"));
		
        if( message != null )
        	theModel.addAttribute("message", message);
        theModel.addAttribute("cp", request.getContextPath());
*/
        return "listcustomersnew";

	}
	
    @RequestMapping(value="/editcustomerlistcontainer", method= RequestMethod.POST)
    public String editcustomerListContainer(@ModelAttribute CustomerListContainer customerListContainer, HttpSession session) {
        for( Customer c : customerListContainer.getCustomerList() ) {
            System.out.println("f Name: " + c.getFirstName());
            System.out.println("email: " + c.getEmail());
            System.out.println("l name " + c.getLastName());
            System.out.println("id " + c.getId());
            
        }
        
        // first check what rows were modified
        // check new rows added
        // ignore unchanged rows
        //persist modified row or new rows and send updated list back to page..
        
        session.setAttribute("customerListContainer",customerListContainer);
        return "listcustomersnew";
        //return "redirect:/listcustomersnew?message=Form Submitted Ok. Number of rows is: ["+personListContainer.getPersonList().size()+"]";
        //return "listcustomersnew?message=Form Submitted Ok. Number of rows is: ["+customerListContainer.getCustomerList().size()+"]";
    }
    
	
	  private CustomerListContainer getDummyPersonListContainer() {
	        List<Customer> customerList = new ArrayList<Customer>();
	        for( int i=0; i<2; i++ ) {
	        	customerList.add(new Customer(i, "First Name [" + i + "]", "Last Name [" + i + "]", "Email Name [" + i + "]" ));
	        }
	        return new CustomerListContainer(customerList);
	    }
	
	
}










