package com.yzj.jep.app.contract.service.Impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.base.utils.Configs;
import com.yzj.jep.app.contract.model.ApiRequest;
import com.yzj.jep.app.contract.service.FileContractService;
import com.yzj.jep.app.contract.util.ContractUtil;
import com.yzj.jep.app.contract.util.HttpClientUtil;
import com.yzj.jep.app.ins.model.*;
import com.yzj.jep.app.ins.service.*;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.BatchNumberUtil;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.service.AppConfigService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author LONG
 * @date 2020/11/12
 * @description
 */

@Service
public class FileContractServiceImpl implements FileContractService {

    private static final Logger logger = LoggerFactory.getLogger(FileContractServiceImpl.class);

    @Autowired
    private TraFundsService traFundsService;
    @Autowired
    private TraServiceService traServiceService;
    @Autowired
    private TraInterestService traInterestService;
    @Autowired
    private TraBusinessService traBusinessService;
    @Autowired
    private TraInvestmentService traInvestmentService;
    @Autowired
    private TraInsenclosureService traInsenclosureService;
    @Autowired
    private TraOtherService traOtherService;
    @Autowired
    private Configs config;
    @Autowired
    private AppConfigService appConfigService;

    /**
     * 根据获取到的文件进行入库操作
     *
     * @param contractNo
     * @param path
     * @param name
     */
    @Override
    public boolean save(String contractNo, String path, String name) {
        try {
            List<Filter> filters = new Filter.Builder().
                    eq("delflag", "1").
                    eq("contractNo", contractNo).build();
            //获取关联交易数据
            List<TraFunds> traFunds = traFundsService.select(filters);
            List<TraOther> traOthers = traOtherService.select(filters);
            List<TraService> traServices = traServiceService.select(filters);
            List<TraInterest> traInterests = traInterestService.select(filters);
            List<TraBusiness> traBusinesses = traBusinessService.select(filters);
            List<TraInvestment> trainvestments = traInvestmentService.select(filters);

            Map<String, JSONObject> describeFile = getDescribeFile(path, name);

            File directory = new File(path + File.separator + name);
            File[] files = directory.listFiles();
            for (File file : files) {
                String fileName = file.getName();
                if (fileName.endsWith(".pdf")) {
                    //更新合同和附件的关联关系
                    updateAttachRelation(traFunds, traOthers, traServices, traInterests,
                            traBusinesses, trainvestments, file, describeFile);
                }
            }
            return true;
        } catch (ServiceException e) {
            logger.error("exception to library operation based on the obtained file:{}", e);
        }
        return false;
    }

    /**
     * 更新交易和附件的关联关系
     *
     * @param traFunds
     * @param traOthers
     * @param traServices
     * @param traInterests
     * @param traBusinesses
     * @param trainvestments
     * @param file
     */
    private void updateAttachRelation(List<TraFunds> traFunds, List<TraOther> traOthers, List<TraService> traServices,
                                      List<TraInterest> traInterests, List<TraBusiness> traBusinesses,
                                      List<TraInvestment> trainvestments, File file,
                                      Map<String, JSONObject> describeFile) {
        try {
            //投资入股类
            if (trainvestments != null && trainvestments.size() > 0) {
                for (TraInvestment trainvestment : trainvestments) {
                    String transNumber = trainvestment.getTransNum();
                    TraInsenclosure traInsenclosure = getFullTraEnclosure(file);
                    traInsenclosure.setTransNum(transNumber);
                    traInsenclosureService.insertSelective(traInsenclosure);
                }
            }
            //资金运用类
            if (traFunds != null && traFunds.size() > 0) {
                for (TraFunds traFund : traFunds) {
                    String transNumber = traFund.getTransNum();
                    TraInsenclosure traInsenclosure = getFullTraEnclosure(file);
                    traInsenclosure.setTransNum(transNumber);
                    traInsenclosureService.insertSelective(traInsenclosure);
                }
            }
            //保险业务类
            if (traBusinesses != null && traBusinesses.size() > 0) {
                for (TraBusiness traBusiness : traBusinesses) {
                    String transNumber = traBusiness.getTransNum();
                    TraInsenclosure traInsenclosure = getFullTraEnclosure(file);
                    traInsenclosure.setTransNum(transNumber);
                    traInsenclosureService.insertSelective(traInsenclosure);
                }
            }
            //利益转移类
            if (traInterests != null && traInterests.size() > 0) {
                for (TraInterest traInterest : traInterests) {
                    String transNumber = traInterest.getTransNum();
                    TraInsenclosure traInsenclosure = getFullTraEnclosure(file);
                    traInsenclosure.setTransNum(transNumber);
                    traInsenclosureService.insertSelective(traInsenclosure);
                }
            }
            //提供服务类
            if (traServices != null && traServices.size() > 0) {
                for (TraService traService : traServices) {
                    String transNumber = traService.getTransNum();
                    TraInsenclosure traInsenclosure = getFullTraEnclosure(file);
                    traInsenclosure.setTransNum(transNumber);
                    traInsenclosureService.insertSelective(traInsenclosure);
                }
            }
            //其他类
            if (traOthers != null && traOthers.size() > 0) {
                for (TraOther traOther : traOthers) {
                    String transNumber = traOther.getTransNum();
                    TraInsenclosure traInsenclosure = getFullTraEnclosure(file);
                    traInsenclosure.setTransNum(transNumber);
                    traInsenclosureService.insertSelective(traInsenclosure);
                }
            }
        } catch (ServiceException e) {
            logger.error("update the association between the transaction and the attachment:{}", e);
        }
    }

