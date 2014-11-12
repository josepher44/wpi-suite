package edu.wpi.cs.wpisuitetng.modules.taskmanager.tabs.view;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.controller.AddTabController;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.controller.AddWorkflowController;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.WorkflowListModel;

public class NewWorkflowTab extends JPanel{

	private static final long serialVersionUID = 6568533963473785570L;
    

    /** A text field where the user can enter a new work flow */
    private JTextField txtNewWorkflowName;
    
    /** A button for submitting new work flows */
    private final JButton btnSubmit;
    private final MouseAdapter mouseListener;
    private final WorkflowListModel workflowListModel;
   
    public NewWorkflowTab(TaskManagerTabView taskManagerTabView, WorkflowListModel workflowModel) {
    	workflowListModel = workflowModel;
    	
        // Construct the components to be displayed
        txtNewWorkflowName = new JTextField("Enter a work flow name here.");
        btnSubmit = new JButton("Submit");
        
        //Empty the text field when a user clicks on it
        mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                txtNewWorkflowName.setText("");
            }
        };
        
        // Construct the add work flow controller and add it to the submit button
        btnSubmit.addActionListener(new AddWorkflowController(workflowListModel, this));
        
        // Set the layout manager of this panel that controls the positions of the components
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS)); // components will  be arranged vertically
        
        // Clear the contents of the text field when the user clicks on it
        txtNewWorkflowName.addMouseListener(mouseListener);
        // Adjust sizes and alignments
        btnSubmit.setAlignmentX(Component.LEFT_ALIGNMENT);
      
        // Add the components to the panel
        add(Box.createVerticalStrut(20));
        add(txtNewWorkflowName);
        add(Box.createVerticalStrut(20));
        add(btnSubmit);
    }
    
    public JTextField getTxtNewWorkflowName() {
        return txtNewWorkflowName;
    }
    
    void setTxtNewWorkflowName(JTextField txtNewWorkflowName) {
        this.txtNewWorkflowName = txtNewWorkflowName;
    }
    
    /**
     * @return the btnSubmit
     */
    JButton getBtnSubmit() {
        return this.btnSubmit;
    }
    
    WorkflowListModel getWorkflowListModel() {
        return this.workflowListModel;
    }

    static long getSerialversionuid() {
        return serialVersionUID;
    }
    
    /**
     * @return the mouseListener
     */
    MouseAdapter getMouseListener() {
        return this.mouseListener;
    }
}
