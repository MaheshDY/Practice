package com.example.savedata.Service;

import com.example.savedata.Dao.H2Repository;
import com.example.savedata.Model.Employee;
import com.example.savedata.Model.EmployeeDTO;
import com.example.savedata.exception.EmployeeNotFoundException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmployeeService {

  private final H2Repository repository;

  @Autowired
  public EmployeeService(H2Repository repository) {
    this.repository = repository;
  }

  public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
    log.debug("Saving a single employee");
    Employee employee = Employee.builder()
        .name(employeeDTO.getName())
        .department(employeeDTO.getDepartment())
        .salary(employeeDTO.getSalary())
        .build();
    return EmployeeDTO.from(repository.save(employee));
  }

  public List<Employee> getAllEmployees() {
    log.debug("Returning all the employees");
    return repository.findAll();
  }

  public EmployeeDTO findById(long id) {
    log.debug("Searching for the employee with id " + id);
    Employee employee = repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
    return EmployeeDTO.from(employee);
  }

  public List<Employee> enterEmployee(List<Employee> employees) {
    log.debug("Saving list of Employees");
    return repository.saveAll(employees);
  }

  public void deleteById(Long id) {
    log.debug("Checking if employee is present.");
    if (repository.existsById(id)) {
      repository.deleteById(id);
    } else {
      log.debug("Employee is not present");
      throw new EmployeeNotFoundException("Employee with id " + id + " not found");
    }
  }
}
