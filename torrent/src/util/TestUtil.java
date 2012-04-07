package util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

public class TestUtil {

	@Test
	public void test() {
		// test for converBytesToInt
		byte[] a = new byte[] {0, 0, 0, 13};
		
		assertEquals(Util.convertBytesToInt(a), 13);
		assertFalse(Util.convertBytesToInt(a) == 12);
		
		// test for caoncallAll (byte) 
		byte[] one = new byte[] {1, 2}; 
		byte[] two = new byte[] {3, 4}; 
		byte[] three = new byte[] {5, 6}; 
		byte[] four = new byte[] {};
		
		byte[] result = new byte[] {1, 2, 3, 4, 5, 6};
		assertTrue(Arrays.equals(Util.concatAll(one, two, three, four), result));
		assertFalse(Arrays.equals(Util.concatAll(two, three, four), result));
		
		// test for converIntToBytes
		int number = 13; 
		result = Util.converIntToBytes(number, 4); 
		assertTrue(Arrays.equals(result, new byte[] {0, 0, 0, 13}));
	}
	

}
