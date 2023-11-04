package com.thelocalmarketplace.software.test;

import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.scale.ElectronicScaleListener;
import com.thelocalmarketplace.hardware.SelfCheckoutStation;

public class SelfCheckoutStationTest {

	@Before
	void setup() {
		ElectronicScaleListener ScaleListener = null;
		SelfCheckoutStation station = new SelfCheckoutStation();
		station.baggingArea.register(ScaleListener);
	}
	
	
	@Test
	void AddValidProductToCart(){
		
		
	}
}
