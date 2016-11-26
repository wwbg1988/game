package com.ssic.catering.manage.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.admin.pageModel.DataGrid;
import com.ssic.catering.admin.pageModel.Json;
import com.ssic.catering.admin.service.UserServiceI;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.dto.ParamConfigDto;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.game.common.service.IParamConfigService;
import com.ssic.util.UUIDGenerator;

/**
 * <p>
 * Title: ParamConfigController
 * </p>
 * <p>
 * Description: 类描述
 * </p>
 * <p>
 * Copyright (c) 2015
 * </p>
 * <p>
 * Company: 上海天坊信息科技有限公司
 * </p>
 * 
 * @author 朱振
 * @date 2015年11月16日 下午3:05:52
 * @version 1.0
 *          <p>
 *          修改人：朱振
 *          </p>
 *          <p>
 *          修改时间：2015年11月16日 下午3:05:52
 *          </p>
 *          <p>
 *          修改备注：
 *          </p>
 */
@Controller
@RequestMapping("/paramConfigController")
public class ParamConfigController
{
    @Autowired
    private IParamConfigService paramService;

    @Autowired
    private UserServiceI userService;

    /**
     * 显示列表
     * 
     * @author 朱振
     * @date 2015年11月16日 下午3:10:21
     * @version 1.0
     * @param req
     * @param resp
     * @return <p>
     *         修改人：朱振
     *         </p>
     *         <p>
     *         修改时间：2015年11月16日 下午3:10:21
     *         </p>
     *         <p>
     *         修改备注：
     *         </p>
     */
    @RequestMapping("/manager")
    public String manager(HttpServletRequest req, HttpServletResponse resp)
    {
        List<ProjectDto> projects = userService.getProjectBySession(req.getSession());
        if(!CollectionUtils.isEmpty(projects))
        {
            req.setAttribute("projects", projects);
            return "carte/paramConfig/param";
        }
        
        req.setAttribute("errorMsg", "该用户没有所属项目");
        return "error/errorMsg";
    }

    /**
     * 填充数据
     * 
     * @author 朱振
     * @date 2015年11月16日 下午3:14:26
     * @version 1.0
     * @param req
     * @param resp
     * @param param
     * @param ph
     * @return <p>
     *         修改人：朱振
     *         </p>
     *         <p>
     *         修改时间：2015年11月16日 下午3:14:26
     *         </p>
     *         <p>
     *         修改备注：
     *         </p>
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public DataGrid dataGrid(HttpServletRequest req, HttpServletResponse resp, ParamConfigDto paramConfigDto,
        PageHelperDto pageHelperDto)
    {
        DataGrid response = new DataGrid();

        String projectIds = userService.getProjectIdsBySession(req.getSession());

        // 用户是否拥有要查看的项目的权限
        if (!StringUtils.isEmpty(paramConfigDto.getProjId())
            && projectIds.contains(paramConfigDto.getProjId()))
        {
            paramConfigDto.setProjId(paramConfigDto.getProjId());// 以传过来的参数为准
        }
        else
        {
            paramConfigDto.setProjId(projectIds);// 查看该用户所拥有的所有项目
        }

        long count = paramService.getRowsBy(paramConfigDto);
        if (count <= 0)
        {
            response.setTotal(0L);
        }
        else
        {
            List<ParamConfigDto> rows = paramService.getParamConfigDtoBy(paramConfigDto, pageHelperDto);
            if (!CollectionUtils.isEmpty(rows))
            {
                response.setTotal(count);
                response.setRows(rows);
            }
        }

        return response;
    }

    /**
     * 
     * showAddPageConfig：跳转到添加配置项的页面
     * 
     * @param req
     * @param resp
     * @return
     * @exception
     * @author zhuzhen
     * @date 2015年11月23日 下午5:47:05
     */
    @RequestMapping("/addPage")
    public String showAddParamConfig(HttpServletRequest req, HttpServletResponse resp)
    {
        List<ProjectDto> projects = userService.getProjectBySession(req.getSession());
        if (!CollectionUtils.isEmpty(projects))
        {
            req.setAttribute("projects", projects);
        }

        req.setAttribute("id", UUIDGenerator.getUUID());

        return "carte/paramConfig/paramConfigAdd";
    }

