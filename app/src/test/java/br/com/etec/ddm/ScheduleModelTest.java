package br.com.etec.ddm;


import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import br.com.etec.ddm.model.ScheduleModel;
import br.com.etec.ddm.util.DateConverter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ScheduleModelTest {
    ScheduleModel testModel;
    long timeInMilis;

    @Before
    public void setup(){
        Calendar date = Calendar.getInstance();
        date.set(2020, 0,1);
        timeInMilis =  date.getTimeInMillis();
        testModel = new ScheduleModel("test","12:00","14:00", timeInMilis);
    }

    @Test
    public void testSetDate(){
        Calendar date = Calendar.getInstance();
        date.set(2000, 11,25);
        testModel.setDate(date.getTimeInMillis());

        assertEquals(2000, testModel.getYear());
        assertEquals(11, testModel.getMonth());
        assertEquals(25, testModel.getDay());
        assertEquals(DateConverter.dayOfWeek(2), testModel.getWeekDay());
    }

    @Test
    public void testGetDate(){
        assertEquals(timeInMilis, testModel.getDate());
    }

   @Test
    public void testGetHourInterval(){
        assertEquals("2:00", testModel.getHourInterval());

        testModel.setInitialHour("14:30");

       assertEquals("23:30", testModel.getHourInterval());
   }

    @Test
    public void testGetDetail(){
        assertEquals("12:00 ás 14:00 - test", testModel.getDetail());
    }

    @Test
    public void testGetFileName(){
        assertEquals("2020_0_1.lsm", testModel.getFileName());
    }

    @Test
    public void testIsInThisDateCheck(){
        ScheduleModel checkModelDate = new ScheduleModel("other event","00:00","11:00", timeInMilis);
        assertTrue(testModel.isInThisDate(checkModelDate));
    }
}
