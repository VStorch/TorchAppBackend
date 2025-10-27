package com.torchapp.demo.controllers;

import com.torchapp.demo.dtos.evaluate.EvaluatePetShopRequest;
import com.torchapp.demo.dtos.evaluate.EvaluatePetShopResponse;
import com.torchapp.demo.services.EvaluatePetShopService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/evaluate")
public class EvaluatePetShopController {

    private final EvaluatePetShopService evaluateService;

    public EvaluatePetShopController(EvaluatePetShopService evaluateService) {
        this.evaluateService = evaluateService;
    }

    @PostMapping
    public ResponseEntity<EvaluatePetShopResponse> evaluate(@Valid @RequestBody EvaluatePetShopRequest request) {
        EvaluatePetShopResponse response = evaluateService.evaluatePetShop(request);
        return ResponseEntity.status(201).body(response);
    }
}
