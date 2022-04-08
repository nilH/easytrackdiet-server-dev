package nilH.easyTrackDiet.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nilH.easyTrackDiet.dto.TierData;
import nilH.easyTrackDiet.service.AutoCompleteService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/forms/record")
public class RecordController {
    private Logger logger=LoggerFactory.getLogger(RecordController.class);
    @Autowired
    AutoCompleteService autoCompleteService;

    @GetMapping("/init")
    public Mono<TierData> loadNames(){
        logger.info("init with record names");
        return autoCompleteService.getTier("record");
    }

    @PostMapping("/update")
    public Mono<TierData> updateNames(@RequestBody TierData ntree){
        return autoCompleteService.updateTier(ntree, "record");
    }
}
