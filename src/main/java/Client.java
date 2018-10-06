import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс - клиент для отправки и получения данных
 */
public class Client implements Runnable {
    private static Logger a = Logger.getAnonymousLogger();

    private String clientName;

    public String getClientName() {
        return clientName;
    }

    public Client(String clientName) {
        this.clientName = clientName;
    }


    public void run() {

            // Определяем номер порта, на котором нас ожидает сервер для ответа
            int portNumber = 1777;
            // Подготавливаем строку для запроса - просто строка
            String str = "Тестовая строка для передачи от клиента <" + getClientName() + ">";

            // Пишем, что стартовали клиент
            a.log(Level.INFO, "Client <" + getClientName() + "> is started");

            // Открыть сокет (Socket) для обращения к локальному компьютеру
            // Сервер мы будем запускать на этом же компьютере
            // Это специальный класс для сетевого взаимодействия c клиентской стороны
            Socket socket = null;
            try {
                socket = new Socket("127.0.0.1", portNumber);

                // Создать поток для чтения символов из сокета
                // Для этого надо открыть поток сокета - socket.getInputStream()
                // Потом преобразовать его в поток символов - new InputStreamReader
                // И уже потом сделать его читателем строк - BufferedReader
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // Создать поток для записи символов в сокет
                PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);

                // Отправляем тестовую строку в сокет
                pw.println(str);

                // Входим в цикл чтения, что нам ответил сервер
                while ((str = br.readLine()) != null) {
                    // Если пришел ответ “bye”, то заканчиваем цикл
                    if (str.equals("bye")) {
                        break;
                    }
                    // Печатаем ответ от сервера на консоль для проверки
                    a.log(Level.INFO, str);
                    // Посылаем ему "bye" для окончания "разговора"
                    pw.println("bye");
                }

                br.close();
                pw.close();
                socket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            a.log(Level.INFO, "Client <" + getClientName() + "> is stopped");


    }
}