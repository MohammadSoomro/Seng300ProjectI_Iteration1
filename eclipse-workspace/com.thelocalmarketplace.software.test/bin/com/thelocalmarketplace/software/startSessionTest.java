/* Written by: 

 * Name: Ajaypal Sallh
 * UCID: 30023811
 and 
 * Name: Christian Nielsen
 * UCID: 30185718
 */

package com.thelocalmarketplace.software;

import static org.junit.Assert.*;

import java.util.ArrayList;

import com.thelocalmarketplace.hardware.BarcodedProduct;
import org.junit.Test;
import com.thelocalmarketplace.software.*;



public class startSessionTest extends startSession {
	
	private ArrayList<BarcodedProduct> shoppingCart = new ArrayList<>();
	private static class startSessionStub extends startSession{
		public void startSession() {
			this.startSession();
		}
	}
	
	//should pass as startSession empties shopping cart
	@Test
	public void testStartSession() throws Exception  {
		
		startSession();
		assertEquals(0,shoppingCart.size());
		
	}
	
	//Should fail and call exception
	@Test 
	public void testCallStartSessionTwice() throws Exception {
		startSession();
		startSession();
		}
	
	
	
		
}


	


