package edu.wpi.cs.wpisuitetng.modules.taskmanager.tabs.view;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.WorkflowModel;

public class NewStageTab extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7394421664708095366L;
	
	private JLabel titleLabel;
	private JTextField stageTitleField;
	private JButton makeStageButton;
    private final WorkflowModel workflowModel;
	
	//add a combo box here for task status
    
	public NewStageTab(TaskManagerTabView taskManagerTabView, WorkflowModel workflowModel) {
    	this.workflowModel = workflowModel;
		this.titleLabel = new JLabel("Title: ");
		this.stageTitleField = new JTextField();
		this.makeStageButton = new JButton("Create this Stage!");
		
		add(titleLabel);
		add(stageTitleField);
		add(makeStageButton);
		
		setLayout(new FlowLayout() );
		
	}
	
	public WorkflowModel getWorkflowListModel() {
        return this.workflowModel;
    }
}