package com.example.Technical_Assessment_Assignment.Service;

import com.example.Technical_Assessment_Assignment.Model.Claim;
import com.example.Technical_Assessment_Assignment.Model.LostItems;
import com.example.Technical_Assessment_Assignment.Repository.ClaimRepository;
import com.example.Technical_Assessment_Assignment.Repository.LostItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LostItemsService {

    @Autowired
    LostItemsRepository lostItemsRepository;

    public List<LostItems> getAllLostItems(){
        return lostItemsRepository.findAll();
    }

    public LostItems saveLostItems(LostItems lostItems)throws Exception{
        if (lostItems == null){
            throw new Exception("This Object is Null");
        }
        if (lostItems.getQuantity() < 0 ){
            throw new IllegalArgumentException("must data bigger than 1");
        }
        return lostItemsRepository.save(lostItems);
    }

    public List<LostItems> findByItemName(String itemName){

        if (itemName.trim().isEmpty()) {
            throw new IllegalArgumentException("Item name is empty");
        }
        return lostItemsRepository.findByItemName(itemName);
    }


}
