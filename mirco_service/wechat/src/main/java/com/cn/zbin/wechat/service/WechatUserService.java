package com.cn.zbin.wechat.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.zbin.wechat.bto.WeChatUserBaseInfo;
import com.cn.zbin.wechat.dto.PromotionPartnerInfo;
import com.cn.zbin.wechat.dto.PromotionPartnerInfoExample;
import com.cn.zbin.wechat.dto.WeChatMessageHistory;
import com.cn.zbin.wechat.dto.WeChatMessageHistoryExample;
import com.cn.zbin.wechat.dto.WeChatUserInfo;
import com.cn.zbin.wechat.dto.WeChatUserInfoExample;
import com.cn.zbin.wechat.exception.BusinessException;
import com.cn.zbin.wechat.mapper.PromotionPartnerInfoMapper;
import com.cn.zbin.wechat.mapper.WeChatMessageHistoryMapper;
import com.cn.zbin.wechat.mapper.WeChatUserInfoMapper;
import com.cn.zbin.wechat.utils.Utils;
import com.cn.zbin.wechat.utils.WechatConstants;
import com.cn.zbin.wechat.utils.WechatKeyConstants;

@Service
public class WechatUserService {
	@Autowired
	private WeChatUserInfoMapper wechatUserInfoMapper;
	@Autowired
	private PromotionPartnerInfoMapper promotionPartnerInfoMapper;
    @Autowired
    private WeChatMessageHistoryMapper weChatMessageHistoryMapper;
    
    public List<WeChatMessageHistory> getUnsendMessageList() {
    	WeChatMessageHistoryExample exam_wmh = new WeChatMessageHistoryExample();
    	exam_wmh.createCriteria().andSendFlagEqualTo(Boolean.FALSE);
    	List<WeChatMessageHistory> ret = weChatMessageHistoryMapper
    			.selectOnePageByExample(exam_wmh, 0, WechatKeyConstants.MSG_SEND_LIMIT, "create_time asc");
    	if (!Utils.listNotNull(ret)) ret = new ArrayList<WeChatMessageHistory>();
    	return ret;
    }

	@Transactional
    public void updateMessage(WeChatMessageHistory msg) {
    	weChatMessageHistoryMapper.updateByPrimaryKeySelective(msg);
    }
	
	@Transactional
	public void createPartner(PromotionPartnerInfo partner) 
			throws BusinessException, Exception {
		PromotionPartnerInfoExample exam_pp = new PromotionPartnerInfoExample();
		exam_pp.createCriteria().andSceneStrEqualTo(partner.getSceneStr());
		if (promotionPartnerInfoMapper.countByExample(exam_pp) == 0) {
			partner.setPartnerId(UUID.randomUUID().toString());
			promotionPartnerInfoMapper.insertSelective(partner);
		}
	}
	
	public Integer checkQrSceneStr(PromotionPartnerInfo partner) {
		PromotionPartnerInfoExample exam_pp = new PromotionPartnerInfoExample();
		exam_pp.createCriteria().andSceneStrEqualTo(partner.getSceneStr());
		return promotionPartnerInfoMapper.countByExample(exam_pp);
	}
	
	@Transactional
	public void postUser(WeChatUserBaseInfo userBaseInfo) 
			throws BusinessException, Exception {
		WeChatUserInfo user = convert2UserDto(userBaseInfo);
		WeChatUserInfoExample example = new WeChatUserInfoExample();
		example.createCriteria().andOpenIdEqualTo(user.getOpenId());
		if (wechatUserInfoMapper.countByExample(example) > 0) {
			wechatUserInfoMapper.updateByPrimaryKeySelective(user);
		} else {
			wechatUserInfoMapper.insertSelective(user);
		}
	}
	
	public WeChatUserBaseInfo getOneUser(String openid) {
		return convert2UserBto(wechatUserInfoMapper.selectByPrimaryKey(openid));
	}
	
	private WeChatUserBaseInfo convert2UserBto(WeChatUserInfo base) {
		WeChatUserBaseInfo ret = new WeChatUserBaseInfo();
		if (base != null) {
			ret.setCity(base.getCity());
			ret.setCountry(base.getCountry());
			ret.setGroupid(base.getGroupId());
			ret.setHeadimgurl(base.getHeadImgurl());
			ret.setLanguage(base.getLanguage());
//			ret.setNickname(EmojiUtil.replaceContent(base.getNickName()));
			ret.setNickname(base.getNickName());
			ret.setOpenid(base.getOpenId());
			ret.setProvince(base.getProvince());
			ret.setQr_scene(base.getQrScene());
			ret.setQr_scene_str(base.getQrSceneStr());
			ret.setRemark(base.getRemark());
			ret.setSex(base.getSex());
			ret.setSubscribe(base.getSubscribe());
			ret.setSubscribe_scene(base.getSubscribeScene());
			ret.setSubscribe_time(base.getSubscribeTime());
			ret.setUnionid(base.getUnionId());
		}
		return ret;
	}
	
	private WeChatUserInfo convert2UserDto(WeChatUserBaseInfo base) {
		WeChatUserInfo ret = new WeChatUserInfo();
		ret.setCity(base.getCity());
		ret.setCountry(base.getCountry());
		ret.setGroupId(base.getGroupid());
		ret.setHeadImgurl(base.getHeadimgurl());
		ret.setLanguage(base.getLanguage());
		ret.setNickName(base.getNickname());
		ret.setOpenId(base.getOpenid());
		ret.setProvince(base.getProvince());
		ret.setQrScene(base.getQr_scene());
		ret.setQrSceneStr(base.getQr_scene_str());
		ret.setRemark(base.getRemark());
		ret.setSex(base.getSex());
		ret.setSubscribe(base.getSubscribe());
		ret.setSubscribeScene(base.getSubscribe_scene());
		ret.setSubscribeTime(base.getSubscribe_time());
		ret.setUnionId(base.getUnionid());
		return ret;
	}
	
}
