package com.example.savedata.Controller;

import com.example.savedata.Model.Employee;
import com.example.savedata.Model.EmployeeDTO;
import com.example.savedata.Service.EmployeeService;
import com.example.savedata.exception.EmployeeNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class EmployeeController {

  private final EmployeeService service;

  @Autowired
  public EmployeeController(EmployeeService service) {
    this.service = service;
  }

  @PostMapping("/save")
  public ResponseEntity<EmployeeDTO> save(@RequestBody @Valid EmployeeDTO employeeDTO) {
    try {
      EmployeeDTO dto = service.saveEmployee(employeeDTO);
      return ResponseEntity.status(HttpStatus.OK).body(dto);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @GetMapping("/allemployee")
  public List<Employee> getAllEmployees() {
    return service.getAllEmployees();
  }

  @GetMapping("/getemployeebyid/{id}")
  public EmployeeDTO getEmployeeById(@PathVariable long id) {
    try {
      return service.findById(id);
    } catch (Exception e) {
      throw new EmployeeNotFoundException("Employee not found with id " + id);
    }
  }

  @PostMapping("saveEmployeeList")
  public ResponseEntity<List<EmployeeDTO>> getEmployeeList(@RequestBody List<EmployeeDTO> employeeDTOList) {
    List<Employee> employeeList = service.enterEmployee(employeeDTOList.stream()
        .map(EmployeeDTO::to)
        .collect(Collectors.toList()));

    return ResponseEntity.ok(employeeList.stream()
        .map(EmployeeDTO::from).collect(Collectors.toList()));
  }
}
