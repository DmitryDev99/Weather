package com.example.weather.catApi.domain.useCase;

import com.example.weather.catApi.domain.repositories.CatLocalDataSource;

import java.io.File;

public class GetCachedImageUseCase {
    private final CatLocalDataSource repository;

    public GetCachedImageUseCase(CatLocalDataSource repository) {
        this.repository = repository;
    }

    public File invoke() {
        return repository.getCat();
    }
}
