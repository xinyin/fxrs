package com.yzj.jep.app.fPaas.server;

import com.fPaaS1.core.IFPaaS1Handler;
import com.fPaaS1.core.Message;
import com.yzj.jep.app.base.utils.Configs;
import com.yzj.jep.app.enums.PerSystemCode;
import com.yzj.jep.app.enums.SPType;
import com.yzj.jep.app.holder.SpringHolder;
import com.yzj.jep.app.pub.model.Publog;
import com.yzj.jep.app.pub.service.PublogService;
import com.yzj.jep.app.pub.web.controller.PublogController;
import com.yzj.jep.app.sub.model.Sublog;
import com.yzj.jep.app.sub.service.SublogService;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.model.Staff;
import com.yzj.jep.sys.service.StaffService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 3.1报送及披露文件信息获取接口
 *
 */
public class GetRelTranSubmitFileInfoHandler implements IFPaaS1Handler<Message> {
    private static final Logger logger = LoggerFactory.getLogger(com.fpaas.handl.GatewayServerHandler.class);
    private SublogService sublogService;
    private PublogService publogService;
    private static Configs configs;
    private StaffService StaffService;



    @Override
    public Message handle(Message message) throws Exception {
        //返回fileType和本系统报送/披露类型得映射
        List<Map<String, Object>> fileList = new ArrayList<>();
        Map<String, String> subType = new HashMap<>();
        Map tips = new HashMap<String, Object>();
        Map map = null;
        Staff staff = null;
        try {
            subType.put(SPType.B_01.getDesc(), SPType.B_01.getCode());
            subType.put(SPType.B_02.getDesc(), SPType.B_02.getCode());
            subType.put(SPType.B_03.getDesc(), SPType.B_03.getCode());
            subType.put(SPType.B_04.getDesc(), SPType.B_04.getCode());
            subType.put(SPType.B_05.getDesc(), SPType.B_05.getCode());
            subType.put(SPType.B_06.getDesc(), SPType.B_06.getCode());
            subType.put(SPType.B_07.getDesc(), SPType.B_07.getCode());
            subType.put(SPType.B_08.getDesc(), SPType.B_08.getCode());
            subType.put(SPType.B_09.getDesc(), SPType.B_09.getCode());
            subType.put(SPType.B_10.getDesc(), SPType.B_10.getCode());
            subType.put(SPType.B_11.getDesc(), SPType.B_11.getCode());
            subType.put(SPType.B_12.getDesc(), SPType.B_12.getCode());
            Map<String, String> pubType = new HashMap<>();
            pubType.put(SPType.P_01.getDesc(), SPType.P_01.getCode());
            pubType.put(SPType.P_02.getDesc(), SPType.P_02.getCode());
            pubType.put(SPType.P_03.getDesc(), SPType.P_03.getCode());
            pubType.put(SPType.P_04.getDesc(), SPType.P_04.getCode());

            map = (Map) message.getTarget().getBodys();

            sublogService = SpringHolder.getBean(SublogService.class);
            publogService = SpringHolder.getBean(PublogService.class);
            configs = SpringHolder.getBean(Configs.class);
            StaffService = SpringHolder.getBean(StaffService.class);

            String filePath = configs.getFilePathDown();
            // Map map= (Map) message.getTarget().getBodys();
            //设置查询条件 未报送得所有数据
            List<Filter> filters = new Filter.Builder().eq("status", "01").build();
            List<Filter> filters1 = new Filter.Builder().eq("status","0").build();
            filters1.add(new Filter("delflag", "1"));
            filters.add(new Filter("delflag", "1"));
            List<Sublog> sublogs = sublogService.select(filters);
            List<Publog> publogs = publogService.select(filters1);
            if ((null == sublogs || sublogs.size() <= 0) && (null == publogs || publogs.size() <= 0)) {
                //没有需要OA审批的披露或者报送文件
                tips.put("resultCode", PerSystemCode.EX_1002.getCode());
                tips.put("msg", PerSystemCode.EX_1002.getMsg());
            } else {
                //有需要OA审批的披露或者报送文件
                //创建临时目录
                File temporary = new File("D:" + File.separator + "home" + File.separator + "rptrs" + File.separator + "temporary" + File.separator + "subPub");
                if (!temporary.exists() && !temporary.isDirectory()) {
                    temporary.mkdirs();
                }
                int serialNo = 1;
                for (Sublog sub : sublogs) {
                    Map<String, Object> file = new HashMap();
                    file.put("serialNo", serialNo + "");
                    file.put("fileName", sub.getFileName());
                    file.put("fileType", subType.get(sub.getSubType()));
                    file.put("filePid", sub.getPid());
                    file.put("fileCreatUser", sub.getOperatorName());
                    Date date = sub.getMaxPercentInNetAssets();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    String craetTime = sdf.format(date);
                    file.put("fileCreatTime", craetTime);
                    fileList.add(file);
                    /*File subxml = new File(filePath + "\\" + sub.getFileName() + ".xml");
                    File tusubxxml = new File(temporary, sub.getFileName() + ".xml");
                    if (subxml.exists()) {
                        FileCopyUtils.copy(subxml, tusubxxml);
                    }
                    serialNo++;
                    if (serialNo == 3) {
                        break;
                    }*/

                }

                for (Publog publog : publogs) {
                    Map<String, Object> file = new HashMap();
                    file.put("serialNo", serialNo + "");
                    file.put("fileName", publog.getPubFileName());
                    file.put("fileType", "P_01");
                    file.put("filePid", publog.getPid());
                    String createByName = publog.getCreateByName();
                    try {
                        if (staff != null) {
                            createByName = staff.getStaffName();
                        } else {
                            List<Filter> filters_1 = new Filter.Builder().eq("staffCode", createByName).build();
                            List<Staff> staffList = StaffService.select(filters_1);
                            if (staffList != null && staffList.size() > 0) {
                                staff = staffList.get(0);
                                createByName = staff.getStaffName();
                            }
                        }
                    } catch (Exception e) {
                    }
                    file.put("fileCreatUser", createByName);
                    Date date = publog.getMaxPercentInNetAssets();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    String craetTime = sdf.format(date);
                    file.put("fileCreatTime", craetTime);
                    fileList.add(file);
                    //File subxml = new File(filePath + "\\" + publog.getPubFileName() + ".doc");
                    //File tusubxxml = new File(temporary, publog.getPubFileName() + ".doc");
                    /*if (subxml.exists()) {
                        FileCopyUtils.copy(subxml, tusubxxml);
                    }*/
                    serialNo++;
                    if (serialNo == 3) {
                        break;
                    }

                }
            }

            tips.put("resultCode", PerSystemCode.SUCCESS.getCode());
            tips.put("msg", PerSystemCode.SUCCESS.getMsg());
            //String json = JSON.toJSONString(fileList);
//        writeFile(json,"D:"+File.separator+"home"+File.separator+"rptrs"+File.separator+"temporary"+File.separator+"subPub"+File.separator+"fileList.txt");
//        //File[] files = {new File("D:"+File.separator+"home"+File.separator+"rptrs"+File.separator+"temporary"+File.separator+"subPub"+File.separator+"fileList.txt")};
//        File[] flist = temporary.listFiles();
//        ZipUtil.zip("D:"+File.separator+"home"+File.separator+"rptrs"+File.separator+"temporary","fileList.zip",flist);

        }catch (Exception e){
            tips.put("resultCode", PerSystemCode.EX_9999.getCode());
            tips.put("msg", PerSystemCode.EX_9999.getMsg());
            logger.error("OA系统访问披露/报送文件信息接口<getRelTranSubmitFileInfo>异常::"+e);
        }finally {
            tips.put("CHNL_ID", map.get("chnlId"));
            tips.put("TRANS_CODE", "getRelTranSubmitFileInfo");
            if ("0000".equals(tips.get("resultCode"))) {
                tips.put("fileList", fileList);
            }
            message.getTarget().setBody(tips);
        }
        return message;
    }

    /**
     * 给本地创建文件夹
     *
     * @param json
     * @param FilePath
     */
    public void writeFile(String json, String FilePath) {

        try {
            File file = new File(FilePath);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            } else {
                file.delete();
                file.createNewFile();
            }

            // true = append file
            FileWriter fileWritter = new FileWriter(file, false);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.write(json);
            bufferWritter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
