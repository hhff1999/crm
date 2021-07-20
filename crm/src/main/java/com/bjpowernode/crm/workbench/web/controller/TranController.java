package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.DicValueService;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Customer;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.domain.TranHistory;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.CustomerService;
import com.bjpowernode.crm.workbench.service.TranHistoryService;
import com.bjpowernode.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class TranController {
    @Autowired
    private UserService userService;
    @Autowired
    private DicValueService dicValueService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private TranService tranService;
    @Autowired
    private TranHistoryService tranHistoryService;

    @RequestMapping("workbench/transaction/typeahead.do")
    public @ResponseBody Object typeahead(String customerName) {
        List<Customer> customerList = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId("001");
        customer.setName("动力节点");
        customerList.add(customer);
        customer = new Customer();
        customer.setId("002");
        customer.setName("字节电动");
        customerList.add(customer);
        customer = new Customer();
        customer.setId("003");
        customer.setName("华为集团");
        customerList.add(customer);
        customer = new Customer();
        customer.setId("004");
        customer.setName("中华大国");
        customerList.add(customer);
        return customerList;
    }

    @RequestMapping("/workbench/transaction/index.do")
    public String index(Model model) {
        List<DicValue> stageList = dicValueService.queryDicValueByTypeCode("stage");
        //来源
        List<DicValue> sourceList = dicValueService.queryDicValueByTypeCode("source");
        //类型
        List<DicValue> transactionTypeList = dicValueService.queryDicValueByTypeCode("transactionType");
        model.addAttribute("stageList", stageList);
        model.addAttribute("sourceList", sourceList);
        model.addAttribute("transactionTypeList", transactionTypeList);
        return "/workbench/transaction/index";
    }

    @RequestMapping("/workbench/transaction/createTran.do")
    public String createTran(Model model) {
        List<User> userList = userService.queryAllUsers();
        //阶段参数
        List<DicValue> stageList = dicValueService.queryDicValueByTypeCode("stage");
        //来源
        List<DicValue> sourceList = dicValueService.queryDicValueByTypeCode("source");
        //类型
        List<DicValue> transactionTypeList = dicValueService.queryDicValueByTypeCode("transactionType");
        model.addAttribute("userList", userList);
        model.addAttribute("stageList", stageList);
        model.addAttribute("sourceList", sourceList);
        model.addAttribute("transactionTypeList", transactionTypeList);
        return "/workbench/transaction/save";
    }

    @RequestMapping("/workbench/transaction/queryCustomerByName.do")
    public @ResponseBody Object queryCustomerByName(String customerName) {
        List<Customer> customerList = customerService.queryCustomerByName(customerName);
        return customerList;
    }

    @RequestMapping("/workbench/transaction/getPossibilityByStageValue.do")
    public @ResponseBody Object getPossibilityByStageValue(String stageValue) {
        //绑定资源文件，或许对应值
        ResourceBundle resourceBundle = ResourceBundle.getBundle("possibility");
        String possibility = resourceBundle.getString(stageValue);
        return possibility;
    }

    @RequestMapping("/workbench/transaction/saveCreateTran.do")
    public @ResponseBody Object saveCreateTran(Tran tran, String customerName, HttpSession session) {
        Map<String,Object> map=new HashMap<>();
        User user = (User) session.getAttribute(Contants.SESSION_USER);
        tran.setId(UUIDUtils.getUUID());
        tran.setCreateBy(user.getId());
        tran.setCreateTime(DateUtils.formatDateTime(new Date()));
        map.put("tran",tran);
        map.put("sessionUser",user);
        map.put("customerName",customerName);
        ReturnObject returnObject = new ReturnObject();
        try {
            tranService.saveCreateTran(map);
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        } catch (Exception e) {
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAILO);
            returnObject.setMessage("新建失败");
            e.printStackTrace();
        }
        return returnObject;
    }

    @RequestMapping("/workbench/transaction/detailTran.do")
    public String detailTran(String id,Model model){
        Tran tran = tranService.queryTranForDetailById(id);
        ResourceBundle bundle = ResourceBundle.getBundle("possibility");
        String possibility = bundle.getString(tran.getStage());
        tran.setPossibility(possibility);
        List<TranHistory> tranHistoryList = tranHistoryService.queryTranHistoryForDetailByTranId(id);
        List<DicValue> stageList = dicValueService.queryDicValueByTypeCode("stage");

        model.addAttribute("tran",tran);
        model.addAttribute("tranHistoryList",tranHistoryList);
        model.addAttribute("stageList",stageList);
        return "workbench/transaction/detail";
    }
}
