package com.thelocalmarketplace.software.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

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
	
	@BeforeClass
	public static void initialSetUp() {
		barcodeOne = new Barcode(new Numeral[] {Numeral.one});
		productOne = new BarcodedProduct(barcodeOne, "The first test product", 1, 72);
		itemOne = new BarcodedItem(barcodeOne, new Mass(72 * Mass.MICROGRAMS_PER_GRAM));
		
		barcodeTwo = new Barcode(new Numeral[] {Numeral.two});
		productTwo = new BarcodedProduct(barcodeTwo, "The second test product", 1, 50);
		itemTwo = new BarcodedItem(barcodeTwo, new Mass(30 * Mass.MICROGRAMS_PER_GRAM));		
	}
	
	@Before
	public void setUp() {
		inSession = true;
		station.baggingArea.enable();
		station.scanner.enable();
		station.coinSlot.enable();
		
		station = new SelfCheckoutStation();
		station.plugIn(new PowerGrid());
		shoppingCart = new ArrayList<BarcodedProduct>();
		
	}

	@Test
	public void gram72Test() {
		shoppingCart.add(productOne);
		station.baggingArea.addAnItem(itemOne);
		assertTrue(!station.scanner.isDisabled() & !station.coinSlot.isDisabled());
	}

}
