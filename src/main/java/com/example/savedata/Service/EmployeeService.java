package com.example.savedata.Service;

import com.example.savedata.Dao.H2Repository;
import com.example.savedata.Model.Employee;
import com.example.savedata.Model.EmployeeDTO;
import com.example.savedata.exception.EmployeeNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

  private final H2Repository repository;

  @Autowired
  public EmployeeService(H2Repository repository) {
    this.repository = repository;
  }

  public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
    Employee savedEmployee = repository.save(EmployeeDTO.to(employeeDTO));
    return EmployeeDTO.from(savedEmployee);
  }

  public List<Employee> getAllEmployees() {
    return repository.findAll();
  }

  public EmployeeDTO findById(long id) throws Exception {
    Employee employee = repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
    return EmployeeDTO.from(employee);
  }

  public List<Employee> enterEmployee(List<Employee> employees) {
    return repository.saveAll(employees);
  }
}
