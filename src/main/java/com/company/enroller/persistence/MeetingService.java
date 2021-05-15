package com.company.enroller.persistence;

import java.util.Collection;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import com.company.enroller.model.Meeting;

@Component("meetingService")
public class MeetingService {

	Session session;
	// DatabaseConnector session;

	public MeetingService() {
		// session = DatabaseConnector.getInstance();
		session = DatabaseConnector.getInstance().getSession();
	}

	public void add(Meeting meeting) {
		Transaction transaction = this.session.beginTransaction();
		session.save(meeting);
		transaction.commit();
	}

	public Collection<Meeting> getAll() {
		String hql = "FROM Meeting";
		// Query query = session.getSession().createQuery(hql);
		Query query = session.createQuery(hql);
		return query.list();
	}

	public Meeting findById(long id) {
		return (Meeting) session.get(Meeting.class, id);
	}

}
