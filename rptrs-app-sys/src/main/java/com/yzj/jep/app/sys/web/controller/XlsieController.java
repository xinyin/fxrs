package com.yzj.jep.app.sys.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.sys.code.Type;
import com.yzj.jep.core.exception.MessageException;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.core.utils.XlsMessage;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.core.utils.xlsio.XlsImportCallback;
import com.yzj.jep.core.utils.xlsio.exception.XlsIeException;
import com.yzj.jep.core.utils.xlsio.transformer.XlsTransformer4Merge;
import com.yzj.jep.orm.entity.PO;
import com.yzj.jep.sys.service.AppConfigService;
import com.yzj.jep.web.controller.BaseController;
import net.sf.jxls.reader.*;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 导入导出管理
 *
 * @param <T>
 * @author ke
 * @time 2018年1月31日 下午2:06:47
 * @company 银之杰技术有限公司
 */
@Controller
@RequestMapping("/sys/xlsie")
public class XlsieController<T extends PO> extends BaseController<T> {
    private static Logger logger = LoggerFactory.getLogger(XlsieController.class);
    private static final String contentType = "application/x-msdownload";

    public static final int DEFAULT_UPLOAD_FILE_MAX_SIZE = 20;
    public static final String UPLOAD_SIZE_LIMIT = "UPLOAD_SIZE_LIMIT";
    public static final String READ_STATUS = "readStatus";
    public static final String LST_READED = "lstReaded";
    public static final String POI_READED = "poiReaded";
    public static final String XLSREADER_RETURN_TOKEN = "__OPRISK_XLSREADER_RETURN_TOKEN__";
    public static final String FILE_SUFFIX = "xls,xlsx";

    @Autowired
    private AppConfigService appConfigService;


