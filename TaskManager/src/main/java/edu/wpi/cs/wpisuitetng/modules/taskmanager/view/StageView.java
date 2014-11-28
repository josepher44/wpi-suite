package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.controller.stage.StageController;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.StageModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.tab.ActionType;

import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

import javax.swing.JButton;

/**
 * This view is responsible for rendering a stage that can be placed inside a workflow.
 *
 */
public class StageView extends JPanel {
	private static final long serialVersionUID = 7765491802045400161L;
	private String title;
	private JPanel stagePane;
	private JScrollPane scrollPane;
	private WorkflowView workflowView;
	private ArrayList<TaskView> taskViewList;
	private JLabel lblStageTitle;
	private JButton btnClose;
	private boolean closable;
	private StageModel stageModel;
	private int id;
	
	/**
	 * Constructs a new Stage based off the given model
	 * @param stageModel - the model the view is based off of
	 */
	public StageView(StageModel stageModel, WorkflowView workflowView) {
		this.stageModel = stageModel;
		this.id = stageModel.getID();
		this.taskViewList = new ArrayList<TaskView>();
		title = stageModel.getTitle();
		stagePane = new JPanel();
		this.workflowView = workflowView;
		setLayout(new MigLayout("insets 0", "[grow][]", "[][grow]"));
		this.closable = stageModel.getClosable();
		
		this.lblStageTitle = new JLabel(title);
		lblStageTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblStageTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblStageTitle.setFont(new Font("Tahoma", Font.PLAIN, 15));
		add(lblStageTitle, "cell 0 0,alignx center,aligny center");
		
		btnClose = new JButton("\u2716");
		btnClose.setFont(btnClose.getFont().deriveFont((float) 8));
		btnClose.setMargin(new Insets(0, 0, 0, 0));
		btnClose.addActionListener(new StageController(stageModel, ActionType.DELETE));
		btnClose.setEnabled(closable);
		add(btnClose, "cell 1 0,aligny center");
		
		scrollPane = new JScrollPane(stagePane);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		stagePane.setLayout(new BoxLayout(stagePane, BoxLayout.Y_AXIS));
		add(scrollPane, "cell 0 1 2 1,grow");
		setBackground(new Color(135, 206, 250));
		stagePane.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 20));
		updatePreferredDimensions();
	}
	
	
	/**
	 * Overrides the getPreferredSize method to make task boxes scale dynamically
	 */
	public Dimension getPreferredSize() {
		Component parent = this.getParent();
		final Dimension parentSize = new Dimension( workflowView.getScrollPane().getViewport().getWidth() - 30,
				workflowView.getScrollPane().getViewport().getHeight() - 30 );
		
		if( parent == null ){
			return super.getPreferredSize();
		} else {
			int stagePreferredHeight = stagePane.getPreferredSize().height;
			int stageCount = workflowView.getStageViewList().size();
			int stageWidth = (int) parentSize.width/( stageCount < 4 ? stageCount : 4);
			stagePane.setPreferredSize(new Dimension(this.getWidth(), stagePreferredHeight));
			return new Dimension( stageWidth, parentSize.height );
		}
	}
	
	
	/**
	 * Updates the preferred dimensions of the panel that houses the task views
	 */
	public void updatePreferredDimensions() {
		int heightNeeded = 0;
		
		//Go through each component in the stageView
		for( Component childComponent : stagePane.getComponents() ){
			if( childComponent instanceof TaskView ){
				heightNeeded += ((TaskView) childComponent).getHeight();
			}
			stagePane.setPreferredSize(new Dimension(this.getWidth(), heightNeeded));
		}
	}

	/**
	 * gets the title of the stage
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * sets the title of the stage
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * adds a task to the stage
	 * @param taskView
	 */
	public void addTaskView(TaskView taskView) {
		stagePane.add(taskView);
		taskViewList.add(taskView);
		updatePreferredDimensions();
		redrawStage();
	}
	
	public void redrawStage(){
		stagePane.revalidate();
	}
	
	/**
	 * adds a task to the stage at the given index
	 * @param taskView
	 * @param index - the spot in the list to add the view
	 */
	public void addTaskViewAtIndex(int index, TaskView taskView) {
		stagePane.add(taskView);
		taskViewList.add(index, taskView);
		updatePreferredDimensions();
	}
	
	/**
	 * removes a task from the stage
	 * @param taskView
	 */
	public void removeTaskView(TaskView taskView) {
		stagePane.remove(taskView);
		updatePreferredDimensions();	
		redrawStage();
	}
	
	
	/**
	 * gets the ID of this stage
	 */
	public int getID(){
		return  id;
	}
	
	public ArrayList<TaskView> getTaskViewList(){
		return taskViewList;
	}
	
	/**
	 * Updates the current stage by passing in new stages
	 * @param newStageModel - the stageModel to replace the current stage with
	 */
	public void updateContents(StageModel newStageModel){
		this.lblStageTitle.setText(newStageModel.getTitle());
		this.closable = newStageModel.getClosable();
		this.stageModel = newStageModel;
		btnClose.setEnabled(newStageModel.getClosable());
		this.redrawStage();
	}
}