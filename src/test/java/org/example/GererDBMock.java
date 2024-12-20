package org.example;

// Mock de l'interface GererDB
import org.mockito.Mockito;

public class GererDBMock {
    public static GererDB createMock() {
        return Mockito.mock(GererDB.class);
    }
}