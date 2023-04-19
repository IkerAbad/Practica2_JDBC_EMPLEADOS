package dao;

import models.Employee;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

public class EmployeeDao {
    Connection con = null;
    public Employee getEmployee(String codigoEmpleado){
        //Devuelve un objeto de tipo Employee cuyo código es codigoempleado.
        String query = "SELECT * FROM employees WHERE emp_no= ?";
        boolean check = false;
        Employee c = new Employee();

        try {
            con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, codigoEmpleado);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                check = true;
                c.setEmp_no(rs.getInt("emp_no"));
                c.setBirth_date(rs.getDate("birth_date"));
                c.setFirst_name(rs.getString("first_name"));
                c.setLast_name(rs.getString("last_name"));
                c.setGender(rs.getString("gender"));
                c.setHire_date(rs.getDate("hire_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (check == true) {
            return c;
        } else
            return null;
    }

    public Set<Employee> listaEmpleados(){
        //Devuelve una lista de objetos de tipo Employee que contiene todos los empleados registrados en el sistema.
        String query = "SELECT * FROM employees";
        boolean check = false;
        Set<Employee> employees = new HashSet<>();

        try {
            con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                check = true;
                Employee c = new Employee();
                c.setEmp_no(rs.getInt("emp_no"));
                c.setBirth_date(rs.getDate("birth_date"));
                c.setFirst_name(rs.getString("first_name"));
                c.setLast_name(rs.getString("last_name"));
                c.setGender(rs.getString("gender"));
                c.setHire_date(rs.getDate("hire_date"));
                employees.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (check == true) {
            return employees;
        } else
            return null;
    }

    public Boolean isManager(String codigoDepartamento, String codigoEmpleado){
        //Devuelve verdadero si codigoEmpleado corresponde con el código del manager del departamento cuyo código es codigoDepartamento. Devolverá falso en caso contrario.
        String query
                = "Select * FROM employees, deparments where emp_no=? AND dept_no=? AND emp_no=dept_no";
        PreparedStatement ps = null;
        boolean check = false;
        Connection con = null;
        Employee c = new Employee();
        try {
            con = DatabaseConnection.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(codigoDepartamento));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                check = true;
                c.setEmp_no(rs.getInt("emp_no"));
                c.setBirth_date(rs.getDate("birth_date"));
                c.setFirst_name(rs.getString("first_name"));
                c.setLast_name(rs.getString("last_name"));
                c.setGender(rs.getString("gender"));
                c.setHire_date(rs.getDate("hire_date"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (check){
            return true;
        }else {
            return false;
        }
    }
    public Set<Employee> getEmployeesByTitle(String title){
        //Devuelve una lista de aquellos empleados cuyo titulo sea title. Por ejemplo, title puede corresponder a valores como: “Staff”, “Engineer”, “Senior Engineer”, etc.
        /*
        String query = "SELECT c.* FROM employees c JOIN dept_emp co on c.EMP_NO = co.EMP_NO AND co.DEPT_NO =?";
        boolean check = false;
        Set<Employee> employee = new HashSet<>();


        try {
            con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, codigoDepartamento);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                check = true;
                Employee c = new Employee();
                c.setEmp_no(rs.getInt("emp_no"));
                c.setBirth_date(rs.getDate("birth_date"));
                c.setFirst_name(rs.getString("first_name"));
                c.setLast_name(rs.getString("last_name"));
                c.setGender(rs.getString("gender"));
                c.setHire_date(rs.getDate("hire_date"));
                employee.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (check == true) {
            return employee;
        } else
*/
            return null;
    }

    public int nuevoEmpleado(Employee newEmployee, String noDept, Date inicio, Integer salario){
        //Añade un nuevo empleado a la base de datos, cuyo departamento será aquel con código noDept, y el salario inicial salario.
        String query = "INSERT INTO employees ( emp_no, birth_date, first_name, last_name, gender, hire_date) values (?,?,?,?,?,?)";
        int res = 0;

        try {

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, newEmployee.getEmp_no());
            ps.setDate(2, newEmployee.getBirth_date());
            ps.setString(3, newEmployee.getFirst_name());
            ps.setString(4, newEmployee.getLast_name());
            ps.setString(5, newEmployee.getGender());
            ps.setDate(6, newEmployee.getHire_date());
            res = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
    public Boolean eliminarEmpleado(int emp_no){
        //Elimina el empleado newEmployee de la base de datos. Recuerda que se deberán eliminar también aquellos registros relacionados con dicho empleado.
        String query = "DELETE FROM employees WHERE emp_no= ?";
        int rs=0;

        try {
            con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, emp_no);
            rs = ps.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (rs == 1){
            return true;
        } else{
            return false;
        }
    }

}
