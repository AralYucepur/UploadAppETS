package com.ets.controller;

import com.ets.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ets.constants.RestApi.FILE;

@RestController
@RequestMapping(FILE)
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
}
