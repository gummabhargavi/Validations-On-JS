package com.infinite.hib;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@ManagedBean(name="busDAO")
@SessionScoped
public class BusDAO {
	
	SessionFactory sessionFactory;
	
	public List<Bus> showBus() {
		sessionFactory = SessionHelper.getSession();
  	    Session session=sessionFactory.openSession();  
  	    Query query = session.createQuery("from Bus");
        Criteria cr = session.createCriteria(Bus.class);
        List<Bus> busList=query.list();
        return busList;
		
	}
	
	public String generateBusId() {
		sessionFactory=SessionHelper.getSession();
		Session session=sessionFactory.openSession();
		Criteria cr=session.createCriteria(Bus.class);
		List<Bus> bus =cr.list();
		session.close();

		if(bus.size()==0) {
			return "B001";
		}
		else {
			String id = bus.get(bus.size()-1).getBus_id();
				int id1 = Integer.parseInt(id.substring(1));
				id1++;
				String id2 = String.format("B%03d", id1);
				return id2;
			}
		}

	

	public String addBus(Bus bus){
		//System.out.println("add Bus is called...");
//		bus.setBusId(busId);
//		bus.setBusstatus(Bus_Status.AVAILABLE);
		sessionFactory = SessionHelper.getSession();
		Session session = sessionFactory.openSession();
		String busId=generateBusId();
		bus.setBus_id(busId);
		//bus.setBus_status(BusStatus.AVAILABLE);
		//Criteria cr = session.createCriteria(Bus.class);
		Transaction trans = session.beginTransaction();
		session.save(bus);
		trans.commit();
		return "Bus Added Successfully...";
		
	}
	

}
