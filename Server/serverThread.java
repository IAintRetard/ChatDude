import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class serverThread implements Runnable {
    private Socket socket;

    public serverThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                BufferedWriter fbw = new BufferedWriter(
                        new FileWriter("data/history_chat_record.txt", true))) {

            String request = new String();
            String userName = new String();
            String password = new String();
            while (true) {
                request = br.readLine();
                if (request == null)
                    break;
                if (request.equals("login")) {
                    userName = br.readLine();
                    password = br.readLine();
                    if (!server.userInfo.containsKey(userName) || !server.userInfo.get(userName).equals(password)) {
                        bw.write("false");
                        bw.newLine();
                        bw.flush();
                    } else {
                        bw.write("true");
                        bw.newLine();
                        bw.flush();
                    }
                } else if (request.equals("register")) {
                    userName = br.readLine();
                    password = br.readLine();
                    if (server.userInfo.containsKey(userName)) {
                        bw.write("false");
                        bw.newLine();
                        bw.flush();
                    } else {
                        server.userInfo.put(userName, password);
                        bw.write("true");
                        bw.newLine();
                        bw.flush();
                    }
                } else if (request.equals("chat")) {
                    String sender = br.readLine();
                    String message = br.readLine();
                    fbw.write(sender + ':' + message);
                    fbw.newLine();
                    fbw.flush();
                    for (Socket sk : server.sockets) {
                        if (sk.isClosed() || sk == socket)
                            continue;
                        BufferedWriter skbw = new BufferedWriter(new OutputStreamWriter(sk.getOutputStream()));
                        skbw.write(sender);
                        skbw.newLine();
                        skbw.write(message);
                        skbw.newLine();
                        skbw.flush();
                    }
                } else if (request.equals("modify_name")) {
                    String curName = br.readLine();
                    String newName = br.readLine();
                    if (server.userInfo.containsKey(newName)) {
                        bw.write("false");
                        bw.newLine();
                        bw.flush();
                    } else {
                        String pwd = server.userInfo.get(curName);
                        server.userInfo.remove(curName);
                        server.userInfo.put(newName, pwd);
                        bw.write("true");
                        bw.newLine();
                        bw.flush();
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
