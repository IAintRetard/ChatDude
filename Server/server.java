import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class server {
    public static ArrayList<Socket> sockets = new ArrayList<>();
    public static HashMap<String, String> userInfo = new HashMap<>();

    public static void main(String[] args) {
        importUserInfo();
        System.out.println(userInfo);
        try {
            ServerSocket serverSocket = new ServerSocket(114);
            ThreadPoolExecutor pool = new ThreadPoolExecutor(
                    10,
                    10,
                    60,
                    TimeUnit.SECONDS,
                    new ArrayBlockingQueue<>(20),
                    Executors.defaultThreadFactory(),
                    new ThreadPoolExecutor.AbortPolicy());

            while (true) {
                Socket socket = serverSocket.accept();
                sockets.add(socket);
                pool.submit(new serverThread(socket));

                // 后面给服务端添加UI的话，可以通过按钮控制这一步
                // 当前方法每连接一个主机就执行一次浪费性能没有必要
                pool.submit(() -> {
                    try (ObjectOutputStream oos = new ObjectOutputStream(
                            new FileOutputStream("data/userInfo.dat"))) {
                        oos.writeObject(userInfo);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void importUserInfo() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data/userInfo.dat"));
            userInfo = (HashMap<String, String>) ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
