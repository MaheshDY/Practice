package com.example.savedata.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
  public void testSaveEmployee() {
    // Prepare
    EmployeeDTO employeeDTO = new EmployeeDTO();
    employeeDTO.setName("John");
    employeeDTO.setDepartment("IT");
    employeeDTO.setSalary(5000);

    Employee employee = new Employee();
    employee.setName("John");
    employee.setDepartment("IT");
    employee.setSalary(5000);

    when(repository.save(any(Employee.class))).thenReturn(employee);

    // Execute
    EmployeeDTO result = service.saveEmployee(employeeDTO);

    // Verify
    assertNotNull(result);
    assertEquals(employeeDTO.getName(), result.getName());
    assertEquals(employeeDTO.getDepartment(), result.getDepartment());
    assertEquals(employeeDTO.getSalary(), result.getSalary());
    verify(repository, times(1)).save(any(Employee.class));
  }

  @Test
  public void testGetAllEmployees() {
    // Prepare
    Employee employee1 = new Employee();
    Employee employee2 = new Employee();
    when(repository.findAll()).thenReturn(Arrays.asList(employee1, employee2));

    // Execute
    List<Employee> result = service.getAllEmployees();

    // Verify
    assertNotNull(result);
    assertEquals(2, result.size());
    verify(repository, times(1)).findAll();
  }

  @Test
  public void testFindById() throws Exception {
    // Prepare
    long id = 1L;
    Employee employee = new Employee();
    when(repository.findById(id)).thenReturn(Optional.of(employee));

    // Execute
    EmployeeDTO result = service.findById(id);

    // Verify
    assertNotNull(result);
    verify(repository, times(1)).findById(id);
  }

  @Test
  public void testFindByIdNotFound() {
    // Prepare
    long id = 1L;
    when(repository.findById(id)).thenReturn(Optional.empty());

    // Verify
    assertThrows(EmployeeNotFoundException.class, () -> service.findById(id));
    verify(repository, times(1)).findById(id);
  }

  @Test
  public void testEnterEmployee() {
    // Prepare
    List<Employee> employees = Arrays.asList(new Employee(), new Employee());
    when(repository.saveAll(anyIterable())).thenReturn(employees);

    // Execute
    List<Employee> result = service.enterEmployee(employees);

    // Verify
    assertNotNull(result);
    verify(repository, times(1)).saveAll(anyIterable());
  }
}

