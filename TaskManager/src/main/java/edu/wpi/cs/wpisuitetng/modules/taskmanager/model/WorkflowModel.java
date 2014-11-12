package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import java.util.ArrayList;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

/**
 * @author alec
 *
 */
public class WorkflowModel extends AbstractModel {
	final private String name;
	public ArrayList<CardModel> cardList;
	private ArrayList<UserModel> userList;

	public WorkflowModel(String name, ArrayList<CardModel> cardList){
		this.name = name;
		this.cardList = cardList;
		this.userList = new ArrayList<UserModel>();
	}
	
	public WorkflowModel(String name){
		this.name = name;
	}
	
	/**
	 * @return the string name of the workflow
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * @return a list of cards in the workflow
	 */
	public ArrayList<CardModel> getCardList() {
		return cardList;
	}
	
	/**
	 * @param cardList - set the list of cards to add to the workflow
	 */
	public void setCardList(ArrayList<CardModel> cardList) {
		this.cardList = cardList;
	}
	
	/**
	 * @param card - a CardModel to add to the workflow
	 * @return the updated list of cards in the workflow
	 */
	public ArrayList<CardModel> addCard(CardModel card) {
		cardList.add(card);
		return cardList;
	}
	
	/**
	 * @param user - the user to add to the list
	 * @return the list of users
	 */
	public ArrayList<UserModel> addUserToList(UserModel user) {
		userList.add(user);
		return userList;
	}
	
	/**
	 * @return the list of users that can be assigned to tasks in this workflow
	 */
	public ArrayList<UserModel> getUserList() {
		if( userList == null || userList.size() == 0){
			System.out.println("Warning: WorkflowModel has no users assigned!");
			return new ArrayList<UserModel>();
		}
		return userList;
	}
	
	
	
	@Override
	public void save() {
	}

	@Override
	public void delete() {
	}

	@Override
	public String toJson() {
        return new Gson().toJson(this, WorkflowModel.class);
	}
	

    public static WorkflowModel fromJson(String json) {
        final Gson parser = new Gson();
        return parser.fromJson(json, WorkflowModel.class);
    }

    public static WorkflowModel[] fromJsonArray(String json) {
        final Gson parser = new Gson();
        return parser.fromJson(json, WorkflowModel[].class);
    }
    
	@Override
	public Boolean identify(Object o) {
        return null;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        
        WorkflowModel other = (WorkflowModel) obj;
        
        return this.name == other.getName();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return this.name;
	}
	
	public void copyFrom(WorkflowModel other){
	}
	

}
