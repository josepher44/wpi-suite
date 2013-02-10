package edu.wpi.cs.wpisuitetng.modules.core;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.Permission;
import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.database.DataStore;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.mockobjects.MockDataStore;
import edu.wpi.cs.wpisuitetng.modules.Model;
import edu.wpi.cs.wpisuitetng.modules.core.entitymanagers.ProjectManager;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

public class ProjectManagerTest {

	ProjectManager test;
	ProjectManager testWithRealDB;
	Project temp;
	Project delete1;
	Project delete2;
	Project updateTemp;
	Project conflict;
	Gson json;
	Session tempSession;
	User tempUser;
	Project add1;
	Project add2;
	
	@Before
	public void setUp() throws WPISuiteException
	{
		test = new ProjectManager(MockDataStore.getMockDataStore());
		testWithRealDB = new ProjectManager(DataStore.getDataStore());
		delete1 = new Project("test2", "10");
		delete2 = new Project("test3", "1");
		add1 = new Project("add1", "11");
		add2 = new Project("add2", "12");
		temp = new Project("test","8");
		tempUser = new User("name", "username", "password", 1);
		tempUser.setRole(Role.ADMIN);
		temp.setPermission(Permission.WRITE, tempUser);
		updateTemp = new Project("0", "proj0");
		updateTemp.setPermission(Permission.WRITE, tempUser);
		conflict = new Project("test", "5");
		conflict.setPermission(Permission.WRITE, tempUser);
		tempSession = new Session(tempUser);
		json = new Gson();
	}
	
	
	
	@Test
	public void testMakeEntity() {
		Project u = null;
		temp.setPermission(Permission.WRITE, tempUser);
		try {
			u = test.makeEntity(new Session(tempUser), temp.toJSON());
		} catch (WPISuiteException e) {
			fail("unexpected exception");
		}
		assertEquals(u,temp);
	}
	
	@Test(expected = ConflictException.class)
	public void testMakeEntityExists() throws WPISuiteException {
		test.makeEntity(tempSession, json.toJson(conflict, Project.class));
	}
	
	@Test(expected = BadRequestException.class)
	public void testMakeEntityBadJson() throws WPISuiteException {
		test.makeEntity(tempSession, "Garbage");
	}

	@Ignore
	public void testGetEntitySessionString() {
		fail("GetAll is not yet implemented");
	}

	@Test(expected = NotFoundException.class)
	public void testGetEntityStringEmptyString() throws NotFoundException {
		test.getEntity("");
	}
	
	@Test
	public void testGetEntityStringProjectExists() {
		Project[] u = null;
		try {
			u = test.getEntity("5");
		} catch (NotFoundException e) {
			fail("unexpected exception");
		}
		assertEquals(conflict, u[0]);
	}
	
	@Test(expected = NotFoundException.class)
	public void testGetEntityStringProjectDNE() throws NotFoundException {
		test.getEntity("jefferythegiraffe");
	}

	@Test
	public void testGetAll() throws WPISuiteException {
		Project[] initial = testWithRealDB.getAll(new Session(tempUser));
		int initCount = initial.length;
		
		testWithRealDB.save(tempSession, add1);
		testWithRealDB.save(tempSession, add2);
		Project[] myList = testWithRealDB.getAll(new Session(tempUser));
		assertEquals(initCount + 2, myList.length);
	}

	@Test(expected = WPISuiteException.class)
	public void testSaveFail() throws WPISuiteException {
		new ProjectManager(new Data(){
			@Override
			public <T> boolean save(T aTNG) {return false;}
			@Override
			public List<Model> retrieve(Class anObjectQueried,String aFieldName, Object theGivenValue) {return null;}
			@Override
			public <T> T delete(T aTNG) {return null;}
			@Override
			public void update(Class anObjectToBeModified, String fieldName,Object uniqueID, String changeField, Object changeValue) {}
			@Override
			public <T> List<T> retrieveAll(T arg0) {
				return null;
			}
			@Override
			public <T> List<T> deleteAll(T aSample) {
				// TODO Auto-generated method stub
				return null;
			}
			}
		).save(null, null);
	}

	@Test(expected = WPISuiteException.class)
	public void testDeleteEntityFail() throws WPISuiteException {
		new ProjectManager(new Data(){
			@Override
			public <T> boolean save(T aTNG) {return false;}
			@Override
			public List<Model> retrieve(Class anObjectQueried,String aFieldName, Object theGivenValue) {
				List<Model> a = new ArrayList<Model>();
				a.add(temp);
				return a;
				}
			@Override
			public <T> T delete(T aTNG) {return null;}
			@Override
			public void update(Class anObjectToBeModified, String fieldName,Object uniqueID, String changeField, Object changeValue) {}
			@Override
			public <T> List<T> retrieveAll(T arg0) {
				return null;
			}
			@Override
			public <T> List<T> deleteAll(T aSample) {
				// TODO Auto-generated method stub
				return null;
			}
			}
		).deleteEntity(null, temp.getIdNum());
	}
	
	@Test
	public void testDeleteEntity() throws WPISuiteException
	{
		new ProjectManager(new Data(){
			@Override
			public <T> boolean save(T aTNG) {return false;}
			@Override
			public List<Model> retrieve(Class anObjectQueried,String aFieldName, Object theGivenValue) {
				List<Model> a = new ArrayList<Model>();
				a.add(temp);
				return a;}
			@Override
			public <T> T delete(T aTNG) {return aTNG;}
			@Override
			public void update(Class anObjectToBeModified, String fieldName,Object uniqueID, String changeField, Object changeValue) {}
			@Override
			public <T> List<T> retrieveAll(T arg0) {
				return null;
			}
			@Override
			public <T> List<T> deleteAll(T aSample) {
				return null;
			}
			}
		).deleteEntity(tempSession, temp.getIdNum());
	}

	@Test
	@Ignore
	public void testDeleteAll() throws WPISuiteException {
		Project[] initial = testWithRealDB.getAll(new Session(tempUser));
		int initCount = initial.length;
		
		testWithRealDB.save(tempSession, delete1);
		testWithRealDB.save(tempSession, delete2);
		Project[] myList = testWithRealDB.getAll(new Session(tempUser));
		assertEquals(2, myList.length);
		
		//testWithRealDB.deleteAll(new Session(tempUser));
		myList = testWithRealDB.getAll(new Session(tempUser));
		assertEquals(1, myList.length);
		assertEquals(myList[0], null);
	}

	@Ignore
	public void testCount() {
		fail("Not yet implemented");
	}
	
	@Test
	/**
	 * Tests if the update() function properly maps the JSON string then applies
	 * 	the changes to the given User.
	 * @throws WPISuiteException
	 */
	public void testUpdate() throws WPISuiteException
	{
		Session ses = null;
		String updateString = "{ \"idNum\": \"2\", \"name\": \"proj2\" }";
		Project newTemp = this.test.update(tempSession, updateTemp, updateString);
		
		// TODO: find a way to retrieve the User from storage to run assertions on.
		
		assertTrue(newTemp.getIdNum().equals("2"));
		assertTrue(newTemp.getName().equals("proj2"));
	}
	
	@Test(expected = WPISuiteException.class)
	/**
	 * Tests failure in update's ObjectMapper. 
	 * @throws WPISuiteException	on success
	 */
	public void testUpdateFailure() throws WPISuiteException
	{
		Session ses = null;
		String updateString = "{ \"idNum\": \"2\", \"name\": \"proj2\",,,,,,,,,,, }"; // extra commas cause problems in ObjectMapper
		
		this.test.update(ses, updateTemp, updateString);
		
		fail("Exception should have been thrown");
	}

}
