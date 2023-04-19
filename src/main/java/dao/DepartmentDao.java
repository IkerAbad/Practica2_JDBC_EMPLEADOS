package dao;

import models.Department;
import models.Employee;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class DepartmentDao {
    Connection con = null;

    public Department getDepartment(String codigoDepartamento) {
        //++Devuelve un objeto de tipo Department cuyo código es codigoDepartamento.
        String query = "SELECT * FROM departments WHERE dept_no= ?";
        boolean check = false;
        Department c = new Department();

        try {
            con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, codigoDepartamento);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                check = true;
                c.setDept_no(rs.getString("dept_no"));
                c.setDept_name(rs.getString("dept_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (check == true) {
            return c;
        } else
            return null;
    }

    public Set<Department> listaDepartamentos() {
        //++Devuelve una lista de objetos de tipo Department que contiene todos los departamentos registrados en el sistema.
        String query = "SELECT * FROM departments";
        boolean check = false;
        Set<Department> departments = new HashSet<>();

        try {
            con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                check = true;
                Department c = new Department();
                c.setDept_no(rs.getString("dept_no"));
                c.setDept_name(rs.getString("dept_name"));
                departments.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (check == true) {
            return departments;
        } else
            return null;
    }

    public Set<Employee> listaEmpleadosDepartamento(String codigoDepartamento) {
        //++Devuelve la lista de empleados, objetos de tipo Employee, que pertenecen al departamento con código codigoDepartamento.
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
            return null;

    }

    public Employee managerDepartamento(String codigoDepartamento) {
        //++Devuelve un objeto de tipo Employee que corresponda al empleado “manager” del departamento cuyo código es codigoDepartamento. Nota: to_date será el mayor
        String query = "SELECT * FROM employees e WHERE e.emp_no = (SELECT d.emp_no FROM dept_manager d WHERE d.emp_no = e.emp_no  and d.dept_no= ? ORDER BY d.to_date ASC)";
        boolean check = false;
        Employee e = new Employee();

        try {
            con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, codigoDepartamento);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                check = true;
                e.setEmp_no(rs.getInt("emp_no"));
                e.setBirth_date(rs.getDate("birth_date"));
                e.setFirst_name(rs.getString("first_name"));
                e.setLast_name(rs.getString("last_name"));
                e.setGender(rs.getString("gender"));
                e.setHire_date(rs.getDate("hire_date"));

            }
        } catch (SQLException q) {
            q.printStackTrace();
        }
        if (check == true) {
            return e;
        } else
            return null;
    }

}
