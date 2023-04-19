package idao;

import models.Employee;

import java.util.Date;
import java.util.Set;

public interface iEmployeeDao {

    public Employee getEmployee(String codigoEmpleado);
    public Set<Employee> listaEmpleados();
    public Boolean isManager(String codigoDepartamento, String codigoEmpleado);
    public Set<Employee> getEmployeesByTitle(String title);
    public Boolean nuevoEmpleado(Employee newEmployee, String noDept,
                                 Date inicio, Integer salario);
    public Boolean eliminarEmpleado(Employee newEmployee);


}
