package com.jjjwelectronics.scanner;

/**
 * A complex device hidden behind a simple simulation. They can scan and that is
 * about all.
 * <p>
 * Our premium model: it is extremely unlikely to fail to scan a barcode.
 * 
 * @author JJJW Electronics LLP
 */
public class BarcodeScanner extends AbstractBarcodeScanner {
	/**
	 * Create a barcode scanner.
	 */
	public BarcodeScanner() {
		probabilityOfFailedScan = 0;
	}
}
