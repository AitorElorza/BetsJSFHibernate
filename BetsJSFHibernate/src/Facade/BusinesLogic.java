package Facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.omnifaces.util.Events;
import org.primefaces.event.SelectEvent;

import businessLogic.BLFacadeImplementation;
import domain.Event;
import domain.Question;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

public class BusinesLogic {

	private BLFacadeImplementation blf;
	private List<Event> events = new ArrayList<Event>(); 
	private List<Event> allEvents = new ArrayList<Event>(); 
	private Event event;
	private Question question;
	private String eventString;
	private String questiond;
	private String betmin;
	private List<Question> questions = new ArrayList<Question>();
	private Date data;
	private String select;


	public BusinesLogic (){
		
		blf = FacadeBean.getBusinessLogic();
		select="";
		this.getAlltheEvents();
		
	}

	public void setData(Date date) {
		this.data = date;
	}


	public Date getData() {
		return this.data;
	}

	public void onDateSelect(SelectEvent event) {  
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("Data aukeratua: "+event.getObject()));
		setData((Date) event.getObject());
		getEventsBydate();
		
	} 


	public List<Event> getEvents() {
		return this.events;
	}

	public List<Question> getQuestions(){
		return this.questions;
	}

	public void setEvents(ArrayList<Event> evs) {
		this.events=evs;
	}
	public void setQuestiona(ArrayList<Question> qs) {
		this.questions=qs;
	}


	public Event getEvent() {
		return this.event;
	}

	public Question getQuestion() {
		return this.question;

	}

	public void setEvent(Event e) {
		this.event=e;
		System.out.println(e.getQuestions().size());
		System.out.println(this.event.getDescription());
		FacesContext.getCurrentInstance().addMessage(null,
				 new FacesMessage(this.event.toString()));
	}
	public void setQuestion(Question q) {
		this.question=q;
	}

	public void setQuestiond(String qd) {
		this.questiond=qd;
	}
	public void setBetmin(String bm) {
		this.betmin=bm;
	}

	public String getQuestiond() {
		return this.questiond;
	}
	public String getBetmin() {
		return this.betmin;
	}

	public void createQuestion(){
		float bmin = Float.parseFloat(betmin);
		//BLFacade bl = FacadeBean.getBusinessLogic();
		try {
			blf.createQuestion(event, questiond, bmin);
		} catch (EventFinished e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Failed creating question"));
		} catch (QuestionAlreadyExist e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Question already exists"));
		}
		System.out.println("patata");
	}



	public void getEventsBydate() {

		//BLFacade bl = FacadeBean.getBusinessLogic();
		//Vector<Event> temp = blf.getEvents(data);
		this.events = blf.getEvents(data);
		System.out.println(events.size());
	}

	public String getEventString() {
		return eventString;
	}

	public void setEventString(String eventString) {
		this.eventString = eventString;
	}
	public void listener(AjaxBehaviorEvent event) {
		FacesContext.getCurrentInstance().addMessage(null,  
				new FacesMessage("Erabiltzailearen mota:"));
	}

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}
	
	public void getAlltheEvents() {
		allEvents = blf.getAllEvents();
	}
	public void printMezua(SelectEvent even) {
		FacesContext.getCurrentInstance().addMessage(null,
				 new FacesMessage(this.event.toString()));
		
		
				
		
	}
	public void setAllEvents(List<Event> allEvents) {
		this.allEvents = allEvents;
	}
	public List<Event> getAllEvents() {
		return this.allEvents;
	}
	


}
