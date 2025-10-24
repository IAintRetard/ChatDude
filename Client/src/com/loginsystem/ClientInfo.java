package com.loginsystem;

import java.net.Socket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ClientInfo {

    protected static Socket socket;

    protected static String name;

    protected static List<Message> responseList = Collections.synchronizedList(new LinkedList<>());

    protected static Boolean stop = false;
}
