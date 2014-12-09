package edu.wpi.cs.wpisuitetng.modules.taskmanager.reports;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.controller.WorkflowController;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.controller.datalogger.DataLoggerController;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.task.ActivityListModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.task.TaskModel;


/**
 * @author Joe
 * This class is used to take a snapshot of a task's state at a particular time. 
 * Comparing two snapshots are used to drive changes in the activity list
 * These snapshots are compiled to form the program's statistical output
 */
public class TaskSnapshot {
	private int estimatedEffort, actualEffort;
	private long id;	//starts at zero, increments in order of creation
	private int taskID;	//only variable which isn't a 1:1 correspondance to TaskModel. ID is unique to the snapshot
	private String title, description, creatorName;
	private ArrayList<String> usersAssignedTo;
	private Date creationDate;
	private String dueDate;
	private int stageID;
	private boolean isArchived;
	private Color color;
	private Color CatColor;
	private ActivityListModel activities;
	
	static Hashtable <Color, String> colors;
	
	

	
	
	
	/** The default constructor for a Task Snapshot **/
	public TaskSnapshot(TaskModel task, int id)
	{
		this.id = DataLoggerController.getDataModel().getTaskSnapID();
		DataLoggerController.getDataModel().incrementTaskSnapID();
		this.taskID = task.getID();
		this.estimatedEffort = task.getEstimatedEffort();
		this.actualEffort = task.getActualEffort();
		this.title = task.getTitle();
		this.description = task.getDescription();
		this.creatorName = task.getCreatorName();
		this.creationDate = task.getCreationDate();
		this.usersAssignedTo = task.getUsersAssignedTo();
		this.dueDate = task.getDueDate();
		this.stageID = task.getStageID();
		this.isArchived = task.getIsArchived();
		this.activities = task.getActivities();
		this.color = task.getColor();
		this.CatColor = task.getCatColor();
	}
	
	
	//reverse of hashtable used for setting colors, used to drive string creation
	private Hashtable addColors(){
		colors = new<String, Color> Hashtable();
		colors.put(Color.LIGHT_GRAY, "GRAY");
		colors.put(Color.WHITE, "WHITE");
		colors.put(new Color(0xA67C52), "BROWN");
		colors.put(new Color(0xF7977A), "RED");
		colors.put(new Color(0xF49AC2), "PINK");
		colors.put(new Color(0xFDC68A), "ORANGE");
		colors.put(new Color(0xFFF79A), "YELLOW");
		colors.put(new Color(0x82CA9D), "GREEN");
		colors.put(new Color(0x8493CA), "BLUE");
		colors.put(new Color(0xA187BE), "PURPLE");
		
		
		return colors;
	}
	
	public String colorToString(Color color)
	{
		addColors();
		return colors.get(color);
	}
	
	
	
	public void setID(long id)
	{
		this.id=id;
	}
	
	public long getID()
	{
		return id;
	}
	
	public void setTaskID(int id)
	{
		this.taskID = id;
	}
	
	public int getTaskID()
	{
		return taskID;
	}
	/**
	 * @return the estimatedEffort
	 */
	public int getEstimatedEffort() {
		return estimatedEffort;
	}
	/**
	 * @param estimatedEffort the estimatedEffort to set
	 */
	public void setEstimatedEffort(int estimatedEffort) {
		this.estimatedEffort = estimatedEffort;
	}
	/**
	 * @return the actualEffort
	 */
	public int getActualEffort() {
		return actualEffort;
	}
	/**
	 * @param actualEffort the actualEffort to set
	 */
	public void setActualEffort(int actualEffort) {
		this.actualEffort = actualEffort;
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the creatorName
	 */
	public String getCreatorName() {
		return creatorName;
	}
	/**
	 * @param creatorName the creatorName to set
	 */
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	/**
	 * @return the usersAssignedTo
	 */
	public ArrayList<String> getUsersAssignedTo() {
		return usersAssignedTo;
	}
	/**
	 * @param usersAssignedTo the usersAssignedTo to set
	 */
	public void setUsersAssignedTo(ArrayList<String> usersAssignedTo) {
		this.usersAssignedTo = usersAssignedTo;
	}
	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}
	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	/**
	 * @return the dueDate
	 */
	public String getDueDate() {
		return dueDate;
	}
	/**
	 * @param dueDate the dueDate to set
	 */
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	/**
	 * @return the stageID
	 */
	public int getStageID() {
		return stageID;
	}
	/**
	 * @param stageID the stageID to set
	 */
	public void setStageID(int stageID) {
		this.stageID = stageID;
	}
	/**
	 * @return the isArchived
	 */
	public boolean isArchived() {
		return isArchived;
	}
	/**
	 * @param isArchived the isArchived to set
	 */
	public void setArchived(boolean isArchived) {
		this.isArchived = isArchived;
	}
	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	/**
	 * @return the catColor
	 */
	public Color getCatColor() {
		return CatColor;
	}
	/**
	 * @param catColor the catColor to set
	 */
	public void setCatColor(Color catColor) {
		CatColor = catColor;
	}
	
	public String getCatColorString() {
		return colorToString(this.CatColor);
	}
	
	
	/**
	 * @return the activities
	 */
	public ActivityListModel getActivities() {
		return activities;
	}
	/**
	 * @param activities the activities to set
	 */
	public void setActivities(ActivityListModel activities) {
		this.activities = activities;
	}
	

	

	

}