package org.megam.deccanplato.provider.crm.info;

import java.util.ArrayList;

import org.megam.deccanplato.provider.crm.controller.CRMController;
import org.megam.deccanplato.provider.crm.rest.SugarAdapterTest;

public class SugarCreateUser {
	
	private String session="7ta42d7hu60df6jffj9eqk3363";
	private String module_name="Users";
	java.util.List<Object> name_value_list=new ArrayList<Object>();
	public SugarCreateUser(){
		
		
		User U=new User();
		U.setName("user_name");
		U.setValue("AB");
		name_value_list.add(U);
		User U1=new User();
		U1.setName("last_name");
		U1.setValue("Deviliaris");
		name_value_list.add(U1);
		User U2=new User();
		U2.setName("emailAddress");
		U2.setValue("raja.pan123@yahoo.com");
		name_value_list.add(U2);
	}

}
