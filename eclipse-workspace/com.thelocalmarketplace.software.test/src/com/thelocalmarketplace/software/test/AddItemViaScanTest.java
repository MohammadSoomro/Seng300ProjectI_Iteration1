package com.thelocalmarketplace.software.test;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.scale.*;
import com.jjjwelectronics.scale.ElectronicScaleListener;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodeScannerListener;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.SelfCheckoutStation;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.Main;

import powerutility.PowerGrid;

public class SelfCheckoutStationTest {
	
	private boolean inSession = true;
	boolean skipBagging ;
	private Barcode barcodeOne ;
	BarcodedProduct productOne ;
	Mass productOneMass ;
	BarcodedItem itemOne ;
	
	ElectronicScaleListener ScaleListener = null;
	BarcodeScannerListener ScannerListener = null;
	SelfCheckoutStation station = new SelfCheckoutStation();
	ArrayList<BarcodedProduct> shoppingCart ;
	
	Main main = new Main();
	
	
	@Before
	public void setup() {
		
		barcodeOne = new Barcode(new Numeral[] {Numeral.one});
		productOne = new BarcodedProduct(barcodeOne,"",1,1000);
		productOneMass = new Mass(productOne.getExpectedWeight());
		itemOne = new BarcodedItem(barcodeOne,productOneMass);
		skipBagging = false;
		inSession = true;
		
		station.plugIn(PowerGrid.instance());
		station.turnOn();
		
		station.baggingArea.enable();
		station.baggingArea.register(main);
		
		station.scanner.enable();
		station.scanner.register(main);
		
		shoppingCart = new ArrayList<BarcodedProduct>();
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
	
	
	@After
	public void reset() {
		shoppingCart.removeAll(shoppingCart);
	}
}
