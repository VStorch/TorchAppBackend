package com.torchapp.demo.controllers;

import com.torchapp.demo.dtos.evaluate.EvaluatePetShopRequest;
import com.torchapp.demo.dtos.evaluate.EvaluatePetShopResponse;
import com.torchapp.demo.services.EvaluatePetShopService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{petShopId}")
    public ResponseEntity<List<EvaluatePetShopResponse>> listByPetShop(@PathVariable Long petShopId) {
        List<EvaluatePetShopResponse> responses = evaluateService.listByPetShop(petShopId);
        return ResponseEntity.ok(responses);
    }
}
