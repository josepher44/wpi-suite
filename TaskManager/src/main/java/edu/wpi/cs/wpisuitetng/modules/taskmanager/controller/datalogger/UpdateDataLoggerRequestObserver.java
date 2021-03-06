/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Team What? We Thought This Was Bio!
 *******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.taskmanager.controller.datalogger;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.reports.DataLoggerModel;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * @author Joe
 * A request observer for updating the data logger in the database
 */
public class UpdateDataLoggerRequestObserver implements RequestObserver{

	DataLoggerController controller;
	
	public UpdateDataLoggerRequestObserver(DataLoggerController dataLoggerController){
		controller = dataLoggerController;
	}
	@Override
	public void responseSuccess(IRequest iReq) {
        DataLoggerModel dataLogger = DataLoggerModel.fromJson(iReq.getResponse().getBody());
        //controller.updateStage(stage);
		
	}

	@Override
	public void responseError(IRequest iReq) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		// TODO Auto-generated method stub
		
	}

}
