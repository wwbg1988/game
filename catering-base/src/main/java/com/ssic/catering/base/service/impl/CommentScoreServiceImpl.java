package com.ssic.catering.base.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssic.catering.base.dao.CommentDao;
import com.ssic.catering.base.dao.CommentSensitiveDao;
import com.ssic.catering.base.dao.ConfigScoreDao;
import com.ssic.catering.base.dao.SensitiveDao;
import com.ssic.catering.base.dto.SensitiveDto;
import com.ssic.catering.base.pojo.Comment;
import com.ssic.catering.base.pojo.ConfigScore;
import com.ssic.catering.base.pojo.Sensitive;
import com.ssic.catering.base.service.ICommentScoreService;
import com.ssic.shop.manage.dto.CarteUserDto;
import com.ssic.shop.manage.dto.LuckyDrawHistoryDto;
import com.ssic.shop.manage.service.ICarteUserService;
import com.ssic.shop.manage.service.LuckyDrawHistoryService;
import com.ssic.util.BeanUtils;
import com.ssic.util.DateUtils;
import com.ssic.util.constants.DataStatus;

/**
 * 		
 * <p>Title: ICommentScoreServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年8月5日 下午5:04:19	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年8月5日 下午5:04:19</p>
 * <p>修改备注：</p>
 */
@Service
public class CommentScoreServiceImpl implements ICommentScoreService
{
    @Autowired
    private CommentDao commentDao;

    @Autowired
    private ConfigScoreDao configScore;

    @Autowired
    private SensitiveDao sensitiveDao;
    @Autowired
    private ICarteUserService carteUserService;
    @Autowired
    private CommentSensitiveDao commentSensitive;
    @Autowired
    private LuckyDrawHistoryService luckyDrawHistoryService;

    @Override
    public String insertsCommentScore(HttpServletRequest request)
    {
        String json = request.getParameter("data");
        JSONObject fromObject = JSONObject.fromObject(json);
        //       //保存就餐评论的各项评分
        this.insertScores(fromObject);
        //      //保存就餐评论的内容
        this.insertConments(fromObject);
        //保存一条评论关联的敏感词
        this.insertCommentSensitive(fromObject);
        //更新一条微信用户评论记录到用户抽奖历史记录表(t_ctr_lucky_draw_history)中
        this.insertLuckyDrawHistory(fromObject);
        this.insertCarteUser(fromObject);
        return "1";
    }

    /**
     * 
     * insertScores：保存就餐评论的各项评分
     * @param fromObject
     * @return
     * @exception   
     * @author pengcheng.yang
     * @date 2015年8月5日 下午6:05:54
     */
    private void insertScores(JSONObject fromObject)
    {
        //餐厅Id
        String cafetoriumId = fromObject.getString("cafetoriumId");
        //餐厅评分项
        List<ConfigScore> list = new ArrayList<ConfigScore>();

        JSONObject jsonObject = fromObject.getJSONArray("configScore").getJSONObject(0);
        for (Object obj : jsonObject.keySet())
        {
            ConfigScore configScore = new ConfigScore();
            configScore.setCafetoriumId(cafetoriumId);
            configScore.setConfigId(obj.toString().substring(3));
            configScore.setScore(jsonObject.get(obj).toString());
            list.add(configScore);
        }
        configScore.insertConfigScore(list);
    }

    /**
     * 
     * insertConments：保存就餐评论的内容
     * @param request
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月5日 下午6:02:36
     */
    private void insertConments(JSONObject fromObject)
    {
        //评论内容Id-预先生成
        String uuid = fromObject.getString("uuid");
        //表示用户唯一ID
        String userId = fromObject.getString("userId");
        Comment comment = new Comment();
        //餐厅Id
        String cafetoriumId = fromObject.getString("cafetoriumId");
        //餐厅评论内容
        String comments =
            fromObject.getJSONArray("comments").getJSONObject(0).get("textareavalue").toString();
        if (!StringUtils.isEmpty(cafetoriumId))
        {
            comment.setCafetoriumId(cafetoriumId);
        }
        if (!StringUtils.isEmpty(comments))
        {
            comment.setComment(comments);
        }
        if (!StringUtils.isEmpty(uuid))
        {
            comment.setId(uuid);
        }
        if (!StringUtils.isEmpty(userId))
        {
            comment.setUserUniquenessId(userId);
        }
        commentDao.insertComments(comment);
    }

