package com.yzj.test;

import com.yzj.beans.*;
import com.yzj.controller.*;

import java.util.List;

public class test {
    public static void main(String[] args) {

		List<Report_groupSum> l= Report_groupController.select(null, null,null);
		for (Report_groupSum report_groupSum : l) {
			System.out.println(report_groupSum);
		}


    }
}
