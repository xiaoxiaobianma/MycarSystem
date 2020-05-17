package com.jkxy.car.api.dao;

import com.jkxy.car.api.pojo.Car;
import com.jkxy.car.api.pojo.CarRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface CarDao {
    @Select("select * from carMessage")
    List<Car> findAll();

    @Select("select * from carMessage where id = #{id}")
    Car findById(int id);

    //@Select("select * from carMessage where carName = #{carName}")
    @Select("select * from carMessage where carName like concat('%',#{carName},'%') ")
    List<Car> findByCarName(String carName);
//    @Select("select * from carMessage where carName like '%${value}%' limit #{pageNum},#{limit}")
//    List<Car> findByCarName(@Param("carName") String carName,@Param("pageNum") Integer pageNum,@Param("limit")Integer limit);

    @Delete("delete from carMessage where id = #{id}")
    void deleteById(int id);

    @Update("update carMessage set carName=#{carName},carType=#{carType},price=#{price},carSeries=#{carSeries} where id = #{id}")
    void updateById(Car car);

    @Insert("insert into carMessage (carName,carType,price,carSeries) value (#{carName},#{carType},#{price},#{carSeries})")
    void insertCar(Car car);

    @Select(
            "select * from (SELECT * FROM carMessage "+
                    "WHERE 1=1 "+
                    "AND carSeries like concat('%',#{carSeries},'%') "+
                    "AND carName like concat('%',#{carName},'%') "+
                    " order by id)a"+
                    " limit #{startPage},#{pageSize} "
    )
    List<Car> findAllByKeyWord(CarRequest request);

    @Select(
            "SELECT count(*) FROM carMessage "+
                    "WHERE 1=1 "+
                    "AND carSeries like concat('%',#{carSeries},'%') "+
                    "AND carName like concat('%',#{carName},'%') "
    )
    long countAllByKeyWord(CarRequest request);

}
