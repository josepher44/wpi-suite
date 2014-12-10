package edu.wpi.cs.wpisuitetng.modules.taskmanager.controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.AssignUsersView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.tab.NewTaskTab;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * @author Lillian
 * Responsible for making a call to the database to get requirements
 */
public class RequirementsController implements ActionListener {
	private RequirementsRequestObserver observer;
	private NewTaskTab view;
	//private AssociatedRequirementsView view;
	
	public RequirementsController(NewTaskTab view) {
		this.observer = new RequirementsRequestObserver(this);
		this.view = view;
		//this.view = view;
	}

	/**
	 * Makes request for requirements from database
	 */
	
	public void requestRequirementsList() {
		final Request request = Network.getInstance().makeRequest("requirementmanager/requirement", HttpMethod.GET); // PUT == create
		request.addObserver(observer); // add an observer to process the response
		request.send();	
	}
	
	/**
	 * @param requirements - populate the view's requirements list
	 */
	public void addRequirementsToList(Requirement[] requirements){
		DefaultComboBoxModel<String> requirementsComboModel = view.getRequirementsComboModel();
		for(Requirement req : requirements)
			requirementsComboModel.addElement(req.getName());
		
		view.setTaskRequirementBox();
	}

	
	/**
	 * @param arg0
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		requestRequirementsList();
	}
}