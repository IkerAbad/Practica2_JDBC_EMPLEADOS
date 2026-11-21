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
        String query = "SELECT 1 FROM dept_manager WHERE dept_no = ? AND emp_no = ? "
                + "AND to_date = (SELECT MAX(to_date) FROM dept_manager WHERE dept_no = ?)";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1, codigoDepartamento);
            ps.setInt(2, Integer.parseInt(codigoEmpleado));
            ps.setString(3, codigoDepartamento);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public Set<Employee> getEmployeesByTitle(String title){
        //Devuelve una lista de aquellos empleados cuyo titulo sea title. Por ejemplo, title puede corresponder a valores como: “Staff”, “Engineer”, “Senior Engineer”, etc.
        String query = "SELECT DISTINCT e.* FROM employees e JOIN titles t ON e.emp_no = t.emp_no WHERE t.title = ?";
        Set<Employee> employees = new HashSet<>();

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1, title);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Employee e = new Employee();
                    e.setEmp_no(rs.getInt("emp_no"));
                    e.setBirth_date(rs.getDate("birth_date"));
                    e.setFirst_name(rs.getString("first_name"));
                    e.setLast_name(rs.getString("last_name"));
                    e.setGender(rs.getString("gender"));
                    e.setHire_date(rs.getDate("hire_date"));
                    employees.add(e);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return employees.isEmpty() ? null : employees;
    }

    public int nuevoEmpleado(Employee newEmployee, String noDept, Date inicio, Integer salario){
        //Añade un nuevo empleado a la base de datos, cuyo departamento será aquel con código noDept, y el salario inicial salario.
        String insEmp = "INSERT INTO employees (emp_no, birth_date, first_name, last_name, gender, hire_date) VALUES (?,?,?,?,?,?)";
        String insDept = "INSERT INTO dept_emp (emp_no, dept_no, from_date, to_date) VALUES (?,?,?,?)";
        String insSal = "INSERT INTO salaries (emp_no, salary, from_date, to_date) VALUES (?,?,?,?)";
        Date fin = Date.valueOf("9999-01-01");
        int res = 0;

        try (Connection c = DatabaseConnection.getConnection()) {
            c.setAutoCommit(false);
            try (PreparedStatement ps = c.prepareStatement(insEmp);
                 PreparedStatement pd = c.prepareStatement(insDept);
                 PreparedStatement pss = c.prepareStatement(insSal)) {
                ps.setInt(1, newEmployee.getEmp_no());
                ps.setDate(2, newEmployee.getBirth_date());
                ps.setString(3, newEmployee.getFirst_name());
                ps.setString(4, newEmployee.getLast_name());
                ps.setString(5, newEmployee.getGender());
                ps.setDate(6, newEmployee.getHire_date());
                res = ps.executeUpdate();

                pd.setInt(1, newEmployee.getEmp_no());
                pd.setString(2, noDept);
                pd.setDate(3, inicio);
                pd.setDate(4, fin);
                pd.executeUpdate();

                pss.setInt(1, newEmployee.getEmp_no());
                pss.setInt(2, salario);
                pss.setDate(3, inicio);
                pss.setDate(4, fin);
                pss.executeUpdate();

                c.commit();
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            res = 0;
        }
        return res;
    }

    public Boolean eliminarEmpleado(int emp_no){
        //Elimina el empleado newEmployee de la base de datos. Recuerda que se deberán eliminar también aquellos registros relacionados con dicho empleado.
        String[] tablas = {"salaries", "titles", "dept_emp", "dept_manager"};
        int borrados = 0;

        try (Connection c = DatabaseConnection.getConnection()) {
            c.setAutoCommit(false);
            try {
                for (String t : tablas) {
                    try (PreparedStatement ps = c.prepareStatement("DELETE FROM " + t + " WHERE emp_no = ?")) {
                        ps.setInt(1, emp_no);
                        ps.executeUpdate();
                    }
                }
                try (PreparedStatement ps = c.prepareStatement("DELETE FROM employees WHERE emp_no = ?")) {
                    ps.setInt(1, emp_no);
                    borrados = ps.executeUpdate();
                }
                c.commit();
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return borrados == 1;
    }

}
