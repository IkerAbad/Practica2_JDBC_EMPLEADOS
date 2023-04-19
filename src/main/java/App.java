import dao.DepartmentDao;
import dao.EmployeeDao;
import dao.SalaryDao;
import models.Employee;

import java.sql.Date;


public class App {
    public static void main(String[] args) {
        DepartmentDao departmentDao = new DepartmentDao();
        //++Get department
        //System.out.println(departmentDao.getDepartment("d008"));

        //++Lista departamentos
        //System.out.println(departmentDao.listaDepartamentos());

        //++Lista empleados en departamento
        //System.out.println(departmentDao.listaEmpleadosDepartamento("d008"));

        //++Get Manager
        //System.out.println(departmentDao.managerDepartamento("d001"));

        EmployeeDao employeeDao = new EmployeeDao();
        //Employee employee1 = new Employee(1,new Date(1990,5,3),"Iker","Fernandez","M",new Date(2020,6,1));
        //++Get Employee
        //System.out.println(employeeDao.getEmployee("10001"));

        //Lista Empleados
        //System.out.println(employeeDao.listaEmpleados());

        //Empleado is Manager
        //employeeDao.isManager("1","1");

        //Lista Empleados por titulos


        //Añadir empleado
        //employeeDao.nuevoEmpleado(employee1,"", new Date(2000,5,3),1999);

        //Eliminar empleado
        //employeeDao.eliminarEmpleado(10001);


        SalaryDao salaryDao = new SalaryDao();
        //Get Salario de empleado
        System.out.println(salaryDao.getSalario("10001", new Date(1990,6,25)));

        //Añadir nuevo salario empleado

    }
}
