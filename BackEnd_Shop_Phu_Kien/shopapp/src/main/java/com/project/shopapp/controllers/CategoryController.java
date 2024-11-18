package com.project.shopapp.controllers;

import com.project.shopapp.dtos.requests.CategoryDTO;
import com.project.shopapp.models.Category;
import com.project.shopapp.services.category.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
//@Validated
public class CategoryController {

    private final CategoryService categoryService;

    //    Thêm mới
    @PostMapping("")
    public ResponseEntity<?> createCategory(@RequestBody @Valid CategoryDTO catogoryDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<String> errorMessages =  bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        categoryService.createCategory(catogoryDTO);
        return ResponseEntity.ok("Create category");
    }



//    Hiển thị tất cả
    @GetMapping("")
    public ResponseEntity<List<Category>> getAllCategories(@RequestParam("page") int page, @RequestParam("limit") int limit){
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }


//    Tìm kiếm danh mục theo id
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable int id, @RequestBody CategoryDTO categoryDTO){
       Category category =  categoryService.updateCategory(id, categoryDTO);
       return ResponseEntity.ok(category);
    }

//    Xóa dữ liệu
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable int id) throws Exception {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Delete category");
    }
}
