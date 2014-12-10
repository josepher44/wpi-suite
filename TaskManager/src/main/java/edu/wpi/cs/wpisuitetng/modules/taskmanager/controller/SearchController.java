/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team What? We Thought This Was Bio!
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.controller;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.Border;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.StageModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.WorkflowModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.task.TaskModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.TaskView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.ToolbarView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.WorkflowView;

/**
 * @author Frick
 * @author Jay
 * @author Andrew
 * @author Alec
 * Manages the search bar's searching functionality.
 */

public class SearchController implements ActionListener, KeyListener {
	private static WorkflowModel workflowModel;
	private static JTextField searchBox;
	private static ToolbarView toolbarView;
	
	public SearchController(ToolbarView toolbarView) {
		SearchController.toolbarView = toolbarView;
		SearchController.workflowModel = WorkflowController.getWorkflowModel();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// Intentionally left empty
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// Intentionally left empty
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		search();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Intentionally left empty		
	}
	
	/**
	 * search()
	 * Searches tasks for string in search box, highlighting matches
	 */
	public static void search() {
		boolean foundResult = false;
		searchBox = toolbarView.getSearchBox();
		WorkflowView workflowView = TabController.getTabView().getWorkflowView();
		String searchText = searchBox.getText();

		for (StageModel stage : workflowModel.getStageModelList().values()) {
			for(TaskModel task : stage.getTaskModelList().values()){
				int stageID = stage.getID();
				int taskID = task.getID();
				TaskView taskView = workflowView.getStageViewList().get(stageID).getTaskViewList().get(taskID);
				if (task.getTitle().contains(searchText) && searchText.length() > 0) {
					Border blueHighlight = BorderFactory.createLineBorder(Color.blue, 3);
					taskView.setBorder(blueHighlight);
					foundResult = true;
				} else {
					Border blackHighlight = BorderFactory.createLineBorder(Color.black);
					taskView.setBorder(blackHighlight);
				}
			}
		}
		
		if(!foundResult && !searchText.isEmpty())
			searchBox.setBackground(new Color(255,120,120));
		else
			searchBox.setBackground(Color.WHITE);
	}

}