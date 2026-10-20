package com.yzj.jep.app.sub.web.controller;

import com.yzj.jep.app.sub.model.Bus_rel_list;
import com.yzj.jep.app.sub.service.EffectiveRelatedPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/east")
public class EffectiveRelatedPartyController {

    @Autowired
   private EffectiveRelatedPartyService relatedPartyService;



}
