package com.yzj.jep.app.contract.service;

import com.yzj.jep.app.log.model.LogRecord;
import com.yzj.jep.orm.service.CRUDService;

public interface FileContractService {
    /**
     * 根据获取到的文件进行入库操作
     *
     * @param contractNo
     * @param path
     * @param name
     * @return
     */
    boolean save(String contractNo, String path, String name);

    /**
     * 判断交易是否和附件进行关联
     *
     * @param path
     * @param name
     * @param contractNo
     * @return
     */
    boolean scanFileAttach(String path, String name, String contractNo);

    /**
     * 获取合同文件
     *
     * @param contractNo
     */
    void getContractFiles2Relate(String contractNo);
}
