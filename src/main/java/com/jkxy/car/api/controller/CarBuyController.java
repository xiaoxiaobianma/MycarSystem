package com.jkxy.car.api.controller;

import com.jkxy.car.api.pojo.CarRecord;
import com.jkxy.car.api.service.CarRecordService;
import com.jkxy.car.api.utils.JSONResult;
import com.jkxy.car.api.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("buyCar")
public class CarBuyController {
    @Autowired
    private CarRecordService carService;


    @PostMapping("buyCarById")
    public JSONResult buyCarById(@RequestBody CarRecord car) {
        if(StringUtil.isblank(String.valueOf(car.getId()))||StringUtil.isblank(car.getUserName())
                ||StringUtil.isblank(String.valueOf(car.getAmount()))){
            return JSONResult.errorMsg("缺失必要参数");
        }
        if(car.getAmount()<=0){
            return JSONResult.errorMsg("购买数量不能为负值！");
        }
        CarRecord carInfo = carService.findById(car.getId());
        if(carInfo!=null){
            int count = carInfo.getStockAccount()-car.getAmount();
            if(count<0){
                return JSONResult.errorMsg("库存数量不足:当前库存为:"+carInfo.getStockAccount());
            }else{
                car.setStockAccount(count);
                boolean b = carService.updateById(car);
                if(b){
                    carService.insertBuyRecord(car);
                }else{
                    return JSONResult.errorMsg("购车失败!");
                }
            }
        }else{
            return JSONResult.errorMsg("该车辆不存在！");
        }
        return JSONResult.ok();
    }



}
