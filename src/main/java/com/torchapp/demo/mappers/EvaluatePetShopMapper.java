package com.torchapp.demo.mappers;

import com.torchapp.demo.dtos.evaluate.EvaluatePetShopRequest;
import com.torchapp.demo.dtos.evaluate.EvaluatePetShopResponse;
import com.torchapp.demo.models.EvaluatePetShop;
import com.torchapp.demo.models.PetShop;
import com.torchapp.demo.models.User;

public class EvaluatePetShopMapper {

    public static EvaluatePetShop toEntity(EvaluatePetShopRequest request, PetShop petShop, User user) {
        EvaluatePetShop evaluate = new EvaluatePetShop();
        evaluate.setRating(evaluate.getRating());
        evaluate.setComment(evaluate.getComment());
        evaluate.setPetShop(petShop);
        evaluate.setUser(user);
        return evaluate;
    }

    public static EvaluatePetShopResponse toResponse(EvaluatePetShop evaluate) {
        EvaluatePetShopResponse response = new EvaluatePetShopResponse();
        response.setId(evaluate.getId());
        response.setRating(evaluate.getRating());
        response.setComment(evaluate.getComment());
        response.setDate(evaluate.getDate());
        response.setPetShopId(evaluate.getPetShop().getId());
        response.setPetShopName(evaluate.getPetShop().getName());
        return response;
    }
}
