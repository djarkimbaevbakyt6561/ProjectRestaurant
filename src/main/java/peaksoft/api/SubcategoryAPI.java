package peaksoft.api;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.requests.SubcategoryRequest;
import peaksoft.dto.requests.UpdateSubcategoryRequest;
import peaksoft.dto.responses.SubcategoryResponse;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.services.SubcategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subcategory")
public class SubcategoryAPI {
    private final SubcategoryService subcategoryService;

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @Operation(description = "Get by id")
    @GetMapping("/{subcategoryId}")
    public SubcategoryResponse findById(@PathVariable Long subcategoryId) {
        return subcategoryService.findById(subcategoryId);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    @Operation(description = "Get all")
    @GetMapping("/getAll/category/{categoryId}")
    public List<SubcategoryResponse> getAllEmployeesByRestaurantId(@PathVariable Long categoryId) {
        return subcategoryService.findAllByCategoryId(categoryId);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @Operation(description = "Save ")
    @PostMapping("/save")
    public SimpleResponse save(@RequestBody @Valid SubcategoryRequest subcategoryRequest) {
        return subcategoryService.save(subcategoryRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @Operation(description = "Update")
    @PutMapping("/{subcategoryId}")
    public SimpleResponse update(@PathVariable Long subcategoryId, @RequestBody @Valid UpdateSubcategoryRequest subcategoryRequest) {
        return subcategoryService.update(subcategoryId, subcategoryRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @Operation(description = "Delete")
    @DeleteMapping("/{subcategoryId}")
    public SimpleResponse delete(@PathVariable Long subcategoryId) {
        return subcategoryService.delete(subcategoryId);
    }

}
