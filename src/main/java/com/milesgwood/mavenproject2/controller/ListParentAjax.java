/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milesgwood.mavenproject2.controller;

import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.ParameterNameAware;
import com.milesgwood.mavenproject2.model.Shows;
import com.milesgwood.mavenproject2.model.Songs;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author vice
 */
public class ListParentAjax extends ActionSupport implements SessionAware, ParameterNameAware {

    protected String listOpen = "<li class=\'talk-bubble tri-left round border left-top botmsg\'>";
    protected String listClose = "</li>";
    protected String autoPlay = "&autoplay=1\' ";
    protected String frameOpen = "<iframe src=\'https://archive.org/embed/";
    protected String frameClose = " width='500' height='140' frameborder='0' webkitallowfullscreen='true' mozallowfullscreen='true' allowfullscreen></iframe>";

    protected String responseMessage;
    protected InputStream inputStream;

    protected Map<String, Object> userSession;

    protected static ArrayList<Songs> showPlayList;
    protected static ArrayList<Songs> songPlayList;

    protected static int showIndex = 1;
    protected static int songIndex = 0;

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String execute() throws Exception {
        inputStream = new ByteArrayInputStream(responseMessage.getBytes("UTF-8"));
        return SUCCESS;
    }

    public void setSession(Map<String, Object> session) {
        userSession = session;
    }

    public boolean acceptableParameterName(String parameterName) {

        boolean allowedParameterName = true;

        if (parameterName.contains("session") || parameterName.contains("request")) {
            allowedParameterName = false;
        }

        return allowedParameterName;
    }
    
    

}
