/**
 * 
 */
package com.ssic.catering.manage.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.admin.pageModel.Json;
import com.ssic.catering.admin.service.UserServiceI;
import com.ssic.catering.base.dto.AddressDto;
import com.ssic.catering.base.dto.AddressUserDto;
import com.ssic.catering.base.dto.Tree;
import com.ssic.catering.base.pojo.Address;
import com.ssic.catering.base.service.IAddressService;
import com.ssic.catering.base.service.IAddressUserService;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.game.common.service.IImsUserService;

/**		
 * <p>Title: AddressController </p>
 * <p>Description: 区域controller</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博
 * @date 2015年8月11日 上午10:46:32	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年8月11日 上午10:46:32</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping("/addressController")
public class AddressController
{
    @Autowired
    private IAddressService addressService;
    @Autowired
    private IAddressUserService addressUserService;
    @Autowired
    private IImsUserService imsUserService;
    @Autowired
    private UserServiceI userServiceI;
    @Autowired
    private ImsUserController imsUserController;

    /**
     * 跳转到区域管理页面
     * 
     * @return
     */
    @RequestMapping("/manager")
    public String manager()
    {
        return "carte/address/address";
    }

    @RequestMapping("/tree")
    @ResponseBody
    public List<Tree> tree(String addressId, HttpSession session)
    {
        //获取用户拥有的项目权限
        List<ProjectDto> listProject = userServiceI.getProjectBySession(session);
        String projId = "";
        if (!CollectionUtils.isEmpty(listProject))
        {
            if (listProject.size() > 1)
            {//超管 
                projId = null;
            }
            else
            {
                projId = listProject.get(0).getId();
            }
        }
        return addressService.tree(addressId, projId);
    }

    @RequestMapping("/treeGrid")
    @ResponseBody
    public List<AddressDto> treeGrid(AddressDto addressDto, HttpSession session)
    {
        //获取用户拥有的项目权限
        List<ProjectDto> listProject = userServiceI.getProjectBySession(session);
        if (!CollectionUtils.isEmpty(listProject))
        {
            if (listProject.size() > 1)
            {//超管 
                addressDto.setProjId(null);
            }
            else
            {
                addressDto.setProjId(listProject.get(0).getId());
            }
        }
        return addressService.treeGrid(addressDto);
    }

    /**
     * 跳转到添加区域页面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/addPage")
    public String addPage(HttpServletRequest request)
    {

        AddressDto u = new AddressDto();
        u.setId(UUID.randomUUID().toString());
        request.setAttribute("addressDto", u);
        return "carte/address/addressAdd";
    }

    /**
     * 添加区域
     * 
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Json add(AddressDto addressDto, HttpSession session)
    {
        //获取用户拥有的项目权限
        List<ProjectDto> listProject = userServiceI.getProjectBySession(session);
        if (!CollectionUtils.isEmpty(listProject))
        {
            if (listProject.size() > 1)
            {//超管 
                addressDto.setProjId(null);
            }
            else
            {
                addressDto.setProjId(listProject.get(0).getId());
            }
        }
        Json j = new Json();
        //如果不是超管角色，且修改的区域父节点为空，才进行根节点的校验
        if (!StringUtils.isEmpty(addressDto.getProjId()) && StringUtils.isEmpty(addressDto.getPid()))
        {
            AddressDto dto = addressService.validAddressRootExists(addressDto);
            if (dto != null)
            {
                j.setSuccess(false);
                j.setMsg("根节点区域是唯一的，添加失败!");
                return j;
            }
        }
        AddressDto tempDept = new AddressDto();
        tempDept.setAddressName(addressDto.getAddressName());
        tempDept.setStat(1);
        tempDept.setProjId(addressDto.getProjId());
        int counts = addressService.vailAddressName(tempDept);
        if (counts > 0)
        {
            j.setSuccess(false);
            j.setMsg("区域名称已存在");
            return j;
        }
        try
        {
            addressDto.setStat(1);
            addressDto.setCreateTime(new Date());
            addressService.setAddressCode(addressDto);
            addressService.insert(addressDto);
            j.setSuccess(true);
            j.setMsg("添加区域成功！");
            j.setObj(addressDto);
        }
        catch (Exception e)
        {
            // e.printStackTrace();
            j.setMsg(e.getMessage());
        }
        return j;
    }

    /**
     * 跳转到区域编辑页面
     * 
     * @return
     */
    @RequestMapping("/editPage")
    public String editPage(HttpServletRequest request, String id)
    {

        AddressDto r = addressService.findById(id);
        r.setPid(r.getParentId());
        request.setAttribute("addressDto", r);
        return "carte/address/addressEdit";
    }

    /**
     * 编辑区域
     * 
     * @param resource
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Json edit(AddressDto addressDto, HttpSession session)
    {

        //获取用户拥有的项目权限
        List<ProjectDto> listProject = userServiceI.getProjectBySession(session);
        if (!CollectionUtils.isEmpty(listProject))
        {
            if (listProject.size() > 1)
            {//超管 
                addressDto.setProjId(null);
            }
            else
            {
                addressDto.setProjId(listProject.get(0).getId());
            }
        }
        Json j = new Json();
        AddressDto tempDept = new AddressDto();
        tempDept.setAddressName(addressDto.getAddressName());
        tempDept.setId(addressDto.getId());
        tempDept.setPid(addressDto.getPid());
        tempDept.setStat(1);
        int counts = addressService.vailAddressName(tempDept);

        if (counts > 0)
        {
            j.setSuccess(false);
            j.setMsg("区域已存在,请重新修改，亲.");
            return j;
        }
        //如果不是超管角色，且修改的区域父节点为空，才进行根节点的校验
        if (!StringUtils.isEmpty(addressDto.getProjId()) && StringUtils.isEmpty(addressDto.getPid()))
        {
            //如果添加的部门的没有父节点,则去数据库查找有没有一条父节点为空的记录，有的话，则提示不能添加。根节点只能有一个.
            AddressDto dto = addressService.validAddressRootExists(addressDto);
            if (dto != null)
            {
                j.setSuccess(false);
                j.setMsg("区域根节点是唯一的，修改无效!");
                return j;
            }
        }
        AddressDto dd = addressService.findById(addressDto.getId());
        /*  if (!StringUtils.isEmpty(dd.getPid()) && (!dd.getPid().equals(addressDto.getPid())))
          {
              addressService.setAddressCode(addressDto);
          }*/
        if (!StringUtils.isEmpty(dd.getPid()) && !StringUtils.isEmpty(addressDto.getPid())
            && !dd.getPid().equals(addressDto.getPid()))
        {//如果当前区域的父节点发生改变，则区域编码也要更改;
            addressService.setAddressCode(addressDto);
        }
        addressDto.setCreateTime(dd.getCreateTime());
        addressService.update(addressDto);
        j.setSuccess(true);
        j.setMsg("编辑成功！");
        return j;
    }

    /**
     * 删除区域
     * 
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Json delete(String id)
    {
        Json j = new Json();
        boolean canDelete1 = true;
        boolean canDelete2 = true;
        List<AddressDto> addressList = addressService.findChildListByParentId(id);

        //部门子集合是否存在用户判断;
        if (!CollectionUtils.isEmpty(addressList))
        {
            j.setSuccess(false);
            j.setMsg("删除失败,该区域下还有子区域,不能删除!");
            return j;
            /*  for (AddressDto dto : addressList)
              {
                  List<AddressUserDto> users = addressUserService.finAddressListByAddressId(dto.getId());
                  if (!CollectionUtils.isEmpty(users))
                  {
                      for (AddressUserDto address_user : users)
                      {
                          if (!StringUtils.isEmpty(address_user.getUserId()))
                          {
                              canDelete1 = false;
                              j.setSuccess(false);
                              j.setMsg("删除失败,该区域的下级区域还存在负责人，不能删除!");
                              return j;
                          }
                      }
                  }
              }*/
        }
        List<AddressUserDto> addressUsers = addressUserService.finAddressListByAddressId(id);
        if (!CollectionUtils.isEmpty(addressUsers))
        {//便利区域用户，如果该区域下的用户id还存在，则不能删除;
            for (AddressUserDto addressUser : addressUsers)
            {
                if (!StringUtils.isEmpty(addressUser.getUserId()))
                {
                    canDelete2 = false;
                    j.setSuccess(false);
                    j.setMsg("删除失败,该区域存在负责人，不能删除!");
                    return j;
                }
            }
        }
        if (canDelete1 == true && canDelete2 == true)
        {
            //先删除区域负责人关系表
            AddressUserDto addressUser = new AddressUserDto();
            addressUser.setAddressId(id);
            List<AddressUserDto> list = addressUserService.findAll(addressUser);
            for (AddressUserDto dto : list)
            {//删除
                addressUserService.delete(dto);
            }
            //再删除区域
            Address address = addressService.findAddressById(id);
            AddressDto tempDept = new AddressDto();
            tempDept.setId(id);
            addressService.setAddressCode(tempDept);
            tempDept.setAddressCode(address.getAddressCode());
            tempDept.setAddressName(address.getAddressName());
            tempDept.setCreateTime(address.getCreateTime());
            if (!StringUtils.isEmpty(address.getParentId()))
            {
                tempDept.setParentId(address.getParentId());
            }
            tempDept.setProjId(address.getProjId());
            addressService.delete(tempDept);

            j.setMsg("删除成功！");
            j.setSuccess(true);
            return j;
        }
        return j;
    }

    /**
     * 跳转到添加区域负责人页面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/addAddressUserPage")
    public String addAddressUserPage(String addressId, HttpServletRequest request, HttpSession session)
    {
        Address address = addressService.findAddressById(addressId);
        AddressUserDto u = new AddressUserDto();
        if (address != null)
        {
            String code = address.getAddressCode();
            //总公司
            if (code.length() == 2)
            {
                u.setAddressType(0);
            }
            //大区
            if (code.length() == 4)
            {
                u.setAddressType(1);
            }
            //省
            if (code.length() == 6)
            {
                u.setAddressType(2);
            }
            //市
            if (code.length() == 8)
            {
                u.setAddressType(3);
            }

        }
        u.setAddressId(addressId);
        u.setId(UUID.randomUUID().toString());
        request.setAttribute("addressUserDto", u);
        List<ImsUsersDto> userList = imsUserController.findAll(session);
        request.setAttribute("userList", userList);
        return "carte/addressUser/addressUser";

    }

    /**
     *添加区域负责人
     * 
     * @param request
     * @return
     */
    @RequestMapping("/addUserAddress")
    @ResponseBody
    public Json addUserAddress(AddressUserDto addressUserDto, HttpSession session)
    {
        Json j = new Json();
        //获取用户拥有的项目权限
        List<ProjectDto> listProject = userServiceI.getProjectBySession(session);
        if (!CollectionUtils.isEmpty(listProject))
        {
            if (listProject.size() > 1)
            {//超管
                addressUserDto.setProjId(null);
            }
            else
            {
                addressUserDto.setProjId(listProject.get(0).getId());
            }
        }
        AddressUserDto addressUsers = new AddressUserDto();
        addressUsers.setProjId(addressUserDto.getProjId());
        List<AddressUserDto> addreUserLists = addressUserService.findAll(addressUsers);
        if (!CollectionUtils.isEmpty(addreUserLists))
        {
            for (AddressUserDto adto : addreUserLists)
            { //如果当前食堂的负责人已经是其他食堂负责人或市负责人，则提示错误消息
                if (!StringUtils.isEmpty(addressUserDto.getUserId())
                    && addressUserDto.getUserId().equals(adto.getUserId())
                //&& !addressUserDto.getAddressId().equals(adto.getAddressId())
                )
                {
                    j.setMsg("请勿重复添加负责人!");
                    j.setSuccess(false);
                    j.setObj(addressUserDto);
                    return j;
                }
            }
        }

        AddressUserDto addressUser = new AddressUserDto();
        addressUser.setAddressId(addressUserDto.getAddressId());
        addressUser.setAddressType(addressUserDto.getAddressType());
        List<AddressUserDto> dtoList = addressUserService.findAll(addressUser);
        if (!CollectionUtils.isEmpty(dtoList))
        {//如果当前区域存在负责人则更新,否则执行添加
            AddressUserDto addUserDto = dtoList.get(0);
            addressUserDto.setId(addUserDto.getId());
            addressUserDto.setCreateTime(addUserDto.getCreateTime());
            addressUserService.update(addressUserDto);
        }
        else
        {
            addressUserDto.setCreateTime(new Date());
            addressUserDto.setLastUpdateTime(new Date());
            addressUserService.insert(addressUserDto);
        }
        j.setMsg("编辑负责人成功.");
        j.setSuccess(true);
        j.setObj(addressUserDto);
        return j;
    }

    /**
     * 删除区域
     * 
     * @param id
     * @return
     */
    @RequestMapping("/deleteAddressUser")
    @ResponseBody
    public Json deleteAddressUser(String addressId, String userId)
    {
        AddressUserDto addressUser = new AddressUserDto();
        addressUser.setAddressId(addressId);
        addressUser.setUserId(userId);
        List<AddressUserDto> list = addressUserService.findAll(addressUser);
        if (!CollectionUtils.isEmpty(list))
        {
            AddressUserDto dto = list.get(0);
            dto.setUserId("");
            addressUserService.update(dto);
            Json j = new Json();
            j.setMsg("删除区域负责人成功.");
            j.setSuccess(true);
            return j;
        }
        Json j = new Json();
        j.setMsg("删除区域负责人失败.");
        j.setSuccess(false);
        return j;
    }
}
