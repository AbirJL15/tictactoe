// Classe de tests TestJeu
import org.example.GererDB;
import org.example.GererDBMock;
import org.example.Jeu;
import org.example.TicTacToeSave;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.xml.crypto.Data;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestJeu {
    private GererDB mockGererDB;
    private Jeu jeu;
    private TicTacToeSave ticTacToeSave;

    @BeforeEach
    public void setUp() {
        mockGererDB = GererDBMock.createMock();
        jeu = new Jeu();
        ticTacToeSave = new TicTacToeSave(mockGererDB);
    }

    @Test
    public void testInitializeDatabase_Success() throws Exception {
        doNothing().when(mockGererDB).verify();
        doNothing().when(mockGererDB).connect();
        doNothing().when(mockGererDB).drop();
        doNothing().when(mockGererDB).create();

        boolean result = ticTacToeSave.initializeDatabase();

        assertTrue(result, "Database should initialize successfully.");
        verify(mockGererDB).verify();
        verify(mockGererDB).connect();
        verify(mockGererDB).drop();
        verify(mockGererDB).create();
    }

    @Test
    public void testInitializeDatabase_Failure() throws Exception {
        doThrow(new RuntimeException("Database error")).when(mockGererDB).verify();

        boolean result = ticTacToeSave.initializeDatabase();

        assertFalse(result, "Database initialization should fail.");
        verify(mockGererDB).verify();
        verify(mockGererDB, never()).connect();
        verify(mockGererDB, never()).drop();
        verify(mockGererDB, never()).create();
    }

    @Test
    public void testSaveMove_Success() throws Exception {
        doNothing().when(mockGererDB).verify();
        doNothing().when(mockGererDB).connect();
        doNothing().when(mockGererDB).save(any(Data.class));

        Data data = new Data(1, 1, 1, 'X');
        boolean result = ticTacToeSave.saveMove(data);

        assertTrue(result, "Move should be saved successfully.");
        verify(mockGererDB).verify();
        verify(mockGererDB).connect();
        verify(mockGererDB).save(data);
    }

    @Test
    public void testSaveMove_Failure() throws Exception {
        doNothing().when(mockGererDB).verify();
        doNothing().when(mockGererDB).connect();
        doThrow(new RuntimeException("Database error")).when(mockGererDB).save(any(Data.class));

        Data data = new Data(1, 1, 1, 'X');
        boolean result = ticTacToeSave.saveMove(data);

        assertFalse(result, "Move saving should fail.");
        verify(mockGererDB).verify();
        verify(mockGererDB).connect();
        verify(mockGererDB).save(data);
    }

    @Test
    public void testPlacePiece_ValidMove() {
        jeu.placePiece(0, 0);
        assertDoesNotThrow(() -> jeu.placePiece(1, 1));
    }

    @Test
    public void testPlacePiece_InvalidMove_OutOfBounds() {
        assertThrows(RuntimeException.class, () -> jeu.placePiece(-1, 0));
        assertThrows(RuntimeException.class, () -> jeu.placePiece(0, 3));
    }

    @Test
    public void testPlacePiece_InvalidMove_OccupiedSpace() {
        jeu.placePiece(0, 0);
        assertThrows(RuntimeException.class, () -> jeu.placePiece(0, 0));
    }

    @Test
    public void testPlayerAlternation() {
        jeu.placePiece(0, 0); // X's turn
        assertEquals('O', jeu.getCurrentPlayer(), "Next player should be O.");
        jeu.placePiece(1, 1); // O's turn
        assertEquals('X', jeu.getCurrentPlayer(), "Next player should be X.");
    }

    @Test
    public void testWinningCondition() {
        jeu.placePiece(0, 0);
        jeu.placePiece(1, 0);
        jeu.placePiece(0, 1);
        jeu.placePiece(1, 1);
        jeu.placePiece(0, 2);
        // X should win
        assertTrue(jeu.getGrid()[0][2] == 'X');
    }

    @Test
    public void testDrawCondition() {
        jeu.placePiece(0, 0);
        jeu.placePiece(0, 1);
        jeu.placePiece(0, 2);
        jeu.placePiece(1, 0);
        jeu.placePiece(1, 1);
        jeu.placePiece(1, 2);
        jeu.placePiece(2, 0);
        jeu.placePiece(2, 2);
        jeu.placePiece(2, 1);
        // Draw
        assertEquals(' ', jeu.getGrid()[0][0], "Grid should reset after draw.");
    }
}
