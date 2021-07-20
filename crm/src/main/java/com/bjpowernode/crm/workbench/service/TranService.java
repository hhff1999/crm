package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Tran;

import java.util.Map;

public interface TranService {

    int saveCreateTran(Map<String,Object> map);
    //根据交易id去找交易详情
    Tran queryTranForDetailById(String id);

}
