package peaksoft.services.impls;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.requests.SubcategoryRequest;
import peaksoft.dto.requests.UpdateSubcategoryRequest;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.dto.responses.SubcategoryResponse;
import peaksoft.entities.Category;
import peaksoft.entities.Subcategory;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repositories.CategoryRepository;
import peaksoft.repositories.SubcategoryRepository;
import peaksoft.services.SubcategoryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubcategoryServiceImpl implements SubcategoryService {
    private final SubcategoryRepository subcategoryRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public SubcategoryResponse findById(Long subcategoryId) {
        Subcategory subcategory = subcategoryRepository.findById(subcategoryId)
                .orElseThrow(() -> new NotFoundException("Subcategory with id " + subcategoryId + " not found!"));

        return new SubcategoryResponse(subcategory.getId(), subcategory.getName(), subcategory.getCategory().getId());
    }

    @Override
    public List<SubcategoryResponse> findAllByCategoryId(Long categoryId) {
        List<Subcategory> subcategories = subcategoryRepository.findAllByCategoryId(categoryId);
        return subcategories.stream()
                .map(subcategory -> new SubcategoryResponse(subcategory.getId(), subcategory.getName(), subcategory.getCategory().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public SimpleResponse save(SubcategoryRequest subcategoryRequest) {
        Category category = categoryRepository.findById(subcategoryRequest.categoryId()).orElseThrow(() -> new NotFoundException("Category with id " + subcategoryRequest.categoryId() + " not found!"));
        Subcategory subcategory = new Subcategory();
        subcategory.setName(subcategoryRequest.name());
        subcategory.setCategory(category);
        subcategoryRepository.save(subcategory);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully saved!").build();
    }

    @Override
    @Transactional
    public SimpleResponse update(Long subcategoryId, UpdateSubcategoryRequest subcategoryRequest) {
        Subcategory subcategory = subcategoryRepository.findById(subcategoryId)
                .orElseThrow(() -> new NotFoundException("Subcategory with id " + subcategoryId + " not found!"));
        subcategory.setName(subcategoryRequest.name());
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully updated!").build();

    }

    @Override
    public SimpleResponse delete(Long subcategoryId) {
        subcategoryRepository.findById(subcategoryId)
                .orElseThrow(() -> new NotFoundException("Subcategory with id " + subcategoryId + " not found!"));
        subcategoryRepository.deleteById(subcategoryId);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully deleted!").build();
    }
}
