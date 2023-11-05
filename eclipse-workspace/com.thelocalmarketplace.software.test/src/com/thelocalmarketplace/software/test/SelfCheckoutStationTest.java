package com.thelocalmarketplace.software.test;

import java.beans.Transient;

import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.scale.ElectronicScaleListener;
import com.thelocalmarketplace.hardware.SelfCheckoutStation;

public class SelfCheckoutStationTest {

	ElectronicScaleListener ScaleListener = null;
	SelfCheckoutStation station = new SelfCheckoutStation();
	BarcodeScannerListener Barcode = null;

	@Before
	void setup() {
		ElectronicScaleListener ScaleListener = null;
		SelfCheckoutStation station = new SelfCheckoutStation();
		station.baggingArea.register(ScaleListener);
	}
	
	
	@Test
	void AddValidProductToCart(){
		if (addItemViaScan == True) {
			addItemViaScan();
		} 
		
	}

	@Test
	public void barcode() {
		Main.addItemViaScan(barcode);
	}

	

}
