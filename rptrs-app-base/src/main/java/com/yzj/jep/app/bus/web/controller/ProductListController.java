package com.yzj.jep.app.bus.web.controller;

import com.alibaba.fastjson.JSON;
import com.yzj.jep.app.base.constant.Log;
import com.yzj.jep.app.bus.model.Product;
import com.yzj.jep.app.bus.service.ProductService;
import com.yzj.jep.app.util.MenuTree;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.entity.BasicNameValuePair;
import com.yzj.jep.web.entity.NameValuePair;
import com.yzj.jep.web.util.NVUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/bus/product")
public class ProductListController extends CrudController<Product> {

    Logger logger = LoggerFactory.getLogger(ProductListController.class);

    @Autowired
    private ProductService productService;


    /**
     * 获取产品树页面
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/list")
    @Log(module = "产品管理", description = "获取产品树页面")
    public String list(Model model, HttpServletRequest request) {
        return "bus/product";
    }


    /**
     * 树形展示
     *
     * @return
     */
    @RequestMapping(value = "/treeNode", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @Log(module = "产品管理", description = "树形展示")
    public String treeNode() {
//        List<Product> products = productService.selectAll();
        List<Filter> filters = new Filter.Builder().eq("delflag", "1").build();
        List<Product> products = productService.select(filters);
        MenuTree menuTree = new MenuTree();
        List<Object> objects = menuTree.menuList(products);
        String jsonString = JSON.toJSONString(objects);
        System.out.println(jsonString);
        return jsonString;
    }

    /**
     * 單擊查询产品信息
     *
     * @param proName
     * @return
     */
    @RequestMapping(value = "/queryProductByProName", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @Log(module = "产品管理", description = "單擊查询产品信息")
    public String queryProductByProName(@RequestParam("proNamePuzzy") String proName) {
        System.out.println(proName);
        List<Filter> filters = new Filter.Builder().like("proName", proName).build();
        List<Product> list = productService.select(filters);

        Product product = list.get(0);
        String jsonString = JSON.toJSONString(product);
        System.out.println(jsonString);
        return jsonString;

    }


    /**
     * 点击查询节点信息
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/queryProductByPid", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @Log(module = "产品管理", description = "点击查询节点信息")
    public String query(@RequestParam("productPid") String pid) {
        int i = Integer.parseInt(pid);
        Product product = productService.get(i);
        if (product.getPaProId() != 0) {
            product.setPaProName(productService.get(product.getPaProId()).getProName());
        }
        String jsonString = JSON.toJSONString(product);
        return jsonString;
    }

    /**
     * 编辑节点.
     */
    @RequestMapping(value = "/editNode")
    @ResponseBody
    @Log(module = "产品管理", description = "编辑节点")
    public Map<String, Object> editNode(@RequestParam("pid") String pid,
                                        @RequestParam("proName") String proName,
                                        @RequestParam("status") String status,
                                        @RequestParam("isPro") String isPro,
                                        @RequestParam("maxAmount") String maxAmount,
                                        @RequestParam("minAmount") String minAmount,
                                        @RequestParam("description") String description) {
        Map<String, Object> tips = new HashMap<String, Object>();
        Integer id = Integer.parseInt(pid);
        Product product1 = productService.get(id);
        product1.setProName(proName);
        product1.setMaxAmount(maxAmount);
        product1.setMinAmount(minAmount);
        product1.setStatus(status);
        product1.setIsPro(isPro);
        product1.setDescription(description);
        //在此写入前端接收的要修改的字段并修改
        int update = productService.update(product1);
        if (update > 0) {
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
            return tips;
        } else {
            tips.put(WebCode.FAILED, true);
            tips.put(WebCode.MSG, WebCode.FAULURE_RENAME);
            return tips;
        }

    }


    /**
     * 增加同级节点.
     *
     * @param id      同级id
     * @param proName 设置的同级节点名称
     * @return
     */

    @RequestMapping(value = "/addVisNode")
    @ResponseBody
    @Log(module = "产品管理", description = "增加同级节点")
    public Map<String, Object> addVisNode(@RequestParam("pid") String id,
                                          @RequestParam("proName") String proName,
                                          @RequestParam("maxAmount") String maxAmount,
                                          @RequestParam("minAmount") String minAmount,
                                          @RequestParam("status") String status,
                                          @RequestParam("isPro") String isPro,
                                          @RequestParam("description") String description) {
        Map<String, Object> tips = new HashMap<String, Object>();
        Integer pid = Integer.parseInt(id);
        Product product1 = productService.get(pid);
        //在同级下获取本级父级id
        Integer paProId = product1.getPaProId();
        //在同级下获取本级父级节点名
        String paProName = product1.getPaProName();
        //在同级下获取本级业务级别
        String busLevel = product1.getBusLevel();

        //uuid生成产品或目录唯一标识的编号
        UUID uuid = UUID.randomUUID();
        String replaceAll = uuid.toString().replaceAll("-", "");

        //将父级id保存到新增同级对象下
        Product product = new Product();
        product.setDelflag("1");
        product.setBusLevel(busLevel);
        product.setPaProId(paProId);
        product.setPaProName(paProName);
        product.setProName(proName);
        product.setMinAmount(minAmount);
        product.setMaxAmount(maxAmount);
        product.setStatus(status);
        product.setIsPro(isPro);
        product.setDescription(description);
        product.setProId(replaceAll);
        int insert = productService.insert(product);
        if (insert > 0) {
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
            return tips;
        } else {
            tips.put(WebCode.FAILED, true);
            tips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
            return tips;
        }

    }

    /**
     * 增加下一节点.
     *
     * @param curPid  要新增节点的父id，即点击新增下一节点的当时id
     * @param proName 获取的节点名，
     * @return
     */
    @RequestMapping(value = "/addNextNode")
    @ResponseBody
    @Log(module = "产品管理", description = "增加下一节点")
    public Map<String, Object> addNextNode(@RequestParam("pid") String curPid,
                                           @RequestParam("proName") String proName,
                                           @RequestParam("maxAmount") String maxAmount,
                                           @RequestParam("minAmount") String minAmount,
                                           @RequestParam("status") String status,
                                           @RequestParam("isPro") String isPro,
                                           @RequestParam("description") String description) {
        Map<String, Object> tips = new HashMap<String, Object>();
        int id = Integer.parseInt(curPid);
        Product product1 = productService.get(id);
        //获取当前id对应的节点名设置为新增的父节点名
        String paproName1 = product1.getProName();
        //获取当前节点级别+1为本级节点的级别
        String busLevel = product1.getBusLevel();
        int nextBusLevel = Integer.parseInt(busLevel) + 1;
        Product product = new Product();

        UUID uuid = UUID.randomUUID();
        String replaceAll = uuid.toString().replaceAll("-", "");
//        product.setBusLevel("0" + (Integer.valueOf(product1.getBusLevel()) + 1));
        //获取前端的pid为要新增目录的父级id
        product.setPaProId(id);
        //父目录为前端页面获取id的的目录名
        product.setDelflag("1");
        product.setPaProName(paproName1);
        product.setProId(replaceAll);
        product.setProName(proName);
        product.setMaxAmount(maxAmount);
        product.setMinAmount(minAmount);
        product.setStatus(status);
        product.setIsPro(isPro);
        product.setBusLevel(nextBusLevel + "");
        product.setDescription(description);

        int insert = productService.insert(product);
        if (insert > 0) {
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
            return tips;
        } else {
            tips.put(WebCode.FAILED, true);
            tips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
            return tips;
        }

    }

    /**
     * 移除节点
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/removeNode")
    @ResponseBody
    @Log(module = "产品管理", description = "移除节点")
    public Map<String, Object> removeNode(@RequestParam("productPid") String pid) {
        Map<String, Object> tips = new HashMap<String, Object>();
        int id = Integer.parseInt(pid);
        Product product = productService.get(id);
        product.setDelflag("0");
        int update = productService.update(product);
        if (update > 0) {
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
            return tips;
        } else {
            tips.put(WebCode.FAILED, true);
            tips.put(WebCode.MSG, WebCode.FAULURE_RENAME);
            return tips;
        }
    }

    /**
     * 根据产品名称获取产品信息
     */
    @RequestMapping(value = "/proInfo")
    @ResponseBody
    @Log(module = "产品管理", description = "根据产品名称获取产品信息")
    public Map<String, Object> getProductInfo(String productName) {
        Map<String, Object> tips = productService.getProductInfo(productName);
        return tips;
    }

    /**
     * 显示业务级别名称
     *
     * @return
     */
    @RequestMapping(value = "/seletT")
    @ResponseBody
    @Log(module = "产品管理", description = "显示业务级别名称")
    public List<NameValuePair> seletT() {

        List<Filter> filters = new Filter.Builder().eq("isActive", 1).build();
        List<Product> products = productService.select(filters);
        List<NameValuePair> nameValuePairs = NVUtils.convert(products, products.size(), "pid", "proName");
        nameValuePairs.add(0, new BasicNameValuePair("N/A", ""));
        return nameValuePairs;

    }


}

