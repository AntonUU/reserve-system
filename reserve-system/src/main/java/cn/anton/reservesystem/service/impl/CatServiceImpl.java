package cn.anton.reservesystem.service.impl;

import cn.anton.commonpackage.common.utils.PageUtils;
import cn.anton.commonpackage.common.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.anton.reservesystem.dao.CatDao;
import cn.anton.reservesystem.entity.CatEntity;
import cn.anton.reservesystem.service.CatService;


@Service("catService")
public class CatServiceImpl extends ServiceImpl<CatDao, CatEntity> implements CatService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CatEntity> page = this.page(
                new Query<CatEntity>().getPage(params),
                new QueryWrapper<CatEntity>()
        );

        return new PageUtils(page);
    }

}