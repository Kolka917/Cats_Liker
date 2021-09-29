package com.example.Mimimimetr.repository;
import com.example.Mimimimetr.domain.Cat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatRepo extends JpaRepository<Cat, Long> {
    Cat findByName(String name);

}