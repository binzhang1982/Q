package com.cn.zbin.wechat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.zbin.wechat.bto.WeChatUserBaseInfo;
import com.cn.zbin.wechat.dto.WeChatUserInfo;
import com.cn.zbin.wechat.dto.WeChatUserInfoExample;
import com.cn.zbin.wechat.mapper.WeChatUserInfoMapper;
import com.github.binarywang.java.emoji.EmojiConverter;

@Service
public class WechatUserService {
	@Autowired
	private WeChatUserInfoMapper wechatUserInfoMapper;
//	private EmojiConverter emojiConverter = EmojiConverter.getInstance();
	
	@Transactional
	public void postUser(WeChatUserBaseInfo userBaseInfo) {
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
//		ret.setNickName(emojiConverter.toHtml(base.getNickname()));
//		try {
//			ret.setNickName(EmojiUtil.emojiConvert(base.getNickname()));
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
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
