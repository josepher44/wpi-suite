package edu.wpi.cs.wpisuitetng.modules.taskmanager.tabs.view;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.WorkflowModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.WorkflowView;

/**
 * This view creates a tabbed window that can contain other views. 
 * It is also responsible for creating the default workflow tab
 */
public class TaskManagerTabView extends JTabbedPane{
	private static final long serialVersionUID = -8772129104939459349L;
	WorkflowView workflowView;
	
	//creates a new tab called "Card Overview"
	public TaskManagerTabView() {
		this.workflowView = new WorkflowView(new WorkflowModel("Blank"));
		
		this.setLayout( new BorderLayout() );
		setTabPlacement(TOP);
		setTabLayoutPolicy(SCROLL_TAB_LAYOUT);
		addTab("Workflow", new ImageIcon(), workflowView,
		       "An overview of the task workflow");
	}
	
	/**
	 * @return the view that contains the workflow
	 */
	public WorkflowView getWorkflowView(){
		return workflowView;
	}
	
	/**
	 * @param workflowView - set the workflowView
	 */
	public void setWorkflowTab(WorkflowView workflowView) {
		//this.setTabComponentAt(0, workflowView);
	}

}
