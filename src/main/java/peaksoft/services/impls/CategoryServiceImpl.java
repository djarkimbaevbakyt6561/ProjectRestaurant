package peaksoft.services.impls;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.requests.CategoryRequest;
import peaksoft.dto.responses.CategoryResponse;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.entities.Category;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repositories.CategoryRepository;
import peaksoft.services.CategoryService;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse findById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category with id " + categoryId + " not found!"));

        return new CategoryResponse(category.getId(), category.getName());
    }

    @Override
    public SimpleResponse save(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.name());
        categoryRepository.save(category);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully saved!").build();

    }

    @Override
    @Transactional
    public SimpleResponse update(Long categoryId, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category with id " + categoryId + " not found!"));

        category.setName(categoryRequest.name());
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully updated!").build();

    }

    @Override
    public SimpleResponse delete(Long categoryId) {
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category with id " + categoryId + " not found!"));

        categoryRepository.deleteById(categoryId);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully deleted!").build();

    }

}
