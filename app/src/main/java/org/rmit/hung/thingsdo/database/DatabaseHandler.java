/*
 * DatabaseHandler.java
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

package org.rmit.hung.thingsdo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.rmit.hung.thingsdo.model.Category;
import org.rmit.hung.thingsdo.model.Task;

import java.util.ArrayList;

/**
 * Created by Hung on 03/07/14.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
	// database version
	private static final int DATABASE_VERSION = 4;

	// database name
	private static final String DATABASE_NAME = "Task";

	// table names
	private static final String TABLE_CATEGORIES = "Categories";
	private static final String TABLE_TASKS      = "Tasks";

	// "Categories" table column names
	private static final String CAT_KEY_ID       = "id";
	private static final String CAT_KEY_CATEGORY = "category";

	// "Tasks" table column names
	private static final String TASK_KEY_ID             = "id";
	private static final String TASK_KEY_GOOGLE_ID      = "google_id";
	private static final String TASK_KEY_TITTLE         = "tittle";
	private static final String TASK_KEY_UPDATE_DATE    = "updateDate";
	private static final String TASK_KEY_PARENT         = "parent";
	private static final String TASK_KEY_NOTES          = "notes";
	private static final String TASK_KEY_STATUS         = "status";
	private static final String TASK_KEY_DUE_DATE       = "dueDate";
	private static final String TASK_KEY_COMPLETED_DATE = "completedDate";
	private static final String TASK_KEY_CATEGORY       = "category";

	/**
	 * Create a helper object to create, open, and/or manage a database.
	 * This method always returns very quickly.  The database is not actually
	 * created or opened until one of {@link #getWritableDatabase} or
	 * {@link #getReadableDatabase} is called.
	 *
	 * @param context
	 * 		to use to open or create the database
	 * 		number of the database (starting at 1); if the database is older,
	 * 		{@link #onUpgrade} will be used to upgrade the database; if the database is
	 * 		newer, {@link #onDowngrade} will be used to downgrade the database
	 */
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/** Adding new category */
	public void addCategory(Category category) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(CAT_KEY_CATEGORY, category.getCategory());

		db.insert(TABLE_CATEGORIES, null, values);

		db.close();
	}

	/** Adding new task */
	public void addTask(Task task) {
		Log.v("Database", "Adding task \"" + task.getTittle() + "\" to \"" + task.getCategory() + "\"");
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(TASK_KEY_GOOGLE_ID, task.getGoogleID());
		values.put(TASK_KEY_TITTLE, task.getTittle());
		values.put(TASK_KEY_UPDATE_DATE, task.getUpdateDate());
		values.put(TASK_KEY_PARENT, task.getParent());
		values.put(TASK_KEY_NOTES, task.getNotes());
		values.put(TASK_KEY_STATUS, task.getStatus());
		values.put(TASK_KEY_DUE_DATE, task.getDueDate());
		values.put(TASK_KEY_COMPLETED_DATE, task.getCompletedDate());
		values.put(TASK_KEY_CATEGORY, task.getCategory());

		db.insert(TABLE_TASKS, null, values);

		db.close();
	}

	/** Getting single category */
	public Category getCategory(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor c = db.query(TABLE_CATEGORIES, null, CAT_KEY_ID + "=?",
		                    new String[]{String.valueOf(id)}, null, null, CAT_KEY_CATEGORY + " ASC");

		if (c != null) {
			c.moveToFirst();
		}

		assert c != null;
		Category category = new Category(Integer.parseInt(c.getString(c.getColumnIndex(CAT_KEY_ID))), c.getString(c.getColumnIndex(CAT_KEY_CATEGORY)));

		db.close();

		return category;
	}

	/** Getting single task */
	public Task getTask(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor c = db.query(TABLE_TASKS, null, TASK_KEY_ID + "=?",
		                    new String[]{String.valueOf(id)}, null, null, null);

		if (c != null) {
			c.moveToFirst();
		}

		assert c != null;
		Task task = new Task(Integer.parseInt(c.getString(c.getColumnIndex(TASK_KEY_ID))),
		                     c.getString(c.getColumnIndex(TASK_KEY_GOOGLE_ID)),
		                     c.getString(c.getColumnIndex(TASK_KEY_TITTLE)),
		                     c.getString(c.getColumnIndex(TASK_KEY_UPDATE_DATE)),
		                     c.getString(c.getColumnIndex(TASK_KEY_PARENT)),
		                     c.getString(c.getColumnIndex(TASK_KEY_NOTES)),
		                     c.getString(c.getColumnIndex(TASK_KEY_STATUS)),
		                     c.getString(c.getColumnIndex(TASK_KEY_DUE_DATE)),
		                     c.getString(c.getColumnIndex(TASK_KEY_COMPLETED_DATE)),
		                     c.getString(c.getColumnIndex(TASK_KEY_CATEGORY)));

		db.close();

		return task;
	}

	/** Getting single task */
	public Task getTask(String tittle) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor c = db.query(TABLE_TASKS, null, TASK_KEY_TITTLE + "=?",
		                    new String[]{String.valueOf(tittle)}, null, null, null);

		if (c != null) {
			c.moveToFirst();
		}

		assert c != null;
		Task task = new Task(Integer.parseInt(c.getString(c.getColumnIndex(TASK_KEY_ID))),
		                     c.getString(c.getColumnIndex(TASK_KEY_GOOGLE_ID)),
		                     c.getString(c.getColumnIndex(TASK_KEY_TITTLE)),
		                     c.getString(c.getColumnIndex(TASK_KEY_UPDATE_DATE)),
		                     c.getString(c.getColumnIndex(TASK_KEY_PARENT)),
		                     c.getString(c.getColumnIndex(TASK_KEY_NOTES)),
		                     c.getString(c.getColumnIndex(TASK_KEY_STATUS)),
		                     c.getString(c.getColumnIndex(TASK_KEY_DUE_DATE)),
		                     c.getString(c.getColumnIndex(TASK_KEY_COMPLETED_DATE)),
		                     c.getString(c.getColumnIndex(TASK_KEY_CATEGORY)));

		db.close();

		return task;
	}

	/** Getting all categories */
	public ArrayList<Category> getAllCategories() {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor c = db.query(TABLE_CATEGORIES, null, null, null, null, null,
		                    CAT_KEY_CATEGORY + " ASC");

		ArrayList<Category> categories = new ArrayList<Category>();

		if (c.moveToFirst())
			do {
				Category category = new Category();

				category.setID(Integer.parseInt(c.getString(c.getColumnIndex(CAT_KEY_ID))));
				category.setCategory(c.getString(c.getColumnIndex(CAT_KEY_CATEGORY)));

				categories.add(category);
			} while (c.moveToNext());

		db.close();

		return categories;
	}

	/** Getting all tasks */
	public ArrayList<Task> getAllTasks() {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor c = db.query(TABLE_TASKS, null, null, null, null, null, TASK_KEY_TITTLE + " ASC");

		ArrayList<Task> tasks = new ArrayList<Task>();

		if (c.moveToFirst())
			do {
				Task task = new Task();

				task.setID(Integer.parseInt(c.getString(c.getColumnIndex(TASK_KEY_ID))));
				task.setGoogleID(c.getString(c.getColumnIndex(TASK_KEY_GOOGLE_ID)));
				task.setTittle(c.getString(c.getColumnIndex(TASK_KEY_TITTLE)));
				task.setUpdateDate(c.getString(c.getColumnIndex(TASK_KEY_UPDATE_DATE)));
				task.setParent(c.getString(c.getColumnIndex(TASK_KEY_PARENT)));
				task.setNotes(c.getString(c.getColumnIndex(TASK_KEY_NOTES)));
				task.setStatus(c.getString(c.getColumnIndex(TASK_KEY_STATUS)));
				task.setDueDate(c.getString(c.getColumnIndex(TASK_KEY_DUE_DATE)));
				task.setCompletedDate(c.getString(c.getColumnIndex(TASK_KEY_COMPLETED_DATE)));
				task.setCategory(c.getString(c.getColumnIndex(TASK_KEY_CATEGORY)));

				tasks.add(task);
			} while (c.moveToNext());

		db.close();

		return tasks;
	}

	/** Getting all tasks under category */
	public ArrayList<Task> getTasksByCategory(Category category, String order) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor c = db.query(TABLE_TASKS, null, TASK_KEY_CATEGORY + "=?", new String[]{category.getCategory()}, null,
		                    null, "date(" + TASK_KEY_DUE_DATE + ") " + order);

		ArrayList<Task> tasks = new ArrayList<Task>();

		if (c.moveToFirst())
			do {
				Task task = new Task();

				task.setID(Integer.parseInt(c.getString(c.getColumnIndex(TASK_KEY_ID))));
				task.setGoogleID(c.getString(c.getColumnIndex(TASK_KEY_GOOGLE_ID)));
				task.setTittle(c.getString(c.getColumnIndex(TASK_KEY_TITTLE)));
				task.setUpdateDate(c.getString(c.getColumnIndex(TASK_KEY_UPDATE_DATE)));
				task.setParent(c.getString(c.getColumnIndex(TASK_KEY_PARENT)));
				task.setNotes(c.getString(c.getColumnIndex(TASK_KEY_NOTES)));
				task.setStatus(c.getString(c.getColumnIndex(TASK_KEY_STATUS)));
				task.setDueDate(c.getString(c.getColumnIndex(TASK_KEY_DUE_DATE)));
				task.setCompletedDate(c.getString(c.getColumnIndex(TASK_KEY_COMPLETED_DATE)));
				task.setCategory(c.getString(c.getColumnIndex(TASK_KEY_CATEGORY)));

				tasks.add(task);
			} while (c.moveToNext());

		db.close();

		return tasks;
	}

	/** Getting all tasks under priority */
	public ArrayList<Task> getTasksByPriority(String priority, String order) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor c = db.query(TABLE_TASKS, null, TASK_KEY_PARENT + "=?", new String[]{priority}, null,
		                    null, "date(" + TASK_KEY_DUE_DATE + ") " + order);

		ArrayList<Task> tasks = new ArrayList<Task>();

		if (c.moveToFirst())
			do {
				Task task = new Task();

				task.setID(Integer.parseInt(c.getString(c.getColumnIndex(TASK_KEY_ID))));
				task.setGoogleID(c.getString(c.getColumnIndex(TASK_KEY_GOOGLE_ID)));
				task.setTittle(c.getString(c.getColumnIndex(TASK_KEY_TITTLE)));
				task.setUpdateDate(c.getString(c.getColumnIndex(TASK_KEY_UPDATE_DATE)));
				task.setParent(c.getString(c.getColumnIndex(TASK_KEY_PARENT)));
				task.setNotes(c.getString(c.getColumnIndex(TASK_KEY_NOTES)));
				task.setStatus(c.getString(c.getColumnIndex(TASK_KEY_STATUS)));
				task.setDueDate(c.getString(c.getColumnIndex(TASK_KEY_DUE_DATE)));
				task.setCompletedDate(c.getString(c.getColumnIndex(TASK_KEY_COMPLETED_DATE)));
				task.setCategory(c.getString(c.getColumnIndex(TASK_KEY_CATEGORY)));

				tasks.add(task);
			} while (c.moveToNext());

		db.close();

		return tasks;
	}

	/** Getting all tasks under priority */
	public ArrayList<Task> getTasksByDueDate(String date, String comparison, String order) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor c;

		if (date.equals("None")) {
			// no date specific
			c = db.query(TABLE_TASKS, null, TASK_KEY_DUE_DATE + comparison + "?",
			             new String[]{date}, null,
			             null, TASK_KEY_PARENT + " " + order);
		} else {
			c = db.rawQuery("SELECT * FROM " + TABLE_TASKS + " WHERE date(" + TASK_KEY_DUE_DATE + ")" + comparison +
			                "date('" + date + "') ORDER BY " + TASK_KEY_PARENT + " " + order,
			                null
			               );
		}

		ArrayList<Task> tasks = new ArrayList<Task>();

		if (c.moveToFirst())
			do {
				Task task = new Task();

				task.setID(Integer.parseInt(c.getString(c.getColumnIndex(TASK_KEY_ID))));
				task.setGoogleID(c.getString(c.getColumnIndex(TASK_KEY_GOOGLE_ID)));
				task.setTittle(c.getString(c.getColumnIndex(TASK_KEY_TITTLE)));
				task.setUpdateDate(c.getString(c.getColumnIndex(TASK_KEY_UPDATE_DATE)));
				task.setParent(c.getString(c.getColumnIndex(TASK_KEY_PARENT)));
				task.setNotes(c.getString(c.getColumnIndex(TASK_KEY_NOTES)));
				task.setStatus(c.getString(c.getColumnIndex(TASK_KEY_STATUS)));
				task.setDueDate(c.getString(c.getColumnIndex(TASK_KEY_DUE_DATE)));
				task.setCompletedDate(c.getString(c.getColumnIndex(TASK_KEY_COMPLETED_DATE)));
				task.setCategory(c.getString(c.getColumnIndex(TASK_KEY_CATEGORY)));

				tasks.add(task);
			} while (c.moveToNext());

		db.close();

		return tasks;
	}

	/** Getting categories count */
	public int getCategoriesCount() {
		SQLiteDatabase db = this.getReadableDatabase();

		final String SELECT_ALL = "SELECT * FROM " + TABLE_CATEGORIES;

		Cursor c = db.rawQuery(SELECT_ALL, null);

		final int count = c.getCount();

		db.close();

		return count;
	}

	/** Getting tasks count */
	public int getTasksCount() {
		SQLiteDatabase db = this.getReadableDatabase();

		final String SELECT_ALL = "SELECT * FROM " + TABLE_TASKS;

		Cursor c = db.rawQuery(SELECT_ALL, null);

		final int count = c.getCount();

		db.close();

		return count;
	}

	/** Updating single category */
	public int updateCategory(Category category) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(CAT_KEY_ID, category.getID());
		values.put(CAT_KEY_CATEGORY, category.getCategory());

		final int returnCode = db.update(TABLE_CATEGORIES, values, CAT_KEY_ID + "=?", new String[]{String.valueOf(category.getID())});

		db.close();

		return returnCode;
	}

	/** Updating single category */
	public int updateTask(Task task) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(TASK_KEY_GOOGLE_ID, task.getGoogleID());
		values.put(TASK_KEY_TITTLE, task.getTittle());
		values.put(TASK_KEY_UPDATE_DATE, task.getUpdateDate());
		values.put(TASK_KEY_PARENT, task.getParent());
		values.put(TASK_KEY_NOTES, task.getNotes());
		values.put(TASK_KEY_STATUS, task.getStatus());
		values.put(TASK_KEY_DUE_DATE, task.getDueDate());
		values.put(TASK_KEY_COMPLETED_DATE, task.getCompletedDate());
		values.put(TASK_KEY_CATEGORY, task.getCategory());

		final int returnCode = db.update(TABLE_TASKS, values, TASK_KEY_ID + "=?", new String[]{String.valueOf(task.getID())});

		db.close();

		return returnCode;
	}

	/** Deleting single category */
	public void deleteCategory(Category category) {
		SQLiteDatabase db = this.getWritableDatabase();

		db.delete(TABLE_CATEGORIES, CAT_KEY_ID + "=?", new String[]{String.valueOf(category.getID())});

		db.close();
	}

	/** Deleting single task */
	public void deleteTask(Task task) {
		SQLiteDatabase db = this.getWritableDatabase();

		Log.v("Database", "Remove task \"" + task.getTittle() + "\", id: " + task.getID());

		db.delete(TABLE_TASKS, TASK_KEY_ID + "=?", new String[]{String.valueOf(task.getID())});

		db.close();
	}

	/**
	 * Called when the database is created for the first time. This is where the
	 * creation of tables and the initial population of the tables should happen.
	 *
	 * @param db
	 * 		The database.
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// create query
		final String CREATE_CAT_TABLE = "CREATE TABLE " + TABLE_CATEGORIES + "(" + CAT_KEY_ID +
		                                " INTEGER PRIMARY KEY, " + CAT_KEY_CATEGORY + " TEXT)";

		final String CREATE_TASK_TABLE = "CREATE TABLE " + TABLE_TASKS + "(" +
		                                 TASK_KEY_ID + " INTEGER PRIMARY KEY, " +
		                                 TASK_KEY_GOOGLE_ID + " TEXT, " +
		                                 TASK_KEY_TITTLE + " TEXT, " +
		                                 TASK_KEY_UPDATE_DATE + " TEXT, " +
		                                 TASK_KEY_PARENT + " TEXT, " +
		                                 TASK_KEY_NOTES + " TEXT, " +
		                                 TASK_KEY_STATUS + " TEXT, " +
		                                 TASK_KEY_DUE_DATE + " TEXT, " +
		                                 TASK_KEY_COMPLETED_DATE + " TEXT, " +
		                                 TASK_KEY_CATEGORY + " TEXT)";

		db.execSQL(CREATE_CAT_TABLE);
		db.execSQL(CREATE_TASK_TABLE);
	}


	/**
	 * Called when the database needs to be upgraded. The implementation
	 * should use this method to drop tables, add tables, or do anything else it
	 * needs to upgrade to the new schema version.
	 * <p/>
	 * <p>
	 * The SQLite ALTER TABLE documentation can be found
	 * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
	 * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
	 * you can use ALTER TABLE to rename the old table, then create the new table and then
	 * populate the new table with the contents of the old table.
	 * </p><p>
	 * This method executes within a transaction.  If an exception is thrown, all changes
	 * will automatically be rolled back.
	 * </p>
	 *
	 * @param db
	 * 		The database.
	 * @param oldVersion
	 * 		The old database version.
	 * @param newVersion
	 * 		The new database version.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// drop table query
		final String DROP_CAT_TABLE = "DROP TABLE IF EXISTS " + TABLE_CATEGORIES;
		final String DROP_TASK_TABLE = "DROP TABLE IF EXISTS " + TABLE_TASKS;

		db.execSQL(DROP_CAT_TABLE);
		db.execSQL(DROP_TASK_TABLE);

		onCreate(db);
	}
}
