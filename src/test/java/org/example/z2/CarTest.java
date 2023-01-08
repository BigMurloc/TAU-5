package org.example.z2;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {
    private final Car car = EasyMock.createMock(Car.class);

    @Test
    public void assertCarIsNotNull(){
        assertNotNull(car);
    }

    @Test
    public void shouldReturnTrueWhenCarNeedsFuel(){
        EasyMock.expect(car.needsFuel()).andReturn(true);
        EasyMock.replay(car);
        assertTrue(car.needsFuel());
    }

    @Test
    public void shouldReturnFalseWhenCarDoesNotNeedFuel(){
        EasyMock.expect(car.needsFuel()).andReturn(false);
        EasyMock.replay(car);
        assertFalse(car.needsFuel());
    }

    @Test
    void shouldReturnEngineTemperature() {
        EasyMock.expect(car.getEngineTemperature()).andReturn(55.0);
        EasyMock.replay(car);
        assertEquals(car.getEngineTemperature(), 55.0);
    }

    @Test
    public void shouldNotThrowAnyExceptionWhenDrivingToDestination(){
        assertDoesNotThrow(() -> car.driveTo("destination"));
    }
}
