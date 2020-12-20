package dataAccess;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.hibernate.Query;
import org.hibernate.Session;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.Event;
import domain.Question;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;
import hibernateUtil.HibernateUtil;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess  {


	/*public DataAccess(boolean initializeMode)  {

		//c=ConfigXML.getInstance();

		//System.out.println("Creating DataAccess instance => isDatabaseLocal: "+c.isDatabaseLocal()+" getDatabBaseOpenMode: "+c.getDataBaseOpenMode());

		//String fileName=c.getDbFilename();
		String fileName = "bets.temp";
		if (initializeMode)
			fileName=fileName+";drop";

		//if (c.isDatabaseLocal()) {
		//		emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
		//		db = emf.createEntityManager();
		//		} else {
		//			Map<String, String> properties = new HashMap<String, String>();
		//			  properties.put("javax.persistence.jdbc.user", c.getUser());
		//			  properties.put("javax.persistence.jdbc.password", c.getPassword());
		//
		//			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);
		//
		//			  db = emf.createEntityManager();
		//    	   }
	}

	public DataAccess()  {	
		new DataAccess(false);
		
	}*/


	/**
	 * This is the data access method that initializes the database with some events and questions.
	 * This method is invoked by the business logic (constructor of BLFacadeImplementation) when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	public void initializeDB(){

		Session session= HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		Calendar today = Calendar.getInstance();
		int month=today.get(Calendar.MONTH);
		month++;
		int year=today.get(Calendar.YEAR);
		if (month==12) { month=0; year+=1;}  

		Event ev1=new Event(1, "Atlético-Athletic", UtilDate.newDate(year,month,17));
		Event ev2=new Event(2, "Eibar-Barcelona", UtilDate.newDate(year,month,17));
		Event ev3=new Event(3, "Getafe-Celta", UtilDate.newDate(year,month,17));
		Event ev4=new Event(4, "Alavés-Deportivo", UtilDate.newDate(year,month,17));
		Event ev5=new Event(5, "Español-Villareal", UtilDate.newDate(year,month,17));
		Event ev6=new Event(6, "Las Palmas-Sevilla", UtilDate.newDate(year,month,17));
		Event ev7=new Event(7, "Malaga-Valencia", UtilDate.newDate(year,month,17));
		Event ev8=new Event(8, "Girona-Leganés", UtilDate.newDate(year,month,17));
		Event ev9=new Event(9, "Real Sociedad-Levante", UtilDate.newDate(year,month,17));
		Event ev10=new Event(10, "Betis-Real Madrid", UtilDate.newDate(year,month,17));

		Event ev11=new Event(11, "Atletico-Athletic", UtilDate.newDate(year,month,1));
		Event ev12=new Event(12, "Eibar-Barcelona", UtilDate.newDate(year,month,1));
		Event ev13=new Event(13, "Getafe-Celta", UtilDate.newDate(year,month,1));
		Event ev14=new Event(14, "Alavés-Deportivo", UtilDate.newDate(year,month,1));
		Event ev15=new Event(15, "Español-Villareal", UtilDate.newDate(year,month,1));
		Event ev16=new Event(16, "Las Palmas-Sevilla", UtilDate.newDate(year,month,1));


		Event ev17=new Event(17, "Málaga-Valencia", UtilDate.newDate(year,month,28));
		Event ev18=new Event(18, "Girona-Leganés", UtilDate.newDate(year,month,28));
		Event ev19=new Event(19, "Real Sociedad-Levante", UtilDate.newDate(year,month,28));
		Event ev20=new Event(20, "Betis-Real Madrid", UtilDate.newDate(year,month,28));

		Question q1;
		Question q2;
		Question q3;
		Question q4;
		Question q5;
		Question q6;



		q1=ev1.addQuestion("Zeinek irabaziko du partidua?",1);
		q2=ev1.addQuestion("Zeinek sartuko du lehenengo gola?",2);
		q3=ev11.addQuestion("Zeinek irabaziko du partidua?",1);
		q4=ev11.addQuestion("Zenbat gol sartuko dira?",2);
		q5=ev17.addQuestion("Zeinek irabaziko du partidua?",1);
		q6=ev17.addQuestion("Golak sartuko dira lehenengo zatian?",2);

		
		session.save(q1);
		session.save(q2);
		session.save(q3);
		session.save(q4);
		session.save(q5);
		session.save(q6);


		session.save(ev1);
		session.save(ev2);
		session.save(ev3);
		session.save(ev4);
		session.save(ev5);
		session.save(ev6);
		session.save(ev7);
		session.save(ev8);
		session.save(ev9);
		session.save(ev10);
		session.save(ev11);
		session.save(ev12);
		session.save(ev13);
		session.save(ev14);
		session.save(ev15);
		session.save(ev16);
		session.save(ev17);
		session.save(ev18);
		session.save(ev19);
		session.save(ev20);
		
		

		session.getTransaction().commit();
		System.out.println("Session initialized");


	}

	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
	public Question createQuestion(Event event, String question, float betMinimum)  {
		System.out.println(">> DataAccess: createQuestion=> event= "+event+" question= "+question+" betMinimum="+betMinimum);
		//Send creation message to the terminal


		//get the current sesion
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Question q= event.addQuestion(question, betMinimum);
		q.setEvent(event);
		
		System.out.println(event.getQuestions().size());
		
		
		session.save(q);
		
		
		
		
		session.getTransaction().commit();
		return q;

	}

	/**
	 * This method retrieves from the database the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	public List<Event> getEvents(Date date) {
		System.out.println(">> DataAccess: getEvents");
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		String myDate=sdf.format(date);
		List<Event> res = new ArrayList<Event>();



		//Get database session
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		List search =  session.createQuery("from Event ").list();
		System.out.println(search.size());
		for(int i=0;i<search.size();i++) {
			Event ev=(Event) search.get(i);
			String eventDate=sdf.format(ev.getEventDate());
			if(eventDate.compareTo(myDate)==0) {
				res.add(ev);
			}
		}
		
		
		
		return res;
	}
	
	public List<Event> getAllEvents(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List search =  session.createQuery("from Event ").list();
		return search;
	}

	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	public List<Date> getEventsMonth(Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		List<Date> res = new ArrayList<Date>();	

		Date firstDayMonthDate= UtilDate.firstDayMonth(date);
		Date lastDayMonthDate= UtilDate.lastDayMonth(date);
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Query q =  session.createQuery("Select ev from Event WHERE ev.eventDate BETWEEN :firstDate AND lastDate" );
				q.setParameter("lastDate", lastDayMonthDate);
				q.setParameter("firstDate", firstDayMonthDate);
		res=q.list();

		return res;
	}



	/*public void close(){
		db.close();
		System.out.println("DataBase closed");
	}*/

}
