/*
 * Collaborator.java
 * Task Management Application
 * 1, COSC2543 - Mobile Application Development
 * RMIT International University Vietnam
 *
 * Copyright 2014 Ly Quoc Hung (s3426511)
 *
 * Refer to the NOTICE file in the root of the source tree for
 * acknowledgements of third party works used in this software.
 *
 * Date created:       17/07/2014
 * Date last modified: 17/07/2014
 */

package org.rmit.hung.thingsdo.model;

/**
 * @author Ly Quoc Hung <s3426511@rmit.edu.vn>
 * @version %I%
 */
public class Collaborator {
	private String collaboratorID;
	private String name;
	private String phoneID;
	private String notify;
	private String sent;

	public Collaborator(String collaboratorID, String name, String phoneID, String notify, String sent) {
		this.collaboratorID = collaboratorID;
		this.name = name;
		this.phoneID = phoneID;
		this.notify = notify;
		this.sent = sent;
	}

	public String getCollaboratorID() {
		return collaboratorID;
	}

	public void setCollaboratorID(String collaboratorID) {
		this.collaboratorID = collaboratorID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneID() {
		return phoneID;
	}

	public void setPhoneID(String phoneID) {
		this.phoneID = phoneID;
	}

	public String getNotify() {
		return notify;
	}

	public void setNotify(String notify) {
		this.notify = notify;
	}

	public String getSent() {
		return sent;
	}

	public void setSent(String sent) {
		this.sent = sent;
	}

	public String exportForSQLite(int position) {
		String result = "";

		if (position > 0) {
			result = "|";
		}
		result = result + notify + "," + sent + "," + collaboratorID + "," + phoneID;

		return result;
	}
}
