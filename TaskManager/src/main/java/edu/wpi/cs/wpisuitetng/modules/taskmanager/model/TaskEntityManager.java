package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import java.util.List;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.UnauthorizedException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.Model;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

public class TaskEntityManager implements EntityManager<TaskModel> {
	
	private Data db;

	public TaskEntityManager(Data db){
		this.db = db;
	}

	/** Creates a new entity for saving in the database
	 * 
	 */
	@Override
	public TaskModel makeEntity(Session s, String content)
			throws BadRequestException, ConflictException, WPISuiteException {
		final TaskModel newTaskModel = TaskModel.fromJson( content );
		
		if( !db.save(newTaskModel, s.getProject())){
			throw new WPISuiteException();
		}
		// TODO Auto-generated method stub
		return newTaskModel;
	}

	/**
	 * Gets a list of taskmodels with the given ID
	 */
	@Override
	public TaskModel[] getEntity(Session s, String id)
			throws NotFoundException, WPISuiteException {
		TaskModel[] taskModels = null;
		try{
			taskModels = db.retrieve(TaskModel.class, "id", s.getProject()).toArray(new TaskModel[0]);
		} catch (WPISuiteException e) {
			e.printStackTrace();
		}
		if( taskModels.length < 1 || taskModels[0] == null){
			throw new NotFoundException();
		}
		// TODO Auto-generated method stub
		return taskModels;
	}

	@Override
	/**
	 * Gets all of the database entitys that are taskModels
	 */
	public TaskModel[] getAll(Session s) throws WPISuiteException {
		List<Model> tasks = db.retrieveAll(new TaskModel(), s.getProject());
		return tasks.toArray(new TaskModel[0]);
	}

	/**
	 * Updates the given taskmodel in the database
	 */
	@Override
	public TaskModel update(Session s, String content)
			throws WPISuiteException {
		TaskModel updatedTask = TaskModel.fromJson(content);
		//TODO tasks need IDs to distinguish one another
		List<Model> oldTasks = db.retrieve(TaskModel.class, "name", updatedTask.getTitle(), s.getProject());
		if( oldTasks.size() < 1 || oldTasks.get(0) == null) {
			throw new NotFoundException();
		}
		TaskModel existingTask = (TaskModel)oldTasks.get(0);
		existingTask = new TaskModel(updatedTask);
		
		if(!db.save(existingTask, s.getProject() )){
			throw new WPISuiteException();
		}
		// TODO Auto-generated method stub
		return existingTask;
	}

	@Override
	public void save(Session s, TaskModel model) throws WPISuiteException {
		db.save(model);
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {
		// TODO Auto-generated method stub
		ensureRole(s, Role.ADMIN);
		return (db.delete(getEntity(s, id)[0]) != null) ? true : false;
	}

	@Override
	public String advancedGet(Session s, String[] args)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAll(Session s) throws WPISuiteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int Count() throws WPISuiteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String advancedPut(Session s, String[] args, String content)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String advancedPost(Session s, String string, String content)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Ensures that a user is of the specified role
	 * @param session the session
	 * @param role the role being verified
	
	 * @throws WPISuiteException user isn't authorized for the given role */
	private void ensureRole(Session session, Role role) throws WPISuiteException {
		User user = (User) db.retrieve(User.class, "username", session.getUsername()).get(0);
		if(!user.getRole().equals(role)) {
			throw new UnauthorizedException();
		}
	}

}