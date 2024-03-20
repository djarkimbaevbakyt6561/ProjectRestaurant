package peaksoft.services;

import peaksoft.dto.requests.SubcategoryRequest;
import peaksoft.dto.requests.UpdateSubcategoryRequest;
import peaksoft.dto.responses.SubcategoryResponse;
import peaksoft.dto.responses.SimpleResponse;

import java.util.List;

public interface SubcategoryService {

    SubcategoryResponse findById(Long subcategoryId);
    List<SubcategoryResponse> findAllByCategoryId(Long categoryId);

    SimpleResponse save(SubcategoryRequest subcategoryRequest);

    SimpleResponse update(Long subcategoryId, UpdateSubcategoryRequest subcategoryRequest);

    SimpleResponse delete(Long subcategoryId);
}
