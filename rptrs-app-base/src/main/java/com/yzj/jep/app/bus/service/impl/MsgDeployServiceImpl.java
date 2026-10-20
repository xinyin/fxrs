/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2021 pactera.com. All rights reserved.
 *
 * Generated at 2021年06月28日 16:09:20
 *
 */

package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.base.utils.Configs;
import com.yzj.jep.app.bus.model.TraEnclosure;
import com.yzj.jep.web.code.WebCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.bus.mapper.MsgDeployMapper;
import com.yzj.jep.app.bus.service.MsgDeployService;
import com.yzj.jep.app.bus.model.MsgDeploy;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class MsgDeployServiceImpl extends MyBatisCRUDService<MsgDeploy, Integer> implements MsgDeployService {

	Logger	logger	= LoggerFactory.getLogger(MsgDeployServiceImpl.class);

	@Autowired
	private MsgDeployMapper	 msgDeployMapper;
	@Autowired
	private Configs configs;

	@Override
	public Mapper<MsgDeploy, Integer> getMapper() {
		return msgDeployMapper;
	}
	//上传文件
	@Override
	public Map<String,Object> fileUpload(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> tips = new HashMap<String, Object>();
		TraEnclosure enclosure = null;
		try {
			CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
			commonsMultipartResolver.setServletContext(request.getSession().getServletContext());
			MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(request);
			List<MultipartFile> multipartFileList= multipartRequest.getFiles("img");

			if (!multipartFileList.isEmpty()) {
				String uuid= UUID.randomUUID().toString();
				String path = configs.getMsgimg();
				for(int i=0;i<multipartFileList.size();i++){
					String fileNameAll = multipartFileList.get(i).getOriginalFilename(); //文件全名
					String fileName = fileNameAll.substring(0, fileNameAll.lastIndexOf("."));  //文件名
					String[] strArray = fileNameAll.split("\\.");
					int suffixIndex = strArray.length - 1;
					String fileType = strArray[suffixIndex] + ""; //文件类型
					double size = multipartFileList.get(i).getSize();
					System.out.println("文件类型为 " + fileType);
					double fileSizeMB = new BigDecimal(size / 1024 / 1024).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); //转换单位为MB
					System.out.println(fileNameAll + "-->" + fileSizeMB + "MB");;
					File destDir=new File(path + "/"+uuid);
					File dest = new File(path + "/"+uuid+"/" + fileNameAll);
					if(!destDir.getParentFile().exists()){
						destDir.getParentFile().mkdir();
					}
					if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
						dest.getParentFile().mkdir();
					}
					multipartFileList.get(i).transferTo(dest); //保存文件
				}
				//存入数据
				enclosure = new TraEnclosure();
				enclosure.setPath(uuid);
				tips.put("fileUrl", enclosure);
				tips.put(WebCode.SUCCESS, true);
				tips.put(WebCode.MSG, "上传成功");

			}
		} catch (Exception e) {
			e.printStackTrace();
			tips.put(WebCode.SUCCESS, false);
			tips.put(WebCode.MSG, "上传失败");
		}
		return tips;

	};
	@Override
	public List<MsgDeploy> selectReleaseMsg(){
		return msgDeployMapper.selectReleaseMsg();
	}

	@Override
	public List<MsgDeploy> selectReleaseNews(){
		return msgDeployMapper.selectReleaseNews();
	}


	
}