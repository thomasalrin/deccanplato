package org.megam.deccanplato.provider.zoho.crm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.StringTokenizer;

public class AccessParser {
	private static final String AUTHTOKEN="AUTHTOKEN";
	private String auth_token="";
	
	public AccessParser(String token) throws IOException{		
		BufferedReader reader=new BufferedReader(new StringReader(token));
		String result=null;
		while((result=reader.readLine())!=null){
			if(result.trim().startsWith(AUTHTOKEN)){
				StringTokenizer strk=new StringTokenizer(result,"=");
				strk.nextToken();
				auth_token=strk.nextToken();
			}
		}
	}

	public String getAuthtoken(){
		return auth_token;
	}
	public String toString(){
		
		return(getAuthtoken());
		
	}
}