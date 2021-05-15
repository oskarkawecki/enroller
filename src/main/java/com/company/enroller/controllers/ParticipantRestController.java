package com.company.enroller.controllers;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;
import com.company.enroller.persistence.MeetingService;
import com.company.enroller.persistence.ParticipantService;


@RestController

@RequestMapping("/meetings/")
public class ParticipantRestController {
	@Autowired
	MeetingService meetingService;
	ParticipantService participantService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> getMeetings() {
		// Collection<Meeting> participants = meetingService.getAll();
		Collection<Meeting> meetings = meetingService.getAll();
		return new ResponseEntity<Collection<Meeting>>(meetings, HttpStatus.OK);
	}

	@RequestMapping(value = "/x/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getMeeting(@PathVariable("id") long id) {
		// Meeting meeting = meetingService.findByLogin(id);
		Meeting meeting = meetingService.findById(id);
		if (meeting == null)
			return new ResponseEntity("abc", HttpStatus.NO_CONTENT);// HttpStatus.NOT_FOUND);
		// return new ResponseEntity<Participant>(Meeting, HttpStatus.OK);
		return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
	}

	// POST http://localhost
	// znajdz spotkanie
	// znajdz uczestnika w systemie
	// meeting.addParticipant(participant)
	// update meeting

	@RequestMapping(value = "/participants", method = RequestMethod.POST)
	public ResponseEntity<?> registerParticipant(@RequestBody Participant participant) {
		Participant foundParticipant = participantService.findByLogin(participant.getLogin());
		if (foundParticipant != null) {
			return new ResponseEntity(
					"Unable to create. A participant with login " + participant.getLogin() + " already exist.",
					HttpStatus.CONFLICT);
		}
		participantService.add(participant);
		return new ResponseEntity<Participant>(participant, HttpStatus.CREATED);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<?> registerMeeting(@RequestBody Meeting meeting) {
		Meeting foundMeeting = meetingService.findById(meeting.getId());
		if (foundMeeting != null) {
			return new ResponseEntity("Unable to create. A meeting with id " + meeting.getId() + " already exist.",
					HttpStatus.CONFLICT);
		}
		meetingService.add(meeting);
		return new ResponseEntity<Meeting>(meeting, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getParticipants(@PathVariable("id") long id) {
		Meeting meeting = meetingService.findById(id);
		if (meeting == null)
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);

		/*
		 * Meeting foundMeeting = meetingService.findById(meeting.getId()); if
		 * (foundMeeting != null) { return new
		 * ResponseEntity("Unable to create. A meeting with id " + meeting.getId() +
		 * " already exist.", HttpStatus.CONFLICT); } meetingService.add(meeting);
		 * return new ResponseEntity<Meeting>(meeting, HttpStatus.CREATED);
		 */
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	// public ResponseEntity<?> delete(@PathVariable("id") String login) {
	public ResponseEntity<?> deleteParticipant(@PathVariable("id") String login) {
		Participant participant = participantService.findByLogin(login);
		if (participant == null)
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		participantService.delete(participant);
		return new ResponseEntity<Participant>(participant, HttpStatus.OK);
		// return new ResponseEntity<Participant>(HttpStatus.NO_CONTENT);
	}

}