    /**
     * 
     * addParamConfig：添加记录
     * 
     * @param req
     * @param resp
     * @param paramConfigDto
     * @return
     * @exception
     * @author zhuzhen
     * @date 2015年11月23日 下午5:47:28
     */
    @ResponseBody
    @RequestMapping("/add")
    public Json addParamConfig(HttpServletRequest req, HttpServletResponse resp, ParamConfigDto paramConfigDto)
    {
        Json response = new Json();

        // id
        if (StringUtils.isEmpty(paramConfigDto.getId()))
        {
            response.setSuccess(false);
            response.setMsg("id不能为空");
            return response;
        }

        // 参数名
        if (StringUtils.isEmpty(paramConfigDto.getParamName()))
        {
            response.setSuccess(false);
            response.setMsg("参数名不能为空");
            return response;
        }

        // 参数描述
        if (StringUtils.isEmpty(paramConfigDto.getParamDescribe()))
        {
            response.setSuccess(false);
            response.setMsg("参数描述不能为空");
            return response;
        }

        // 参数类型
        if (StringUtils.isEmpty(paramConfigDto.getParamType()))
        {
            response.setSuccess(false);
            response.setMsg("参数类型不能为空");
            return response;
        }

        // 项目不能为空
        if (StringUtils.isEmpty(paramConfigDto.getProjId()))
        {
            response.setSuccess(false);
            response.setMsg("项目不能为空");
            return response;
        }

        ParamConfigDto param = new ParamConfigDto();
        param.setParamName(paramConfigDto.getParamName());
        param.setProjId(paramConfigDto.getProjId());
        param.setParamType(paramConfigDto.getParamType());

        // 是否存在相同的配置(参数名、类型和项目相同)
        if (!CollectionUtils.isEmpty(paramService.findBy(param)))
        {
            response.setSuccess(false);
            response.setMsg("配置项已存在");
            return response;
        }

        ParamConfigDto newRecord = new ParamConfigDto();
        newRecord.setId(paramConfigDto.getId());
        newRecord.setParamName(paramConfigDto.getParamName());
        newRecord.setParamDescribe(paramConfigDto.getParamDescribe());
        newRecord.setParamValue(paramConfigDto.getParamValue());
        newRecord.setProjId(paramConfigDto.getProjId());
        newRecord.setParamType(paramConfigDto.getParamType());

        int count = paramService.addParamConfig(newRecord);
        if (count > 0)
        {
            response.setSuccess(true);
            response.setMsg("添加成功");
        }
        else
        {
            response.setSuccess(false);
            response.setMsg("添加失败");
        }

        return response;

    }

    /**
     * 
     * showConfigSupplierRole：一句话描述方法功能
     * 
     * @param req
     * @param resp
     * @return
     * @exception
     * @author zhuzhen
     * @date 2015年11月25日 下午4:23:59
     */
    @RequestMapping("/configSupplierRolePage")
    public String showConfigSupplierRole(HttpServletRequest req, HttpServletResponse resp)
    {
        List<ProjectDto> projects = userService.getProjectBySession(req.getSession());
        if (!CollectionUtils.isEmpty(projects))
        {
            req.setAttribute("projects", projects);
        }

        req.setAttribute("id", UUIDGenerator.getUUID());

        return "carte/paramConfig/supplierRoleConfig";
    }

