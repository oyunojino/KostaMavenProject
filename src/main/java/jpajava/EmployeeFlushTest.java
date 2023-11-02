package jpajava;

import domain.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class EmployeeFlushTest {
  public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    System.out.println("##### 트랜잭션 시작 #####");

    try {
      Employee emp = new Employee();

      Employee e1 = em.find(Employee.class, "202301");
      System.out.println("<-- DB에서 가져옴 -->");
      System.out.println("<-- 영속 상태 -->");

      System.out.println("<-- 수정 전 -->");
      e1.setDeptId(10);

      em.persist(e1);
      System.out.println("<-- Flush 직접 호출 전 -->");
      em.flush();
      System.out.println("<-- Flush 직접 호출 후 -->");

      System.out.println("<-- 커밋 전 -->");
      tx.commit();
      System.out.println("<-- 커밋 후 -->");

      Employee e2 = em.find(Employee.class, "202301");
      System.out.println("e1 == e2 >>> " + (e1 == e2));
    } catch (Exception e) {
      System.out.println(e.getMessage());
      tx.rollback();
    } finally {
      em.close();
      emf.close();
    }

    System.out.println("##### 트랜잭션 종료 #####");
  }
}
