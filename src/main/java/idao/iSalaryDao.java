package idao;

import models.Employee;

import java.util.Date;

public interface iSalaryDao {

    public Integer getSalario(String codigoEmpleado, Date fecha);
    public Boolean nuevoSalario(Employee newEmployee, Integer salario);

}
