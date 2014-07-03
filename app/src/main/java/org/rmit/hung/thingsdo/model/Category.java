/*
 * Category.java
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
public class Category {
	private int    _id;
	private String _category;

	public Category() {
	}

	public Category(String _category) {
		this._category = _category;
	}

	public Category(int _id, String _category) {

		this._id = _id;
		this._category = _category;
	}

	public int getID() {
		return _id;
	}

	public void setID(int _id) {
		this._id = _id;
	}

	public String getCategory() {
		return _category;
	}

	public void setCategory(String _category) {
		this._category = _category;
	}
}
