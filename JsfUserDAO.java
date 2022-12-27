package Training.BusBookingProject;

import java.util.List;
import java.util.StringTokenizer;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


@ManagedBean(name="jsfUserDAO")
@SessionScoped
public class JsfUserDAO {
	
	SessionFactory sessionFactory;
	
	public String generateUserId() {
		
		sessionFactory = SessionHelper.getConnection();
		Session session = sessionFactory.openSession();
		Criteria cr = session.createCriteria(User.class);
		List<User> userList = cr.list();
		if (userList.size() == 0) {
			return "U001";
		}
		session.close();
		String id = userList.get(userList.size() - 1).getUserid();
		int id1 = Integer.parseInt(id.substring(1));
		id1++;
		String id2 = String.format("U%03d", id1);
		return id2;

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
						"invalid ContactNumber");
				context.addMessage(comp.getClientId(context), message); 
	      }
	
	}
	public void validateEmail(FacesContext context, UIComponent comp,
			Object value) {

		System.out.println("inside validate method");
		//String mno = (String) value;
		//String REGEX = 
		//		"^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$"; 		
		String mno = (String) value;
		// boolean result=mno.matches(REGEX);
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
//	 public static boolean emailIdValidation(FacesContext context, UIComponent comp,
//				String ip) {
//		    int i = 0;
//		    boolean b = false;
//		    StringTokenizer t = new StringTokenizer(ip, "@");
//		    String s1 = t.nextToken();
//		    String s2 = t.nextToken();
//		    StringTokenizer t1 = new StringTokenizer(s2, ".");
//		    String s3 = t1.nextToken();
//		    String s4 = t1.nextToken();
//		    if (ip.contains("@") && ip.contains(".")){
//		      i++;
//		      ((UIInput) comp).setValid(false);
//
//				FacesMessage message = new FacesMessage(
//						"invalid Email");
//				context.addMessage(comp.getClientId(context), message);
//			}
//		    if (i == 1)
//		      if (s3.length() == 5)
//		        if (s1.length() >= 3)
//		          if (s4.equals("com"))
//		            b = true;
//		    return b;
//		  }

	//Add User:
	
public String Adduser(User user) {
	String userid = generateUserId();
	user.setUserid(userid);
	sessionFactory = SessionHelper.getConnection();
	Session session = sessionFactory.openSession();
	Transaction trans = session.beginTransaction();
	session.save(user);
	trans.commit();
	session.close();
	
	session = sessionFactory.openSession();
	Wallet wallet = new Wallet();
	wallet.setWalletId(generateWalletId());
	wallet.setWalletAmount(0);
	wallet.setUserId(userid);
	
	session.save(wallet);
	session.beginTransaction().commit();
	
	return "User Details added Successfully";

}
public String generateWalletId(){
	 sessionFactory = SessionHelper.getConnection();
	 Session session = sessionFactory.openSession();
	 Criteria cr = session.createCriteria(Wallet.class);
	 List<Wallet> walletList = cr.list();
	 if(walletList.size()==0){
		 return "W001";
	 }
	 session.close();
	 String id = walletList.get(walletList.size()-1).getWalletId();
	 int id1 = Integer.parseInt(id.substring(1));
	 id1++;
	 String id2 = String.format("W%03d", id1);
	return id2;
	 
	}
	
	
}