    /**
     * 简单导出
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/simple")
    public String simple(Model model) {

        return "sys/exportSimpleList";
    }

    @RequestMapping(value = "/import")
    public String showDefaultUploadPage(Model model, String bean) {
        //获取上传文件大小配置项
        long maxSize = DEFAULT_UPLOAD_FILE_MAX_SIZE;
        String limitSize = appConfigService.getConfigValue(UPLOAD_SIZE_LIMIT);
        if (StringUtils.isNotBlank(limitSize)) {
            try {
                maxSize = Long.parseLong(limitSize);
            } catch (NumberFormatException nfe) {
                logger.warn("文件大小超出系统限制,请检查应用配置项[UPLOAD_SIZE_LIMIT]");
            }
        }
        model.addAttribute("bean", bean);
        model.addAttribute("maxSize", maxSize);

        return "sys/xlsImport";
    }

    /**
     * 上传并导入xls文件<br>
     *
     * @param request  : 参数 className,bean
     * @param response
     * @return
     * @throws ServiceException
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST, headers = {"Accept=application/json"})
    public void excuteUpload(HttpServletRequest request, HttpServletResponse response) {
        List<XlsMessage> lstMsg = new ArrayList<XlsMessage>();
        Map<String, Object> tips = new HashMap<String, Object>();
        tips.put("success", false);

        String suffix = "";
        MultipartFile fileXls = null;
        MultipartResolver resolver = new CommonsMultipartResolver();
        //判断 request 是否有文件上传,即多部分请求
        if (resolver.isMultipart(request)) {
            MultipartRequest multipartRequest = resolver.resolveMultipart(request);
            Map<String, MultipartFile> fileMaps = multipartRequest.getFileMap();
            /*只处理一个文件*/
            for (MultipartFile file : fileMaps.values()) {
                suffix= file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
                fileXls = file;
                break;
            }
        }

        //判断当前是否有文件需要处理
        if (null != fileXls && FILE_SUFFIX.contains(suffix.toLowerCase())) {
            try {
                String beanName = request.getParameter("bean");
                if (StringUtils.isNotBlank(beanName)) {
                    Map parameters = new HashMap();
                    Enumeration e = (Enumeration) request.getParameterNames();
                    while (e.hasMoreElements()) {
                        String parameterName = (String) e.nextElement();
                        if (StringUtils.isNotBlank(parameterName)) {
                            parameters.put(parameterName, request.getParameter(parameterName));
                        }
                    }
                    XlsImportCallback iCallback = (XlsImportCallback) SpringContextHolder.getBean(beanName);

                    //读取并解析Excel数据,返回需要的结果集合
                    Map mapReaded = parseXls(fileXls.getInputStream(), beanName);
                    //读取后的结果集
                    XLSReadStatus readStatus = (XLSReadStatus) mapReaded.get(READ_STATUS);
                    //文件内容合法性检验
                    String msgCheck = iCallback.checkBeforeImport(parameters, mapReaded);
                    if (StringUtils.isNotBlank(msgCheck)) {
                        XLSReadMessage msgInvalide = new XLSReadMessage(msgCheck);
                        //readStatus.getReadMessages().add(0,msgInvalide);
                        readStatus.getReadMessages().add(msgInvalide);
                        if (parameters.get("bean").equals("PsnImportCallbackImpl") ||
                                parameters.get("bean").equals("RelOrgImportCallbackImpl") ||
                                parameters.get("bean").equals("ProImportCallbackImpl") ||
                                parameters.get("bean").equals("SusPsnImportCallBackImpl") ||
                                parameters.get("bean").equals("SusOrgImportCallBackImpl")) {
                            readStatus.setStatusOK(true);
                        } else {
                            readStatus.setStatusOK(false);
                        }
                    }

                    if (readStatus.isStatusOK()) {
                        //删除readStatus
                        mapReaded.remove(READ_STATUS);
                        //回到业务方法保存数据
                        try {
                            XlsMessage returnMsg = iCallback.executeSaveCallback(parameters, mapReaded);
                            if (returnMsg.getException() != null)//返回异常，代表导入失败
                            {
                                lstMsg.add(returnMsg);
                                tips.put("success", false);
                            } else//没有异常，代表导入成功
                            {
                                lstMsg.add(returnMsg);
                                tips.put("success", true);
                            }
                        } catch (Exception be) {
                            throw new XlsIeException("保存业务数据错误！");
                        }

                        //保存成功信息返回前台  如果在具体处理类中没有添加导入成功与否的处理，这里添加通用的提示信息
                        if (lstMsg.size() == 0) {
                            XlsMessage msg = new XlsMessage();
                            msg.setMessage("导入成功。");
                            lstMsg.add(msg);
                            tips.put("success", true);
                        }
                    } else {
                        //异常信息返回前台
                        lstMsg.addAll(readStatus.getReadMessages());
                    }
                }
            } catch (MessageException e) {

            } catch (Exception e) {

                logger.error("系统错误:" + e.getMessage());
                //异常信息返回前台
                XlsMessage msg = new XlsMessage();
                msg.setMessage("系统错误");
                msg.setException(e.getMessage());
                lstMsg.add(msg);
            }
        } else {
            XlsMessage msg = new XlsMessage();
            msg.setMessage("文件类型不匹配。");
            lstMsg.add(msg);
        }

        tips.put("msg", lstMsg);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");

        try {
            //将导入日志信息写到前端页面
            JSONObject returnData = JSONObject.parseObject(JSON.toJSONString(tips));
            response.getWriter().write(returnData.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 复杂导出管理
     *
     * @param bean
     * @param id
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "/{bean}/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public void exportXls(@PathVariable String bean, @PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //参数为空 则不处理
        if (StringUtils.isNotBlank(bean) && StringUtils.isNotBlank(id)) {
            //获取bean
            XlsExportCallback eCallback = (XlsExportCallback) SpringContextHolder.getBean(bean);
            try {
                //请求参数上下文
                Map parameters = new HashMap();
                parameters.put("id", id);//添加请求中的路径参数
//                parameters.put("type", Type.ZERO.getLabel());
                Enumeration e = (Enumeration) request.getParameterNames();
                while (e.hasMoreElements()) {
                    String parameterName = (String) e.nextElement();
                    if (StringUtils.isNotBlank(parameterName)) {
                        parameters.put(parameterName, request.getParameter(parameterName));
                    }
                }

                String templateName = eCallback.getExportTemplate();
                String filename = eCallback.getExportFilename(parameters);
                if (StringUtils.isBlank(filename)) {
                    filename = templateName.substring(templateName.lastIndexOf("/") + 1);
                }
                filename = URLEncoder.encode(filename, "utf8");

                //调用业务方法,组装导出XLS报表所需数据
                Map context = eCallback.executeExportCallback(parameters);
                if (context == null) {
                    context = eCallback.executeExportCallback(request);
                }

                response.reset();
                response.setContentType(contentType);
                response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
                ServletOutputStream os = response.getOutputStream();
                XlsTransformer4Merge transformer = new XlsTransformer4Merge();
                transformer.setBeanId(bean);
                transformer.transformXLS(templateName, context, os);

                if (null != context) {
                    context.clear();
                }
                if (null != parameters) {
                    parameters.clear();
                }
                os.flush();
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 复杂导出管理
     *
     * @param bean
     * @param
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "/{bean}", method = {RequestMethod.GET, RequestMethod.POST})
    public void exportXlsSelect(@PathVariable String bean, @RequestParam("selectData") String selectData, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (StringUtils.isNotBlank(bean)) {
            //获取bean
            XlsExportCallback eCallback = (XlsExportCallback) SpringContextHolder.getBean(bean);
            try {
                //请求参数上下文
                Map parameters = new HashMap();
                parameters.put("id", selectData);
                parameters.put("type", Type.ONE.getLabel());
                Enumeration e = (Enumeration) request.getParameterNames();
                while (e.hasMoreElements()) {
                    String parameterName = (String) e.nextElement();
                    if (StringUtils.isNotBlank(parameterName)) {
                        parameters.put(parameterName, request.getParameter(parameterName));
                    }
                }
                String templateName = eCallback.getExportTemplate();
                String filename = eCallback.getExportFilename(parameters);
                if (StringUtils.isBlank(filename)) {
                    filename = templateName.substring(templateName.lastIndexOf("/") + 1);
                }
                filename = URLEncoder.encode(filename, "utf8");

                //调用业务方法,组装导出XLS报表所需数据
                Map context = eCallback.executeExportCallback(parameters);
                if (context == null) {
                    context = eCallback.executeExportCallback(request);
                }

                response.reset();
                response.setContentType(contentType);
                response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
                ServletOutputStream os = response.getOutputStream();
                XlsTransformer4Merge transformer = new XlsTransformer4Merge();
                transformer.setBeanId(bean);
                transformer.transformXLS(templateName, context, os);

                if (null != context) {
                    context.clear();
                }
                if (null != parameters) {
                    parameters.clear();
                }
                os.flush();
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




    private Map parseXls(InputStream inputXLS, String bean) throws ServiceException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream input;
        InputStream input2;
        XlsImportCallback iCallback = (XlsImportCallback) SpringContextHolder.getBean(bean);
        try {

            //byte[] buffer = new byte[1024 * 1024 * 3];
            byte[] buffer = new byte[inputXLS.available()];
            int len;
            while ((len = inputXLS.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }

            baos.flush();

            input = new ByteArrayInputStream(baos.toByteArray());
            input2 = new ByteArrayInputStream(baos.toByteArray());

            List lstRtn = new ArrayList();

            String xmlTemplate = iCallback.getImportXmlTemplate();
            if (StringUtils.isEmpty(xmlTemplate)) {
                throw new XlsIeException("导入Excel的规则配置文件为空!");
            }
            //设置读取时忽略错误
            ReaderConfig.getInstance().setSkipErrors(true);
            InputStream inputXML = new BufferedInputStream(getClass().getResourceAsStream(xmlTemplate));
            XLSReader mainReader = ReaderBuilder.buildFromXML(inputXML);

            //读取excel回调,组装
            Map beans = new HashMap();
            iCallback.setXlsReaderBeans(beans);
            XLSReadStatus readStatus = mainReader.read(input2, beans);
            beans = iCallback.setReturnBeanFromXls(beans, input);
            if (inputXLS != null) {
                inputXLS.close();
                inputXLS = null;
            }
            if (baos != null) {
                baos.close();
                baos = null;
            }
            if (input != null) {
                input.close();
                input = null;
            }
            if (input2 != null) {
                input2.close();
                input2 = null;
            }

            //lstRtn.add(readStatus);
            beans.put(READ_STATUS, readStatus);
            if (!readStatus.isStatusOK()) {
                beans.put(READ_STATUS, readStatus);
                return beans;
            } else {
                return beans;
            }


        } catch (IOException e) {
            throw new ServiceException(e);
        } catch (SAXException e) {
            throw new ServiceException(e);
        } catch (InvalidFormatException e) {
            throw new ServiceException(e);
        }catch (Exception e){
            logger.error(":",e);
            throw new ServiceException(e);
        }
    }


    private String getBasePath() throws Exception {
        String uploadPath = getUploadPath() + File.separator + "import";
        File basedir = new File(uploadPath);
        if (!basedir.exists()) {
            basedir.mkdirs();
        }
        return uploadPath;
    }

    /**
     * 获取上传磁盘路径
     *
     * @return
     * @throws ServiceException
     */
    public String getUploadPath() throws ServiceException {
        return appConfigService.getConfigValue("UPLOADPATH");
    }

    /**
     * 解析Excel的类
     *
     * @param inputStream
     * @param
     * @param
     * @return
     */
    public static List<List<Object>> parseExcel(InputStream inputStream, String fileName) throws IOException {
        List<List<Object>> listall = new ArrayList<List<Object>>();
        //获取文件名的后缀名
        String suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        //1 定义Excel的对象变量
        Workbook workbook;
        //2.判断后缀，决定使用的解析方式
        if (".xls".equals(suffix)) {
            workbook = new HSSFWorkbook(inputStream);
        } else if (".xlsx".equals(suffix)) {
            workbook = new XSSFWorkbook(inputStream);
        } else {
            return null;
        }
        //获取工作表的sheet
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            sheet = workbook.getSheetAt(i);
            if (sheet == null) {
                continue;
            }
            //获取表格最后一行的行号

            int lastRowNum = sheet.getLastRowNum();
            for (int j = 1; j <= lastRowNum; j++) {
                row = sheet.getRow(j);
                if (row == null || row.getFirstCellNum() == j) {
                    continue;
                }
                List<Object> list = new ArrayList<Object>();
                for (int k = row.getFirstCellNum(); k < row.getLastCellNum(); k++) {
                    cell = row.getCell(k);
                    if (cell == null && cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                        continue;
                    }
                    list.add(getCellValue(cell));
                }
                listall.add(list);
            }
        }
        return listall;
    }


    /**
     * 对单元格中的数据类型格式化
     *
     * @param cell
     * @return
     */
    public static Object getCellValue(Cell cell) {
        Object value = null;
        DecimalFormat df = new DecimalFormat("0");//对数字进行整数格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//对日期进行格式化
        DecimalFormat df2 = new DecimalFormat("0.00");//对数字进行保留有效数字

        //判断单元格的类型
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                //字符串类型的单元格
                value = cell.getRichStringCellValue();
                break;
            //空数据
            case Cell.CELL_TYPE_BLANK:
                value = "";
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case Cell.CELL_TYPE_NUMERIC: //数学类型  包含日期，时间，数字
                if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                    value = df.format(cell.getNumericCellValue());
                } else if ("yyyy/mm;@".equals(cell.getCellStyle().getDataFormatString())
                        || "m/d/yy".equals(cell.getCellStyle().getDataFormatString())
                        || "yy/m/d".equals(cell.getCellStyle().getDataFormatString())
                        || "mm/dd/yy".equals(cell.getCellStyle().getDataFormatString())
                        || "dd-mmm-yy".equals(cell.getCellStyle().getDataFormatString())
                        || "yyyy/m/d".equals(cell.getCellStyle().getDataFormatString())
                ) {
                    value = sdf.format(cell.getDateCellValue());
                } else {
                    value = df2.format(cell.getNumericCellValue());
                }
                break;
            default:
                break;
        }
        return value;
    }

    @RequestMapping(value = "/importList")
    public String ImprotList(Model model, String bean) {
        //获取上传文件大小配置项
        long maxSize = DEFAULT_UPLOAD_FILE_MAX_SIZE;
        String limitSize = appConfigService.getConfigValue(UPLOAD_SIZE_LIMIT);
        if (StringUtils.isNotBlank(limitSize)) {
            try {
                maxSize = Long.parseLong(limitSize);
            } catch (NumberFormatException nfe) {
                logger.warn("文件大小超出系统限制,请检查应用配置项[UPLOAD_SIZE_LIMIT]");
            }
        }
        model.addAttribute("bean", bean);
        model.addAttribute("maxSize", maxSize);

        return "sys/xlsImport";
    }


    @RequestMapping(value = "/importBatch")
    public String importBatch(Model model, String bean,String type) {
        //获取上传文件大小配置项
        long maxSize = DEFAULT_UPLOAD_FILE_MAX_SIZE;
        String limitSize = appConfigService.getConfigValue(UPLOAD_SIZE_LIMIT);
        if (StringUtils.isNotBlank(limitSize)) {
            try {
                maxSize = Long.parseLong(limitSize);
            } catch (NumberFormatException nfe) {
                logger.warn("文件大小超出系统限制,请检查应用配置项[UPLOAD_SIZE_LIMIT]");
            }
        }
        model.addAttribute("bean", bean);
        model.addAttribute("type", type);
        model.addAttribute("maxSize", maxSize);

        return "sys/xlsImportBatch";
    }
}
