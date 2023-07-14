package com.example.savedata.Model;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

  private long id;
  private String name;
  private String department;
  private Integer Salary;

  public static Employee to(EmployeeDTO employeeDTO) {
    ModelMapper mapper = new ModelMapper();
    return mapper.map(employeeDTO, Employee.class);
  }

  public static EmployeeDTO from(Employee employee) {
    ModelMapper mapper = new ModelMapper();
    return mapper.map(employee, EmployeeDTO.class);
  }

  public static List<EmployeeDTO> mapToEmployeeDto(List<Employee> employeeList) {
    return employeeList.stream()
        .map(EmployeeDTO::from)
        .collect(Collectors.toList());
  }
}
