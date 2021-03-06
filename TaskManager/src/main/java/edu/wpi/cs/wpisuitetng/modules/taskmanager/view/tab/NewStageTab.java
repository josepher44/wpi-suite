/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Team What? We Thought This Was Bio!
 *******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.tab;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.StageModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.controller.TabController;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.controller.WorkflowController;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.WorkflowModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.task.TaskModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.controller.stage.StageController;
import net.miginfocom.swing.MigLayout;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.SwingConstants;

/**
 * this is a tab for creating stages
 */
public class NewStageTab extends JPanel implements KeyListener, IHashableTab{
	private static final long serialVersionUID = 7394421664708095366L;
	
	private JLabel titleLabel;
	private JTextField stageTitleField;
	private JButton sbmtStageButton;
    private final WorkflowModel workflowModel;
    private boolean isValidTitle = false;
    private JLabel stageTitleError;
    private StageModel model;
    private ActionType action;
    private NewStageTab thisTab;
	
	//add a combo box here for task status
    
	/**
	 * create a new tab for making a new stage
	 * 
	 * @param taskManagerTabView - the main view that holds tabs
	 */
	public NewStageTab(StageModel model, ActionType action) {
		if(model == null){
			this.model = new StageModel();
			this.action = ActionType.CREATE;
		} else{
			this.model = model;
			this.action = ActionType.EDIT;
		}
		
		
    	workflowModel = WorkflowController.getWorkflowModel();
		setLayout(new MigLayout("", "[grow]", "[][][grow]"));
    	
		titleLabel = new JLabel("Stage Title(*)");
		titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		add(titleLabel, "cell 0 0,alignx left, aligny top");
		
		stageTitleField = new JTextField();
		stageTitleField.setColumns(30);
		stageTitleField.addKeyListener(this);
		add(stageTitleField, "flowx,cell 0 1,alignx left,aligny top");
		
		sbmtStageButton = new JButton("Submit");
		thisTab = this;
		sbmtStageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				StageModel stage = buildStage();
				new StageController(stage, action).act();
				TabController.getInstance().removeTab(thisTab);
			}	
		});
		sbmtStageButton.setEnabled(false);
		add(sbmtStageButton, "cell 0 2,alignx left, aligny top");
		
		stageTitleError = new JLabel("Must enter a valid, non-duplicate title for the stage");
		stageTitleError.setForeground(Color.red);
		add(stageTitleError, "cell 0 1,alignx left,aligny top");
	}
	
	/**
	 * @return the workflowModel passed in
	 */
	public WorkflowModel getWorkflowModel() {
        return workflowModel;
    }
	
	/**
	 * @return the stage title the user entered
	 */
	public String getStageTitle(){
		return stageTitleField.getText().trim();
	}
	
	
	/** Given the user inputs, construct the stage model
	 * @return - the constructed stage
	 */
	public StageModel buildStage(){
		StageModel stageModel = new StageModel(this.getStageTitle(), workflowModel.getStageModelList().size());
		return stageModel;
	}

	
	@Override
	public void keyPressed(KeyEvent arg0) {
		//Intentionally left blank
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		String currentTitle = stageTitleField.getText().trim();
		isValidTitle = currentTitle.length() > 0 && currentTitle != null  ? true : false;
		for(StageModel stage: WorkflowController.getWorkflowModel().getStageModelList().values()){
			if(stage.getTitle().trim().equals(currentTitle)){
				isValidTitle = false;
			}
		}
		sbmtStageButton.setEnabled(isValidTitle);
		stageTitleError.setVisible(!isValidTitle);
		hasBeenModified();
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		//Intentionally left blank	
	}


	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.view.tab.IHashableTab#hasBeenModified()
	 */
	public boolean hasBeenModified() {
		return (stageTitleField.getText().length() > 0 && stageTitleField.getText() != null);
	}
	
	@Override
	public int getModelID() {
		return model.getID();
	}

	@Override
	public TabType getTabType() {
		return TabType.STAGE;
	}
}
