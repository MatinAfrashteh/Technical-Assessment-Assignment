package com.example.Technical_Assessment_Assignment.Repository;

import com.example.Technical_Assessment_Assignment.Model.LostItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LostItemsRepository extends JpaRepository<LostItems,Integer> {
}
