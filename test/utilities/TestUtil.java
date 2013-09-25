/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import board.Board;
import board.BoardTest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static org.junit.Assert.fail;

/**
 *
 * @author michal
 */
public class TestUtil {

    public static Board initBoard(String mapName) {
        Path path = Paths.get(BoardTest.testMapDir, mapName);
        try {
            return Board.read(Files.newBufferedReader(path, Charset.defaultCharset()));
        } catch (IOException e) {
            e.printStackTrace();
            fail("File not found: " + path.toAbsolutePath());
            return null;
        }
    }

    public static Board initBoardFromString(String map) {
        return Board.read(new BufferedReader(new StringReader(map)));
    }
    

}