    private void insertLuckyDrawHistory(JSONObject fromObject)
    {
        //获取威信用户唯一标识id :openId
        String openId = fromObject.getString("userId");
        //餐厅Id
        String cafetoriumId = fromObject.getString("cafetoriumId");
        String comment_date = null;
        //根据微信用户唯一id获取用户历史抽奖记录集合;
        List<LuckyDrawHistoryDto> historyList =
            luckyDrawHistoryService.findLuckDrawHistoryByOpenIdAndCafeId(openId, cafetoriumId);
        Comment param = new Comment();
        param.setUserUniquenessId(openId);
        List<Comment> commentList = commentDao.findBy(param);
        if (!CollectionUtils.isEmpty(commentList))
        {
            Comment dto = commentList.get(0);
            comment_date = DateUtils.format(dto.getCreateTime(), "yyyy-MM-dd");
        }
        String now_date = DateUtils.format(new Date(), "yyyy-MM-dd");

        if (!StringUtils.isEmpty(comment_date) && comment_date.equals(now_date))
        {//如果当天已经评论过,抽奖历史记录表则更新抽奖记录表的评论次数数据;
            if (CollectionUtils.isEmpty(historyList))
            {//如果历史抽奖记录不存在，则新添加一条新数据，包括(总的评论条数，总的次数);

                LuckyDrawHistoryDto historyDto = new LuckyDrawHistoryDto();
                historyDto.setCafetoriumId(cafetoriumId);
                historyDto.setId(UUID.randomUUID().toString());
                historyDto.setOpenId(openId);
                historyDto.setCreateTime(new Date());
                historyDto.setLastUpdateTime(new Date());
                historyDto.setIsNowLuckyDraw(DataStatus.DISABLED);
                historyDto.setIsExchange(DataStatus.DISABLED);
                historyDto.setIsLottery(DataStatus.DISABLED);
                //放入总的评论条数:1条
                historyDto.setTotalCommentCount(1);
                //放入总的抽奖次数:1次
                historyDto.setTotalLuckyCount(1);
                luckyDrawHistoryService.insertLuckyDrawHistory(historyDto);
            }
            else
            { //获取最新的一条历史抽奖记录;更新总评论条数合纵抽奖
                LuckyDrawHistoryDto history_Dto = historyList.get(0);
                //插入一条新数据:总评论条数=上一次评论时间的评论条数+1; 总抽奖次数=上一次抽奖时间的抽奖次数+1；
                LuckyDrawHistoryDto historyDto = new LuckyDrawHistoryDto();
                historyDto.setId(UUID.randomUUID().toString());
                historyDto.setOpenId(openId);
                if (!StringUtils.isEmpty(history_Dto.getCafetoriumId()))
                {
                    historyDto.setCafetoriumId(history_Dto.getCafetoriumId());
                }
                else
                {
                    historyDto.setCafetoriumId(cafetoriumId);
                }
                historyDto.setCreateTime(new Date());
                historyDto.setLastUpdateTime(new Date());
                historyDto.setIsNowLuckyDraw(DataStatus.DISABLED);
                //放入总的评论条数:1条
                historyDto.setTotalCommentCount(history_Dto.getTotalCommentCount() + 1);
                //放入总的抽奖次数:1次
                historyDto.setTotalLuckyCount(history_Dto.getTotalLuckyCount() + 1);
                historyDto.setIsExchange(DataStatus.DISABLED);
                historyDto.setIsLottery(DataStatus.DISABLED);
                luckyDrawHistoryService.insertLuckyDrawHistory(historyDto);
            }
        }
    }

    private void insertCarteUser(JSONObject fromObject)
    {
        //获取威信用户唯一标识id :openId
        String openId = fromObject.getString("userId");
        String cafetoriumId = fromObject.getString("cafetoriumId");

        CarteUserDto userDto = carteUserService.findByWxUniqueIdAndCafetoriumId(openId, cafetoriumId);
        if (userDto == null)
        {
            CarteUserDto user = new CarteUserDto();
            user.setOpenId(openId);
            user.setIntegral(0);
            user.setId(UUID.randomUUID().toString());
            user.setCreateTime(new Date());
            user.setLastUpdateTime(new Date());
            user.setCafetoriumId(cafetoriumId);
            carteUserService.insertCarteUser(user);

        }

    }

    /**
     * 
     * updataSensitive：拼接敏感词Id
     * @param fromObject
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月5日 下午8:12:49
     */
    private List<String> subSensitive(JSONObject fromObject)
    {

        //餐厅评论内容
        String comments = fromObject.getJSONArray("comments").getJSONObject(0).get("textareavalue").toString();

        String cafetoriumId = fromObject.getString("cafetoriumId");
        
        List<Sensitive> tempList = sensitiveDao.findByTop(cafetoriumId);
        if(tempList != null){
            List<SensitiveDto> resultList = BeanUtils.createBeanListByTarget(tempList, SensitiveDto.class);
            List<String> list = new ArrayList<String>();
            for (int i = 0; i < resultList.size(); i++)
            {
                String sName = resultList.get(i).getSensitiveName();
                if (comments.contains(sName))
                {
                    list.add(resultList.get(i).getId());
                }
            }
            if (list.size() > 0)
            {
                return list;
                
            }
        }
        return new ArrayList<String>();
    }

    /**
     * 
     * insertCommentSensitive：保存一条评论相关的敏感词
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月6日 下午2:58:16
     */
    private void insertCommentSensitive(JSONObject fromObject)
    {
        //评论内容Id-预先生成
        String uuid = fromObject.getString("uuid");
        List<String> list = new ArrayList<String>();
        list = this.subSensitive(fromObject);
        if(list.size()>0){
            commentSensitive.insertCommentSensitive(list, uuid);
        }
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.ICommentScoreService#commentCount(java.lang.String)   
    * 
    * @author yuanbin
    */
    @Override
    public String commentCount(String addressId)
    {
        // TODO 添加方法注释
        return commentDao.commentCount(addressId);
    }

}
