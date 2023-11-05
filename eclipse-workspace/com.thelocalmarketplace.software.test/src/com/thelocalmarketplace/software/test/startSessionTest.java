 package com.thelocalmarketplace.softwaretest;                                    
                                                                                  
 import static org.junit.Assert.*;                                                
                                                                                  
 import java.math.BigDecimal;                                                     
 import java.util.ArrayList;                                                      
 import java.util.Currency;                                                       
                                                                                  
 import org.junit.Assert;                                                         
 import org.junit.Before;                                                         
 import org.junit.Test;                                                           
                                                                                  
 import com.jjjwelectronics.Numeral;                                              
 import com.jjjwelectronics.scanner.Barcode;                                      
 import com.tdc.CashOverloadException;                                            
 import com.tdc.DisabledException;                                                
 import com.tdc.IComponent;                                                       
 import com.tdc.IComponentObserver;                                               
 import com.tdc.coin.Coin;                                                        
import com.tdc.coin.CoinSlot;                                                    	
 import com.tdc.coin.CoinSlotObserver;                                            
 import com.thelocalmarketplace.hardware.BarcodedProduct;                         
 import com.thelocalmarketplace.hardware.SelfCheckoutStation;                     
 import com.thelocalmarketplace.software.Main;                                    
                                                                                  
 import ca.ucalgary.seng300.simulation.NullPointerSimulationException;            
import powerutility.NoPowerException;                                            	
 import powerutility.PowerGrid;                                                   
                                                                                  
 public class startSessionTest {                                                    
 	                                                                                
 	private static class MainStub extends Main{                                     
         public void startSession() {                                             
             this.startSession();                                                 
         }                                                                        
                                                                                  
         public void payForOrder(CoinStub coin) {                                 
         	this.payForOrder(coin);                                                 
         }                                                                        
                                                                                  
     }                                                                            
		                                                                                
 	private static class CoinStub extends Coin{                                     
                                                                                  
 		public CoinStub() {                                                            
 			super(Currency.getInstance("CAD"), BigDecimal.ONE);                           
 			// TODO Auto-generated constructor stub                                       
 		}                                                                              
 		                                                                               
 	}                                                                               
 	                                                                                
		private static class coinSlotObserverStub implements CoinSlotObserver{          
                                                                                  
 		@Override                                                                      
 		public void enabled(IComponent<? extends IComponentObserver> component) {      
 			// TODO Auto-generated method stub                                            
 			                                                                              
 		}                                                                              
	                                                                                 
			@Override                                                                      
			public void disabled(IComponent<? extends IComponentObserver> component) {     
 			// TODO Auto-generated method stub                                            
 			                                                                              
 		}                                                                              
                                                                                  
 		@Override                                                                      
 		public void turnedOn(IComponent<? extends IComponentObserver> component) {     
 			// TODO Auto-generated method stub                                            
 			                                                                              
 		}                                                                              
                                                                                  
 		@Override                                                                      
 		public void turnedOff(IComponent<? extends IComponentObserver> component) {    
 			// TODO Auto-generated method stub                                            
				                                                                              
 		}                                                                              
                                                                                  
 		@Override                                                                      
			public void coinInserted(CoinSlot slot) {                                      
				// TODO Auto-generated method stub                                            
				                                                                              
			}                                                                              
 		                                                                               
 	}                                                                               
		                                                                                
 	                                                                                
 	                                                                                
    private CoinStub coin;                                                       
 	private SelfCheckoutStation station;                                            
 	private coinSlotObserverStub listener = new coinSlotObserverStub();             
 	private ArrayList<BarcodedProduct> shoppingCart;                                
 	private Barcode barcodeOne;                                                     
 	private BarcodedProduct productOne;                                             
 	private BarcodedProduct productTwo;                                             
 	private MainStub software;                                                      
                                                                                  
 	                                                                                
 	@Before                                                                         
 	public void setup() {                                                           
 		barcodeOne = new Barcode(new Numeral[] {Numeral.one});                         
 		productOne = new BarcodedProduct(barcodeOne, "The first test product", 1, 72); 
 		productTwo = new BarcodedProduct(barcodeOne, "The Second test product", 2, 72);
 		station = new SelfCheckoutStation();                                           
 		station.coinSlot.attach(listener);                                             
 		coin = new CoinStub(); 
 		
 		shoppingCart = new ArrayList<>();                                              
 		software = new MainStub();                                                     
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