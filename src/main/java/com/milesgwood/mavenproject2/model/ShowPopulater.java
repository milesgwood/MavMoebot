/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milesgwood.mavenproject2.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author vicetad
 */
public class ShowPopulater extends DbHandler {

//    /**
//     * Method for populating the songs database . Another method will be made to
//     * update the two tables with the new shows and songs as they are added to
//     * archive.
//     *
//     * @param args
//     */
//    public static void main(String[] args) {
//        ShowPopulater sp = new ShowPopulater();
//        sp.initialSongPopulation();
//    }

    /**
     * Creates the DB connection needed to add all of the Song data.
     */
    public ShowPopulater() {
        super();
        try {
            if (!isOpen) {
                open();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    /**
     * Populates the database with songs
     */
    private void initialSongPopulation() {
        try {
           c.setAutoCommit(false);
        
        
        //Get all of the identifiers
        ArrayList<Shows> shows = getAllShows();
        
        String showJSON;
        //Use those identifiers to get show JSON documents
        for (Shows s : shows) {
            try { showJSON = fetchShowJSON(s.showUrl); } 
            catch (Exception ex) {
                Logger.getLogger(ShowPopulater.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("error with :" + s.showUrl);
                continue;
            }
            
            parseNewShowForSongs(showJSON);

        }
        
        //Here all of the changes are actually committed
        c.commit();
        } catch (SQLException ex) {
            Logger.getLogger(ShowPopulater.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Gets the JSON document from archive.org - JSON for a specific show's
     * metadata.
     *
     * @param showUrl - The Show url is fed to this method from the shows
     * database. The shows database was populated with a csv file.
     * @return - the result is a JSON document
     * @throws Exception
     */
    public String fetchShowJSON(String showUrl) throws Exception {
        String urlRoot = "https://archive.org/metadata/";
        String urlToRead = urlRoot + showUrl;
        String showJsonString;
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        showJsonString = result.toString();
        //System.out.println(showJsonString);
        return showJsonString;
    }

    /**
     * Takes a JSON received and parses it for the data wanted. We want url,
     * identifier, date, venue, and songname.
     *
     * @param json - the full JSON for a single show gotten from the
     * fetchShowJSON() method
     */
    public void parseNewShowForSongs(String json) {
        try {
            if (!isOpen) {
                open();
            }
            JSONParser parser = new JSONParser();
            //Object obj = parser.parse(new FileReader("/home/vice/Downloads/simple.json"));
            Object obj = parser.parse(json);
            JSONObject jsonObject = (JSONObject) obj;

            //Get the show metadata
            JSONObject metaArray = (JSONObject) jsonObject.get("metadata");
            Shows show = parseShowMetadata(metaArray);
            show.id = getShowIdFromUri(show.identifier);

            //Now that we have this new show metadata we can update the database
            updateShowVenue(show.venue, show.id);

            //Get the songs in the show
            JSONArray filesArray = (JSONArray) jsonObject.get("files");
            for (Iterator fileIterator = filesArray.iterator(); fileIterator.hasNext();) {
                JSONObject fList = (JSONObject) fileIterator.next();
                Songs song = parseAudioFileJSONObject(fList);
                if (null != song) {
                    show.songs.add(song);
                }
            }

            //Add the show and songs to the database
            for (Songs s : show.songs) {
                s.showId = show.id;
                insertSongFullData(s);
                System.out.println(show.identifier + '/' + s.name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This parses the JSON for audio files. It only grabs the original audio
     * files. It ignores all the other files by returning null. Gets called in
     * the process of parsing a new show.
     *
     * @param fList
     * @return
     */
    public Songs parseAudioFileJSONObject(JSONObject fList) {
        String name, source, format, length, title, album, track, external_identifier, mtime;

        length = "default";
        title = "default";

        name = (String) fList.get("name");
        source = (String) fList.get("source");
        format = (String) fList.get("format");
        length = (String) fList.get("length");
        mtime = (String) fList.get("mtime");
        title = (String) fList.get("title");
        album = (String) fList.get("album");
        track = (String) fList.get("track");
        external_identifier = (String) fList.get("external-identifier");

        //The original tag is on the original uploaded files - they aren't just audio
        //So we need to limit which files we add by the length piece.
        if (null == source || !source.equals("original")) {
            return null;
        }
        //The length value is only on the music files
        if (null == length || length.equals("default")) {
            return null;
        }
        //txt files and meta files have no title.
        if (null == title || title.equals("default")) {
            return null;
        }

        Songs newSong = new Songs(name, source, format, length, title, album, track, external_identifier, mtime);
        return newSong;
    }

    /**
     * This method gets called in parseNewShowForSongs and finds all of the
     * metadata about the show stored in the root > metadata portion of the
     * JSON.
     *
     * @param metaArray
     * @return
     */
    private Shows parseShowMetadata(JSONObject metaArray) {
        String identifier, uploader, date, title, venue;
        String location; //The shows location is stored in the XML as coverage

        identifier = (String) metaArray.get("identifier");
        uploader = (String) metaArray.get("uploader");
        date = (String) metaArray.get("date");
        title = (String) metaArray.get("title");
        venue = (String) metaArray.get("venue");

        //new fields
        location = (String) metaArray.get("coverage");

        Shows show = new Shows(identifier, uploader, date, title, venue);
        show.setLocation(location);
        return show;
    }
}
