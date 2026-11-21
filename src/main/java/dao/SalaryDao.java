package dao;

import models.Employee;
import models.Salary;
import utils.DatabaseConnection;

import java.sql.*;

public class SalaryDao {
    Connection con = null;
    public Salary getSalario(String codigoEmpleado, Date fecha) {
        //Devuelve el salario de un empleado cuyo código es codigoEmpleado, para una fecha determinada.
        String query = "SELECT * FROM salaries WHERE emp_no= ? and from_date <= ? and to_date >= ?";
        boolean check = false;
        Salary s = new Salary();

        try {
            con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(codigoEmpleado));
            ps.setDate(2, fecha);
            ps.setDate(3, fecha);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                check = true;
                s.setEmp_no(rs.getInt("emp_no"));
                s.setFrom_date(rs.getDate("from_date"));
                s.setSalary(rs.getInt("salary"));
                s.setTo_date(rs.getDate("to_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (check == true) {
            return s;
        } else
            return null;
    }


    public Boolean nuevoSalario(Employee newEmployee, Integer salario) {
        //Añadir un nuevo salario (parámetro salario) al empleado newEmployee. Como un empleado solo puede tener un salario en un momento determinado, las fechas deberán de ser consistentes, actualizando el salario vigente en ese momento.
        String cerrar = "UPDATE salaries SET to_date = ? WHERE emp_no = ? AND to_date = ?";
        String insertar = "INSERT INTO salaries (emp_no, salary, from_date, to_date) VALUES (?,?,?,?)";
        Date hoy = new Date(System.currentTimeMillis());
        Date fin = Date.valueOf("9999-01-01");

        try (Connection c = DatabaseConnection.getConnection()) {
            c.setAutoCommit(false);
            try (PreparedStatement pc = c.prepareStatement(cerrar);
                 PreparedStatement pi = c.prepareStatement(insertar)) {
                pc.setDate(1, hoy);
                pc.setInt(2, newEmployee.getEmp_no());
                pc.setDate(3, fin);
                pc.executeUpdate();

                pi.setInt(1, newEmployee.getEmp_no());
                pi.setInt(2, salario);
                pi.setDate(3, hoy);
                pi.setDate(4, fin);
                pi.executeUpdate();
                c.commit();
                return true;
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
