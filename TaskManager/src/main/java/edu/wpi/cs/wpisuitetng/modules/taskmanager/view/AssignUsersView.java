package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.controller.AssignUnassignUserController;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.UserModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.WorkflowModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.tabs.view.TaskManagerTabView;

/**
 * @author Alec
 * A view that is used to assign viewers between two lists
 */
public class AssignUsersView extends JPanel{
	private ArrayList<UserModel> userList;
	private WorkflowModel workflowModel;
	
	private JList<String> unassignedListComponent;
	private JList<String> assignedListComponent;
	private DefaultListModel<String> unassignedListModel;
	private DefaultListModel<String> assignedListModel;
	
	public AssignUsersView(TaskManagerTabView tabView, WorkflowModel workflowModel) {
		this.workflowModel = workflowModel;
		userList = workflowModel.getUserList();
		setLayout(new MigLayout("", "[][][]", "[][][grow][]"));
		
		JLabel lblAssignUsers = new JLabel("Assign Users To This Task");
		add(lblAssignUsers, "cell 1 0 5 1,alignx center,aligny top");
		
		JLabel lblUsersNotAssigned = new JLabel("Users not assigned:");
		lblUsersNotAssigned.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblUsersNotAssigned, "cell 1 1");
		
		JLabel lblUsersAssigned = new JLabel("Users assigned");
		lblUsersAssigned.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblUsersAssigned, "cell 5 1");
		
		JScrollPane unassignedScrollPane = new JScrollPane();
		add(unassignedScrollPane, "cell 1 2,growy");
		
		//List of unassigned users
		unassignedListModel = new DefaultListModel<String>();
		this.addElementsToList(unassignedListModel, this.getUsernameList() );
		unassignedListComponent = new JList<String>( unassignedListModel );
		unassignedListComponent.setFixedCellWidth(150);
		unassignedScrollPane.setViewportView(unassignedListComponent);
		
		JScrollPane assignedScrollPane = new JScrollPane();
		add(assignedScrollPane, "cell 5 2,growy");
		
		//List of assigned users
		assignedListModel = new DefaultListModel<String>();
		assignedListComponent = new JList<String>( assignedListModel );
		assignedListComponent.setFixedCellWidth(150);
		assignedScrollPane.setViewportView(assignedListComponent);
		
		JButton btnAssign = new JButton("Assign >>");
		btnAssign.addActionListener( new AssignUnassignUserController(this, AssignRemoveEnum.ASSIGN) );
		add(btnAssign, "cell 1 3,alignx center");
		
		JButton buttonUnassign = new JButton("<< Unassign");
		buttonUnassign.addActionListener( new AssignUnassignUserController(this, AssignRemoveEnum.UNASSIGN) );
		add(buttonUnassign, "cell 5 3,alignx center");
	
	}
	
	/**
	 * @return an array of userName strings from the provided list of userModels
	 */
	private String[] getUsernameList() {
		String[] userNameList = new String[]{""};
		if(userList.size() == 0){
			return userNameList;
		} else{
			for(int index = 0; index < userList.size(); index++ )
				userNameList[index] = userList.get(index).getUsername();	
		}
		return userNameList;
	}
	
	/**
	 * 
	 * @return the name of the user that is selected in the unassigned list
	 */
	public String getUnassignedListSelectedName() {
		return this.unassignedListComponent.getSelectedValue();
	}
	
	/**
	 * 
	 * @return the name of the user that is selected in the unassigned list
	 */
	public String getAssignedListSelectedName() {
		return this.assignedListComponent.getSelectedValue();
	}
	
	/**
	 * 
	 * @return the index of the selected element in the unassigned list
	 */
	public int getUnassignedListSelectedIndex() {
		int index = this.unassignedListComponent.getSelectedIndex();
		return index;
	}
	
	/**
	 * 
	 * @return the index of the selected element in the assigned list
	 */
	public int getAssignedListSelectedIndex() {
		int index = this.assignedListComponent.getSelectedIndex();
		return index;
	}
	
	/**
	 * 
	 * @param list - the list to add elements to
	 * @param usernameList - the list to add elements from
	 */
	public void addElementsToList(DefaultListModel<String> list, String[] usernameList ){
		for( String username : usernameList ){
			System.out.println("Added " + username + " to the model");
			list.addElement(username);
		}
	}
	
	public JList<String> getAssignedListComponent() {
		return assignedListComponent;
	}
	
	public JList<String> getUnssignedListComponent() {
		return unassignedListComponent;
	}
	
	public DefaultListModel<String> getAssignedListModel() {
		return assignedListModel;
	}
	
	public DefaultListModel<String> getUnassignedListModel() {
		return unassignedListModel;
	}
	

}