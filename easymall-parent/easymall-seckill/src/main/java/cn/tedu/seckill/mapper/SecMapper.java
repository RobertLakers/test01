package cn.tedu.seckill.mapper;

import com.jt.common.pojo.Seckill;
import com.jt.common.pojo.Success;

import java.util.List;

public interface SecMapper {
    List<Seckill> selectAll();

    Seckill selectOne(String seckillId);

    int decrSeckillNum(Long seckillId);

    void insertSuccess(Success suc);

    List<Success> selectSuccess(Long seckillId);
}
