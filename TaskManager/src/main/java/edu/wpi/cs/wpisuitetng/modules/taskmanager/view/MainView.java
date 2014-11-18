package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.controller.TabController;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.controller.WorkflowController;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.UserModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.WorkflowModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.tabs.view.TabView;

/**
 * MainView is a wrapper class that holds all of the JPanels in the main section of the interface
 */
public class MainView extends JPanel{


	private static final long serialVersionUID = -2682288714623307153L;
	
	WorkflowModel workflowModel;
	TabView mainTabView;
	/**
	 * Constructs the main view based off the main workflow model
	 * 
	 * @param mainTabView -the panel containing all the tabs
	 */
	public MainView(){
		workflowModel = WorkflowController.getWorkflowModel();
		mainTabView = TabController.getTabView();
		
		setLayout(new BorderLayout());
		workflowModel.addUserToList(new UserModel());

		WorkflowView view = new WorkflowView(workflowModel);
		mainTabView.setWorkflowTab(view);
		add(mainTabView, BorderLayout.CENTER);
	}

}
