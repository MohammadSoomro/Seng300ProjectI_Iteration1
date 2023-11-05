package com.thelocalmarketplace.software.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.SelfCheckoutStation;
import com.thelocalmarketplace.software.Main;

import powerutility.PowerGrid;

public class MainTest extends Main{
	
	private static Barcode barcodeOne;
	private static BarcodedProduct productOne;
	private static BarcodedItem itemOne;
	
	private static Barcode barcodeTwo;
	private static BarcodedProduct productTwo;
	private static BarcodedItem itemTwo;
	
	private static double d1 = 72.0;
	private static double d2 = 50.0;
	private static double d3 = 30.0;
	
	private static Main main = new Main();
	
	/**
	 * Initialize objects required for test
	 */
	@BeforeClass
	public static void initialSetUp() {
		barcodeOne = new Barcode(new Numeral[] {Numeral.one});
		productOne = new BarcodedProduct(barcodeOne, "The first test product", 1, d1);
		productOneMass = new Mass(productOne.getExpectedWeight());
		itemOne = new BarcodedItem(barcodeOne, new Mass(d1));
		
		barcodeTwo = new Barcode(new Numeral[] {Numeral.two});
		productTwo = new BarcodedProduct(barcodeTwo, "The second test product", 1, d2);
		itemTwo = new BarcodedItem(barcodeTwo, new Mass(d3));
		
		station = new SelfCheckoutStation();
		
		station.plugIn(PowerGrid.instance());
		station.turnOn();
		station.baggingArea.register(main);
		
		station.baggingArea.enable();
		station.scanner.enable();
		station.scanner.register(main);
		station.coinSlot.enable();
		inSession = true;
		shoppingCart = new ArrayList<BarcodedProduct>();
	}
	
	
	/**
	 * Reset variables
	 */
	@Before
	public void setUp() {
		shoppingCart.clear();
		station.baggingArea.enable();
		station.scanner.enable();
		station.coinSlot.enable();
		inSession = true;
	}
	
	@Test
	public void AddProductTest(){
		station.scanner.scan(itemOne);
		assertEquals(1,shoppingCart.size());
	}

	@Test 
	public void notInSessionTest() {
		main.inSession = false;
		station.scanner.scan(itemOne);
		assertEquals(0,shoppingCart.size());
	}
	
	/**
	 * Test behavior when the expected mass is the same as the actual mass on scale.
	 * The scanner and coin slot should both remain enabled.
	 */
	@Test
	public void sameMassTest() {
		shoppingCart.add(productOne);
		station.baggingArea.addAnItem(itemOne);
		assertTrue(!station.scanner.isDisabled() & !station.coinSlot.isDisabled());
		station.baggingArea.removeAnItem(itemOne);
	}
	
	/**
	 * Test behavior when the expected mass is different from the actual mass on scale.
	 * the scanner and coin slot should both be disabled.
	 */
	@Test
	public void differentMassTest() {
		shoppingCart.add(productTwo);
		station.baggingArea.enable();
		station.baggingArea.addAnItem(itemTwo);
		assertTrue(station.scanner.isDisabled() & station.coinSlot.isDisabled());
		station.baggingArea.removeAnItem(itemTwo);
	}
	
	/**
	 * Test behavior when the bagging area is disabled.
	 * The scanner and coin slot should both remain enabled.
	 */
	@Test
	public void disabledTest() {
		station.baggingArea.disable();
		shoppingCart.add(productOne);
		station.baggingArea.addAnItem(itemOne);
		assertTrue(!station.scanner.isDisabled() & !station.coinSlot.isDisabled());
		station.baggingArea.removeAnItem(itemOne);
	}
	
	/**
	 * Test behavior when the bagging area is not in session.
	 * The scanner and coin slot should both remain enabled.
	 */
	@Test
	public void notInSessionTest() {
		inSession = false;
		shoppingCart.add(productOne);
		station.baggingArea.addAnItem(itemOne);
		assertTrue(!station.scanner.isDisabled() & !station.coinSlot.isDisabled());
		station.baggingArea.removeAnItem(itemOne);
	}

	@After
	public void reset() {
		shoppingCart.removeAll(shoppingCart);
	}

}
