/**
 * “Copyright 2012 Megam Systems”
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 **/
package org.megam.deccanplato.provider.salesforce.chatter.handler;

import static org.megam.deccanplato.provider.Constants.*;
import static org.megam.deccanplato.provider.salesforce.crm.Constants.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.megam.deccanplato.http.TransportMachinery;
import org.megam.deccanplato.http.TransportResponse;
import org.megam.deccanplato.http.TransportTools;
import org.megam.deccanplato.provider.BusinessActivity;
import org.megam.deccanplato.provider.core.BusinessActivityInfo;

/**
 * @author pandiyaraja
 *
 *Like class implements salesforce chatter like business activity
 *this class allows us to view like and delete a specific like.
 */
public class LikesImpl implements BusinessActivity{

	private BusinessActivityInfo bizInfo;
	private Map<String, String> args = new HashMap<String, String>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.megam.deccanplato.provider.BusinessActivity#setArguments(org.megam
	 * .deccanplato.provider.core.BusinessActivityInfo, java.util.Map)
	 */
	@Override
	public void setArguments(BusinessActivityInfo tempBizInfo,
			Map<String, String> tempArgs) {
		this.bizInfo = tempBizInfo;
		this.args = tempArgs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.megam.deccanplato.provider.BusinessActivity#run()
	 */
	@Override
	public Map<String, String> run() {
		Map<String, String> outMap = null;
		switch (bizInfo.getActivityFunction()) {
		case DELETE:
			outMap = delete();
			break;
		case VIEW:
			outMap=view();
			break;
		case LIST:
			outMap=list();
			break;
		}
		return outMap;
	}

	/**
	 * This method lists likes of an organization
	 * @param outMap
	 * @return
	 */
	private Map<String, String> list() {
		Map<String, String> outMap=new HashMap<>();
		final String SALESFORCE_CHATTER_CONVERSATION_URL = "/services/data/v25.0/chatter/feed-items/"+args.get(ID)+"/likes";
		
		Map<String, String> header = new HashMap<String, String>();
		header.put(S_AUTHORIZATION, S_OAUTH + args.get(ACCESS_TOKEN));

		TransportTools tst = new TransportTools(args.get(INSTANCE_URL)+SALESFORCE_CHATTER_CONVERSATION_URL,
				null, header);
		try {
			String response = TransportMachinery.get(tst).entityToString();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return outMap;
	}

	/**
	 * this business method deletes a specfic like item
	 * it takes like id as an argument we can get like id 
	 * from any feed item.
	 * @param outMap
	 * @return
	 */
	private Map<String, String> delete() {
		Map<String, String> outMap=new HashMap<>();
		final String SALESFORCECRM_CHATTER_URL = "/services/data/v25.0/chatter/likes/"+args.get(ID);
		Map<String, String> header = new HashMap<String, String>();
		header.put(S_AUTHORIZATION, S_OAUTH + args.get(ACCESS_TOKEN));

		TransportTools tst = new TransportTools(args.get(INSTANCE_URL)
				+ SALESFORCECRM_CHATTER_URL, null,
				header);
		try {
			TransportMachinery.delete(tst);
			outMap.put(OUTPUT, DELETE_STRING + args.get(ID));
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return outMap;
	}

	/**
	 * This method shows a specfic like item details
	 * it takes like id as input
	 * @param outMap
	 * @return
	 */
	private Map<String, String> view() {
		Map<String, String> outMap=new HashMap<>();
		final String SALESFORCE_CHATTER_CONVERSATION_URL = "/services/data/v25.0/chatter/likes/"+args.get(ID);
		
		Map<String, String> header = new HashMap<String, String>();
		header.put(S_AUTHORIZATION, S_OAUTH + args.get(ACCESS_TOKEN));

		TransportTools tst = new TransportTools(args.get(INSTANCE_URL)+SALESFORCE_CHATTER_CONVERSATION_URL,
				null, header);
		try {
			String response = TransportMachinery.get(tst).entityToString();
			outMap.put(OUTPUT, response);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return outMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.megam.deccanplato.provider.BusinessActivity#name()
	 */
	@Override
	public String name() {
		return "like";
	}
}
