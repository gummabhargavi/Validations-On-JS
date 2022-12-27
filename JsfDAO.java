package Training.BusBookingProject;

import java.util.ArrayList;
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
import org.hibernate.criterion.Restrictions;

@ManagedBean
@SessionScoped
public class JsfDAO{
	SessionFactory sessionFactory;
	
	
	public String checkUsers(User user) {
		sessionFactory = SessionHelper.getConnection();
		Session session = sessionFactory.openSession();
		Criteria cr = session.createCriteria(User.class);
		cr.add(Restrictions.eq("username", user.getUsername()));
		cr.add(Restrictions.eq("password", user.getPassword()));
		List<User> usrLst = cr.list();
		System.out.println(usrLst.size());
		FacesContext context = FacesContext.getCurrentInstance();
		if(usrLst.size()==1) {
			context.getExternalContext().getSessionMap().put("userId", usrLst.get(0).getUserid());
			context.getExternalContext().getSessionMap().put("username", usrLst.get(0).getUsername());
			return "/JSF_Files/UserHome.xhtml?faces-redirect=true";
			
		}
		else {
              context.addMessage(null,new FacesMessage("Invalid User"));
			
		}
		return "/JSF_Files/Login.xhtml?faces-redirect=true";
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
	public String checkAdmin(Admin admin) {
		sessionFactory = SessionHelper.getConnection();
		Session session = sessionFactory.openSession();
		Criteria cr = session.createCriteria(Admin.class);
		cr.add(Restrictions.eq("adminName", admin.getAdminName()));
		cr.add(Restrictions.eq("password", admin.getPassword()));
		List<Admin> adminLst = cr.list();
		System.out.println(adminLst.size());
		FacesContext context = FacesContext.getCurrentInstance();
		if(adminLst.size()==1) {
			context.getExternalContext().getSessionMap().put("userId", adminLst.get(0).getAdminName());
			context.getExternalContext().getSessionMap().put("username", adminLst.get(0).getAdminId());
			return "/JSF_Files/AdminHomePage.xhtml?faces-redirect=true";
			
		}
		else {
              context.addMessage(null,new FacesMessage("Invalid User"));
			
		}
		return "/JSF_Files/Login.xhtml?faces-redirect=true";
	}
	
	
	
	
	
	public String generateBookingId() {
		sessionFactory = SessionHelper.getConnection();
		Session session = sessionFactory.openSession();
		Criteria cr = session.createCriteria(Booking.class);
		List<Booking> bookingList = cr.list();
		if (bookingList.size() == 0) {
			return "K001";
		}
		session.close();
		String id = bookingList.get(bookingList.size() - 1).getBookingId();
		int id1 = Integer.parseInt(id.substring(1));
		id1++;
		String id2 = String.format("K%03d", id1);
		return id2;
	}
	
	public void validateAge(FacesContext context, UIComponent comp,
            Object value) {
       System.out.println("inside validate method");
  
        Integer mno = (Integer)value;
        System.out.println(mno);
        if (mno>=99 || mno<=12) {
           
            System.out.println("run");
           FacesMessage message = new FacesMessage(
                    "Invalid Age");
            context.addMessage(comp.getClientId(context), message);
 
        }
       
    }
	
	public String addBooking(Booking booking) {
		sessionFactory = SessionHelper.getConnection();
		Session session = sessionFactory.openSession();
		String bookId = generateBookingId();
		booking.setBookingStatus(BookingStatus.PENDING);
		booking.setBookingId(bookId);
		java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
		// booking.setDateofBooking(sqlDate);
		Transaction t = session.beginTransaction();
		session.save(booking);
		t.commit();
		return "/JSF_Files/Booking.xhtml?faces-redirect=true";
	}
	
	
	public List<Integer> book(String id) {
		List<Booking> booking = seats(id);
		List<Integer> seat = new ArrayList<Integer>();
		List<Integer> seat2 = new ArrayList<Integer>();
		for (Booking bookings : booking) {
			System.out.println(bookings.getSeatNo());
			int a = bookings.getSeatNo();
			seat.add(a);
		}
		for (int i = 1; i <= 20; i++) {
			if (!seat.contains(i)) {
				seat2.add(i);
			}

		}
		return seat2;
	}
	
	
	public List<Booking> seats(String a) {
		sessionFactory = SessionHelper.getConnection();
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("From Booking where scheduleId=:s").setParameter("s", a);
		List<Booking> leavelist1 = query.list();
		return leavelist1;
	}
	
}
