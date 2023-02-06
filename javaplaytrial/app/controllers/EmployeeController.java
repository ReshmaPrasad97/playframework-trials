package controllers;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Employee;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import play.api.db.Database;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.With;
import store.EmployeeStore;

import javax.inject.Inject;
import java.sql.*;
import java.util.HashSet;

import static org.elasticsearch.rest.RestRequest.request;

public class EmployeeController extends Controller {
    private EmployeeStore employeeStore;
    private FormFactory formFactory;


    private RestHighLevelClient client = new RestHighLevelClient(
            RestClient.builder(
                    new HttpHost("localhost", 9200, "http")
            )
    );

    @Inject
    public EmployeeController(EmployeeStore employeeStore, FormFactory formFactory) throws SQLException {
        this.employeeStore = employeeStore;
        this.formFactory = formFactory;
    }
    @Inject
    Database db;

    public Result getEmployee() throws SQLException {
        Connection connection = db.getConnection();
        Statement statement = connection.createStatement();
        ArrayNode result = JsonNodeFactory.instance.arrayNode();
        ResultSet resultSet = statement.executeQuery("select * from employee  "  );
        while (resultSet.next()){
            int empId = resultSet.getInt("id");
            String empName = resultSet.getString("empName");
            String gender = resultSet.getString("gender");
            int phoneNumber = resultSet.getInt("phoneNumber");
            String address = resultSet.getString("address");
            String emailId = resultSet.getString("emailId");
            result.add(JsonNodeFactory.instance.objectNode().put("id", empId).put("empName", empName).put("gender",gender).put("phoneNumber",phoneNumber).put("address",address).put("emailId",emailId));
        }
//        resultSet.close();
//        statement.close();
//        connection.close();
        return ok( result);
    }

    public  Result getEmployeebyId(int id) throws SQLException {
        Connection connection = db.getConnection();
        PreparedStatement statement = connection.prepareStatement("select * from employee where id = ?");
        statement.setInt(1,id);
        ResultSet resultSet = statement.executeQuery();
        ObjectNode result =Json.newObject();
        while(resultSet.next()){
            int empId = resultSet.getInt("id");
            String empName = resultSet.getString("empName");
            String gender = resultSet.getString("gender");
            int phoneNumber = resultSet.getInt("phoneNumber");
            String address = resultSet.getString("address");
            String emailId = resultSet.getString("emailId");
            result.put("id", empId);
            result.put("empName", empName);
            result.put("gender",gender);
            result.put("phoneNumber",phoneNumber);
            result.put("address",address);
            result.put("emailId",emailId);

        }

        return ok(result);
    }

//    public Result addEmployee(Http.Request employee){
//        Form<Employee> employeeForm = formFactory.form(Employee.class).bindFromRequest(employee);
//        return ok(employeeStore.addEmployee(employeeForm.get()).toString());
//    }
////
//    public Result getEmployee(int id) {
//        return ok(employeeStore.getEmployee(id).toString());
//}
//
//        public  Result employeeDetails(Statement st) throws SQLException {
////            System.out.println("employee whose employee id is 102");
//            ResultSet rs = st.executeQuery("select * from employee where emp_id=102");
//            while (rs.next()) {
//                System.out.println(rs.getString("emp_id") + ":" + rs.getString("emp_name"));
//
//            }
//            return ok(rs.getString("emp_name"));
//        }
//    @Inject
//    Database db;
//       public  Result fetchdata() {
//
//           try (
//                Connection connection = db.getConnection()) {
//            try (Statement statement = connection.createStatement()) {
//                ResultSet resultSet = statement.executeQuery("SELECT * FROM employee");
//                while (resultSet.next()){
//                                System.out.println(resultSet.getString("emp_name" + ":" + resultSet.getString("emp_id")));
//                }
//            }
//        } catch (SQLException e) {
//            return internalServerError("Failed to connect to the database");
//        }
//
//        return ok("Successfully connected to the database");
//    }


//    public Result addEmployee() throws SQLException {
//        Connection connection = db.getConnection();
//        String query = "Insert into employee (id,emp_name,gender,phone_number,address,email_id) values (?,?,?,?,?,?)";
//        PreparedStatement ps= connection.prepareStatement(query);
//        JsonNode jsonNode = request().body().asJson();
//        int id = jsonNode.get("id").asInt();
//        String emp_name = jsonNode.get("emp_name").asText();
//        String gender = jsonNode.get("gender").asText();
//        int phone_number = jsonNode.get("phone_number").asInt();
//        String address = jsonNode.get("address").asText();
//        String email_id = jsonNode.get("email_id").asText();
//
//
//
//
//        ps.setInt(1,id);
//        ps.setString(2,emp_name);
//        ps.setString(3,gender);
//        ps.setInt(4,phone_number);
//        ps.setString(5,address);
//        ps.setString(6,email_id);
////        int rowsAffected = ps.executeUpdate();
////        System.out.println(rowsAffected + " rows affected");
//
//    int result = ps.executeUpdate();
//
//        return ok("added succesfully");
//    }
//
//

}

