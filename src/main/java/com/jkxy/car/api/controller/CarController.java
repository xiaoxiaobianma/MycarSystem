package com.jkxy.car.api.controller;


import com.jkxy.car.api.pojo.Car;
import com.jkxy.car.api.pojo.CarRequest;
import com.jkxy.car.api.service.CarService;
import com.jkxy.car.api.utils.JSONResult;
import com.jkxy.car.api.utils.StringUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("car")
public class CarController {
    @Autowired
    private CarService carService;

    /**
     * 查询所有
     *
     * @return
     */
    @GetMapping("findAll")
    public JSONResult findAll() {
        List<Car> cars = carService.findAll();
        return JSONResult.ok(cars);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @GetMapping("findById/{id}")
    public JSONResult findById(@PathVariable int id) {
        Car car = carService.findById(id);
        return JSONResult.ok(car);
    }

    /**
     * 通过车名查询（支持模糊查询）
     *
     * @param carName
     * @return
     */
    @GetMapping("findByCarName/{carName}")
    public JSONResult findByCarName(@PathVariable String carName) {
        //查询全部数据
        List<Car> cars = carService.findByCarName(carName);
        return JSONResult.ok(cars);
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @GetMapping("deleteById/{id}")
    public JSONResult deleteById(@PathVariable int id) {
        carService.deleteById(id);
        return JSONResult.ok();
    }

    /**
     * 通过id更新全部信息
     *
     * @return
     */
    @PostMapping("updateById")
    public JSONResult updateById(Car car) {
        carService.updateById(car);
        return JSONResult.ok();
    }

    /**
     * 通过id增加
     * 1.通过使用@RequestBody 实现Json格式的数据插入
     *
     * @param car
     * @return
     */
    @PostMapping("insertCar")
    public JSONResult insertCar(@RequestBody Car car) {
        carService.insertCar(car);
        return JSONResult.ok();
    }

    /**
     * 2.使用“表单样式”进行数据插入提交
     */
//    @PostMapping("insertCar")
//    public JSONResult insertCar(Car car) {
//        carService.insertCar(car);
//        return JSONResult.ok();
//    }
    @PostMapping("findAllByKeyWord")
    public JSONResult findAllByKeyWord(@RequestBody CarRequest car) {
        if (StringUtil.isblank(String.valueOf(car.getStartPage())) || StringUtil.isblank(String.valueOf(car.getPageSize()))) {
            return JSONResult.errorMsg("缺失必要参数");
        }
        if (car.getCarName() == null) {
            car.setCarName("");
        }
        if (car.getCarSeries() == null) {
            car.setCarSeries("");
        }
        long row = carService.countAllByKeyWord(car);
        HashMap<Object, Object> map = new HashMap<>();
        map.put("total", row);
        List<Car> list = new ArrayList<>();
        if (row > 0) {
            list = carService.findAllByKeyWord(car);
        }
        map.put("list", list);
        return JSONResult.ok(map);

    }
}
