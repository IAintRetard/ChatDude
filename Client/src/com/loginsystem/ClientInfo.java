package com.loginsystem;

import java.net.Socket;
import java.util.LinkedList;

public class ClientInfo {

    protected static Socket socket;

    protected static String name;

    protected static LinkedList<Message> responseList = new LinkedList<>();
}
