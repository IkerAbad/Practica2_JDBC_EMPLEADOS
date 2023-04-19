package idao;

import models.Department;
import models.Employee;

import java.util.Set;

public interface iDepartmentDao {

    public Department getDepartment(String codigoDepartamento);
    public Set<Department> listaDepartamentos();
    public Set<Employee> listaEmpleadosDepartamento(String codigoDepartamento);
    public Employee managerDepartamento(String codigoDepartamento);


}
