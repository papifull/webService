package serverWs;
import javax.xml.ws.Endpoint;

import DataAccesObject.AccesBD;
import ws.UserService;

import java.util.logging.ConsoleHandler;

public class Server {


    public static void main(String[] args) {
        String url = "http://localhost:5000/";
        AccesBD connection = new AccesBD();
        Endpoint.publish(url, new UserService(connection.database));
        System.out.println(url+"wsdl?wsdl");

    }
}
