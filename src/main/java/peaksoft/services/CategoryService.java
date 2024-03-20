package peaksoft.services;

import peaksoft.dto.requests.CategoryRequest;
import peaksoft.dto.responses.CategoryResponse;
import peaksoft.dto.responses.SimpleResponse;

public interface CategoryService {
    CategoryResponse findById(Long categoryId);

    SimpleResponse save(CategoryRequest categoryRequest);

    SimpleResponse update(Long categoryId, CategoryRequest categoryRequest);

    SimpleResponse delete(Long categoryId);
}
