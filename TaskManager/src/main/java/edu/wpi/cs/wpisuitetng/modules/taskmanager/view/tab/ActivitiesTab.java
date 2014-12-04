package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.tab;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.controller.WorkflowController;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.controller.stage.StageController;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.StageModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.WorkflowModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.task.ActivityModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.task.TaskModel;
import net.miginfocom.swing.MigLayout;

public class ActivitiesTab extends JPanel {
	
	private WorkflowModel workflowModel;
	private TaskModel taskModel;
	private JList<ActivityModel> activitiesBoard;
	private JScrollPane activitiesScrollPane;
	private JTextField newCommentTxt;
	private JButton btnSubmit;

	public ActivitiesTab(TaskModel model){
		taskModel = model;
		workflowModel = WorkflowController.getWorkflowModel();
		setLayout(new MigLayout("", "[grow]", "[grow][]"));
     
		//make
        activitiesBoard = new JList<ActivityModel>(taskModel.getActivities());
        activitiesScrollPane = new JScrollPane(activitiesBoard);
        activitiesScrollPane.setPreferredSize(new Dimension(500, 400));
        add(activitiesScrollPane, "cell 0 0");
        
        //make the txt for new comments
        newCommentTxt = new JTextField("Enter a comment here.");
        newCommentTxt.setColumns(30);
        newCommentTxt.addMouseListener(
        		new MouseAdapter() {
        			public void mouseClicked(MouseEvent e) {
        				newCommentTxt.setText("");
        			}});
        add(newCommentTxt, "alignx left, flowy, cell 0 1");
        
        //make the submit button
        btnSubmit = new JButton("Submit");
        btnSubmit.setAlignmentX(Component.CENTER_ALIGNMENT);

		btnSubmit.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				addActivity();
			}
		});
        //btnSubmit.addActionListener(new AddCommentController());
        add(btnSubmit, "alignx left, flowy, cell 0 1");
	}
	
	/**
	 * Given the input that the user provided, construct the task
	 * @return - the task that has been built with the fields that the user entered
	 */
	public void addActivity() {
		StageModel stageModel = null;
		for(StageModel stage : workflowModel.getStageModelList().values()){
			if(stage.getID() == taskModel.getStageID())
				stageModel = stage;
		}
		
		taskModel.addActivity(new ActivityModel(newCommentTxt.getText()));
		newCommentTxt.setText("");
		//Updates task on the stage model
		StageController.sendUpdateRequest(stageModel);
	}
	
	public TaskModel getTaskModel(){
		return taskModel;
	}
}