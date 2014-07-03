/*
 * Task.java
 * Task Management Application
 * 1, COSC2543 - Mobile Application Development
 * RMIT International University Vietnam
 *
 * Copyright 2014 Ly Quoc Hung (s3426511)
 *
 * Refer to the NOTICE file in the root of the source tree for
 * acknowledgements of third party works used in this software.
 *
 * Date created:       03/07/2014
 * Date last modified: 03/07/2014
 */

package org.rmit.hung.thingsdo.model;

/**
 * Created by Hung on 03/07/14.
 */
public class Task {
	private int    _id;
	private String _googleID;
	private String _tittle;
	private String _parent;
	private String _notes;

	public Task(String _googleID, String _tittle, String _parent, String _notes) {
		this._googleID = _googleID;
		this._tittle = _tittle;
		this._parent = _parent;
		this._notes = _notes;
	}

	public Task(int _id, String _googleID, String _tittle, String _parent, String _notes) {

		this._id = _id;
		this._googleID = _googleID;
		this._tittle = _tittle;
		this._parent = _parent;
		this._notes = _notes;
	}


	public Task() {

	}

	public String getNotes() {
		return _notes;
	}

	public void setNotes(String _notes) {
		this._notes = _notes;
	}

	public int getID() {
		return _id;
	}

	public void setID(int _id) {
		this._id = _id;
	}

	public String getGoogleID() {
		return _googleID;
	}

	public void setGoogleID(String _googleID) {
		this._googleID = _googleID;
	}

	public String getTittle() {
		return _tittle;
	}

	public void setTittle(String _tittle) {
		this._tittle = _tittle;
	}

	public String getParrent() {
		return _parent;
	}

	public void setParrent(String _parrent) {
		this._parent = _parrent;
	}
}