    /**
     * 
     * showEditParamConfigPage：一句话描述方法功能
     * 
     * @param req
     * @param resp
     * @return
     * @exception
     * @author zhuzhen
     * @date 2015年12月1日 上午10:08:02
     */
    @RequestMapping("/editPage")
    public String showEditParamConfigPage(HttpServletRequest req, HttpServletResponse resp, String id)
    {
        List<ProjectDto> projects = userService.getProjectBySession(req.getSession());
        if (!CollectionUtils.isEmpty(projects))
        {
            ParamConfigDto paramConfig = paramService.findByID(id);
            if (paramConfig != null)
            {
                req.setAttribute("projects", projects);
                req.setAttribute("paramConfig", paramConfig);
                return "carte/paramConfig/paramConfigEdit";
            }

            req.setAttribute("errorMsg", "该配置不存在");
            return "error/errorMsg";
        }

        req.setAttribute("errorMsg", "该用户没有所属项目");
        return "error/errorMsg";
    }

    /**
     * 
     * updateParamConfig：一句话描述方法功能
     * @param req
     * @param resp
     * @param paramConfigDto
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年12月1日 上午11:12:41
     */
    @ResponseBody
    @RequestMapping("/edit")
    public Json updateParamConfig(HttpServletRequest req, HttpServletResponse resp,
        ParamConfigDto paramConfigDto)
    {
        Json response = new Json();

        // id
        if (StringUtils.isEmpty(paramConfigDto.getId()))
        {
            response.setSuccess(false);
            response.setMsg("id不能为空");
            return response;
        }

        // 参数名
        if (StringUtils.isEmpty(paramConfigDto.getParamName()))
        {
            response.setSuccess(false);
            response.setMsg("参数名不能为空");
            return response;
        }

        // 参数描述
        if (StringUtils.isEmpty(paramConfigDto.getParamDescribe()))
        {
            response.setSuccess(false);
            response.setMsg("参数描述不能为空");
            return response;
        }

        // 参数类型
        if (StringUtils.isEmpty(paramConfigDto.getParamType()))
        {
            response.setSuccess(false);
            response.setMsg("参数类型不能为空");
            return response;
        }

        // 项目不能为空
        if (StringUtils.isEmpty(paramConfigDto.getProjId()))
        {
            response.setSuccess(false);
            response.setMsg("项目不能为空");
            return response;
        }

        // 是否存在相同的配置(参数名、类型和项目相同)
        if (paramService.findByID(paramConfigDto.getId()) == null)
        {
            response.setSuccess(false);
            response.setMsg("配置项不存在");
            return response;
        }

        ParamConfigDto newRecord = new ParamConfigDto();
        newRecord.setId(paramConfigDto.getId());
        newRecord.setParamName(paramConfigDto.getParamName());
        newRecord.setProjId(paramConfigDto.getProjId());
        newRecord.setParamType(paramConfigDto.getParamType());
        newRecord.setParamDescribe(paramConfigDto.getParamDescribe());

        int count = paramService.updateParamConfig(newRecord);
        if (count > 0)
        {
            response.setSuccess(true);
            response.setMsg("保存成功");
        }
        else
        {
            response.setSuccess(false);
            response.setMsg("保存失败");
        }

        return response;

    }

    /**
     * 
     * deleteParamConfig：一句话描述方法功能
     * 
     * @param req
     * @param resp
     * @param id
     * @return
     * @exception
     * @author zhuzhen
     * @date 2015年12月1日 上午10:20:19
     */
    @ResponseBody
    @RequestMapping("/delete")
    public Json deleteParamConfig(HttpServletRequest req, HttpServletResponse resp, String id)
    {
        Json response = new Json();

        // id
        if (StringUtils.isEmpty(id))
        {
            response.setSuccess(false);
            response.setMsg("id不能为空");
            return response;
        }

        // 是否存在相同的配置(参数名、类型和项目相同)
        if (paramService.findByID(id) != null)
        {
            response.setSuccess(false);
            response.setMsg("配置项已存在");
            return response;
        }

        ParamConfigDto newRecord = new ParamConfigDto();
        newRecord.setId(id);
        newRecord.setStat(0);// 无效

        int count = paramService.updateParamConfig(newRecord);
        if (count > 0)
        {
            response.setSuccess(true);
            response.setMsg("保存成功");
        }
        else
        {
            response.setSuccess(false);
            response.setMsg("保存失败");
        }

        return response;

    }
}
