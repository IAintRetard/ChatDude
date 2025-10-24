package com.loginsystem;

public class Message {

    protected String command;

    protected String sender;

    protected String content;

    protected String success;

    protected Message(String command, String sender, String content) {
        this.command = command;
        this.sender = sender;
        this.content = content;
    }

    protected Message(String command, String success) {
        this.command = command;
        this.success = success;
    }
}
