package com.example.savedata.Controller;

import com.example.savedata.Model.Employee;
import com.example.savedata.Model.EmployeeDTO;
import com.example.savedata.Service.EmployeeService;
import com.example.savedata.exception.EmployeeNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@Slf4j
public class EmployeeController {

  private final EmployeeService service;

  @Autowired
  public EmployeeController(EmployeeService service) {
    this.service = service;
  }

  @PostMapping("/save")
  public ResponseEntity<EmployeeDTO> saveEmployee(@RequestBody @Valid EmployeeDTO employeeDTO) {
    log.debug("Trying to save the employee");
    return new ResponseEntity<>(service.saveEmployee(employeeDTO), HttpStatus.CREATED);
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
      log.debug("Not able to find the employee : " + e);
      throw new EmployeeNotFoundException("Employee not found with id " + id);
    }
  }

  @PostMapping("saveEmployeeList")
  public ResponseEntity<List<EmployeeDTO>> getEmployeeList(@RequestBody @Valid List<EmployeeDTO> employeeDTOList) {
    List<Employee> employeeList = service.enterEmployee(employeeDTOList.stream()
        .map(EmployeeDTO::to)
        .collect(Collectors.toList()));

    return ResponseEntity.ok(employeeList.stream()
        .map(EmployeeDTO::from).collect(Collectors.toList()));
  }

  @DeleteMapping("/deleteEmployeeById")
  public ResponseEntity<String> deleteByEmployeeId(@RequestParam(value = "id", required = true) Long id) {
    service.deleteById(id);
    return ResponseEntity.ok("Employee with id " + id + " deleted successfully");
  }
}
