package board;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

import utilities.SokobanUtil;

public class BoardDynamicLockTest
{

	@Test
	public void test()
	{
		Board b = SokobanUtil.readMap("./maps/test/dynamicLockTest.map");
		
		assertEquals(b.isBoxLocked(new Point(1,1)), true);
		assertEquals(b.isBoxLocked(new Point(2,3)), false);
		assertEquals(b.isBoxLocked(new Point(7,1)), true);
		assertEquals(b.isBoxLocked(new Point(4,6)), true);
		assertEquals(b.isBoxLocked(new Point(11,5)), false);
		assertEquals(b.isBoxLocked(new Point(9,4)), false);
		assertEquals(b.isBoxLocked(new Point(3,2)), true);
	}

}
