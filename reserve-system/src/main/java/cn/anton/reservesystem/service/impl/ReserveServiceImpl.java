package cn.anton.reservesystem.service.impl;

import cn.anton.commonpackage.common.utils.PageUtils;
import cn.anton.commonpackage.common.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.anton.reservesystem.dao.ReserveDao;
import cn.anton.reservesystem.entity.ReserveEntity;
import cn.anton.reservesystem.service.ReserveService;


@Service("reserveService")
public class ReserveServiceImpl extends ServiceImpl<ReserveDao, ReserveEntity> implements ReserveService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ReserveEntity> page = this.page(
                new Query<ReserveEntity>().getPage(params),
                new QueryWrapper<ReserveEntity>()
        );

        return new PageUtils(page);
    }

}