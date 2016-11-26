package com.ssic.catering.base.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.catering.base.dao.MeetingUserDao;
import com.ssic.catering.base.dto.MeetUserDeptDto;
import com.ssic.catering.base.dto.MeetingPerCDto;
import com.ssic.catering.base.dto.MeetingUserDto;
import com.ssic.catering.base.pojo.MeetingUser;
import com.ssic.catering.base.service.IMeetingService;
import com.ssic.catering.base.service.IMeetingUserService;
import com.ssic.game.common.dao.DeptDao;
import com.ssic.game.common.dao.DeptUserDao;
import com.ssic.game.common.dao.ImsUserDao;
import com.ssic.game.common.dto.DeptDto;
import com.ssic.game.common.dto.DeptUsersDto;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.pojo.ImsUsers;
import com.ssic.game.common.service.IDeptService;
import com.ssic.util.BeanUtils;

@Service
public class MeetingUserServiceImpl implements IMeetingUserService
{

    @Autowired
    private MeetingUserDao meetingUserDao;
    //	@Autowired
    //	private DeptDao deptDao; 
    //	@Autowired
    //	private DeptUserDao  deptUserDao; 
    //	@Autowired
    //	private ImsUserDao imsUserDao;
    @Autowired
    private IMeetingService meetingService;
    @Autowired
    private IDeptService deptService;
    
    

    @Override
    public List<MeetingUserDto> findBy(MeetingUserDto param)
    {
        // TODO Auto-generated method stub
        MeetingUser meetingUser = new MeetingUser();
        BeanUtils.copyProperties(param, meetingUser);
        List<MeetingUser> list = meetingUserDao.findBy(meetingUser);
        List<MeetingUserDto> listDto = BeanUtils.createBeanListByTarget(list, MeetingUserDto.class);
        return listDto;
    }

    @Override
    public MeetingUserDto findById(String id)
    {
        // TODO Auto-generated method stub
        MeetingUser meetingUser = meetingUserDao.findById(id);
        MeetingUserDto mdto = new MeetingUserDto();
        BeanUtils.copyProperties(meetingUser, mdto);
        return mdto;
    }

    @Override
    public void insertMeetingU(MeetingUserDto meetingUserDto)
    {
        // TODO Auto-generated method stub
        MeetingUser meetingUser = new MeetingUser();
        BeanUtils.copyProperties(meetingUserDto, meetingUser);
        meetingUserDao.insertMeetingU(meetingUser);
    }

    @Override
    public void updateMeetingU(MeetingUserDto meetingUserDto)
    {
        // TODO Auto-generated method stub
        MeetingUser meetingUser = new MeetingUser();
        BeanUtils.copyProperties(meetingUserDto, meetingUser);
        meetingUserDao.updateMeetingU(meetingUser);
    }

    @Override
    public List<MeetUserDeptDto> findUserDept(String userIdk, String projId)
    {
        // TODO Auto-generated method stub
        List<MeetUserDeptDto> listMUserD = new ArrayList<MeetUserDeptDto>();
        DeptDto deptDto = new DeptDto();
        //查询团餐项目下的部门
        deptDto.setProjId(projId);
        //查询团餐的子部门
      //  deptDto.setPid("");
      //  deptDto.setPid("e015854d-e4a2-41b2-b9bf-a36fdb491fb5");
        List<DeptDto> listDept = meetingService.findDeptAll(deptDto);
//        if (listDept != null)
//        {
//            DeptDto deptDto2 = new DeptDto();
//            deptDto2.setProjId(projId);
//            deptDto2.setPid(listDept.get(0).getId());
//            listDept = meetingService.findDeptAll(deptDto2);
//        }

        
        if (listDept != null && listDept.size() > 0)
        {
            for (DeptDto deptDto2 : listDept)
            {
                String id2 = deptDto2.getId();
                String deptCode2 = deptDto2.getDeptCode();
                String deptName2 = deptDto2.getDeptName();
                MeetUserDeptDto mUserD2 = new MeetUserDeptDto();
                mUserD2.setDeptId(id2);
                ;
                mUserD2.setDeptCode(deptCode2);
                mUserD2.setDeptName(deptName2);
                DeptUsersDto deptUsersDto3 = new DeptUsersDto();
                deptUsersDto3.setDeptId(id2);
                //查询部门下的用户
                List<DeptUsersDto> listdeptUser3 = meetingService.findUserByDept(deptUsersDto3);
                //List<DeptUsersDto> listdeptUser3 = deptUserDao.findUser(deptUsersDto3);
                List<MeetingPerCDto> listuserDto = new ArrayList<MeetingPerCDto>();
                if (listdeptUser3 != null && listdeptUser3.size() > 0)
                {
                    for (DeptUsersDto dUserDto3 : listdeptUser3)
                    {
                        String userId3 = dUserDto3.getUserId();
                        if (userIdk != null)
                        {
                            if (!userId3.equals(userIdk))
                            {
                                ImsUsersDto imsUsers3 = meetingService.findByUserId(userId3);
                                if (imsUsers3 != null)
                                {
                                    //ImsUsers  imsUsers3=imsUserDao.findByUserId(userId3);
                                    String userName3 = imsUsers3.getName();
                                    int age3 = imsUsers3.getAge();
                                    String email3 = imsUsers3.getEmail();
                                    String qjAccount3 = imsUsers3.getQjyAccount();
                                    String userAccount3 = imsUsers3.getUserAccount();
                                    String userNo3 = imsUsers3.getUserNo();
                                    MeetingPerCDto imsUsersDto3 = new MeetingPerCDto();
                                    imsUsersDto3.setId(userId3);
                                    imsUsersDto3.setName(userName3);
                                    imsUsersDto3.setAge(age3);
                                    imsUsersDto3.setQjyAccount(qjAccount3);
                                    imsUsersDto3.setUserAccount(userAccount3);
                                    imsUsersDto3.setUserNo(userNo3);
                                    listuserDto.add(imsUsersDto3);
                                }
                            }
                        }
                        else
                        {
                            ImsUsersDto imsUsers3 = meetingService.findByUserId(userId3);
                            if (imsUsers3 != null)
                            {
                                //ImsUsers  imsUsers3=imsUserDao.findByUserId(userId3);
                                String userName3 = imsUsers3.getName();
                                int age3 = imsUsers3.getAge();
                                String email3 = imsUsers3.getEmail();
                                String qjAccount3 = imsUsers3.getQjyAccount();
                                String userAccount3 = imsUsers3.getUserAccount();
                                String userNo3 = imsUsers3.getUserNo();
                                MeetingPerCDto imsUsersDto3 = new MeetingPerCDto();
                                imsUsersDto3.setId(userId3);
                                imsUsersDto3.setName(userName3);
                                imsUsersDto3.setAge(age3);
                                imsUsersDto3.setQjyAccount(qjAccount3);
                                imsUsersDto3.setUserAccount(userAccount3);
                                imsUsersDto3.setUserNo(userNo3);
                                listuserDto.add(imsUsersDto3);
                            }
                        }
                    }
                }
                mUserD2.setListUsers(listuserDto);
                listMUserD.add(mUserD2);
            }
        }
        return listMUserD;
    }

    @Override
    public void updateInMeeting(MeetingUserDto meetingUserDto)
    {
        // TODO Auto-generated method stub
        MeetingUser meetingUser = new MeetingUser();
        BeanUtils.copyProperties(meetingUserDto, meetingUser);
        meetingUserDao.updateInMeeting(meetingUser);
    }

}
