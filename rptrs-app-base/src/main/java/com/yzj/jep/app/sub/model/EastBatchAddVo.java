package com.yzj.jep.app.sub.model;

import javax.persistence.Entity;
import java.util.LinkedList;
import java.util.List;

@Entity
public class EastBatchAddVo {

	/**
	 * 主键
	 */
	private Integer id;

	/**
	 * 文件名称
	 */
	private String fileName;

	public EastBatchAddVo() {
	}

	public EastBatchAddVo(Integer id, String fileName) {
		this.id = id;
		this.fileName = fileName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public static List<EastBatchAddVo> eastBatchAddVoList(){
		List<EastBatchAddVo> list = new LinkedList<>();
		list.add(new EastBatchAddVo(1, "关联方信息表"));
		list.add(new EastBatchAddVo(2, "金融产品信息表"));
		list.add(new EastBatchAddVo(3, "关联关系表"));
		list.add(new EastBatchAddVo(4, "资金运用类关联交易协议信息表"));
		list.add(new EastBatchAddVo(5, "服务类关联交易协议信息表"));
		list.add(new EastBatchAddVo(6, "利益转移类关联交易协议信息表"));
		list.add(new EastBatchAddVo(7, "保险业务和其他类关联交易协议信息表"));
		list.add(new EastBatchAddVo(8, "关联交易明细表"));
		list.add(new EastBatchAddVo(9, "资金运用关联交易统计表"));
		list.add(new EastBatchAddVo(10, "与单一关联方资金运用关联交易统计表"));
		return list;
	}
}

