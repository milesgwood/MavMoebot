/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milesgwood.mavenproject2.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import static java.lang.System.out;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.NoInitialContextException;
import org.json.simple.*;
import org.json.simple.parser.*;

/**
 *
 * @author vice
 */
public class DbHandler {
    
    //Database access credentials if needed
    protected String driverName = null, url = null, userId = null, password = null;
    
    //Connection and entities
    Connection c;
    PreparedStatement stmt;
    ResultSet rs;
    String sql;
    boolean isOpen = false;
    
    /**
     * Constructor that gets the needed info from the application context
     * Info is stored in the web.xml AND in the exception below for if I'm testing.
     */
    public DbHandler() {
    try {
            Context envCtx = (Context) (new InitialContext()).lookup("java:comp/env");
            driverName = (String) envCtx.lookup("DriverClassName");
            url = (String) envCtx.lookup("Url");
            userId = (String) envCtx.lookup("UserId");
            password = (String) envCtx.lookup("Password");
            
            //This catch is for Testing JUnit tests. Where no server context is setup.
        } catch (NoInitialContextException contextError) {
            driverName = "com.mysql.jdbc.Driver";
            url = "jdbc:mysql://rds-mysql-10mintutorial.cr8bdnqgcx5a.us-east-1.rds.amazonaws.com:3306/moe_db";
            userId = "milesgwood";
            password = "FuckR6ddit";
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Open the DB connection
     *
     * @throws SQLException if the DB connection cannot be established
     */
    public void open() throws SQLException {
        try {
            Class.forName(driverName);
            c = DriverManager.getConnection(url, userId, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        isOpen = true;
    }
    
    /**
     * For closing a database connection.
     * @throws SQLException 
     */
    public void close() throws SQLException {
        stmt.close();
        c.close();
        isOpen = false;
    }
    
    

    /**
     * Adds a song object to the database.
     * @param s 
     */
    protected void insertSong(Songs s) {
        try {
            if (!isOpen) {
                open();
            }
            sql = "INSERT INTO songs  (show_id, name, mtime ) VALUES ( ?, ? , ? );";
            stmt = c.prepareStatement(sql);
            stmt.setInt(1, s.showId);
            stmt.setString(2, s.name);
            stmt.setString(3, s.mtime);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DbHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    /**
     * Adds a song object to the database.
     * @param s 
     */
    protected void insertSongFullData(Songs s) {
        try {
            if (!isOpen) {
                open();
            }
            sql = "INSERT INTO songs  (show_id, name, mtime, source, format, length, title, album, track ) VALUES (?,?,?,?,?,?,?,?,? );";
            stmt = c.prepareStatement(sql);
            stmt.setInt(1, s.showId);
            stmt.setString(2, s.name);
            stmt.setString(3, s.mtime);
            //New datapoints
            stmt.setString(4, s.source);
            stmt.setString(5, s.format);
            stmt.setString(6, s.length);
            stmt.setString(7, s.title);
            stmt.setString(8, s.album);
            stmt.setString(9, s.track);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DbHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Gets the id from the show identifier. The identifier is the first half of the URL for a song.
     * @param identifier
     * @return 
     */
    protected Integer getShowIdFromUri(String identifier) {
        int id = 0;
        try {
            if (!isOpen) {
                open();
            }
            sql = "SELECT id FROM shows WHERE show_url = ?;";
            stmt = c.prepareStatement(sql);
            stmt.setString(1 , identifier);
            rs = stmt.executeQuery();

            while (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
 
    
    /**
     * Old method not used anymore.
     * @return 
     */
    public ArrayList<Songs> getNext100Songs()
    {
        ArrayList<Songs> songPlayList = new ArrayList<Songs>();
        String date;
        
        String songName;
        String showUrl;
        
        Songs song;

        try {
            if (!isOpen) {
                open();
            }
            sql = "SELECT * FROM songs JOIN shows ON songs.show_id = shows.id LIMIT 100;";
            stmt = c.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                song = new Songs();
                song.id = rs.getInt("id");
                song.showId = rs.getInt("show_id");
                songName = rs.getString("name");
                showUrl = rs.getString("show_url");
                song.mtime = rs.getString("mtime");
                date = rs.getString("date");
                               
                //Here we are adding the resource location directly in the song.name attribute
                song.name = showUrl + "/" + songName;
                songPlayList.add(song);
            }
            close();
        } catch (SQLException ex) {
            Logger.getLogger(DbHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return songPlayList;
    }
    
    public ArrayList<Songs> getNext20ShowSkips()
    {
        ArrayList<Songs> songPlayList = new ArrayList<Songs>();
        String date;
        
        String songName;
        String showUrl;
        
        Songs song;

        try {
            if (!isOpen) {
                open();
            }
            sql = "SELECT * FROM songs JOIN shows ON songs.show_id = shows.id WHERE songs.id LIMIT 20;";
            stmt = c.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                song = new Songs();
                song.id = rs.getInt("id");
                song.showId = rs.getInt("show_id");
                songName = rs.getString("name");
                showUrl = rs.getString("show_url");
                song.mtime = rs.getString("mtime");
                date = rs.getString("date");
                               
                //Here we are adding the resource location directly in the song.name attribute
                song.name = showUrl + "/" + songName;
                songPlayList.add(song);
            }
            close();
        } catch (SQLException ex) {
            Logger.getLogger(DbHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return songPlayList;
    }
    

    
    /**
     * @return a single show ID from the database representing a show with at least one song.
     */
    public int getRandomShowID(){
        
        int id = 5;
        
        try {
            if (!isOpen) {
                open();
            }
            sql = "SELECT show_id FROM songs  ORDER BY RAND()  LIMIT 1;";
            stmt = c.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                id = rs.getInt("show_id");
            }
            close();
        } catch (SQLException ex) {
            Logger.getLogger(DbHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
    
    public Shows getRandomShowFromDB() {
        ArrayList<Songs> showSetlist = new ArrayList<Songs>();
        Shows randShow = null;
        while(showSetlist.size() < 1){
        int id = getRandomShowID();
        randShow = getShowMetaDataFromId(id);
        showSetlist = populateShowSetlist(id);
        randShow.songs = showSetlist;
        }
        return randShow;
    }
    
    private Shows getShowMetaDataFromId(int id)
    {
        Shows show = null;
        String date = "BAD DATE";
        String venue = "No Venue Yet";
        String showUrl = "BAD URL";

        try {
            if (!isOpen) {
                open();
            }
            sql = "SELECT * FROM shows WHERE id = ?;";
            stmt = c.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                date = rs.getString("date");
                showUrl = rs.getString("show_url");
                //VENUE IS ALWAYS NULL NOW SO I WON'T BOTHER WITH IT
            }
            show = new Shows(id, date, venue, showUrl);
        } catch (SQLException ex) {
            Logger.getLogger(DbHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return show;
    }

    /**
     * Take a showID integer and populate a song list for that show ID. This method is 
     * for filling the setlist of a show that already has it's metadata.
     * @param showId
     * @return 
     */
    private ArrayList<Songs> populateShowSetlist(int showId) {
        ArrayList<Songs> showSetlist = new ArrayList<Songs>();
        Songs song;

        try {
            if (!isOpen) {
                open();
            }
            sql = "SELECT * FROM songs WHERE show_id = ?;";
            stmt = c.prepareStatement(sql);
            stmt.setInt(1, showId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                song = new Songs();
                song.id = rs.getInt("id");
                song.showId = rs.getInt("show_id");
                song.name = rs.getString("name");
                song.mtime = rs.getString("mtime");
                showSetlist.add(song);
            }
            close();
        } catch (SQLException ex) {
            Logger.getLogger(DbHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return showSetlist;
    }
    
    /**
     * Gets all of the shows from the database.
     * @return 
     */
    public ArrayList<Shows> getAllShows()
    {
        ArrayList<Shows> shows = new ArrayList<Shows>();
        Integer id;
        String date;
        String venue;
        String showUrl;

        try {
            if (!isOpen) {
                open();
            }
            sql = "SELECT * FROM shows;";
            stmt = c.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                id = rs.getInt("id");
                date = rs.getString("date");
                venue = rs.getString("venue");
                showUrl = rs.getString("show_url");
                shows.add(new Shows(id, date, venue, showUrl));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return shows;
    }
    
    public void updateShowVenue(String venue, int id) {
        try 
        {if (!isOpen) {
                open();
            }
            sql = "UPDATE shows SET venue = ? WHERE id = ?";
            stmt = c.prepareStatement(sql);
            stmt.setString(1, venue);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DbHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}