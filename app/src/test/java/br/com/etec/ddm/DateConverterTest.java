package br.com.etec.ddm;

import org.junit.Test;
import br.com.etec.ddm.util.DateConverter;

import static org.junit.Assert.assertEquals;

public class DateConverterTest {
	
	@Test
	public void testShouldReturnCorrectDayOfWeek() {
		String correctDay = "Terça";
		String day = DateConverter.dayOfWeek(3);
		
		assertEquals(correctDay, day);
	}
	
	@Test
	public void testShouldReturnEmptyDayOfWeek() {
		String correctReturn = "";
		
		assertEquals(correctReturn, DateConverter.dayOfWeek(-1));
	}
	
	@Test
	public void testShouldReturnCorrectMonthName() {
		String correctMonthName = "Maio";
		String monthName = DateConverter.monthName(4);
		
		assertEquals(correctMonthName, monthName);
	}
	
	@Test
	public void testShouldReturnEmptyMonthName() {
		String correctReturn = "";
		
		assertEquals(correctReturn, DateConverter.monthName(-1));
	}
}