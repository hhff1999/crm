package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationSetvice {
    int saveCreateClueActivityRelationByList(List<ClueActivityRelation> relationList);
    int deleteClueActivityRelationByClueIdActivityId(ClueActivityRelation relation);
}
