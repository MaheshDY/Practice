package com.example.savedata.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyIterable;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.savedata.Dao.H2Repository;
import com.example.savedata.Model.Employee;
import com.example.savedata.Model.EmployeeDTO;
import com.example.savedata.Service.EmployeeService;
import com.example.savedata.exception.EmployeeNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


class EmployeeServiceTest {

  @Mock
  private H2Repository repository;

  private EmployeeService service;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    service = new EmployeeService(repository);
  }

  @Test
  void testSaveEmployee() {
    // Prepare
    EmployeeDTO employeeDTO = new EmployeeDTO(1L, "John", "IT", 5000);
    Employee employee = new Employee(1L, "John", "IT", 5000);
    when(repository.save(any(Employee.class))).thenReturn(employee);

    // Execute
    EmployeeDTO result = service.saveEmployee(employeeDTO);

    // Verify
    assertNotNull(result);
    assertEquals(employeeDTO.getId(), result.getId());
    assertEquals(employeeDTO.getName(), result.getName());
    assertEquals(employeeDTO.getDepartment(), result.getDepartment());
    assertEquals(employeeDTO.getSalary(), result.getSalary());
    verify(repository, times(1)).save(any(Employee.class));
  }

  @Test
  void testGetAllEmployees() {
    // Prepare
    Employee employee1 = new Employee(1L, "John", "IT", 5000);
    Employee employee2 = new Employee(2L, "Jane", "HR", 6000);
    when(repository.findAll()).thenReturn(Arrays.asList(employee1, employee2));

    // Execute
    List<Employee> result = service.getAllEmployees();

    // Verify
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals(employee1.getId(), result.get(0).getId());
    assertEquals(employee1.getName(), result.get(0).getName());
    assertEquals(employee1.getDepartment(), result.get(0).getDepartment());
    assertEquals(employee1.getSalary(), result.get(0).getSalary());
    assertEquals(employee2.getId(), result.get(1).getId());
    assertEquals(employee2.getName(), result.get(1).getName());
    assertEquals(employee2.getDepartment(), result.get(1).getDepartment());
    assertEquals(employee2.getSalary(), result.get(1).getSalary());
    verify(repository, times(1)).findAll();
  }

  @Test
  void testFindById() throws Exception {
    // Prepare
    long id = 1L;
    Employee employee = new Employee(1L, "John", "IT", 5000);
    when(repository.findById(id)).thenReturn(Optional.of(employee));

    // Execute
    EmployeeDTO result = service.findById(id);

    // Verify
    assertNotNull(result);
    assertEquals(Optional.of(employee.getId()), Optional.of(result.getId()));
    assertEquals(employee.getName(), result.getName());
    assertEquals(employee.getDepartment(), result.getDepartment());
    assertEquals(employee.getSalary(), result.getSalary());
    verify(repository, times(1)).findById(id);
  }

  @Test
  void testFindByIdNotFound() {
    // Prepare
    long id = 1L;
    when(repository.findById(id)).thenReturn(Optional.empty());

    // Verify
    assertThrows(EmployeeNotFoundException.class, () -> service.findById(id));
    verify(repository, times(1)).findById(id);
  }

  @Test
  void testEnterEmployee() {
    // Prepare
    List<Employee> employees = Arrays.asList(
        new Employee(1L, "John", "IT", 5000),
        new Employee(2L, "Jane", "HR", 6000)
    );
    when(repository.saveAll(anyIterable())).thenReturn(employees);

    // Execute
    List<Employee> result = service.enterEmployee(employees);

    // Verify
    assertNotNull(result);
    assertEquals(employees.size(), result.size());
    verify(repository, times(1)).saveAll(anyIterable());
  }
}
