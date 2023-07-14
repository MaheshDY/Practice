package com.example.savedata.Dao;

import com.example.savedata.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface H2Repository extends JpaRepository<Employee, Long> {

}
