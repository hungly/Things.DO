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
	private String _updateDate;
	private String _parent;
	private String _notes;
	private String _status;
	private String _dueDate;
	private String _completedDate;
	private String _category;

	public Task(int _id, String _googleID, String _tittle, String _updateDate, String _parent, String _notes, String _dueDate, String _completedDate, String _category) {
		this._id = _id;
		this._googleID = _googleID;
		this._tittle = _tittle;
		this._updateDate = _updateDate;
		this._parent = _parent;
		this._notes = _notes;
		this._dueDate = _dueDate;
		this._completedDate = _completedDate;
		this._category = _category;
	}

	public Task(int _id, String _googleID, String _tittle, String _updateDate, String _parent, String _notes, String _status, String _dueDate, String _completedDate, String _category) {

		this._id = _id;
		this._googleID = _googleID;
		this._tittle = _tittle;
		this._updateDate = _updateDate;
		this._parent = _parent;
		this._notes = _notes;
		this._status = _status;
		this._dueDate = _dueDate;
		this._completedDate = _completedDate;
		this._category = _category;
	}

	public Task() {
		this._status = "needsAction";
	}

	public String getCategory() {
		return _category;
	}

	public void setCategory(String _category) {
		this._category = _category;
	}

	public String getStatus() {
		return _status;
	}

	public void setStatus(String _status) {
		this._status = _status;
	}

	public String getDueDate() {
		return _dueDate;
	}

	public void setDueDate(String _dueDate) {
		this._dueDate = _dueDate;
	}

	public String getCompletedDate() {
		return _completedDate;
	}

	public void setCompletedDate(String _completedDate) {
		this._completedDate = _completedDate;
	}

	public String getUpdateDate() {
		return _updateDate;
	}

	public void setUpdateDate(String _updateDate) {
		this._updateDate = _updateDate;
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

	public String getParent() {
		return _parent;
	}

	public void setParent(String _parent) {
		this._parent = _parent;
	}
}
