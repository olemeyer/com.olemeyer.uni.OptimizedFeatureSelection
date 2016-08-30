package com.olemeyer.uni.optfeatureselection.controller;

import com.olemeyer.uni.optfeatureselection.rga.RGA;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Ole Meyer
 */

@RestController
public class APIController {

    @RequestMapping(value = "/optimize", method = RequestMethod.POST)
    public Response optimize(@Valid @RequestBody Request request) {
        RGA rga = new RGA(request.getFeatureModel(), request.getPopulationSize());

        return rga.optimize(request.getRgMutationProbability(), request.getSgMutationProbability(),
                request.getReproductionQuota(), request.getMaxIterations(), request.getMinAttractorLength());

    }

}
