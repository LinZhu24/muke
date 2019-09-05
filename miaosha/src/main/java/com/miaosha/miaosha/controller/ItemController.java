package com.miaosha.miaosha.controller;

import com.miaosha.miaosha.controller.vo.ItemVO;
import com.miaosha.miaosha.error.BusinessErrorEnum;
import com.miaosha.miaosha.error.BusinessException;
import com.miaosha.miaosha.service.ItemService;
import com.miaosha.miaosha.service.model.ItemModel;
import com.miaosha.miaosha.util.response.ResponseEntity;
import com.xiaoleilu.hutool.util.ObjectUtil;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.swing.text.DateFormatter;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: LinXueRui
 * @Date: 2019/8/15 21:50
 * @Desc:
 */
@Controller
@RequestMapping("/item")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class ItemController extends BaseController {

    @Autowired
    ItemService itemService;

    /**
     * 创建商品
     * @param title
     * @param description
     * @param price
     * @param stock
     * @param imgUrl
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "/createItem",method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public ResponseEntity createItem(String title, String description, BigDecimal price,
                                     Integer stock, String imgUrl) throws BusinessException {
        //填充model
        ItemModel itemModel = new ItemModel();
        itemModel.setTitle(title);
        itemModel.setDescription(description);
        itemModel.setPrice(price);
        itemModel.setStock(stock);
        itemModel.setImgUrl(imgUrl);
        //向数据库中添加商品Item记录，同时返回商品领域模型的Model
        ItemModel modelReturn = itemService.createItem(itemModel);
        ItemVO itemVO = convertItemVOFromItemModel(modelReturn);
        return ResponseEntity.create(itemVO);
    }

    /**
     * 获取商品的详情
     * @param id
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "/getItemDetail",method = {RequestMethod.GET})
    @ResponseBody
    public ResponseEntity getItemDetail(Integer id) throws BusinessException {
        if (ObjectUtil.isNull(id)){
            throw new BusinessException(BusinessErrorEnum.PARAMETER_VALIDATION_ERROR,"商品主键为空");
        }
        ItemModel itemModel = itemService.getItemById(id);
        ItemVO itemVO = convertItemVOFromItemModel(itemModel);
        return ResponseEntity.create(itemVO);
    }

    /**
     * 获取商品的列表
     * @return
     */
    @RequestMapping(value = "/getItemList",method = {RequestMethod.GET})
    @ResponseBody
    public ResponseEntity getItemDetail() {
        List<ItemModel> modelList = itemService.listItem();
        List<ItemVO> itemVOList = modelList.stream().map(this::convertItemVOFromItemModel).collect(Collectors.toList());
        return ResponseEntity.create(itemVOList);
    }

    /**
     * ItemModel ----> ItemVO
     * @param model
     * @return
     */
    private ItemVO convertItemVOFromItemModel(ItemModel model){
        if (model == null){
            return null;
        }
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(model,itemVO);
        if (model.getPromoModel() != null) {
            //表明有即将进行或者正在进行的秒杀活动
            itemVO.setPromoId(model.getPromoModel().getId());
            itemVO.setPromoPrice(model.getPromoModel().getPromoItemPrice());
            itemVO.setStartDate(model.getPromoModel().getStartDate().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
            itemVO.setPromoStatus(model.getPromoModel().getStatus());
        }
        return itemVO;
    }
}
