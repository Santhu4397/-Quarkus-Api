package org.tns.dao;

import io.quarkus.logging.Log;
import org.tns.dto.Model;
import org.tns.repo.ModelRepo;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ModelDao  {

    @Inject
    ModelRepo modelRepo;
    public Model saveModel(Model model){
        try {
            modelRepo.persist(model);
        }catch (Exception ex){
            Log.error("!!!!!!!!!"+ex+"!!!!!!!");
        }
        return model;
    }

}
