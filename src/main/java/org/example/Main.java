package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.example.model.Employee;
import org.example.model.Operations;
import org.example.model.request.Request;
import org.example.model.response.Response;
import org.example.service.EmployeeManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;

@Log4j2
public class Main {
    private static final int PORT = 6666;
    private static final EmployeeManager manager = new EmployeeManager();
    private static final String ADMIN_PASS = "admin123";
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String DATA_FILE = "data.bin";

    public static void main(String[] args) {
        log.info("Server is starting...");


        try {
            manager.loadFromFile(DATA_FILE);
            log.info("Data auto-loaded from {}", DATA_FILE);
        } catch (Exception e) {
            log.warn("Could not auto-load data (it's okay if the file doesn't exist yet)");
        }

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            log.info("Server is listening on port {}", PORT);

            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    log.info("Accepted connection from {}", socket.getInetAddress());
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                    Request request = (Request) in.readObject();
                    Response response = handleRequest(request);

                    out.writeObject(response);
                    out.flush();
                } catch (Exception e) {
                    log.error("Connection error: {}", e.getMessage());
                }
            }
        } catch (IOException e) {
            log.fatal("Critical server error: {}", e.getMessage());
        }
    }

    private static Response handleRequest(Request request) {
        try {
            Operations op = request.getOperations();
            String inputData = request.getData();
            log.info("Processing operation: {}", op);

            switch (op) {
                case GET_ALL:
                    return new Response(true, "Employee list retrieved", listToJson(manager.getAll()));

                case SEARCH_BY_NAME:
                    return new Response(true, "Search by name results", listToJson(manager.searchByName(inputData)));

                case SEARCH_BY_SKILLS:
                    return new Response(true, "Search by skills results", listToJson(manager.searchBySkills(inputData)));

                case ADD_EMPLOYEE:
                    Employee emp = mapper.readValue(inputData, Employee.class);
                    manager.addEmployee(emp);
                    return new Response(true, "Employee added successfully", null);

                case DELETE_EMPLOYEE:
                    if (!ADMIN_PASS.equals(request.getPassword())) {
                        return new Response(false, "SUDO: Access denied", null);
                    }
                    boolean removed = manager.removeEmployee(inputData);
                    return removed ? new Response(true, "Employee deleted", null)
                            : new Response(false, "Employee not found", null);

                case EDIT_EMPLOYEE:
                    if (!ADMIN_PASS.equals(request.getPassword())) {
                        return new Response(false, "SUDO: Access denied", null);
                    }
                    Map<String, String> editMap = mapper.readValue(inputData, Map.class);
                    boolean edited = manager.editProject(editMap.get("id"), editMap.get("newProject"));
                    return edited ? new Response(true, "Project updated", null)
                            : new Response(false, "Employee not found", null);

                case GET_STATISTICS:
                    return new Response(true, "Statistics retrieved", manager.getStatisticsAsString());

                case GET_TOP_SALARY:
                    return new Response(true, "Top 3 Salary retrieved", manager.getTopThreePerSalaryAsString());

                case GET_TOP_EXP:
                    return new Response(true, "Top 3 Experience retrieved", manager.getTopThreePerExperienceAsString());

                case GET_HISTORY_TRANSFER:
                    return new Response(true, "Transfer history retrieved", manager.getTransfersAsString());

                case SAVE_DATA:
                    if (!ADMIN_PASS.equals(request.getPassword())) {
                        return new Response(false, "SUDO: Access denied", null);
                    }
                    manager.saveToFile(DATA_FILE);
                    return new Response(true, "Data saved successfully on server", null);

                case LOAD_DATA:
                    manager.loadFromFile(DATA_FILE);
                    return new Response(true, "Data loaded from server storage", null);

                case EXIT:
                    return new Response(true, "Session closed", null);

                default:
                    return new Response(false, "Operation not supported", null);
            }
        } catch (Exception e) {
            log.error("Request processing error: ", e);
            return new Response(false, "Critical Server Error: " + e.getMessage(), null);
        }
    }

    private static String listToJson(List<Employee> list) throws IOException {
        return mapper.writerFor(new TypeReference<List<Employee>>() {})
                .writeValueAsString(list);
    }
}