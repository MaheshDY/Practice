package com.example.savedata.Model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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

  @NotNull(message = "Name should not be null")
  @NotBlank
  private String name;
  @NotBlank(message = "Department cannot be blank")
  private String department;
  private String gender;
  @NotEmpty(message = "Enter valid salary")
  @Min(value = 1000)
  private Integer salary;

  public static Employee to(EmployeeDTO employeeDTO) {
    ModelMapper mapper = new ModelMapper();
    return mapper.map(employeeDTO, Employee.class);
  }

  public static EmployeeDTO from(Employee employee) {
    ModelMapper mapper = new ModelMapper();
    return mapper.map(employee, EmployeeDTO.class);
  }

}
