package controllers;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Employee;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
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
import play.twirl.api.Content;
import store.EmployeeStore;

import javax.inject.Inject;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
        ObjectNode result = Json.newObject();
        try {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int empId = resultSet.getInt("id");
                String empName = resultSet.getString("empName");
                String gender = resultSet.getString("gender");
                int phoneNumber = resultSet.getInt("phoneNumber");
                String address = resultSet.getString("address");
                String emailId = resultSet.getString("emailId");
                result.put("id", empId);
                result.put("empName", empName);
                result.put("gender", gender);
                result.put("phoneNumber", phoneNumber);
                result.put("address", address);
                result.put("emailId", emailId);

            }
        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return ok( result);
    }
    public Result addEmployee(Http.Request request) throws SQLException {
        Connection connection = db.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into employee (id,empName,gender,phoneNumber,address,emailId) values(?,?,?,?,?,?)");
        try {
            JsonNode json = request.body().asJson();
            int id = Integer.parseInt(json.get("id").asText());
            String empName = json.get("empName").asText();
            String gender = json.get("gender").asText();
            int phoneNumber = Integer.parseInt(json.get("phoneNumber").asText());
            String address = json.get("address").asText();
            String emailId = json.get("emailId").asText();

            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, empName);
            preparedStatement.setString(3, gender);
            preparedStatement.setInt(4, phoneNumber);
            preparedStatement.setString(5, address);
            preparedStatement.setString(6, emailId);
            preparedStatement.executeUpdate();
        }  catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

        return ok("Data inserted successfully");
    }
    public Result updateEmployee(Http.Request request) throws SQLException, IOException {
        Connection connection = db.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("update employee set empName=?,gender=?,phoneNumber=?,address=?, emailId=? where id = ?");

        JsonNode jsonData = request.body().asJson();
        try {
            int id = jsonData.get("id").asInt();
            String name = jsonData.get("empName").asText();
            String gender = jsonData.get("gender").asText();
            int phno = jsonData.get("phoneNumber").asInt();
            String address = jsonData.get("address").asText();
            String email = jsonData.get("emailId").asText();

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, gender);
            preparedStatement.setInt(3, phno);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, email);
            preparedStatement.setInt(6, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return ok("Data updated successfully");
        }
    }
    public Result deleteEmployee(int id) {
        Connection connection = null;
        try {
            connection = db.getConnection();
            String checkSql = "SELECT * FROM employee WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(checkSql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.next()) {
                return notFound("Data not found with id " + id);
            }
            String sql = "DELETE FROM employee WHERE id=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return ok("Data deleted successfully");
        } catch (SQLException e) {
            return internalServerError("Error deleting data: " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {

            }
        }
    }


//    public Result createIndex() throws IOException {
//        CreateIndexRequest request = new CreateIndexRequest("myindex");
//        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
//        return ok("Index created: " + response.isAcknowledged());
//    }
//
//
//    public Result searchDocument() throws IOException {
//        SearchRequest request = new SearchRequest("myindex");
//        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
//        return ok("Search result: " + response.getHits().getTotalHits());
//    }


//    public Result addEmployee(Http.Request employee) throws SQLException {
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


//

//    public Result addEmployee(String jsonData) throws SQLException {
//        Connection connection = db.getConnection();
//        PreparedStatement statement1 = connection.prepareStatement("insert into employee_details (id,empName,gender,phoneNumber,address,emailId) values(?,?,?,?,?,?)");
//        statement1.setString(1,jsonData);
//        statement1.executeUpdate();
//        return ok(jsonData);
//    }

  


}

