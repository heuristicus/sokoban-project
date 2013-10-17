import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import utilities.BoardAction;
import search.AStar;
import search.BFSNoDuplication;
import search.BestFirst;
import search.Bidirectional;
import search.Heuristic;
import search.Heuristic.ManhattanClosestHeuristic;
import search.MemoSearchMethod;
import search.SearchMethod;
import search.SearchMethod.Direction;
import search.SearchNode;
import utilities.SokobanUtil;
import utilities.SokobanUtil.Action;
import board.Board;
import board.StaticBoard;
import exceptions.IllegalMoveException;


public class Main {

	/**
	 * @param args
	 */
	
	public static boolean USE_BOARD_EXPANSION = true;
	
	{
		Handler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(Level.FINER);
		Logger.getAnonymousLogger().addHandler(consoleHandler);
	}
	
	public static void main(String[] args) throws IOException {
        stdIn();
//        profile();
//        boardExpand();
    }
	    
	public static List<Action> solveBoard(Board start){
        Board goal = SokobanUtil.getSolvedBoard(start);
//        SearchMethod search = new AStar(new Heuristic.RealClosestHeuristic());
        SearchMethod search = new BestFirst(start, goal, new Heuristic.ManhattanClosestHeuristic(), USE_BOARD_EXPANSION);
//		((AStar)search).printTrace = true;
//        SearchMethod search = new AStar(new Heuristic.RealClosestHeuristic());
//        SearchMethod search = new BestFirst(new Heuristic.ManhattanClosestHeuristic());
//		((AStar)search).printTrace = true;
        ArrayList<BoardAction> path = search.findPath();
        
        if (path == null) // happens on timeout or failed search
            return null;
        
//        System.out.println("Box movements:");
//        System.out.println(SokobanUtil.actionListAsString(BoardAction.convertToActionList(pathas)));
        List<Action> pathWithMoves = null;
        if (USE_BOARD_EXPANSION)
        {
            try
            {
                pathWithMoves = start.generateFullActionList(path);
            }
    		catch (IllegalMoveException e)
    		{
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        }else
        {
            pathWithMoves = BoardAction.convertToActionList(path);
        }
//        System.out.println("Full path:");
        return pathWithMoves;
        
    }
	
	public static List<Action> solveBoardBidirectional(Board start){
        Board goal = SokobanUtil.getSolvedBoard(start);

        MemoSearchMethod forward = new BestFirst(start, goal, new Heuristic.ManhattanClosestHeuristic(), Direction.FORWARDS, USE_BOARD_EXPANSION);
       
//        System.out.println("GOAL");
//        System.out.println(goal);
//        ArrayList<Board> debug = goal.generateAllPlayerPositions();
//        System.out.println("PRINT");
//        for (Board b : debug)
//        	System.out.println(b);
        //backward search is initialized with multiple solved states containing all available player positions.
        MemoSearchMethod backward = new BestFirst(goal.generateAllPlayerPositions(), start, new Heuristic.ManhattanClosestHeuristic(), Direction.BACKWARDS, USE_BOARD_EXPANSION);

        SearchMethod bidirectional = new Bidirectional(forward, backward);
        
        ArrayList<BoardAction> path = bidirectional.findPath();
        if (path == null) // happens on timeout or search fail
            return null;

        List<Action> pathWithMoves = null;
        if (USE_BOARD_EXPANSION)
        {
            try
            {
                pathWithMoves = start.generateFullActionList(path);
            }
    		catch (IllegalMoveException e)
    		{
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        }else
        {
            pathWithMoves = BoardAction.convertToActionList(path);
        }
//        System.out.println("Full path:");
        return pathWithMoves;
        
    }
    
    public static void stdIn(){
        //		printExpandedBoards();
    	Board start = Board.read(new BufferedReader(new InputStreamReader(System.in)));
    	System.out.print(SokobanUtil.actionListAsString(solveBoardBidirectional(start)));
    }
    

    
    public static void profile() throws IOException {
        Board startas = Board.read(Files.newBufferedReader(Paths.get("./maps/test/fullTest.map"), Charset.defaultCharset()));
        System.out.print(SokobanUtil.actionListAsString(solveBoard(startas)));
    }
    
    public static void mainTest() throws IOException{
    	Board board = Board.read(new BufferedReader(new InputStreamReader(System.in)));
		System.out.println("Static map only:");
		System.out.println(StaticBoard.getInstance());
		System.out.println("Dynamic board:");
		System.out.println(board);
        System.out.println("Board.equals(board) ? " + (board.equals(board)));
        

        
        Board tt, tt2;
        try {
            tt = board.applyAction(Action.UP, false, true);
            tt2 = tt.applyAction(Action.DOWN, false, true);
            System.out.println("board");
            System.out.println(board);
            System.out.println("tt");
            System.out.println(tt);
            System.out.println("tt2");
            System.out.println(tt2);

            System.out.println("tt==board?" + (tt==board));
            System.out.println("tt.equals(board)?" + (tt.equals(board)));
            System.out.println("tt2.equals(board)?" + (tt2.equals(board)));
            SearchNode sb = new SearchNode(board, null, null, false);
            SearchNode st2 = new SearchNode(tt2, null, null, false);
            SearchNode st = new SearchNode(tt, null, null, false);
            System.out.println("searchnode sb is:");
            System.out.println(sb);
            System.out.println("searchnode st is:");
            System.out.println(st);
            System.out.println("searchnode st2 is:");
            System.out.println(st2);
            System.out.println("sb.equals(st)? " + sb.equals(st));
            System.out.println("sb.equals(st2)? " + sb.equals(st2));
            System.out.println("st.equals(st2)? " + st.equals(st2));
        } catch (IllegalMoveException ex) {
            
        }

        
		
		System.out.println("Free neighbours of (4,2): ");
		for (Point p : board.getFreeNeighbours(new Point(4, 2))) {
			System.out.println(p);
		}
		
		System.out.println("All box access points : ");
		for (Point p : board.getBoxAccessPoints()) {
			System.out.println(p);
		}

		System.out.println(board.get(new Point(4, 2)));
		System.out.println(board.get(new Point(4, 3)));
		System.out.println(board.get(new Point(6, 1)));

		board.moveElement(new Point(4, 2), new Point(4, 3));

		System.out.println("Updated board:");
		System.out.println(board);
		System.out.println("Moving player up");
		Board newBoard = null;

		// Can throw IOException, but that is not recoverable inside the method. So throw it anyway.
        Board start = Board.read(Files.newBufferedReader(Paths.get("./maps/test/searchTestStart.map"), Charset.defaultCharset()));
        Board goal = Board.read(Files.newBufferedReader(Paths.get("./maps/test/searchTestGoal.map"), Charset.defaultCharset()));
        
        System.out.println("BFS finding solution for initial map");
        System.out.println(start);
        SearchMethod bfs = new BFSNoDuplication(start, goal, false);
        ArrayList<BoardAction> path = bfs.findPath();

        if (path != null){
            System.out.println("BFS completed, path length " + path.size());
            System.out.println(SokobanUtil.actionListAsString(BoardAction.convertToActionList(path)));
        } else {
            System.out.println("BFS could not find path.");
        }
        
        
        Board startas = Board.read(Files.newBufferedReader(Paths.get("./maps/test/searchTestStart.map"), Charset.defaultCharset()));
        Board goalas = Board.read(Files.newBufferedReader(Paths.get("./maps/test/searchTestGoal.map"), Charset.defaultCharset()));
        
        System.out.println("astar finding solution for initial map");
        System.out.println(startas);
        
        // Essentially does DFS!
        SearchMethod astar = new AStar(startas, goalas, new ManhattanClosestHeuristic(), false);
        
        ArrayList<BoardAction> pathas = astar.findPath();

        if (pathas != null){
            System.out.println("astar completed, path length " + pathas.size());
            System.out.println(SokobanUtil.actionListAsString(BoardAction.convertToActionList(pathas)));
        } else {
            System.out.println("astar could not find path.");
        }
        
 		// Solution for map zero
		Action sa[] = { Action.RIGHT, Action.UP, Action.RIGHT, Action.UP,
						Action.LEFT, Action.LEFT, Action.LEFT, Action.UP, Action.LEFT,
						Action.LEFT, Action.DOWN, Action.RIGHT, Action.RIGHT,
						Action.RIGHT, Action.RIGHT, Action.DOWN, Action.RIGHT,
						Action.UP, Action.DOWN, Action.LEFT, Action.DOWN, Action.LEFT,
						Action.UP, Action.RIGHT, Action.UP, Action.LEFT, Action.LEFT,
						Action.UP, Action.LEFT, Action.LEFT, Action.DOWN, Action.RIGHT,
						Action.RIGHT, Action.RIGHT, Action.RIGHT };
		ArrayList<Action> solve = new ArrayList<>(Arrays.asList(sa));

		try {
			Board solvedBoard = board.applyActionChained(solve, false);
			System.out.println(solvedBoard);
			newBoard = board.applyAction(Action.UP, false, true);
			System.out.println(newBoard);
			newBoard.applyAction(Action.RIGHT, true, true);
			System.out.println(newBoard);
			newBoard.applyAction(Action.UP, true, true);
			System.out.println(newBoard);
		} catch (IllegalMoveException ex) {
			System.out.println("Could not move player.");
		}

    }
    
}
