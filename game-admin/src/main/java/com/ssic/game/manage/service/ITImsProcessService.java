package com.ssic.game.manage.service;

import java.util.List;

import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.admin.pageModel.Tree;
import com.ssic.game.common.dto.ProcessDto;

public interface ITImsProcessService {
      List<ProcessDto> findProcess (ProcessDto processDto);
      void insertPro(ProcessDto processDto);
      void updatePro(ProcessDto processDto);
      void deletePro(ProcessDto processDto);
      List<ProcessDto> findInst (ProcessDto processDto);
      String findUserPers(String ids);
      List<Tree> userTree(String searchName,String projId);
      void grantUser(String userpress,String resourceIds);
      void insertImageUrl(ProcessDto processDto);
      void updateStarTask(ProcessDto processDto);
      List<ProcessDto> findProcessALL(ProcessDto processDto,PageHelper ph);
      int findCount(ProcessDto processDto);
}
