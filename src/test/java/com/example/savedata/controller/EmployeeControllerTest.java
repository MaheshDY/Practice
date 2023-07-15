package com.example.savedata.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import com.example.savedata.Controller.EmployeeController;
import com.example.savedata.Model.Employee;
import com.example.savedata.Model.EmployeeDTO;
import com.example.savedata.Service.EmployeeService;
import com.example.savedata.exception.EmployeeNotFoundException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringJUnitConfig
@WebMvcTest(EmployeeController.class)
@AutoConfigureMockMvc
class EmployeeControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private EmployeeService employeeService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testSaveEmployee() throws Exception {
    EmployeeDTO employeeDTO = new EmployeeDTO();
    employeeDTO.setName("John");
    employeeDTO.setDepartment("IT");

    EmployeeDTO savedEmployeeDTO = new EmployeeDTO();
    savedEmployeeDTO.setName("John");
    savedEmployeeDTO.setDepartment("IT");

    when(employeeService.saveEmployee(any(EmployeeDTO.class))).thenReturn(savedEmployeeDTO);

    mockMvc.perform(MockMvcRequestBuilders.post("/save")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"John\",\"department\":\"IT\"}"))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(savedEmployeeDTO.getName()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.department").value(savedEmployeeDTO.getDepartment()));
  }

  @Test
  void testGetAllEmployees() throws Exception {
    Employee employee1 = new Employee();
    employee1.setName("John");
    employee1.setDepartment("IT");
    employee1.setSalary(5000);

    Employee employee2 = new Employee();
    employee2.setName("Jane");
    employee2.setDepartment("HR");
    employee2.setSalary(6000);
    List<Employee> employeeList = Arrays.asList(employee1, employee2);

    when(employeeService.getAllEmployees()).thenReturn(employeeList);

    mockMvc.perform(MockMvcRequestBuilders.get("/allemployee"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(employee1.getName()))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].department").value(employee1.getDepartment()))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(employee2.getName()))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].department").value(employee2.getDepartment()));
  }

  @Test
  void testGetEmployeeById() throws Exception {
    long id = 1L;
    EmployeeDTO employeeDTO = new EmployeeDTO();
    employeeDTO.setName("John");
    employeeDTO.setDepartment("IT");

    when(employeeService.findById(id)).thenReturn(employeeDTO);

    mockMvc.perform(MockMvcRequestBuilders.get("/getemployeebyid/{id}", id))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(employeeDTO.getName()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.department").value(employeeDTO.getDepartment()));
  }

  @Test
  void testGetEmployeeById_NotFound() throws Exception {
    long id = 1L;

    when(employeeService.findById(id)).thenThrow(new EmployeeNotFoundException("Employee not found with id " + id));

    mockMvc.perform(MockMvcRequestBuilders.get("/getemployeebyid/{id}", id))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  void testGetEmployeeList() throws Exception {
    EmployeeDTO employeeDTO1 = new EmployeeDTO();
    employeeDTO1.setName("John");
    employeeDTO1.setDepartment("IT");

    EmployeeDTO employeeDTO2 = new EmployeeDTO();
    employeeDTO2.setName("Jane");
    employeeDTO2.setDepartment("HR");

    List<EmployeeDTO> employeeDTOList = Arrays.asList(employeeDTO1, employeeDTO2);

    List<Employee> employeeList = Arrays.asList(
        new Employee(1L, "John", "IT", null, 1000),
        new Employee(2L, "Jane", "HR", null, 2000)
    );

    when(employeeService.enterEmployee(anyList())).thenReturn(employeeList);

    mockMvc.perform(MockMvcRequestBuilders.post("/saveEmployeeList")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "[{\"id\":1,\"name\":\"John\",\"department\":\"IT\"}, {\"id\":2,\"name\":\"Jane\","
                    + "\"department\":\"HR\"}]"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(employeeDTO1.getName()))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].department").value(employeeDTO1.getDepartment()))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(employeeDTO2.getName()))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].department").value(employeeDTO2.getDepartment()));
  }
}