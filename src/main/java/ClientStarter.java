



public class ClientStarter {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(new Client("Client № " + i)).start();
        }
    }
}
