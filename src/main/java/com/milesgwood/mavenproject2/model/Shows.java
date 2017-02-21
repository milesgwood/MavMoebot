/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milesgwood.mavenproject2.model;

import java.util.ArrayList;

/**
 *
 * @author vice
 */
public class Shows {
    ArrayList<Songs> songs;
    String identifier, uploader, date, title, venue, showUrl;
    String location;
    Integer id;

    public Shows(String identifier, String uploader, String date, String title, String venue) {
        this.identifier = identifier;
        this.uploader = uploader;
        this.date = date;
        this.title = title;
        this.venue = venue;
        this.songs = new ArrayList<Songs>();
    }

    Shows(Integer id, String date, String venue, String showUrl) 
    {
        this.id = id;
        this.date = date;
        this.venue = venue;
        this.identifier = showUrl;
        this.showUrl = showUrl;
        this.songs = new ArrayList<Songs>();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    
    
    public void addSongToShow(Songs song)
    {
        songs.add(song);
    }

    public ArrayList<Songs> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<Songs> songs) {
        this.songs = songs;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShowUrl() {
        return showUrl;
    }

    public void setShowUrl(String showUrl) {
        this.showUrl = showUrl;
    }

    void addLocation(String location) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
