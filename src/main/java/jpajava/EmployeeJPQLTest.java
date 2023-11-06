package jpajava;

import domain.EmpType;
import domain.Employee;

import javax.persistence.*;
import java.util.List;

public class EmployeeJPQLTest {
  public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    System.out.println("##### 트랜잭션 시작 #####");

    Employee e3 = new Employee();
    e3.setEmpId("202305");
//    e3.setDeptId(1);
    e3.getEmpType();
    e3.setEmpName("test5");
    em.persist(e3);
    Employee e4 = new Employee();
    e4.setEmpId("202306");
//    e4.setDeptId(1);
    e4.setEmpName("test6");
    em.persist(e4);

    String jpql = "select e from Employee e where e.department = :deptId";
    em.flush(); // flushmode가 auto일 때,
    TypedQuery<Employee> query = em.createQuery(jpql, Employee.class);
    query.setParameter("deptId", 1);
    List<Employee> resultList = query.getResultList();
    System.out.println("Dept가 1인 사원은 " + resultList.size() + "명이 있습니다.");


    jpql = "select count(e), sum(e.salary), avg(e.salary), max(e.salary), min(e.salary) " +
        "from Employee e";
    Object singleResult = em.createQuery(jpql).getSingleResult();

    Employee e1 = em.find(Employee.class, "202301");
    System.out.println("DB에서 가져옴");
    System.out.println("<-- 영속 상태 -->");

    Employee e2 = em.find(Employee.class, "202301");
    System.out.println("<-- 1차 캐시에서 가져옴 -->");
    System.out.println("e1 == e2 >>> " + (e1 == e2));

    System.out.println("<-- 커밋 전 -->");
    tx.commit();
    System.out.println("<-- 커밋 후 -->");
    em.close();
    emf.close();

    System.out.println("##### 트랜잭션 종료 #####");
  }
}
