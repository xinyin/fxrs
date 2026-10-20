package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.utils.XlsMessage;
import com.yzj.jep.core.utils.xlsio.XlsImportCallback;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Map;

/**
 * 机构模板导出实现
 *
 * @author wrf
 * @time 2020年3月19日 下午3:47:25
 * @company 银之杰技术有限公司
 */
@Service(value = LegalPersonImportCallbackImpl.BEANID)
public class LegalPersonImportCallbackImpl implements XlsImportCallback {

    public static final String BEANID = "LegalPersonImportCallbackImpl";

    @Override
    public String getImportXmlTemplate() {
        return null;
    }

    @Override
    public void setXlsReaderBeans(Map beans) {

    }

    @Override
    public String checkBeforeImport(Map parameters, Map mapReaded) throws ServiceException {
        return null;
    }

    @Override
    public Map setReturnBeanFromXls(Map beans, InputStream inputXLS) throws ServiceException {
        return null;
    }

    @Override
    public XlsMessage executeSaveCallback(Map parameters, Map beans) throws ServiceException {
        return null;
    }
}
