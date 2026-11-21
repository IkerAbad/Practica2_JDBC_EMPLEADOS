# Practica2_JDBC_EMPLEADOS

Segunda práctica de JDBC del ciclo de DAM (abril de 2023). Proyecto Maven en Java que consulta y modifica la base de datos de ejemplo `employees` de MySQL con el patrón DAO: un DAO por entidad (`DepartmentDao`, `EmployeeDao`, `SalaryDao`), las clases del modelo y una utilidad de conexión.

## Qué hay

- `DepartmentDao`: obtener un departamento, listarlos, listar los empleados de un departamento y obtener su manager actual.
- `EmployeeDao`: obtener o listar empleados, comprobar si un empleado es manager de un departamento, buscar por título, dar de alta (con departamento y salario inicial) y eliminar (junto con sus registros relacionados). Alta y baja usan transacciones.
- `SalaryDao`: salario de un empleado en una fecha y alta de un nuevo salario cerrando el vigente.
- `App.java` es un programa de prueba con las llamadas comentadas.
- Compila con Java 17 y Maven. Solo he comprobado que compila: no lo he ejecutado contra una base de datos real, así que las consultas están sin probar.

## Cómo probarlo

Hace falta MySQL con la base `employees`. Por defecto se conecta a `jdbc:mysql://192.168.56.101:3306/employees` (la máquina virtual de clase) con el usuario `user`; se puede cambiar con las variables de entorno `DB_URL`, `DB_USER` y `DB_PASS`.

```
mvn compile
mvn exec:java -Dexec.mainClass=App
```

## Licencia

MIT, ver [LICENSE](LICENSE).

---

# Practica2_JDBC_EMPLEADOS (English)

Second JDBC assignment from my DAM vocational course (April 2023). A Maven project in Java that queries and updates the MySQL sample database `employees` using the DAO pattern: one DAO per entity (`DepartmentDao`, `EmployeeDao`, `SalaryDao`), the model classes and a connection helper.

## What's inside

- `DepartmentDao`: fetch a department, list them all, list a department's employees and get its current manager.
- `EmployeeDao`: fetch or list employees, check whether an employee manages a department, search by title, insert (with department and starting salary) and delete (along with related rows). Insert and delete run in transactions.
- `SalaryDao`: an employee's salary on a given date, and adding a new salary by closing the current one.
- `App.java` is a test program with the calls commented out.
- It builds with Java 17 and Maven. I have only checked that it compiles; I have not run it against a real database, so the queries are untested.

## Trying it

You need MySQL with the `employees` database. By default it connects to `jdbc:mysql://192.168.56.101:3306/employees` (the classroom virtual machine) as user `user`; override it with the `DB_URL`, `DB_USER` and `DB_PASS` environment variables.

```
mvn compile
mvn exec:java -Dexec.mainClass=App
```

## License

MIT, see [LICENSE](LICENSE).
