package jpajava;

import domain.Department;
import domain.EmpType;
import domain.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Emp_Dept_Test {
  public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    System.out.println("##### 트랜잭션 시작 #####");

    try {
      Department dept = new Department();
      dept.setDeptName("Team2");
      em.persist(dept); // 즉시 insert

      Employee emp = new Employee();
      emp.setEmpId("202301");
      emp.setEmpName("홍길동1");
//      emp.setDeptId(dept.getDeptId());
      emp.setDepartment(dept);
      emp.setEmpType(EmpType.B);
      emp.setJoinDate("2023-01-01");
      emp.setSalary(180_000_000L);
      em.persist(emp); // 지연 쓰기

      Employee e1 = em.find(Employee.class, emp.getEmpId());
//      1. 연관 관계가 없을 때,
//      Department d1 = em.find(Department.class, dept.getDeptId());
//      2. 연관 관계가 있을 떄,
      Department d1 = e1.getDepartment();

      System.out.println("<-- 1차 캐시에서 가져옴 -->");

      System.out.println("<-- 커밋 전 -->");
      tx.commit();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      tx.rollback();
    }
    System.out.println("<-- 커밋 후 -->");
    em.close();
    emf.close();

    System.out.println("##### 트랜잭션 종료 #####");
  }
}
