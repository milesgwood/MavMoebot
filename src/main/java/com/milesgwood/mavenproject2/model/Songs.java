/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milesgwood.mavenproject2.model;

/**
 *
 * @author vice
 */
public class Songs {
    
    String name, source, format, length, title, album, track, external_identifier, mtime;
    Integer showId, id;

    public Songs(String name, String source, String format, String length, String title, String album, String track, String external_identifier, String mtime) {
        this.name = name;
        this.source = source;
        this.format = format;
        this.length = length;
        this.title = title;
        this.album = album;
        this.track = track;
        this.external_identifier = external_identifier;
        this.mtime = mtime;
    }
    
    public Songs()
    {
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public String getExternal_identifier() {
        return external_identifier;
    }

    public void setExternal_identifier(String external_identifier) {
        this.external_identifier = external_identifier;
    }

    public String getMtime() {
        return mtime;
    }

    public void setMtime(String mtime) {
        this.mtime = mtime;
    }

    public Integer getShowId() {
        return showId;
    }

    public void setShowId(Integer showId) {
        this.showId = showId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    
    
    
    
    
}
