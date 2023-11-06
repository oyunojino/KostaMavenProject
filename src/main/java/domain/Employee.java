package domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Employee {
  @Id
  @Column(name = "emp_id")
  private String empId;
  @Column(name = "emp_name", nullable = false, length = 10)
  private String empName;

//  @Column(name = "dept_id")
//  private int deptId;

  @ManyToOne
  @JoinColumn(name = "dept_id")
  private Department department;

  @Enumerated(EnumType.STRING)
  private EmpType empType;
  @Column(name = "join_date", nullable = false, length = 10)
  private String joinDate;
  @Column(nullable = false)
  private Long salary;
}
