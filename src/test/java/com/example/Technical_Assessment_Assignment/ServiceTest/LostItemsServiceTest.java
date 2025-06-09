package com.example.Technical_Assessment_Assignment.ServiceTest;

import com.example.Technical_Assessment_Assignment.Model.LostItems;
import com.example.Technical_Assessment_Assignment.Repository.LostItemsRepository;
import com.example.Technical_Assessment_Assignment.Service.LostItemsService;
import org.aspectj.bridge.Message;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LostItemsServiceTest {

    @Mock
    LostItemsRepository lostItemsRepository;

    @InjectMocks
    LostItemsService lostItemsService;

    LostItems fakeLostItems;
    LostItems fakeLostItem;

    @BeforeEach
    void setup(){
        fakeLostItems = new LostItems(1,null,5,null,null);
    }

    @Test
    void shouldReturnListOfLostItems_whenItemsExist(){
        when(lostItemsRepository.findAll()).thenReturn(List.of(fakeLostItems));
        assertEquals(1,lostItemsService.getAllLostItems().size());
    }

    @Test
    void shouldReturnEmptyList_whenNoLostItemsExist(){
        when(lostItemsRepository.findAll()).thenReturn(Collections.emptyList());
        List<LostItems> result = lostItemsService.getAllLostItems();
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void testShouldEqualDataBase(){
        when(lostItemsRepository.findAll()).thenReturn(List.of(fakeLostItems));
        List<LostItems> result = lostItemsService.getAllLostItems();
        assertEquals(fakeLostItems,result.get(0));
    }

    @Test
    void shouldSaveValidLostItemSuccessfully() throws Exception {
        when(lostItemsRepository.save(fakeLostItems)).thenReturn(fakeLostItems);
        LostItems result = lostItemsService.saveLostItems(fakeLostItems);
        assertEquals(fakeLostItems,result);
        verify(lostItemsRepository).save(fakeLostItems);
    }

    @Test
    void shouldThrowException_whenSavingNullItem() {
        assertThrows(Exception.class,
                () -> lostItemsService.saveLostItems(null), "This Object is Null");
    }

    @Test
    void testSaveShouldThrowForInvalidInput(){
        LostItems result = new LostItems(1,null,-1,null,null);
        assertThrows(Exception.class,
                () -> lostItemsService.saveLostItems(result));
        verify(lostItemsRepository, never()).save(any());
    }


    @Test
    void testFindBYItemNameShouldThrowForInvalidInputItemName(){
        String ItemName = "";
        assertThrows(Exception.class,
                () -> lostItemsService.findByItemName(ItemName));
        verify(lostItemsRepository, never()).findByItemName(any());
    }

    @Test
    void shouldReturnLostItemMatchingGivenName_whenNameExists(){
        String validName = "ghjghj";
        LostItems fakeItem = new LostItems();
        fakeItem.setItemName(validName);

        when(lostItemsRepository.findByItemName(validName)).thenReturn(List.of(fakeItem));

        List<LostItems> result = lostItemsService.findByItemName(validName);

        assertEquals(1, result.size());
        assertEquals(validName, result.get(0).getItemName());
    }

    @Test
    void testShouldReturnItemListForValidName() {
        String name = "ali";
        when(lostItemsRepository.findByItemName(name))
                .thenReturn(List.of(fakeLostItems));

        List<LostItems> result = lostItemsService.findByItemName(name);

        assertEquals(1, result.size());
        assertEquals(fakeLostItems, result.get(0));
        verify(lostItemsRepository).findByItemName(name);
    }

}
