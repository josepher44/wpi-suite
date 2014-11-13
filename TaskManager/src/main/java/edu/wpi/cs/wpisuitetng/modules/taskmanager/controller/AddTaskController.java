package edu.wpi.cs.wpisuitetng.modules.taskmanager.controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.TaskModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.UserModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.tabs.view.NewTaskTab;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.tabs.view.TaskManagerTabView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.AssignUsersView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.CardView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.TaskContainerView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.TaskView;

/**
 * @author alec
 * A contoller that is used to add a task to the workflow
 */
public class AddTaskController implements ActionListener {
	private CardView cardView;
	private TaskContainerView taskView;
	private AssignUsersView assignUsersView;
	private TaskManagerTabView tabView;
	private NewTaskTab taskCreationView;
	private int cardIndex;
	

	/**
	 * @param tabView - the main JTabbedPane that holds the tab
	 * @param taskModel - the taskModel to add to the card
	 * @param cardIndex - the index of the card to remove
	 */
	public AddTaskController(TaskManagerTabView tabView, NewTaskTab taskCreationView, AssignUsersView assignUsersView, int cardIndex){
		//Parent tab pane
		this.tabView = tabView;
		//Tab the request was made on
		this.taskCreationView = taskCreationView;
		//the pane that is used for adding users
		this.assignUsersView = assignUsersView;
		this.cardIndex = cardIndex;
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		this.cardView = tabView.getWorkflowView().getCardViewList().get(cardIndex);
		TaskModel taskModel = new TaskModel(taskCreationView.getTitleLabelText(), taskCreationView.getDescriptionText());
		taskModel.setUsersAssignedTo( this.getAssignedUsers() );
		this.taskView = new TaskContainerView(taskModel, cardView);
		cardView.addTaskView(taskView);
	}
	
	//returns the final list of assigned 
	public ArrayList<UserModel> getAssignedUsers() {
		ArrayList<UserModel> assignedUsers = new ArrayList<UserModel>();
		DefaultListModel<String> assignedListModel = assignUsersView.getAssignedListModel();
		
		int size = assignedListModel.getSize();
		for(int index = 0; index < size; index++) {
			UserModel userModel = new UserModel( assignedListModel.getElementAt(index) );
			assignedUsers.add( userModel );
		}
		return assignedUsers;
	}

}
