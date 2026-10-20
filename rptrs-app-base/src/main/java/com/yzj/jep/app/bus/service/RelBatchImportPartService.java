package com.yzj.jep.app.bus.service;


import com.yzj.jep.app.bus.model.RelVo;
import com.yzj.jep.core.utils.XlsMessage;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author root
 */

public interface RelBatchImportPartService {

    XlsMessage BatchService(StringBuilder builder, StringBuilder errorMsg, List<RelVo> list,int num,int num2,int num3,String recordNumberNew);
}
