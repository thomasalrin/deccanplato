/* 
** Copyright [2012] [Megam Systems]
**
** Licensed under the Apache License, Version 2.0 (the "License");
** you may not use this file except in compliance with the License.
** You may obtain a copy of the License at
**
** http://www.apache.org/licenses/LICENSE-2.0
**
** Unless required by applicable law or agreed to in writing, software
** distributed under the License is distributed on an "AS IS" BASIS,
** WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
** See the License for the specific language governing permissions and
** limitations under the License.
*/
package org.megam.deccanplato.provider.xero.handler;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.megam.deccanplato.provider.BusinessActivity;
import org.megam.deccanplato.provider.core.BusinessActivityInfo;

import com.rossjourdain.util.xero.Account;
import com.rossjourdain.util.xero.ArrayOfPayment;
import com.rossjourdain.util.xero.Invoice;
import com.rossjourdain.util.xero.Payment;
import com.rossjourdain.util.xero.XeroClientException;
import com.rossjourdain.util.xero.XeroClientUnexpectedException;
import com.rossjourdain.util.xero.XeroPublicClient;
import com.rossjourdain.util.xero.XeroXmlManager;

import static org.megam.deccanplato.provider.xero.Constants.*;
import static org.megam.deccanplato.provider.Constants.*;

/**
 * @author pandiyaraja
 *
 * This class implements xero payment details, this class 
 * updates invoice item for a particular account lists payments  
 */
public class PaymentImpl implements BusinessActivity{

	private Map<String, String> args=new HashMap<String, String>();
	private BusinessActivityInfo bizInfo;
	/* (non-Javadoc)
	 * @see org.megam.deccanplato.provider.BusinessActivity#setArguments(org.megam.deccanplato.provider.core.BusinessActivityInfo, java.util.Map)
	 */
	@Override
	public void setArguments(BusinessActivityInfo tempBizInfo,
			Map<String, String> tempArgs) {
		this.args=tempArgs;
		this.bizInfo=tempBizInfo;
	}

	/* (non-Javadoc)
	 * @see org.megam.deccanplato.provider.BusinessActivity#run()
	 */
	@Override
	public Map<String, String> run() {
		Map<String, String> outMap=null;
		switch(bizInfo.getActivityFunction()) {
		case CREATE:
			outMap=create();
			break;
		case LIST:
			outMap=listAll();
			break;
		case VIEW:
			outMap=list();
			break;
		}
		return outMap;
	}

	/**
	 * This method returns a particular payment details by 
	 * usin payment id.
	 * @return
	 */
	private Map<String, String> list() {
		Map<String,String> outMap = new HashMap<String,String>();	
		
		try {
			XeroPublicClient client=new XeroPublicClient(args);
        	String responseString =client.list(args.get(ID), 
        			new StringTokenizer(args.get(BIZ_FUNCTION), "#").nextToken());
    		outMap.put(OUTPUT, responseString);
		} catch (XeroClientException e) {
			e.printStackTrace();
		} catch (XeroClientUnexpectedException e) {
			e.printStackTrace();
		} 
		return outMap; 
	}

	/**
	 * This method returns all payment items from xero with
	 * account and invoice details.
	 * @return
	 */
	private Map<String, String> listAll() {
		 Map<String,String> outMap = new HashMap<String,String>();	
			
			try {
				XeroPublicClient client=new XeroPublicClient(args);
	        	String response =client.listAll(new StringTokenizer(args.get(BIZ_FUNCTION), "#").nextToken());
	    		outMap.put(OUTPUT, response);	    		
			} catch (XeroClientException e) {
				e.printStackTrace();
			} catch (XeroClientUnexpectedException e) {
				e.printStackTrace();
			} 
			return outMap;  
	}

	/**
	 * This method updates payment details in invoice of a particular 
	 * account. this method takes invoice id and account id as input to update 
	 * and invoice item. 
	 * @return
	 */
	private Map<String, String> create() {
		Map<String, String> outMap=new HashMap<>();
		XeroPublicClient client=new XeroPublicClient(args);
		try {

            Invoice invoice = new Invoice();
            invoice.setInvoiceID(args.get(INVOICE_ID));

            Account account = new Account();
            account.setAccountID(args.get(ACCOUNT_ID));

            Payment payment = new Payment();
            payment.setAccount(account);
            payment.setInvoice(invoice);
            payment.setAmount(new BigDecimal(args.get(AMOUNT)));
            payment.setDate(Calendar.getInstance());

            ArrayOfPayment arrayOfPayment = new ArrayOfPayment();
            List<Payment> payments = arrayOfPayment.getPayment();
            payments.add(payment);
            
            String response =client.post(XeroXmlManager.paymentsToXml(arrayOfPayment), 
            		new StringTokenizer(args.get(BIZ_FUNCTION), "#").nextToken());
            outMap.put(OUTPUT, response);
        } catch (XeroClientException ex) {
            ex.printDetails();
        } catch (XeroClientUnexpectedException ex) {
            ex.printStackTrace();
        }
		return outMap;
	}

	/* (non-Javadoc)
	 * @see org.megam.deccanplato.provider.BusinessActivity#name()
	 */
	@Override
	public String name() {
		return "payment";
	}

}
