package edu.wpi.cs.wpisuitetng.modules.taskmanager.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.TaskModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.CardView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.TaskContainerView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.TaskView;

public class ExpandTaskController implements MouseListener{
	TaskModel taskModel;
	TaskContainerView taskContainerView;
	public ExpandTaskController(TaskContainerView taskContainerView, TaskModel taskModel){
		this.taskModel = taskModel;
		this.taskContainerView = taskContainerView;
		TaskView taskView = new TaskView(new CardView(null), taskModel);
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		boolean isExpanded = taskModel.getIsExpanded();
		if(isExpanded == true)
			taskContainerView.hideDetails();
		else
			taskContainerView.showDetails();
		
		taskModel.setIsExpanded(!isExpanded);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


}