    /**
     * 补全TraInsenclosure附件信息
     *
     * @param file
     * @return
     */
    private TraInsenclosure getFullTraEnclosure(File file) {
        TraInsenclosure enclosure = null;
        try {
            String name = file.getName();
            String absolutePath = file.getAbsolutePath();
            String fileType = name.substring(name.indexOf(".") + 1);
            String relatedName = name.substring(0, name.indexOf("."));
            long size = FileUtils.sizeOf(file);
            double fileSizeMB = new BigDecimal(size / 1024 / 1024).
                    setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); //转换单位为MB
            //把获取到的文件信息写入到关联交易附件表
            enclosure = new TraInsenclosure();
            //确保唯一
            enclosure.setEnclosurePid(BatchNumberUtil.makeBatchNumber());
            enclosure.setPath(absolutePath);
            enclosure.setRelatedName(relatedName);
            enclosure.setFileType(fileType);
            enclosure.setDelflag("1");
            enclosure.setFileSize(String.valueOf(fileSizeMB));
            enclosure.setCreateTime(new Date());
            enclosure.setUploadTime(DateUtils.formatDate(new Date()));
            UserProfile userProfile = UserProfileUtils.getUserProfile();
            if (userProfile != null) {
                enclosure.setUploadDept(userProfile.getDeptName());
                enclosure.setUploadOrg(userProfile.getUnitCode());
            }
            enclosure.setCreateTimeString(String.valueOf(new Date()));
        } catch (Exception e) {
            logger.error("exception of information in trainsenclosure is completed:{}", e);
        }
        return enclosure;
    }

    /**
     * 判断合同是否和附件进行关联
     *
     * @param path
     * @param name
     * @param contractNo
     */
    @Override
    public boolean scanFileAttach(String path, String name, String contractNo) {
        boolean isNeedDel = true;
        try {
            List<Filter> filters = new Filter.Builder().
                    eq("delflag", "1").
                    eq("contractNo", contractNo).build();
            //获取关联交易数据
            List<TraFunds> traFunds = traFundsService.select(filters);
            List<TraOther> traOthers = traOtherService.select(filters);
            List<TraService> traServices = traServiceService.select(filters);
            List<TraInterest> traInterests = traInterestService.select(filters);
            List<TraBusiness> traBusinesses = traBusinessService.select(filters);
            List<TraInvestment> trainvestments = traInvestmentService.select(filters);

            //处理描述文件
            Map<String, JSONObject> describeFile = getDescribeFile(path, name);

            File directory = new File(path + File.separator + name);
            File[] files = directory.listFiles();

            for (File file : files) {
                String fileName = file.getName();
                if (fileName.endsWith(".pdf")) {
                    if (traFunds != null && traFunds.size() > 0) {
                        boolean attached = isFundsNoAttached(traFunds, file);
                        if (attached) {
                            updateAttachRelation(traFunds, null, null,
                                    null, null, null,
                                    file, describeFile);
                            isNeedDel = false;
                        }
                    }
                    if (traOthers != null && traOthers.size() > 0) {
                        boolean attached = isOtherNoAttached(traOthers, file);
                        if (attached) {
                            updateAttachRelation(null, traOthers, null,
                                    null, null, null,
                                    file, describeFile);
                            isNeedDel = false;
                        }
                    }
                    if (traServices != null && traServices.size() > 0) {
                        boolean attached = isServiceNoAttached(traServices, file);
                        if (attached) {
                            updateAttachRelation(null, null, traServices,
                                    null, null, null,
                                    file, describeFile);
                            isNeedDel = false;
                        }

                    }
                    if (traInterests != null && traInterests.size() > 0) {
                        boolean attached = isInterestsNoAttached(traInterests, file);
                        if (attached) {
                            updateAttachRelation(null, null, null,
                                    traInterests, null, null
                                    , file, describeFile);
                            isNeedDel = false;
                        }
                    }
                    if (traBusinesses != null && traBusinesses.size() > 0) {
                        boolean attached = isBusinessNoAttached(traBusinesses, file);
                        if (attached) {
                            updateAttachRelation(null, null, null,
                                    null, traBusinesses, null,
                                    file, describeFile);
                            isNeedDel = false;
                        }
                    }
                    if (trainvestments != null && trainvestments.size() > 0) {
                        boolean attached = isInvestmentNoAttached(trainvestments, file);
                        if (attached) {
                            updateAttachRelation(null, null, null,
                                    null, null, trainvestments,
                                    file, describeFile);
                            isNeedDel = false;
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("determine if the contract is abnormal from the attachment:{}", e);
        }
        return isNeedDel;
    }


    private boolean isInvestmentNoAttached(List<TraInvestment> trainvestments, File file) {
        for (TraInvestment traInvestment : trainvestments) {
            String transNum = traInvestment.getTransNum();
            String name = file.getName();
            String fileType = name.substring(name.indexOf(".") + 1);
            String relatedName = name.substring(0, name.indexOf("."));
            List<Filter> filers = new Filter.Builder().eq("fileType", fileType).
                    eq("transNum", transNum).
                    eq("delflag", "1").
                    eq("relatedName", relatedName).build();
            boolean exists = traInsenclosureService.exists(filers);
            if (!exists) {
                return true;
            }
        }
        return false;
    }

    private boolean isBusinessNoAttached(List<TraBusiness> traBusinesses, File file) {
        for (TraBusiness traBusiness : traBusinesses) {
            String transNum = traBusiness.getTransNum();
            String name = file.getName();
            String fileType = name.substring(name.indexOf(".") + 1);
            String relatedName = name.substring(0, name.indexOf("."));
            List<Filter> filers = new Filter.Builder().eq("fileType", fileType).
                    eq("transNum", transNum).
                    eq("delflag", "1").
                    eq("relatedName", relatedName).build();
            boolean exists = traInsenclosureService.exists(filers);
            if (!exists) {
                return true;
            }
        }
        return false;
    }

    private boolean isInterestsNoAttached(List<TraInterest> traInterests, File file) {
        for (TraInterest traInterest : traInterests) {
            String transNum = traInterest.getTransNum();
            String name = file.getName();
            String fileType = name.substring(name.indexOf(".") + 1);
            String relatedName = name.substring(0, name.indexOf("."));
            List<Filter> filers = new Filter.Builder().eq("fileType", fileType).
                    eq("transNum", transNum).
                    eq("delflag", "1").
                    eq("relatedName", relatedName).build();
            boolean exists = traInsenclosureService.exists(filers);
            if (!exists) {
                return true;
            }
        }
        return false;
    }

    private boolean isServiceNoAttached(List<TraService> traServices, File file) {
        for (TraService traService : traServices) {
            String transNum = traService.getTransNum();
            String name = file.getName();
            String fileType = name.substring(name.indexOf(".") + 1);
            String relatedName = name.substring(0, name.indexOf("."));
            List<Filter> filers = new Filter.Builder().eq("fileType", fileType).
                    eq("transNum", transNum).
                    eq("delflag", "1").
                    eq("relatedName", relatedName).build();
            boolean exists = traInsenclosureService.exists(filers);
            if (!exists) {
                return true;
            }
        }
        return false;
    }

    private boolean isOtherNoAttached(List<TraOther> traOthers, File file) {
        for (TraOther traOther : traOthers) {
            String transNum = traOther.getTransNum();
            String name = file.getName();
            String fileType = name.substring(name.indexOf(".") + 1);
            String relatedName = name.substring(0, name.indexOf("."));
            List<Filter> filers = new Filter.Builder().eq("fileType", fileType).
                    eq("transNum", transNum).
                    eq("delflag", "1").
                    eq("relatedName", relatedName).build();
            boolean exists = traInsenclosureService.exists(filers);
            if (!exists) {
                return true;
            }
        }
        return false;
    }

    private boolean isFundsNoAttached(List<TraFunds> traFunds, File file) {
        for (TraFunds traFund : traFunds) {
            String transNum = traFund.getTransNum();
            String name = file.getName();
            String fileType = name.substring(name.indexOf(".") + 1);
            String relatedName = name.substring(0, name.indexOf("."));
            List<Filter> filers = new Filter.Builder().eq("fileType", fileType).
                    eq("transNum", transNum).
                    eq("delflag", "1").
                    eq("relatedName", relatedName).build();
            boolean exists = traInsenclosureService.exists(filers);
            if (!exists) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取描述文件信息
     *
     * @param path
     * @param name
     * @return
     */
    private static Map<String, JSONObject> getDescribeFile(String path, String name) {
        Map<String, JSONObject> map = new HashMap<>();
        FileInputStream fileInputStream = null;
        String zipPath = path + File.separator + name + File.separator + "json.txt";
        try {
            fileInputStream = new FileInputStream(new File(zipPath));
            List<String> strings = IOUtils.readLines(fileInputStream, Charset.forName("UTF-8"));
            StringBuffer stringBuffer = new StringBuffer();
            for (String line : strings) {
                stringBuffer.append(line.trim());
            }
            JSONObject jsonObject = JSONObject.parseObject(stringBuffer.toString());
            JSONArray fileList = jsonObject.getJSONArray("fileList");
            for (int i = 0; i < fileList.size(); i++) {
                JSONObject obj = fileList.getJSONObject(i);
                map.put(obj.getString("fileName"), obj);
            }
            return map;
        } catch (Exception e) {
            logger.error("failed to get description file information:{}", e);
        } finally {
            IOUtils.closeQuietly(fileInputStream);
            ContractUtil.delete(new File(zipPath));//删除描述文件
        }
        return null;
    }

    /**
     * 根据合同号获取文件
     *
     * @param contractNo
     */
    @Override
    public void getContractFiles2Relate(String contractNo) {
        String directory = null;
        logger.info("获取接口合同文件:{}", contractNo);
        try {
            String appId = appConfigService.getConfigValue("channel_app_id");
            String requestUrl = appConfigService.getConfigValue("channel_Contract_Files");
            String secretkey = appConfigService.getConfigValue("channel_contract_secretkey");

            String nonce = ContractUtil.getUUID();
            long timestamp = ContractUtil.getTimeStamp();
            //请求参数类
            ApiRequest request = new ApiRequest();
            //构建业务请求数据
            JSONObject busMsg = new JSONObject();
            busMsg.put("contractNo", contractNo);
            request.setAppId(appId);
            request.setNonce(nonce);
            request.setData(busMsg);
            request.setTimestamp(timestamp);
            //获取签名数据
            String sign = ContractUtil.getSign(secretkey, nonce, JSON.toJSONString(busMsg));
            request.setSig(sign);
            //获取返回信息
            logger.info("调用接口请求信息:{}", JSON.toJSONString(request));
            Map<String, String> map = HttpClientUtil.httpPostByte(JSON.toJSONString(request), requestUrl, config.getTemp());
            logger.info("调用接口获取返回信息:{}", map);
            if ("SUCCESS".equals(map.get("code"))) {
                String path = map.get("path");
                String name = map.get("name");
                ContractUtil.unZipFile(path, name);
                //根据抓取到的文件更新数据库
                save(contractNo, path, name);
                //zip文件路径
                directory = path + File.separator + name + ".zip";
            }
        } catch (Exception e) {
            logger.info("failed to call the signed contract information list interface " +
                    "to get the relevant information:{}", e);
        } finally {
            //删除zip文件
            if (directory != null) {
                File dirFile = new File(directory);
                if (dirFile.exists()) {
                    ContractUtil.delete(dirFile);
                }
            }
        }
    }

}