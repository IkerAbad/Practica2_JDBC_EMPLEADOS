package dao;

import models.Employee;
import models.Salary;
import utils.DatabaseConnection;

import java.sql.*;

public class SalaryDao {
    Connection con = null;
    public Salary getSalario(String codigoEmpleado, Date fecha) {
        //Devuelve el salario de un empleado cuyo código es codigoEmpleado, para una fecha determinada.
        String query = "SELECT * FROM salaries WHERE emp_no= ? and from_date <= ? and to_date >= ?;\n";
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
       /* String query = "SELECT * FROM doctors";
        boolean check = false;
        List<Doctor> doctors = new ArrayList<Doctor>();

        try {
            con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                check = true;
                Doctor d = new Doctor();
                d.setId(rs.getInt("id"));
                d.setDni(rs.getString("dni"));
                d.setLastname(rs.getString("lastname"));
                d.setName(rs.getString("name"));
                d.setSalary(rs.getDouble("salary"));
                d.setSpeciality(rs.getString("speciality"));
                doctors.add(d);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (check == true) {
            return doctors;
        } else*/
            return null;
    }

}
