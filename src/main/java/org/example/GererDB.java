package org.example;

public interface GererDB {
    // Permet de spécifier le nom de la BD
    public void connect() throws Exception;
    // Génère une exception si la BD n'existe pas
    public void verify() throws Exception;
    // Crée la BD
    public void create() throws Exception;
    // Supprime les données de la BD
    public void drop() throws Exception;
    // Sauvegarde dans la BD les données de d
    public void save(Data d) throws Exception;
}