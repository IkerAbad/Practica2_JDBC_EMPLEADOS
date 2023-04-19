package models;

import java.util.Date;

public class Salary {

    private int emp_no;
    private int salary;
    private Date from_date;
    private Date to_date;

    public Salary(){}

    public Salary(int emp_no, int salary, Date from_date, Date to_date) {
        this.emp_no = emp_no;
        this.salary = salary;
        this.from_date = from_date;
        this.to_date = to_date;
    }

    public int getEmp_no() {
        return emp_no;
    }

    public void setEmp_no(int emp_no) {
        this.emp_no = emp_no;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Date getFrom_date() {
        return from_date;
    }

    public void setFrom_date(Date from_date) {
        this.from_date = from_date;
    }

    public Date getTo_date() {
        return to_date;
    }

    public void setTo_date(Date to_date) {
        this.to_date = to_date;
    }

    @Override
    public String toString() {
        return "Salary{" +
                "emp_no=" + emp_no +
                ", salary=" + salary +
                ", from_date=" + from_date +
                ", to_date=" + to_date +
                '}';
    }
}
