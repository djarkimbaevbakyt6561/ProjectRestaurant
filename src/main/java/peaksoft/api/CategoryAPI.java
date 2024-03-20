package peaksoft.api;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.requests.CategoryRequest;
import peaksoft.dto.responses.CategoryResponse;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.services.CategoryService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryAPI {
    private final CategoryService categoryService;
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @Operation(description = "Get by id")
    @GetMapping("/{categoryId}")
    public CategoryResponse findById(@PathVariable Long categoryId) {
        return categoryService.findById(categoryId);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @Operation(description = "Save ")
    @PostMapping("/save")
    public SimpleResponse save(@RequestBody @Valid CategoryRequest categoryRequest){
        return categoryService.save(categoryRequest);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @Operation(description = "Update")
    @PutMapping("/{categoryId}")
    public SimpleResponse update(@PathVariable Long categoryId, @RequestBody @Valid CategoryRequest categoryRequest) {
        return categoryService.update(categoryId, categoryRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @Operation(description = "Delete")
    @DeleteMapping("/{categoryId}")
    public SimpleResponse delete(@PathVariable Long categoryId) {
        return categoryService.delete(categoryId);
    }

}
