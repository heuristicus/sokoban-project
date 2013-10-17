import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

import utilities.SokobanUtil;
import utilities.SokobanUtil.Action;
import utilities.TestUtil;
import board.Board;
import java.util.concurrent.TimeUnit;
import utilities.ProfilingUtil;


public class FullTests {

	@Test
	public void test() {
		Board start = TestUtil.initBoard("../test100/test014.in");
		System.out.print(SokobanUtil.actionListAsString(Main.solveBoardBidirectional(start)));
	}
	
	@Test
	public void test100Maps() {
		final int startMap = 0;
		final int endMap = 10;
		final boolean abortOnException = false; 
//		final List<Integer> exceptList = Arrays.asList(12, 14); // Maps to ignore 
        // How long to run search before timing out
        int timeOutSeconds = 5;
        ProfilingUtil.useProfiling = true;

		
		final int nbTests = endMap - startMap;
		Boolean[] results = new Boolean[nbTests];
		Map<Integer, String> times = new TreeMap<>();
		for (int i = 0; i < nbTests; ++i) {
//			if (exceptList.contains(i)) continue;
			
			Board start = TestUtil.initBoard(String.format("../test100/test%03d.in", i + startMap));
			Board copy = new Board(start);
			List<Action> moves = null;
			
			try {
				long startTime = System.nanoTime();
                ProfilingUtil.timeOut = startTime + TimeUnit.SECONDS.toNanos(timeOutSeconds);
				moves = Main.solveBoardBidirectional(start);	
				long endTime = System.nanoTime();
                if (moves != null){
                    times.put(i + startMap, String.format("%.2g",(endTime - startTime) / 10e9));
                } else {
                    System.out.println("Time limit exceeded for board " + i);
                    times.put(i + startMap, ">" + timeOutSeconds);
                }
			} catch(Exception e) {
				System.err.println("Exception on map " + (i + startMap) );
				e.printStackTrace();
				if (abortOnException) fail();
			}
			if (moves != null) {
				System.out.println("Board " + (i + startMap) + ": " + SokobanUtil.actionListAsString(moves));
				results[i] = copy.applyActionChained(moves, true).isSolved();				
			} else {
				results[i] = false;
			}
            System.out.println("Discarded nodes: " + ProfilingUtil.discardedNodes + "\n"
                    + "Opened nodes: " + ProfilingUtil.openedNodes + "\n"
                    + "Expanded nodes: " + ProfilingUtil.expandedNodes);
            ProfilingUtil.reset();
			
		}
        int passed = 0;
        int failed = 0;
        for (Boolean tRes : results) {
            if (tRes)
                passed++;
            else 
                failed++;
        }
        System.out.println("Passed maps: " + passed + "\n"
                + "Failed maps: " + failed);
		Boolean[] expected = new Boolean[nbTests];
		Arrays.fill(expected, true);
		assertArrayEquals(expected, results);
		System.out.println(times);
	}

}
