package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.example.model.Employee;
import org.example.model.Operations;
import org.example.model.request.Request;
import org.example.model.response.Response;
import org.example.network.ClientHandler;
import org.example.repository.impl.EmployeeRepositoryImpl;
import org.example.service.EmployeeManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Log4j2
public class Main {
    private static final int PORT = 6666;
    private static final EmployeeManager manager = new EmployeeManager(new EmployeeRepositoryImpl());
    private static final String ADMIN_PASS = "admin123";
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String DATA_FILE = "data.bin";
    private static final int THREAD_POOL_SIZE = 5;

    public static void main(String[] args) {
        log.info("Server is starting...");

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        try {
            manager.loadFromFile();
            log.info("Data auto-loaded from {}", DATA_FILE);
        } catch (Exception e) {
            log.warn("Could not auto-load data (it's okay if the file doesn't exist yet)");
        }

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            log.info("Server is listening on port {}", PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                    ClientHandler clientHandler = new ClientHandler(ADMIN_PASS, socket, manager, mapper);
                    executor.execute(clientHandler);

            }
        } catch (IOException e) {
            log.fatal("Critical server error: {}", e.getMessage());
        } catch (Exception e) {
            log.fatal("Critical server error: {}", e.getMessage());
        }
    }


}