package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import javax.swing.ImageIcon;
import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

public class ClosableTabModel extends AbstractModel {
	private String tabTitle;
	private ImageIcon icon;
	private int index;
	
	/**
	 * @param tabTitle - the title of the icon
	 * @param icon - the ImageIcon to use
	 */
	public ClosableTabModel(String tabTitle, ImageIcon icon, int index) {
		this.tabTitle = tabTitle;
		this.icon = icon;
		this.index = index;
	}
	
	public ClosableTabModel(String tabTitle, int index) {
		this.tabTitle = tabTitle;
		this.icon = new ImageIcon();
		this.index = index;
	}
	
	public ClosableTabModel(String tabTitle) {
		this.tabTitle = tabTitle;
		this.icon = new ImageIcon();
	}
	
	public ClosableTabModel() {
		this.tabTitle = null;
		this.icon = new ImageIcon();
	}
	
	public ImageIcon getImageIcon() {
		return icon;
	}
	
	public String getTabTitle(){
		return this.tabTitle;
	}
	
	public void setTabTitle(String tabTitle) {
		this.tabTitle = tabTitle; 
	}
	
	public int getTabIndex() {
		return this.index;
	}
	
	public void setTabIndex(int index) {
		this.index = index;
	}
	
	public void setImageIcon(String path){
		this.icon = new ImageIcon(path);
	}
	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String toJson() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

}