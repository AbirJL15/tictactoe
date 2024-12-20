package org.example;

// Classe TicTacToeSave
public class TicTacToeSave {
    private GererDB gererDB;

    public TicTacToeSave(GererDB gererDB) {
        this.gererDB = gererDB;
    }

    public boolean initializeDatabase() {
        try {
            gererDB.verify();
            gererDB.connect();
            gererDB.drop();
            gererDB.create();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveMove(Data data) {
        try {
            gererDB.verify();
            gererDB.connect();
            gererDB.save(data);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

