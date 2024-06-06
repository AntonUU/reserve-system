package cn.anton.reservesystem.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.anton.commonpackage.common.utils.PageUtils;
import cn.anton.commonpackage.common.utils.Query;

import cn.anton.reservesystem.dao.VisitDao;
import cn.anton.reservesystem.entity.VisitEntity;
import cn.anton.reservesystem.service.VisitService;


@Service("visitService")
public class VisitServiceImpl extends ServiceImpl<VisitDao, VisitEntity> implements VisitService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<VisitEntity> page = this.page(
                new Query<VisitEntity>().getPage(params),
                new QueryWrapper<VisitEntity>()
        );

        return new PageUtils(page);
    }

}