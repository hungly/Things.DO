/*
 * CategoryItem.java
 * Task Management Application
 * 1, COSC2543 - Mobile Application Development
 * RMIT International University Vietnam
 *
 * Copyright 2014 Ly Quoc Hung (s3426511)
 *
 * Refer to the NOTICE file in the root of the source tree for
 * acknowledgements of third party works used in this software.
 *
 * Date created:       01/07/2014
 * Date last modified: 01/07/2014
 */

package org.rmit.hung.thingsdo.model;

import java.util.List;

/**
 * @author Ly Quoc Hung <s3426511@rmit.edu.vn>
 * @version %I%
 */
public class CategoryListItem {
	private String       category;
	private List<String> task;

	public CategoryListItem() {
	}

	public CategoryListItem(String category, List<String> task) {
		this.category = category;
		this.task = task;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<String> getTask() {
		return task;
	}

	public void setTask(List<String> task) {
		this.task = task;
	}
}
