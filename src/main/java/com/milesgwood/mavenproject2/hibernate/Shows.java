package com.milesgwood.mavenproject2.hibernate;
// Generated Feb 20, 2017 1:21:36 PM by Hibernate Tools 4.3.1



/**
 * Shows generated by hbm2java
 */
public class Shows  implements java.io.Serializable {


     private Integer id;
     private String venue;
     private String showUrl;
     private String showName;
     private String date;
     private Integer score;

    public Shows() {
    }

	
    public Shows(String showUrl) {
        this.showUrl = showUrl;
    }
    public Shows(String venue, String showUrl, String showName, String date, Integer score) {
       this.venue = venue;
       this.showUrl = showUrl;
       this.showName = showName;
       this.date = date;
       this.score = score;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getVenue() {
        return this.venue;
    }
    
    public void setVenue(String venue) {
        this.venue = venue;
    }
    public String getShowUrl() {
        return this.showUrl;
    }
    
    public void setShowUrl(String showUrl) {
        this.showUrl = showUrl;
    }
    public String getShowName() {
        return this.showName;
    }
    
    public void setShowName(String showName) {
        this.showName = showName;
    }
    public String getDate() {
        return this.date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    public Integer getScore() {
        return this.score;
    }
    
    public void setScore(Integer score) {
        this.score = score;
    }




}

