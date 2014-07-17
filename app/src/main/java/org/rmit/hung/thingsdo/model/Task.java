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
	private String googleID;
	private String tittle;
	private String updateDate;
	private String parent;
	private String notes;
	private String status;
	private String dueDate;
	private String completedDate;
	private String category;
	private String collaborators;

	public Task(int _id, String googleID, String tittle, String updateDate, String parent, String notes, String dueDate, String completedDate, String category, String collaborators) {
		this._id = _id;
		this.googleID = googleID;
		this.tittle = tittle;
		this.updateDate = updateDate;
		this.parent = parent;
		this.notes = notes;
		this.dueDate = dueDate;
		this.completedDate = completedDate;
		this.category = category;
		this.collaborators = collaborators;
	}

	public Task(int _id, String googleID, String tittle, String updateDate, String parent, String notes, String status, String dueDate, String completedDate, String category, String collaborators) {

		this._id = _id;
		this.googleID = googleID;
		this.tittle = tittle;
		this.updateDate = updateDate;
		this.parent = parent;
		this.notes = notes;
		this.status = status;
		this.dueDate = dueDate;
		this.completedDate = completedDate;
		this.category = category;
		this.collaborators = collaborators;
	}

	public Task() {
		this.status = "needsAction";
	}

	public String getCollaborators() {
		return collaborators;
	}

	public void setCollaborators(String collaborators) {
		this.collaborators = collaborators;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String _category) {
		this.category = _category;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String _status) {
		this.status = _status;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String _dueDate) {
		this.dueDate = _dueDate;
	}

	public String getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(String _completedDate) {
		this.completedDate = _completedDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String _updateDate) {
		this.updateDate = _updateDate;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String _notes) {
		this.notes = _notes;
	}

	public int getID() {
		return _id;
	}

	public void setID(int _id) {
		this._id = _id;
	}

	public String getGoogleID() {
		return googleID;
	}

	public void setGoogleID(String _googleID) {
		this.googleID = _googleID;
	}

	public String getTittle() {
		return tittle;
	}

	public void setTittle(String _tittle) {
		this.tittle = _tittle;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String _parent) {
		this.parent = _parent;
	}
}
