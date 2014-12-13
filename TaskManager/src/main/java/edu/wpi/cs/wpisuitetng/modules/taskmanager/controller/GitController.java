package edu.wpi.cs.wpisuitetng.modules.taskmanager.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.RepositoryIssue;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.GitHubRequest;
import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.client.PagedRequest;
import org.eclipse.egit.github.core.service.GitHubService;
import com.google.gson.reflect.TypeToken;
import static org.eclipse.egit.github.core.client.IGitHubConstants.SEGMENT_ISSUES;
import static org.eclipse.egit.github.core.client.IGitHubConstants.SEGMENT_REPOS;
import static org.eclipse.egit.github.core.client.PagedRequest.PAGE_FIRST;
import static org.eclipse.egit.github.core.client.PagedRequest.PAGE_SIZE;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.controller.stage.StageController;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.StageModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.task.TaskModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.tab.GitLinkTab;

/**
 * @author Alec
 * Integrates github with TaskManager
 */
/**
 * @author Alec
 *
 */
public class GitController extends GitHubService implements ActionListener {
	private GitLinkTab gitTab;

	public GitController(GitLinkTab gitTab){
		this.gitTab = gitTab;
	}
	
	/**
	 * Get a specific issue from github
	 * @param issueNumber
	 * @return
	 * @throws IOException
	 */
	private Issue getIssue(String issueNumber) throws IOException {
		if (issueNumber == null)
			throw new IllegalArgumentException("Issue number cannot be null"); //$NON-NLS-1$
		if (issueNumber.length() == 0)
			throw new IllegalArgumentException("Issue number cannot be empty"); //$NON-NLS-1$

		StringBuilder uri = new StringBuilder(SEGMENT_REPOS);
		//We need to append the path to the repository that the user provided
		uri.append('/').append(gitTab.getRepositoryURL().getText());
		uri.append(SEGMENT_ISSUES);
		uri.append('/').append(issueNumber);
		GitHubRequest request = createRequest();
		request.setUri(uri);
		request.setType(Issue.class);
		return (Issue) client.get(request).getBody();
	}
	
	/**
	 * Retrieves all issues from the github repository
	 * @return a list of repositoryIssues
	 * @throws IOException
	 */
	public List<RepositoryIssue> getAllIssues(GitHubClient client) throws IOException {
		System.out.println("Got all issues");
		List<RepositoryIssue> issues = super.getAll(this.pageIssues(client));
		for(RepositoryIssue issue : issues){
			System.out.println(issue.getTitle());
			createTask(issue);
		}
		return issues;
	}
	
	/**
	 * Log the client in using the provided credentials
	 * @throws IOException 
	 */
	private GitHubClient authenticate(GitHubClient client){
		try{
			client.setCredentials(gitTab.getUsernameField().getText(), gitTab.getPassField().getText());
		}catch(Exception e){
			e.printStackTrace();
			setSuccessMessage("Invalid username or password provided");
			return null;
		}
		setSuccessMessage("Successfully connected to repository!");
		try {
			getAllIssues(client);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return client;
	}
	
	/**
	 * This method is used to iterate through all of the issues and retrieve them all
	 * @return a pageIterator for pageIssues
	 */
	public PageIterator<RepositoryIssue> pageIssues(GitHubClient client) {
		PagedRequest<RepositoryIssue> request = createPagedRequest(PAGE_FIRST, PAGE_SIZE);
		StringBuilder uri = new StringBuilder(SEGMENT_REPOS);
		//We need to append the path to the repository that the user provided
		uri.append('/').append(gitTab.getRepositoryURL().getText());
		uri.append(SEGMENT_ISSUES);
		request.setUri(uri);
		request.setType(new TypeToken<List<RepositoryIssue>>() {
		}.getType());
		return createPageIterator(request);
	}
	
	/**
	 * Create a new github client using the provided credentials
	 * @return the fully authenticated client
	 * @throws IOException
	 */
	public GitHubClient createClient() throws IOException {
		GitHubClient client = null;
			try{
				URL parsedUrl = new URL("https://api.github.com");
				client = new GitHubClient(parsedUrl.getHost(), parsedUrl.getPort(), parsedUrl.getProtocol());
			}catch(Exception e){
				setSuccessMessage("Please enter a valid URL.");
				e.printStackTrace();
			}
		return authenticate(client);
	}
	
	/**
	 * Create a task from an issue and add it to the workflow
	 * @param issue
	 */
	public void createTask(Issue issue){
		String tag = "{TM}";
		
		//Return if the task is not tagged
		if(!issue.getTitle().contains(tag))
			return;
		
		TaskModel task = new TaskModel();
		task.setTitle(issue.getTitle().replace(tag,""));
		task.setDescription(issue.getBody());
		int issueID = new BigDecimal(issue.getId()).intValueExact();
		task.setID(issueID);
		task.setCreator(issue.getUser().getName());
		
		Format formatter = new SimpleDateFormat("MM/dd/yyyy");
		task.setDueDate(formatter.format(issue.getCreatedAt()));
		
		HashMap<Integer,StageModel> stageList = WorkflowController.getWorkflowModel().getStageModelList();
		StageModel stage = StageController.locateTaskStage(issueID);
		stage = stage == null ? (StageModel) stageList.values().toArray()[0] : stage;
		task.setStageID(stage.getID());
		task.setCatColor(Color.WHITE);
		stage.addTaskModel(task);
		StageController.sendUpdateRequest(stage);
	}
	
	/**
	 * Set the success/failure messaage in the tab view
	 * @param message
	 */
	private void setSuccessMessage(String message){
		gitTab.getLblVerification().setVisible(true);
		gitTab.getLblVerification().setText(message);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			createClient();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

}