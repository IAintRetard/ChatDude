package com.loginsystem;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Listen implements Runnable {
    public void run() {
        try {
            while (true) {
                BufferedReader br = new BufferedReader(new InputStreamReader(ClientInfo.socket.getInputStream()));
                String command = br.readLine();
                if (command.equals("LOGIN") || command.equals("REGISTER") || command.equals("MODIFY_NAME")) {
                    String success = br.readLine();
                    ClientInfo.responseList.add(new Message(command, success));
                }
                if (command.equals("CHAT")) {
                    String sender = br.readLine();
                    String content = br.readLine();
                    ClientInfo.responseList.add(new Message(command, sender, content));
                }
                for (Message msg : ClientInfo.responseList) {
                    System.out.print(msg.command + " ");
                }
                System.out.println("");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Listener disconnected");
        }
    }
}