package com.infinite.canteen;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
@ManagedBean(name="customerDAO")
@SessionScoped
public class CustomerDAO {

	SessionFactory sFactory;

	public String GenerateCustomerid() {
		sFactory = SessionHelper.getConnection();
		Session session = sFactory.openSession();
		Criteria cr = session.createCriteria(Customer.class);
		List<Customer> customerList = cr.list();
		session.close();
		if (customerList.size() == 0) {
			return "C001";

		} else {
			String id = customerList.get(customerList.size() - 1).getCust_id();
			int id1 = Integer.parseInt(id.substring(1));
			id1++;
			String id2 = String.format("C%03d", id1);
			return id2;
		}
	}
	
	public void validatePhnNo(FacesContext context, UIComponent comp,
			Object value) {

		System.out.println("inside validate method");

		String mno = (String) value;
		boolean flag=false;
		if (mno.matches("\\d{10}"))  
		{ flag=true;}
		
	      if(flag==false) {
	    	  ((UIInput) comp).setValid(false);

				FacesMessage message = new FacesMessage(
						"invalid PhnNo");
				context.addMessage(comp.getClientId(context), message); 
	      }
		
	

	}
	
	public void validatePassword(FacesContext context, UIComponent comp,
			Object value) {

		System.out.println("inside validate method");

		String mno = (String) value;
		String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
	      boolean result=mno.matches(pattern);
	      if(result==false) {
	    	  ((UIInput) comp).setValid(false);

				FacesMessage message = new FacesMessage(
						"invalid Password");
				context.addMessage(comp.getClientId(context), message); 
	      }
		
	

	}
	public void validateEmail(FacesContext context, UIComponent comp,
			Object value) {

		System.out.println("inside validate method");

		String mno = (String) value;
		if (mno.indexOf('@')==-1) {
			((UIInput) comp).setValid(false);

			FacesMessage message = new FacesMessage(
					"invalid Email");
			context.addMessage(comp.getClientId(context), message);
		}
		if (mno.length() < 6) {
			((UIInput) comp).setValid(false);

			FacesMessage message = new FacesMessage(
					"Minimum length of model number is 6");
			context.addMessage(comp.getClientId(context), message);

		}

	}

	public String addCustomer(Customer customer) {
		sFactory = SessionHelper.getConnection();
		Session session = sFactory.openSession();
		String customerid = GenerateCustomerid();
		customer.setCust_id(customerid);
		Criteria cr = session.createCriteria(Customer.class);
		org.hibernate.Transaction tran = session.beginTransaction();
		session.save(customer);
		tran.commit();
		return "customer details Added";
	}

	public List<Customer> showCustomer() {
		sFactory = SessionHelper.getConnection();
		Session session = sFactory.openSession();
		Criteria cr = session.createCriteria(Customer.class);
		List<Customer> customerList = cr.list();
		return customerList;

	}

	public Customer searchCustomer(String user) {
		sFactory = SessionHelper.getConnection();
		Session session = sFactory.openSession();
		Criteria cr = session.createCriteria(Customer.class);
		cr.add(Restrictions.eq("cust_userName", user));
		List<Customer> customerList = cr.list();
		return customerList.get(0);

	}

	public Customer Updatecustomer(String custid) {
		sFactory = SessionHelper.getConnection();
		Session session = sFactory.openSession();
		Customer customer = searchCustomer(custid);
		Criteria cr = session.createCriteria(Customer.class);
		cr.add(Restrictions.eq("cust_email", customer.getCust_email()));
		cr.add(Restrictions.eq("cust_name", customer.getCust_name()));
		cr.add(Restrictions.eq("cust_phn_no", customer.getCust_phn_no()));
		cr.add(Restrictions.eq("cust_password", customer.getCust_password()));
		List<Customer> customerList = cr.list();
		return customerList.get(0);

	}

	
	public String checkUsers(Customer customer) {
        sFactory = SessionHelper.getConnection();
        Session session = sFactory.openSession();
        Criteria cr = session.createCriteria(Customer.class);
        cr.add(Restrictions.eq("cust_userName", customer.getCust_userName()));
        cr.add(Restrictions.eq("cust_password", customer.getCust_password()));
        List<Customer> listcustomer = cr.list();
        if(listcustomer.size()==1) {
            return "ShowCustomer.xhtml?faces-redirect=true";
        }else {
            return "Login.xhtml?faces-redirect=true";
        }
	}
		
	public List<OrderDetails> searchCustomerOrder(String custId, String searchType) {
		sFactory = SessionHelper.getConnection();
		Session session = sFactory.openSession();
		Criteria cr = session.createCriteria(OrderDetails.class);
		Criterion criterion1, criterion2;
		if (searchType.equals("PENDING")) {
			Query query=session.createQuery("from OrderDetails where custId=:custId AND status=:status"
					+ " AND address is NOT NULL").setParameter("custId", custId).setParameter("status", Status.PENDING);
					
					List<OrderDetails> list = query.list();
					return list;	
		}else {
		if (searchType.equals("ACCEPTED")) {
			criterion1 = Restrictions.eq("custId", custId);
			criterion2 = Restrictions.eq("status", Status.ACCEPTED);
			cr.add(Restrictions.and(criterion1, criterion2));
		}

		if (searchType.equals("CANCELLED")) {
			criterion1 = Restrictions.eq("custId", custId);
			criterion2 = Restrictions.eq("status", Status.CANCELLED);
			cr.add(Restrictions.and(criterion1, criterion2));
		}

		if (searchType.equals("DELIVERED")) {
			criterion1 = Restrictions.eq("custId", custId);
			criterion2 = Restrictions.eq("status", Status.DELIVERED);
			cr.add(Restrictions.and(criterion1, criterion2));
		}

		if (searchType.equals("REJECTED")) {
			criterion1 = Restrictions.eq("custId", custId);
			criterion2 = Restrictions.eq("status", Status.REJECTED);
			cr.add(Restrictions.and(criterion1, criterion2));
		}

		List<OrderDetails> list = cr.list();
		return list;
	}
	}
	
}